/*
 * Jalview - A Sequence Alignment Editor and Viewer (Version 2.8)
 * Copyright (C) 2012 J Procter, AM Waterhouse, LM Lui, J Engelhardt, G Barton, M Clamp, S Searle
 * 
 * This file is part of Jalview.
 * 
 * Jalview is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * Jalview is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Jalview.  If not, see <http://www.gnu.org/licenses/>.
 */
package jalview.commands;

import java.util.*;

import jalview.datamodel.*;

/**
 * 
 * <p>
 * Title: EditCommmand
 * </p>
 * 
 * <p>
 * Description: Essential information for performing undo and redo for cut/paste
 * insert/delete gap which can be stored in the HistoryList
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: Dundee University
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class EditCommand implements CommandI
{
  public static final int INSERT_GAP = 0;

  public static final int DELETE_GAP = 1;

  public static final int CUT = 2;

  public static final int PASTE = 3;

  public static final int REPLACE = 4;

  public static final int INSERT_NUC = 5;

  Edit[] edits;

  String description;

  public EditCommand()
  {
  }

  public EditCommand(String description)
  {
    this.description = description;
  }

  public EditCommand(String description, int command, SequenceI[] seqs,
          int position, int number, AlignmentI al)
  {
    this.description = description;
    if (command == CUT || command == PASTE)
    {
      edits = new Edit[]
      { new Edit(command, seqs, position, number, al) };
    }

    performEdit(0, null);
  }

  public EditCommand(String description, int command, String replace,
          SequenceI[] seqs, int position, int number, AlignmentI al)
  {
    this.description = description;
    if (command == REPLACE)
    {
      edits = new Edit[]
      { new Edit(command, seqs, position, number, al, replace) };
    }

    performEdit(0, null);
  }

  @Override
  final public String getDescription()
  {
    return description;
  }

  @Override
  public int getSize()
  {
    return edits == null ? 0 : edits.length;
  }

  final public AlignmentI getAlignment()
  {
    return edits[0].al;
  }

  /**
   * append a new editCommand Note. this shouldn't be called if the edit is an
   * operation affects more alignment objects than the one referenced in al (for
   * example, cut or pasting whole sequences). Use the form with an additional
   * AlignmentI[] views parameter.
   * 
   * @param command
   * @param seqs
   * @param position
   * @param number
   * @param al
   * @param performEdit
   */
  final public void appendEdit(int command, SequenceI[] seqs, int position,
          int number, AlignmentI al, boolean performEdit)
  {
    appendEdit(command, seqs, position, number, al, performEdit, null);
  }

  /**
   * append a new edit command with a set of alignment views that may be
   * operated on
   * 
   * @param command
   * @param seqs
   * @param position
   * @param number
   * @param al
   * @param performEdit
   * @param views
   */
  final public void appendEdit(int command, SequenceI[] seqs, int position,
          int number, AlignmentI al, boolean performEdit, AlignmentI[] views)
  {
    Edit edit = new Edit(command, seqs, position, number,
            al.getGapCharacter());
    if (al.getHeight() == seqs.length)
    {
      edit.al = al;
      edit.fullAlignmentHeight = true;
    }

    if (edits != null)
    {
      Edit[] temp = new Edit[edits.length + 1];
      System.arraycopy(edits, 0, temp, 0, edits.length);
      edits = temp;
      edits[edits.length - 1] = edit;
    }
    else
    {
      edits = new Edit[]
      { edit };
    }

    if (performEdit)
    {
      performEdit(edits.length - 1, views);
    }
  }

  final void performEdit(int commandIndex, AlignmentI[] views)
  {
    int eSize = edits.length;
    for (int e = commandIndex; e < eSize; e++)
    {
      switch (edits[e].command)
      {
      case INSERT_GAP:
        insertGap(edits[e]);
        break;
      case DELETE_GAP:
        deleteGap(edits[e]);
        break;
      case CUT:
        cut(edits[e], views);
        break;
      case PASTE:
        paste(edits[e], views);
        break;
      case REPLACE:
        replace(edits[e]);
        break;
      // TODO:add deleteNuc for UNDO
      // case INSERT_NUC:
      // insertNuc(edits[e]);
      // break;
      }
    }
  }

  @Override
  final public void doCommand(AlignmentI[] views)
  {
    performEdit(0, views);
  }

  @Override
  final public void undoCommand(AlignmentI[] views)
  {
    int e = 0, eSize = edits.length;
    for (e = eSize - 1; e > -1; e--)
    {
      switch (edits[e].command)
      {
      case INSERT_GAP:
        deleteGap(edits[e]);
        break;
      case DELETE_GAP:
        insertGap(edits[e]);
        break;
      case CUT:
        paste(edits[e], views);
        break;
      case PASTE:
        cut(edits[e], views);
        break;
      case REPLACE:
        replace(edits[e]);
        break;
      }
    }
  }

  final void insertGap(Edit command)
  {

    for (int s = 0; s < command.seqs.length; s++)
    {
      command.seqs[s].insertCharAt(command.position, command.number,
              command.gapChar);
      // System.out.println("pos: "+command.position+" number: "+command.number);
    }

    adjustAnnotations(command, true, false, null);
  }

  //
  // final void insertNuc(Edit command)
  // {
  //
  // for (int s = 0; s < command.seqs.length; s++)
  // {
  // System.out.println("pos: "+command.position+" number: "+command.number);
  // command.seqs[s].insertCharAt(command.position, command.number,'A');
  // }
  //
  // adjustAnnotations(command, true, false, null);
  // }

  final void deleteGap(Edit command)
  {
    for (int s = 0; s < command.seqs.length; s++)
    {
      command.seqs[s].deleteChars(command.position, command.position
              + command.number);
    }

    adjustAnnotations(command, false, false, null);
  }

  void cut(Edit command, AlignmentI[] views)
  {
    boolean seqDeleted = false;
    command.string = new char[command.seqs.length][];

    for (int i = 0; i < command.seqs.length; i++)
    {
      if (command.seqs[i].getLength() > command.position)
      {
        command.string[i] = command.seqs[i].getSequence(command.position,
                command.position + command.number);
        SequenceI oldds = command.seqs[i].getDatasetSequence();
        if (command.oldds != null && command.oldds[i] != null)
        {
          // we are redoing an undone cut.
          command.seqs[i].setDatasetSequence(null);
        }
        command.seqs[i].deleteChars(command.position, command.position
                + command.number);
        if (command.oldds != null && command.oldds[i] != null)
        {
          // oldds entry contains the cut dataset sequence.
          command.seqs[i].setDatasetSequence(command.oldds[i]);
          command.oldds[i] = oldds;
        }
        else
        {
          // modify the oldds if necessary
          if (oldds != command.seqs[i].getDatasetSequence()
                  || command.seqs[i].getSequenceFeatures() != null)
          {
            if (command.oldds == null)
            {
              command.oldds = new SequenceI[command.seqs.length];
            }
            command.oldds[i] = oldds;
            adjustFeatures(
                    command,
                    i,
                    command.seqs[i].findPosition(command.position),
                    command.seqs[i].findPosition(command.position
                            + command.number), false);
          }
        }
      }

      if (command.seqs[i].getLength() < 1)
      {
        command.al.deleteSequence(command.seqs[i]);
        seqDeleted = true;
      }
    }

    adjustAnnotations(command, false, seqDeleted, views);
  }

  void paste(Edit command, AlignmentI[] views)
  {
    StringBuffer tmp;
    boolean newDSNeeded;
    boolean newDSWasNeeded;
    int newstart, newend;
    boolean seqWasDeleted = false;
    int start = 0, end = 0;

    for (int i = 0; i < command.seqs.length; i++)
    {
      newDSNeeded = false;
      newDSWasNeeded = command.oldds != null && command.oldds[i] != null;
      if (command.seqs[i].getLength() < 1)
      {
        // ie this sequence was deleted, we need to
        // read it to the alignment
        if (command.alIndex[i] < command.al.getHeight())
        {
          List<SequenceI> sequences;
          synchronized (sequences = command.al.getSequences())
          {
            sequences.add(command.alIndex[i], command.seqs[i]);
          }
        }
        else
        {
          command.al.addSequence(command.seqs[i]);
        }
        seqWasDeleted = true;
      }
      newstart = command.seqs[i].getStart();
      newend = command.seqs[i].getEnd();

      tmp = new StringBuffer();
      tmp.append(command.seqs[i].getSequence());
      // Undo of a delete does not replace original dataset sequence on to
      // alignment sequence.

      if (command.string != null && command.string[i] != null)
      {
        if (command.position >= tmp.length())
        {
          // This occurs if padding is on, and residues
          // are removed from end of alignment
          int length = command.position - tmp.length();
          while (length > 0)
          {
            tmp.append(command.gapChar);
            length--;
          }
        }
        tmp.insert(command.position, command.string[i]);
        for (int s = 0; s < command.string[i].length; s++)
        {
          if (jalview.schemes.ResidueProperties.aaIndex[command.string[i][s]] != 23)
          {
            if (!newDSNeeded)
            {
              newDSNeeded = true;
              start = command.seqs[i].findPosition(command.position);
              end = command.seqs[i].findPosition(command.position
                      + command.number);
            }
            if (command.seqs[i].getStart() == start)
              newstart--;
            else
              newend++;
          }
        }
        command.string[i] = null;
      }

      command.seqs[i].setSequence(tmp.toString());
      command.seqs[i].setStart(newstart);
      command.seqs[i].setEnd(newend);
      if (newDSNeeded)
      {
        if (command.seqs[i].getDatasetSequence() != null)
        {
          SequenceI ds;
          if (newDSWasNeeded)
          {
            ds = command.oldds[i];
          }
          else
          {
            // make a new DS sequence
            // use new ds mechanism here
            ds = new Sequence(command.seqs[i].getName(),
                    jalview.analysis.AlignSeq.extractGaps(
                            jalview.util.Comparison.GapChars,
                            command.seqs[i].getSequenceAsString()),
                    command.seqs[i].getStart(), command.seqs[i].getEnd());
            ds.setDescription(command.seqs[i].getDescription());
          }
          if (command.oldds == null)
          {
            command.oldds = new SequenceI[command.seqs.length];
          }
          command.oldds[i] = command.seqs[i].getDatasetSequence();
          command.seqs[i].setDatasetSequence(ds);
        }
        adjustFeatures(command, i, start, end, true);
      }
    }
    adjustAnnotations(command, true, seqWasDeleted, views);

    command.string = null;
  }

  void replace(Edit command)
  {
    StringBuffer tmp;
    String oldstring;
    int start = command.position;
    int end = command.number;
    // TODO TUTORIAL - Fix for replacement with different length of sequence (or
    // whole sequence)
    // TODO Jalview 2.4 bugfix change to an aggregate command - original
    // sequence string is cut, new string is pasted in.
    command.number = start + command.string[0].length;
    for (int i = 0; i < command.seqs.length; i++)
    {
      boolean newDSWasNeeded = command.oldds != null
              && command.oldds[i] != null;

      /**
       * cut addHistoryItem(new EditCommand("Cut Sequences", EditCommand.CUT,
       * cut, sg.getStartRes(), sg.getEndRes()-sg.getStartRes()+1,
       * viewport.alignment));
       * 
       */
      /**
       * then addHistoryItem(new EditCommand( "Add sequences",
       * EditCommand.PASTE, sequences, 0, alignment.getWidth(), alignment) );
       * 
       */
      oldstring = command.seqs[i].getSequenceAsString();
      tmp = new StringBuffer(oldstring.substring(0, start));
      tmp.append(command.string[i]);
      String nogaprep = jalview.analysis.AlignSeq.extractGaps(
              jalview.util.Comparison.GapChars, new String(
                      command.string[i]));
      int ipos = command.seqs[i].findPosition(start)
              - command.seqs[i].getStart();
      tmp.append(oldstring.substring(end));
      command.seqs[i].setSequence(tmp.toString());
      command.string[i] = oldstring.substring(start, end).toCharArray();
      String nogapold = jalview.analysis.AlignSeq.extractGaps(
              jalview.util.Comparison.GapChars, new String(
                      command.string[i]));
      if (!nogaprep.toLowerCase().equals(nogapold.toLowerCase()))
      {
        if (newDSWasNeeded)
        {
          SequenceI oldds = command.seqs[i].getDatasetSequence();
          command.seqs[i].setDatasetSequence(command.oldds[i]);
          command.oldds[i] = oldds;
        }
        else
        {
          if (command.oldds == null)
          {
            command.oldds = new SequenceI[command.seqs.length];
          }
          command.oldds[i] = command.seqs[i].getDatasetSequence();
          SequenceI newds = new Sequence(
                  command.seqs[i].getDatasetSequence());
          String fullseq, osp = newds.getSequenceAsString();
          fullseq = osp.substring(0, ipos) + nogaprep
                  + osp.substring(ipos + nogaprep.length());
          newds.setSequence(fullseq.toUpperCase());
          // TODO: JAL-1131 ensure newly created dataset sequence is added to
          // the set of
          // dataset sequences associated with the alignment.
          // TODO: JAL-1131 fix up any annotation associated with new dataset
          // sequence to ensure that original sequence/annotation relationships
          // are preserved.
          command.seqs[i].setDatasetSequence(newds);

        }
      }
      tmp = null;
      oldstring = null;
    }
  }

  final void adjustAnnotations(Edit command, boolean insert,
          boolean modifyVisibility, AlignmentI[] views)
  {
    AlignmentAnnotation[] annotations = null;

    if (modifyVisibility && !insert)
    {
      // only occurs if a sequence was added or deleted.
      command.deletedAnnotationRows = new Hashtable();
    }
    if (command.fullAlignmentHeight)
    {
      annotations = command.al.getAlignmentAnnotation();
    }
    else
    {
      int aSize = 0;
      AlignmentAnnotation[] tmp;
      for (int s = 0; s < command.seqs.length; s++)
      {
        if (modifyVisibility)
        {
          // Rows are only removed or added to sequence object.
          if (!insert)
          {
            // remove rows
            tmp = command.seqs[s].getAnnotation();
            if (tmp != null)
            {
              int alen = tmp.length;
              for (int aa = 0; aa < tmp.length; aa++)
              {
                if (!command.al.deleteAnnotation(tmp[aa]))
                {
                  // strip out annotation not in the current al (will be put
                  // back on insert in all views)
                  tmp[aa] = null;
                  alen--;
                }
              }
              command.seqs[s].setAlignmentAnnotation(null);
              if (alen != tmp.length)
              {
                // save the non-null annotation references only
                AlignmentAnnotation[] saved = new AlignmentAnnotation[alen];
                for (int aa = 0, aapos = 0; aa < tmp.length; aa++)
                {
                  if (tmp[aa] != null)
                  {
                    saved[aapos++] = tmp[aa];
                    tmp[aa] = null;
                  }
                }
                tmp = saved;
                command.deletedAnnotationRows.put(command.seqs[s], saved);
                // and then remove any annotation in the other views
                for (int alview = 0; views != null && alview < views.length; alview++)
                {
                  if (views[alview] != command.al)
                  {
                    AlignmentAnnotation[] toremove = views[alview]
                            .getAlignmentAnnotation();
                    if (toremove == null || toremove.length == 0)
                    {
                      continue;
                    }
                    // remove any alignment annotation on this sequence that's
                    // on that alignment view.
                    for (int aa = 0; aa < toremove.length; aa++)
                    {
                      if (toremove[aa].sequenceRef == command.seqs[s])
                      {
                        views[alview].deleteAnnotation(toremove[aa]);
                      }
                    }
                  }
                }
              }
              else
              {
                // save all the annotation
                command.deletedAnnotationRows.put(command.seqs[s], tmp);
              }
            }
          }
          else
          {
            // recover rows
            if (command.deletedAnnotationRows != null
                    && command.deletedAnnotationRows
                            .containsKey(command.seqs[s]))
            {
              AlignmentAnnotation[] revealed = (AlignmentAnnotation[]) command.deletedAnnotationRows
                      .get(command.seqs[s]);
              command.seqs[s].setAlignmentAnnotation(revealed);
              if (revealed != null)
              {
                for (int aa = 0; aa < revealed.length; aa++)
                {
                  // iterate through al adding original annotation
                  command.al.addAnnotation(revealed[aa]);
                }
                for (int aa = 0; aa < revealed.length; aa++)
                {
                  command.al.setAnnotationIndex(revealed[aa], aa);
                }
                // and then duplicate added annotation on every other alignment
                // view
                for (int vnum = 0; views != null && vnum < views.length; vnum++)
                {
                  if (views[vnum] != command.al)
                  {
                    int avwidth = views[vnum].getWidth() + 1;
                    // duplicate in this view
                    for (int a = 0; a < revealed.length; a++)
                    {
                      AlignmentAnnotation newann = new AlignmentAnnotation(
                              revealed[a]);
                      command.seqs[s].addAlignmentAnnotation(newann);
                      newann.padAnnotation(avwidth);
                      views[vnum].addAnnotation(newann);
                      views[vnum].setAnnotationIndex(newann, a);
                    }
                  }
                }
              }
            }
          }
          continue;
        }

        if (command.seqs[s].getAnnotation() == null)
        {
          continue;
        }

        if (aSize == 0)
        {
          annotations = command.seqs[s].getAnnotation();
        }
        else
        {
          tmp = new AlignmentAnnotation[aSize
                  + command.seqs[s].getAnnotation().length];

          System.arraycopy(annotations, 0, tmp, 0, aSize);

          System.arraycopy(command.seqs[s].getAnnotation(), 0, tmp, aSize,
                  command.seqs[s].getAnnotation().length);

          annotations = tmp;
        }
        aSize = annotations.length;
      }
    }

    if (annotations == null)
    {
      return;
    }

    if (!insert)
    {
      command.deletedAnnotations = new Hashtable();
    }

    int aSize;
    Annotation[] temp;
    for (int a = 0; a < annotations.length; a++)
    {
      if (annotations[a].autoCalculated
              || annotations[a].annotations == null)
      {
        continue;
      }

      int tSize = 0;

      aSize = annotations[a].annotations.length;
      if (insert)
      {
        temp = new Annotation[aSize + command.number];
        if (annotations[a].padGaps)
          for (int aa = 0; aa < temp.length; aa++)
          {
            temp[aa] = new Annotation(command.gapChar + "", null, ' ', 0);
          }
      }
      else
      {
        if (command.position < aSize)
        {
          if (command.position + command.number >= aSize)
          {
            tSize = aSize;
          }
          else
          {
            tSize = aSize - command.number;
          }
        }
        else
        {
          tSize = aSize;
        }

        if (tSize < 0)
        {
          tSize = aSize;
        }
        temp = new Annotation[tSize];
      }

      if (insert)
      {
        if (command.position < annotations[a].annotations.length)
        {
          System.arraycopy(annotations[a].annotations, 0, temp, 0,
                  command.position);

          if (command.deletedAnnotations != null
                  && command.deletedAnnotations
                          .containsKey(annotations[a].annotationId))
          {
            Annotation[] restore = (Annotation[]) command.deletedAnnotations
                    .get(annotations[a].annotationId);

            System.arraycopy(restore, 0, temp, command.position,
                    command.number);

          }

          System.arraycopy(annotations[a].annotations, command.position,
                  temp, command.position + command.number, aSize
                          - command.position);
        }
        else
        {
          if (command.deletedAnnotations != null
                  && command.deletedAnnotations
                          .containsKey(annotations[a].annotationId))
          {
            Annotation[] restore = (Annotation[]) command.deletedAnnotations
                    .get(annotations[a].annotationId);

            temp = new Annotation[annotations[a].annotations.length
                    + restore.length];
            System.arraycopy(annotations[a].annotations, 0, temp, 0,
                    annotations[a].annotations.length);
            System.arraycopy(restore, 0, temp,
                    annotations[a].annotations.length, restore.length);
          }
          else
          {
            temp = annotations[a].annotations;
          }
        }
      }
      else
      {
        if (tSize != aSize || command.position < 2)
        {
          int copylen = Math.min(command.position,
                  annotations[a].annotations.length);
          if (copylen > 0)
            System.arraycopy(annotations[a].annotations, 0, temp, 0,
                    copylen); // command.position);

          Annotation[] deleted = new Annotation[command.number];
          if (copylen >= command.position)
          {
            copylen = Math.min(command.number,
                    annotations[a].annotations.length - command.position);
            if (copylen > 0)
            {
              System.arraycopy(annotations[a].annotations,
                      command.position, deleted, 0, copylen); // command.number);
            }
          }

          command.deletedAnnotations.put(annotations[a].annotationId,
                  deleted);
          if (annotations[a].annotations.length > command.position
                  + command.number)
          {
            System.arraycopy(annotations[a].annotations, command.position
                    + command.number, temp, command.position,
                    annotations[a].annotations.length - command.position
                            - command.number); // aSize
          }
        }
        else
        {
          int dSize = aSize - command.position;

          if (dSize > 0)
          {
            Annotation[] deleted = new Annotation[command.number];
            System.arraycopy(annotations[a].annotations, command.position,
                    deleted, 0, dSize);

            command.deletedAnnotations.put(annotations[a].annotationId,
                    deleted);

            tSize = Math.min(annotations[a].annotations.length,
                    command.position);
            temp = new Annotation[tSize];
            System.arraycopy(annotations[a].annotations, 0, temp, 0, tSize);
          }
          else
          {
            temp = annotations[a].annotations;
          }
        }
      }

      annotations[a].annotations = temp;
    }
  }

  final void adjustFeatures(Edit command, int index, int i, int j,
          boolean insert)
  {
    SequenceI seq = command.seqs[index];
    SequenceI sequence = seq.getDatasetSequence();
    if (sequence == null)
    {
      sequence = seq;
    }

    if (insert)
    {
      if (command.editedFeatures != null
              && command.editedFeatures.containsKey(seq))
      {
        sequence.setSequenceFeatures((SequenceFeature[]) command.editedFeatures
                .get(seq));
      }

      return;
    }

    SequenceFeature[] sf = sequence.getSequenceFeatures();

    if (sf == null)
    {
      return;
    }

    SequenceFeature[] oldsf = new SequenceFeature[sf.length];

    int cSize = j - i;

    for (int s = 0; s < sf.length; s++)
    {
      SequenceFeature copy = new SequenceFeature(sf[s]);

      oldsf[s] = copy;

      if (sf[s].getEnd() < i)
      {
        continue;
      }

      if (sf[s].getBegin() > j)
      {
        sf[s].setBegin(copy.getBegin() - cSize);
        sf[s].setEnd(copy.getEnd() - cSize);
        continue;
      }

      if (sf[s].getBegin() >= i)
      {
        sf[s].setBegin(i);
      }

      if (sf[s].getEnd() < j)
      {
        sf[s].setEnd(j - 1);
      }

      sf[s].setEnd(sf[s].getEnd() - (cSize));

      if (sf[s].getBegin() > sf[s].getEnd())
      {
        sequence.deleteFeature(sf[s]);
      }
    }

    if (command.editedFeatures == null)
    {
      command.editedFeatures = new Hashtable();
    }

    command.editedFeatures.put(seq, oldsf);

  }

  class Edit
  {
    public SequenceI[] oldds;

    boolean fullAlignmentHeight = false;

    Hashtable deletedAnnotationRows;

    Hashtable deletedAnnotations;

    Hashtable editedFeatures;

    AlignmentI al;

    int command;

    char[][] string;

    SequenceI[] seqs;

    int[] alIndex;

    int position, number;

    char gapChar;

    Edit(int command, SequenceI[] seqs, int position, int number,
            char gapChar)
    {
      this.command = command;
      this.seqs = seqs;
      this.position = position;
      this.number = number;
      this.gapChar = gapChar;
    }

    Edit(int command, SequenceI[] seqs, int position, int number,
            AlignmentI al)
    {
      this.gapChar = al.getGapCharacter();
      this.command = command;
      this.seqs = seqs;
      this.position = position;
      this.number = number;
      this.al = al;

      alIndex = new int[seqs.length];
      for (int i = 0; i < seqs.length; i++)
      {
        alIndex[i] = al.findIndex(seqs[i]);
      }

      fullAlignmentHeight = (al.getHeight() == seqs.length);
    }

    Edit(int command, SequenceI[] seqs, int position, int number,
            AlignmentI al, String replace)
    {
      this.command = command;
      this.seqs = seqs;
      this.position = position;
      this.number = number;
      this.al = al;
      this.gapChar = al.getGapCharacter();
      string = new char[seqs.length][];
      for (int i = 0; i < seqs.length; i++)
      {
        string[i] = replace.toCharArray();
      }

      fullAlignmentHeight = (al.getHeight() == seqs.length);
    }
  }
}
