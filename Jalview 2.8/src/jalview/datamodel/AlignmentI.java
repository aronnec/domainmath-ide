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

import java.util.*;

/**
 * Data structure to hold and manipulate a multiple sequence alignment
 */
public interface AlignmentI extends AnnotatedCollectionI
{
  /**
   * Calculates the number of sequences in an alignment
   * 
   * @return Number of sequences in alignment
   */
  public int getHeight();

  /**
   * Calculates the maximum width of the alignment, including gaps.
   * 
   * @return Greatest sequence length within alignment.
   */
  @Override
  public int getWidth();

  /**
   * Calculates if this set of sequences (visible and invisible) are all the
   * same length
   * 
   * @return true if all sequences in alignment are the same length
   */
  public boolean isAligned();

  /**
   * Calculates if this set of sequences is all the same length
   * 
   * @param includeHidden
   *          optionally exclude hidden sequences from test
   * @return true if all (or just visible) sequences are the same length
   */
  public boolean isAligned(boolean includeHidden);

  /**
   * Gets sequences as a Synchronized collection
   * 
   * @return All sequences in alignment.
   */
  @Override
  public List<SequenceI> getSequences();

  /**
   * Gets sequences as a SequenceI[]
   * 
   * @return All sequences in alignment.
   */
  public SequenceI[] getSequencesArray();

  /**
   * Find a specific sequence in this alignment.
   * 
   * @param i
   *          Index of required sequence.
   * 
   * @return SequenceI at given index.
   */
  public SequenceI getSequenceAt(int i);

  /**
   * Add a new sequence to this alignment.
   * 
   * @param seq
   *          New sequence will be added at end of alignment.
   */
  public void addSequence(SequenceI seq);

  /**
   * Used to set a particular index of the alignment with the given sequence.
   * 
   * @param i
   *          Index of sequence to be updated.
   * @param seq
   *          New sequence to be inserted.
   */
  public void setSequenceAt(int i, SequenceI seq);

  /**
   * Deletes a sequence from the alignment
   * 
   * @param s
   *          Sequence to be deleted.
   */
  public void deleteSequence(SequenceI s);

  /**
   * Deletes a sequence from the alignment.
   * 
   * @param i
   *          Index of sequence to be deleted.
   */
  public void deleteSequence(int i);

  /**
   * Finds sequence in alignment using sequence name as query.
   * 
   * @param name
   *          Id of sequence to search for.
   * 
   * @return Sequence matching query, if found. If not found returns null.
   */
  public SequenceI findName(String name);

  public SequenceI[] findSequenceMatch(String name);

  /**
   * Finds index of a given sequence in the alignment.
   * 
   * @param s
   *          Sequence to look for.
   * 
   * @return Index of sequence within the alignment or -1 if not found
   */
  public int findIndex(SequenceI s);

  /**
   * Finds group that given sequence is part of.
   * 
   * @param s
   *          Sequence in alignment.
   * 
   * @return First group found for sequence. WARNING : Sequences may be members
   *         of several groups. This method is incomplete.
   */
  public SequenceGroup findGroup(SequenceI s);

  /**
   * Finds all groups that a given sequence is part of.
   * 
   * @param s
   *          Sequence in alignment.
   * 
   * @return All groups containing given sequence.
   */
  public SequenceGroup[] findAllGroups(SequenceI s);

  /**
   * Adds a new SequenceGroup to this alignment.
   * 
   * @param sg
   *          New group to be added.
   */
  public void addGroup(SequenceGroup sg);

  /**
   * Deletes a specific SequenceGroup
   * 
   * @param g
   *          Group will be deleted from alignment.
   */
  public void deleteGroup(SequenceGroup g);

  /**
   * Get all the groups associated with this alignment.
   * 
   * @return All groups as a list.
   */
  public List<SequenceGroup> getGroups();

  /**
   * Deletes all groups from this alignment.
   */
  public void deleteAllGroups();

  /**
   * Adds a new AlignmentAnnotation to this alignment
   * 
   * @note Care should be taken to ensure that annotation is at least as wide as
   *       the longest sequence in the alignment for rendering purposes.
   */
  public void addAnnotation(AlignmentAnnotation aa);

  /**
   * moves annotation to a specified index in alignment annotation display stack
   * 
   * @param aa
   *          the annotation object to be moved
   * @param index
   *          the destination position
   */
  public void setAnnotationIndex(AlignmentAnnotation aa, int index);

  /**
   * Deletes a specific AlignmentAnnotation from the alignment, and removes its
   * reference from any SequenceI or SequenceGroup object's annotation if and
   * only if aa is contained within the alignment's annotation vector.
   * Otherwise, it will do nothing.
   * 
   * @param aa
   *          the annotation to delete
   * @return true if annotation was deleted from this alignment.
   */
  public boolean deleteAnnotation(AlignmentAnnotation aa);

  /**
   * Deletes a specific AlignmentAnnotation from the alignment, and optionally
   * removes any reference from any SequenceI or SequenceGroup object's
   * annotation if and only if aa is contained within the alignment's annotation
   * vector. Otherwise, it will do nothing.
   * 
   * @param aa
   *          the annotation to delete
   * @param unhook
   *          flag indicating if any references should be removed from
   *          annotation - use this if you intend to add the annotation back
   *          into the alignment
   * @return true if annotation was deleted from this alignment.
   */
  public boolean deleteAnnotation(AlignmentAnnotation aa, boolean unhook);

  /**
   * Get the annotation associated with this alignment (this can be null if no
   * annotation has ever been created on the alignment)
   * 
   * @return array of AlignmentAnnotation objects
   */
  @Override
  public AlignmentAnnotation[] getAlignmentAnnotation();

  /**
   * Change the gap character used in this alignment to 'gc'
   * 
   * @param gc
   *          the new gap character.
   */
  public void setGapCharacter(char gc);

  /**
   * Get the gap character used in this alignment
   * 
   * @return gap character
   */
  public char getGapCharacter();

  /**
   * Test for all nucleotide alignment
   * 
   * @return true if alignment is nucleotide sequence
   */
  public boolean isNucleotide();

  /**
   * Test if alignment contains RNA structure
   * 
   * @return true if RNA structure AligmnentAnnotation was added to alignment
   */
  public boolean hasRNAStructure();

  /**
   * Set alignment to be a nucleotide sequence
   * 
   */
  public void setNucleotide(boolean b);

  /**
   * Get the associated dataset for the alignment.
   * 
   * @return Alignment containing dataset sequences or null of this is a
   *         dataset.
   */
  public Alignment getDataset();

  /**
   * Set the associated dataset for the alignment, or create one.
   * 
   * @param dataset
   *          The dataset alignment or null to construct one.
   */
  public void setDataset(Alignment dataset);

  /**
   * pads sequences with gaps (to ensure the set looks like an alignment)
   * 
   * @return boolean true if alignment was modified
   */
  public boolean padGaps();

  public HiddenSequences getHiddenSequences();

  /**
   * Compact representation of alignment
   * 
   * @return CigarArray
   */
  public CigarArray getCompactAlignment();

  /**
   * Set an arbitrary key value pair for an alignment. Note: both key and value
   * objects should return a meaningful, human readable response to .toString()
   * 
   * @param key
   * @param value
   */
  public void setProperty(Object key, Object value);

  /**
   * Get a named property from the alignment.
   * 
   * @param key
   * @return value of property
   */
  public Object getProperty(Object key);

  /**
   * Get the property hashtable.
   * 
   * @return hashtable of alignment properties (or null if none are defined)
   */
  public Hashtable getProperties();

  /**
   * add a reference to a frame of aligned codons for this alignment
   * 
   * @param codons
   */
  public void addCodonFrame(AlignedCodonFrame codons);

  /**
   * remove a particular codon frame reference from this alignment
   * 
   * @param codons
   * @return true if codon frame was removed.
   */
  public boolean removeCodonFrame(AlignedCodonFrame codons);

  /**
   * get all codon frames associated with this alignment
   * 
   * @return
   */
  public AlignedCodonFrame[] getCodonFrames();

  /**
   * get a particular codon frame
   * 
   * @param index
   * @return
   */
  public AlignedCodonFrame getCodonFrame(int index);

  /**
   * get codon frames involving sequenceI
   */
  public AlignedCodonFrame[] getCodonFrame(SequenceI seq);

  /**
   * find sequence with given name in alignment
   * 
   * @param token
   *          name to find
   * @param b
   *          true implies that case insensitive matching will <em>also</em> be
   *          tried
   * @return matched sequence or null
   */
  public SequenceI findName(String token, boolean b);

  /**
   * find next sequence with given name in alignment starting after a given
   * sequence
   * 
   * @param startAfter
   *          the sequence after which the search will be started (usually the
   *          result of the last call to findName)
   * @param token
   *          name to find
   * @param b
   *          true implies that case insensitive matching will <em>also</em> be
   *          tried
   * @return matched sequence or null
   */
  public SequenceI findName(SequenceI startAfter, String token, boolean b);

  /**
   * find first sequence in alignment which is involved in the given search
   * result object
   * 
   * @param results
   * @return -1 or index of sequence in alignment
   */
  public int findIndex(SearchResults results);

  /**
   * append sequences and annotation from another alignment object to this one.
   * Note: this is a straight transfer of object references, and may result in
   * toappend's dependent data being transformed to fit the alignment (changing
   * gap characters, etc...). If you are uncertain, use the copy Alignment copy
   * constructor to create a new version which can be appended without side
   * effect.
   * 
   * @param toappend
   *          - the alignment to be appended.
   */
  public void append(AlignmentI toappend);

  /**
   * Justify the sequences to the left or right by deleting and inserting gaps
   * before the initial residue or after the terminal residue
   * 
   * @param right
   *          true if alignment padded to right, false to justify to left
   * @return true if alignment was changed TODO: return undo object
   */
  public boolean justify(boolean right);

  /**
   * add given annotation row at given position (0 is start, -1 is end)
   * 
   * @param consensus
   * @param i
   */
  public void addAnnotation(AlignmentAnnotation consensus, int i);

  /**
   * search for or create a specific annotation row on the alignment
   * 
   * @param name
   *          name for annotation (must match)
   * @param calcId
   *          calcId for the annotation (null or must match)
   * @param autoCalc
   *          - value of autocalc flag for the annotation
   * @param seqRef
   *          - null or specific sequence reference
   * @param groupRef
   *          - null or specific group reference
   * @param method
   *          - CalcId for the annotation (must match)
   * 
   * @return existing annotation matching the given attributes
   */
  public AlignmentAnnotation findOrCreateAnnotation(String name,
          String calcId, boolean autoCalc, SequenceI seqRef,
          SequenceGroup groupRef);

  /**
   * move the given group up or down in the alignment by the given number of
   * rows. Implementor assumes given group is already present on alignment - no
   * recalculations are triggered.
   * 
   * @param sg
   * @param map
   * @param up
   * @param i
   */
  public void moveSelectedSequencesByOne(SequenceGroup sg,
          Map<SequenceI, SequenceCollectionI> map, boolean up);
}
