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
package jalview.datamodel;

import java.util.Vector;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public interface SequenceI
{
  /**
   * Set the display name for the sequence
   * 
   * @param name
   */
  public void setName(String name);

  /**
   * Get the display name
   */
  public String getName();

  /**
   * Set start position of first non-gapped symbol in sequence
   * 
   * @param start
   *          new start position
   */
  public void setStart(int start);

  /**
   * get start position of first non-gapped residue in sequence
   * 
   * @return
   */
  public int getStart();

  /**
   * get the displayed id of the sequence
   * 
   * @return true means the id will be returned in the form
   *         DisplayName/Start-End
   */
  public String getDisplayId(boolean jvsuffix);

  /**
   * set end position for last residue in sequence
   * 
   * @param end
   */
  public void setEnd(int end);

  /**
   * get end position for last residue in sequence getEnd()>getStart() unless
   * sequence only consists of gap characters
   * 
   * @return
   */
  public int getEnd();

  /**
   * @return length of sequence including gaps
   * 
   */
  public int getLength();

  /**
   * Replace the sequence with the given string
   * 
   * @param sequence
   *          new sequence string
   */
  public void setSequence(String sequence);

  /**
   * @return sequence as string
   */
  public String getSequenceAsString();

  /**
   * get a range on the seuqence as a string
   * 
   * @param start
   *          DOCUMENT ME!
   * @param end
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getSequenceAsString(int start, int end);

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public char[] getSequence();

  /**
   * get stretch of sequence characters in an array
   * 
   * @param start
   *          absolute index into getSequence()
   * @param end
   *          exclusive index of last position in segment to be returned.
   * 
   * @return char[max(0,end-start)];
   */
  public char[] getSequence(int start, int end);

  /**
   * create a new sequence object from start to end of this sequence
   * 
   * @param start
   *          int
   * @param end
   *          int
   * @return SequenceI
   */
  public SequenceI getSubSequence(int start, int end);

  /**
   * DOCUMENT ME!
   * 
   * @param i
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public char getCharAt(int i);

  /**
   * DOCUMENT ME!
   * 
   * @param desc
   *          DOCUMENT ME!
   */
  public void setDescription(String desc);

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getDescription();

  /**
   * Return the alignment column for a sequence position * Return the alignment
   * position for a sequence position
   * 
   * @param pos
   *          lying from start to end
   * 
   * @return aligned column for residue (0 if residue is upstream from
   *         alignment, -1 if residue is downstream from alignment) note.
   *         Sequence object returns sequence.getEnd() for positions upstream
   *         currently. TODO: change sequence for
   *         assert(findIndex(seq.getEnd()+1)==-1) and fix incremental bugs
   * 
   */
  public int findIndex(int pos);

  /**
   * Returns the sequence position for an alignment position
   * 
   * @param i
   *          column index in alignment (from 1)
   * 
   * @return residue number for residue (left of and) nearest ith column
   */
  public int findPosition(int i);

  /**
   * Returns an int array where indices correspond to each residue in the
   * sequence and the element value gives its position in the alignment
   * 
   * @return int[SequenceI.getEnd()-SequenceI.getStart()+1] or null if no
   *         residues in SequenceI object
   */
  public int[] gapMap();

  /**
   * Returns an int array where indices correspond to each position in sequence
   * char array and the element value gives the result of findPosition for that
   * index in the sequence.
   * 
   * @return int[SequenceI.getLength()]
   */
  public int[] findPositionMap();

  /**
   * Delete a range of aligned sequence columns, creating a new dataset sequence
   * if necessary and adjusting start and end positions accordingly.
   * 
   * @param i
   *          first column in range to delete
   * @param j
   *          last column in range to delete
   */
  public void deleteChars(int i, int j);

  /**
   * DOCUMENT ME!
   * 
   * @param i
   *          DOCUMENT ME!
   * @param c
   *          DOCUMENT ME!
   */
  public void insertCharAt(int i, char c);

  /**
   * DOCUMENT ME!
   * 
   * @param i
   *          DOCUMENT ME!
   * @param c
   *          DOCUMENT ME!
   */
  public void insertCharAt(int i, int length, char c);

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public SequenceFeature[] getSequenceFeatures();

  /**
   * DOCUMENT ME!
   * 
   * @param v
   *          DOCUMENT ME!
   */
  public void setSequenceFeatures(SequenceFeature[] features);

  /**
   * DOCUMENT ME!
   * 
   * @param id
   *          DOCUMENT ME!
   */
  public void setPDBId(Vector ids);

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Vector getPDBId();

  /**
   * add entry to the vector of PDBIds, if it isn't in the list already
   * 
   * @param entry
   */
  public void addPDBId(PDBEntry entry);

  /**
   * update the list of PDBEntrys to include any DBRefEntrys citing structural
   * databases
   * 
   * @return true if PDBEntry list was modified
   */
  public boolean updatePDBIds();

  public String getVamsasId();

  public void setVamsasId(String id);

  public void setDBRef(DBRefEntry[] dbs);

  public DBRefEntry[] getDBRef();

  /**
   * add the given entry to the list of DBRefs for this sequence, or replace a
   * similar one if entry contains a map object and the existing one doesnt.
   * 
   * @param entry
   */
  public void addDBRef(DBRefEntry entry);

  public void addSequenceFeature(SequenceFeature sf);

  public void deleteFeature(SequenceFeature sf);

  public void setDatasetSequence(SequenceI seq);

  public SequenceI getDatasetSequence();

  public AlignmentAnnotation[] getAnnotation();

  public void addAlignmentAnnotation(AlignmentAnnotation annotation);

  public void removeAlignmentAnnotation(AlignmentAnnotation annotation);

  /**
   * Derive a sequence (using this one's dataset or as the dataset)
   * 
   * @return duplicate sequence with valid dataset sequence
   */
  public SequenceI deriveSequence();

  /**
   * set the array of associated AlignmentAnnotation for this sequenceI
   * 
   * @param revealed
   */
  public void setAlignmentAnnotation(AlignmentAnnotation[] annotation);

  /**
   * Get one or more alignment annotations with a particular label.
   * 
   * @param label
   *          string which each returned annotation must have as a label.
   * @return null or array of annotations.
   */
  public AlignmentAnnotation[] getAnnotation(String label);

  /**
   * create a new dataset sequence (if necessary) for this sequence and sets
   * this sequence to refer to it. This call will move any features or
   * references on the sequence onto the dataset.
   * 
   * @return dataset sequence for this sequence
   */
  public SequenceI createDatasetSequence();

  /**
   * Transfer any database references or annotation from entry under a sequence
   * mapping.
   * 
   * @param entry
   * @param mp
   *          null or mapping from entry's numbering to local start/end
   */
  public void transferAnnotation(SequenceI entry, Mapping mp);

  /**
   * @param index
   *          The sequence index in the MSA
   */
  public void setIndex(int index);

  /**
   * @return The index of the sequence in the alignment
   */
  public int getIndex();

}
