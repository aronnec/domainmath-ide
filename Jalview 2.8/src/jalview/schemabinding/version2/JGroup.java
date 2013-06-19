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
package jalview.schemabinding.version2;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class JGroup.
 * 
 * @version $Revision$ $Date$
 */
public class JGroup implements java.io.Serializable
{

  // --------------------------/
  // - Class/Member Variables -/
  // --------------------------/

  /**
   * Field _start.
   */
  private int _start;

  /**
   * keeps track of state for field: _start
   */
  private boolean _has_start;

  /**
   * Field _end.
   */
  private int _end;

  /**
   * keeps track of state for field: _end
   */
  private boolean _has_end;

  /**
   * Field _name.
   */
  private java.lang.String _name;

  /**
   * Field _colour.
   */
  private java.lang.String _colour;

  /**
   * Field _consThreshold.
   */
  private int _consThreshold;

  /**
   * keeps track of state for field: _consThreshold
   */
  private boolean _has_consThreshold;

  /**
   * Field _pidThreshold.
   */
  private int _pidThreshold;

  /**
   * keeps track of state for field: _pidThreshold
   */
  private boolean _has_pidThreshold;

  /**
   * Field _outlineColour.
   */
  private int _outlineColour;

  /**
   * keeps track of state for field: _outlineColour
   */
  private boolean _has_outlineColour;

  /**
   * Field _displayBoxes.
   */
  private boolean _displayBoxes;

  /**
   * keeps track of state for field: _displayBoxes
   */
  private boolean _has_displayBoxes;

  /**
   * Field _displayText.
   */
  private boolean _displayText;

  /**
   * keeps track of state for field: _displayText
   */
  private boolean _has_displayText;

  /**
   * Field _colourText.
   */
  private boolean _colourText;

  /**
   * keeps track of state for field: _colourText
   */
  private boolean _has_colourText;

  /**
   * Field _textCol1.
   */
  private int _textCol1;

  /**
   * keeps track of state for field: _textCol1
   */
  private boolean _has_textCol1;

  /**
   * Field _textCol2.
   */
  private int _textCol2;

  /**
   * keeps track of state for field: _textCol2
   */
  private boolean _has_textCol2;

  /**
   * Field _textColThreshold.
   */
  private int _textColThreshold;

  /**
   * keeps track of state for field: _textColThreshold
   */
  private boolean _has_textColThreshold;

  /**
   * Field _showUnconserved.
   */
  private boolean _showUnconserved;

  /**
   * keeps track of state for field: _showUnconserved
   */
  private boolean _has_showUnconserved;

  /**
   * Field _ignoreGapsinConsensus.
   */
  private boolean _ignoreGapsinConsensus = true;

  /**
   * keeps track of state for field: _ignoreGapsinConsensus
   */
  private boolean _has_ignoreGapsinConsensus;

  /**
   * Field _showConsensusHistogram.
   */
  private boolean _showConsensusHistogram = true;

  /**
   * keeps track of state for field: _showConsensusHistogram
   */
  private boolean _has_showConsensusHistogram;

  /**
   * Field _showSequenceLogo.
   */
  private boolean _showSequenceLogo = false;

  /**
   * keeps track of state for field: _showSequenceLogo
   */
  private boolean _has_showSequenceLogo;

  /**
   * Field _normaliseSequenceLogo.
   */
  private boolean _normaliseSequenceLogo = false;

  /**
   * keeps track of state for field: _normaliseSequenceLogo
   */
  private boolean _has_normaliseSequenceLogo;

  /**
   * Optional sequence group ID (only needs to be unique for this alignment)
   * 
   */
  private java.lang.String _id;

  /**
   * Field _seqList.
   */
  private java.util.Vector _seqList;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public JGroup()
  {
    super();
    this._seqList = new java.util.Vector();
  }

  // -----------/
  // - Methods -/
  // -----------/

  /**
   * 
   * 
   * @param vSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSeq(final java.lang.String vSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    this._seqList.addElement(vSeq);
  }

  /**
   * 
   * 
   * @param index
   * @param vSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void addSeq(final int index, final java.lang.String vSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    this._seqList.add(index, vSeq);
  }

  /**
     */
  public void deleteColourText()
  {
    this._has_colourText = false;
  }

  /**
     */
  public void deleteConsThreshold()
  {
    this._has_consThreshold = false;
  }

  /**
     */
  public void deleteDisplayBoxes()
  {
    this._has_displayBoxes = false;
  }

  /**
     */
  public void deleteDisplayText()
  {
    this._has_displayText = false;
  }

  /**
     */
  public void deleteEnd()
  {
    this._has_end = false;
  }

  /**
     */
  public void deleteIgnoreGapsinConsensus()
  {
    this._has_ignoreGapsinConsensus = false;
  }

  /**
     */
  public void deleteNormaliseSequenceLogo()
  {
    this._has_normaliseSequenceLogo = false;
  }

  /**
     */
  public void deleteOutlineColour()
  {
    this._has_outlineColour = false;
  }

  /**
     */
  public void deletePidThreshold()
  {
    this._has_pidThreshold = false;
  }

  /**
     */
  public void deleteShowConsensusHistogram()
  {
    this._has_showConsensusHistogram = false;
  }

  /**
     */
  public void deleteShowSequenceLogo()
  {
    this._has_showSequenceLogo = false;
  }

  /**
     */
  public void deleteShowUnconserved()
  {
    this._has_showUnconserved = false;
  }

  /**
     */
  public void deleteStart()
  {
    this._has_start = false;
  }

  /**
     */
  public void deleteTextCol1()
  {
    this._has_textCol1 = false;
  }

  /**
     */
  public void deleteTextCol2()
  {
    this._has_textCol2 = false;
  }

  /**
     */
  public void deleteTextColThreshold()
  {
    this._has_textColThreshold = false;
  }

  /**
   * Method enumerateSeq.
   * 
   * @return an Enumeration over all java.lang.String elements
   */
  public java.util.Enumeration enumerateSeq()
  {
    return this._seqList.elements();
  }

  /**
   * Returns the value of field 'colour'.
   * 
   * @return the value of field 'Colour'.
   */
  public java.lang.String getColour()
  {
    return this._colour;
  }

  /**
   * Returns the value of field 'colourText'.
   * 
   * @return the value of field 'ColourText'.
   */
  public boolean getColourText()
  {
    return this._colourText;
  }

  /**
   * Returns the value of field 'consThreshold'.
   * 
   * @return the value of field 'ConsThreshold'.
   */
  public int getConsThreshold()
  {
    return this._consThreshold;
  }

  /**
   * Returns the value of field 'displayBoxes'.
   * 
   * @return the value of field 'DisplayBoxes'.
   */
  public boolean getDisplayBoxes()
  {
    return this._displayBoxes;
  }

  /**
   * Returns the value of field 'displayText'.
   * 
   * @return the value of field 'DisplayText'.
   */
  public boolean getDisplayText()
  {
    return this._displayText;
  }

  /**
   * Returns the value of field 'end'.
   * 
   * @return the value of field 'End'.
   */
  public int getEnd()
  {
    return this._end;
  }

  /**
   * Returns the value of field 'id'. The field 'id' has the following
   * description: Optional sequence group ID (only needs to be unique for this
   * alignment)
   * 
   * 
   * @return the value of field 'Id'.
   */
  public java.lang.String getId()
  {
    return this._id;
  }

  /**
   * Returns the value of field 'ignoreGapsinConsensus'.
   * 
   * @return the value of field 'IgnoreGapsinConsensus'.
   */
  public boolean getIgnoreGapsinConsensus()
  {
    return this._ignoreGapsinConsensus;
  }

  /**
   * Returns the value of field 'name'.
   * 
   * @return the value of field 'Name'.
   */
  public java.lang.String getName()
  {
    return this._name;
  }

  /**
   * Returns the value of field 'normaliseSequenceLogo'.
   * 
   * @return the value of field 'NormaliseSequenceLogo'.
   */
  public boolean getNormaliseSequenceLogo()
  {
    return this._normaliseSequenceLogo;
  }

  /**
   * Returns the value of field 'outlineColour'.
   * 
   * @return the value of field 'OutlineColour'.
   */
  public int getOutlineColour()
  {
    return this._outlineColour;
  }

  /**
   * Returns the value of field 'pidThreshold'.
   * 
   * @return the value of field 'PidThreshold'.
   */
  public int getPidThreshold()
  {
    return this._pidThreshold;
  }

  /**
   * Method getSeq.
   * 
   * @param index
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   * @return the value of the java.lang.String at the given index
   */
  public java.lang.String getSeq(final int index)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._seqList.size())
    {
      throw new IndexOutOfBoundsException("getSeq: Index value '" + index
              + "' not in range [0.." + (this._seqList.size() - 1) + "]");
    }

    return (java.lang.String) _seqList.get(index);
  }

  /**
   * Method getSeq.Returns the contents of the collection in an Array.
   * <p>
   * Note: Just in case the collection contents are changing in another thread,
   * we pass a 0-length Array of the correct type into the API call. This way we
   * <i>know</i> that the Array returned is of exactly the correct length.
   * 
   * @return this collection as an Array
   */
  public java.lang.String[] getSeq()
  {
    java.lang.String[] array = new java.lang.String[0];
    return (java.lang.String[]) this._seqList.toArray(array);
  }

  /**
   * Method getSeqCount.
   * 
   * @return the size of this collection
   */
  public int getSeqCount()
  {
    return this._seqList.size();
  }

  /**
   * Returns the value of field 'showConsensusHistogram'.
   * 
   * @return the value of field 'ShowConsensusHistogram'.
   */
  public boolean getShowConsensusHistogram()
  {
    return this._showConsensusHistogram;
  }

  /**
   * Returns the value of field 'showSequenceLogo'.
   * 
   * @return the value of field 'ShowSequenceLogo'.
   */
  public boolean getShowSequenceLogo()
  {
    return this._showSequenceLogo;
  }

  /**
   * Returns the value of field 'showUnconserved'.
   * 
   * @return the value of field 'ShowUnconserved'.
   */
  public boolean getShowUnconserved()
  {
    return this._showUnconserved;
  }

  /**
   * Returns the value of field 'start'.
   * 
   * @return the value of field 'Start'.
   */
  public int getStart()
  {
    return this._start;
  }

  /**
   * Returns the value of field 'textCol1'.
   * 
   * @return the value of field 'TextCol1'.
   */
  public int getTextCol1()
  {
    return this._textCol1;
  }

  /**
   * Returns the value of field 'textCol2'.
   * 
   * @return the value of field 'TextCol2'.
   */
  public int getTextCol2()
  {
    return this._textCol2;
  }

  /**
   * Returns the value of field 'textColThreshold'.
   * 
   * @return the value of field 'TextColThreshold'.
   */
  public int getTextColThreshold()
  {
    return this._textColThreshold;
  }

  /**
   * Method hasColourText.
   * 
   * @return true if at least one ColourText has been added
   */
  public boolean hasColourText()
  {
    return this._has_colourText;
  }

  /**
   * Method hasConsThreshold.
   * 
   * @return true if at least one ConsThreshold has been added
   */
  public boolean hasConsThreshold()
  {
    return this._has_consThreshold;
  }

  /**
   * Method hasDisplayBoxes.
   * 
   * @return true if at least one DisplayBoxes has been added
   */
  public boolean hasDisplayBoxes()
  {
    return this._has_displayBoxes;
  }

  /**
   * Method hasDisplayText.
   * 
   * @return true if at least one DisplayText has been added
   */
  public boolean hasDisplayText()
  {
    return this._has_displayText;
  }

  /**
   * Method hasEnd.
   * 
   * @return true if at least one End has been added
   */
  public boolean hasEnd()
  {
    return this._has_end;
  }

  /**
   * Method hasIgnoreGapsinConsensus.
   * 
   * @return true if at least one IgnoreGapsinConsensus has been added
   */
  public boolean hasIgnoreGapsinConsensus()
  {
    return this._has_ignoreGapsinConsensus;
  }

  /**
   * Method hasNormaliseSequenceLogo.
   * 
   * @return true if at least one NormaliseSequenceLogo has been added
   */
  public boolean hasNormaliseSequenceLogo()
  {
    return this._has_normaliseSequenceLogo;
  }

  /**
   * Method hasOutlineColour.
   * 
   * @return true if at least one OutlineColour has been added
   */
  public boolean hasOutlineColour()
  {
    return this._has_outlineColour;
  }

  /**
   * Method hasPidThreshold.
   * 
   * @return true if at least one PidThreshold has been added
   */
  public boolean hasPidThreshold()
  {
    return this._has_pidThreshold;
  }

  /**
   * Method hasShowConsensusHistogram.
   * 
   * @return true if at least one ShowConsensusHistogram has been added
   */
  public boolean hasShowConsensusHistogram()
  {
    return this._has_showConsensusHistogram;
  }

  /**
   * Method hasShowSequenceLogo.
   * 
   * @return true if at least one ShowSequenceLogo has been added
   */
  public boolean hasShowSequenceLogo()
  {
    return this._has_showSequenceLogo;
  }

  /**
   * Method hasShowUnconserved.
   * 
   * @return true if at least one ShowUnconserved has been added
   */
  public boolean hasShowUnconserved()
  {
    return this._has_showUnconserved;
  }

  /**
   * Method hasStart.
   * 
   * @return true if at least one Start has been added
   */
  public boolean hasStart()
  {
    return this._has_start;
  }

  /**
   * Method hasTextCol1.
   * 
   * @return true if at least one TextCol1 has been added
   */
  public boolean hasTextCol1()
  {
    return this._has_textCol1;
  }

  /**
   * Method hasTextCol2.
   * 
   * @return true if at least one TextCol2 has been added
   */
  public boolean hasTextCol2()
  {
    return this._has_textCol2;
  }

  /**
   * Method hasTextColThreshold.
   * 
   * @return true if at least one TextColThreshold has been added
   */
  public boolean hasTextColThreshold()
  {
    return this._has_textColThreshold;
  }

  /**
   * Returns the value of field 'colourText'.
   * 
   * @return the value of field 'ColourText'.
   */
  public boolean isColourText()
  {
    return this._colourText;
  }

  /**
   * Returns the value of field 'displayBoxes'.
   * 
   * @return the value of field 'DisplayBoxes'.
   */
  public boolean isDisplayBoxes()
  {
    return this._displayBoxes;
  }

  /**
   * Returns the value of field 'displayText'.
   * 
   * @return the value of field 'DisplayText'.
   */
  public boolean isDisplayText()
  {
    return this._displayText;
  }

  /**
   * Returns the value of field 'ignoreGapsinConsensus'.
   * 
   * @return the value of field 'IgnoreGapsinConsensus'.
   */
  public boolean isIgnoreGapsinConsensus()
  {
    return this._ignoreGapsinConsensus;
  }

  /**
   * Returns the value of field 'normaliseSequenceLogo'.
   * 
   * @return the value of field 'NormaliseSequenceLogo'.
   */
  public boolean isNormaliseSequenceLogo()
  {
    return this._normaliseSequenceLogo;
  }

  /**
   * Returns the value of field 'showConsensusHistogram'.
   * 
   * @return the value of field 'ShowConsensusHistogram'.
   */
  public boolean isShowConsensusHistogram()
  {
    return this._showConsensusHistogram;
  }

  /**
   * Returns the value of field 'showSequenceLogo'.
   * 
   * @return the value of field 'ShowSequenceLogo'.
   */
  public boolean isShowSequenceLogo()
  {
    return this._showSequenceLogo;
  }

  /**
   * Returns the value of field 'showUnconserved'.
   * 
   * @return the value of field 'ShowUnconserved'.
   */
  public boolean isShowUnconserved()
  {
    return this._showUnconserved;
  }

  /**
   * Method isValid.
   * 
   * @return true if this object is valid according to the schema
   */
  public boolean isValid()
  {
    try
    {
      validate();
    } catch (org.exolab.castor.xml.ValidationException vex)
    {
      return false;
    }
    return true;
  }

  /**
   * 
   * 
   * @param out
   * @throws org.exolab.castor.xml.MarshalException
   *           if object is null or if any SAXException is thrown during
   *           marshaling
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   */
  public void marshal(final java.io.Writer out)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    Marshaller.marshal(this, out);
  }

  /**
   * 
   * 
   * @param handler
   * @throws java.io.IOException
   *           if an IOException occurs during marshaling
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   * @throws org.exolab.castor.xml.MarshalException
   *           if object is null or if any SAXException is thrown during
   *           marshaling
   */
  public void marshal(final org.xml.sax.ContentHandler handler)
          throws java.io.IOException,
          org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    Marshaller.marshal(this, handler);
  }

  /**
     */
  public void removeAllSeq()
  {
    this._seqList.clear();
  }

  /**
   * Method removeSeq.
   * 
   * @param vSeq
   * @return true if the object was removed from the collection.
   */
  public boolean removeSeq(final java.lang.String vSeq)
  {
    boolean removed = _seqList.remove(vSeq);
    return removed;
  }

  /**
   * Method removeSeqAt.
   * 
   * @param index
   * @return the element removed from the collection
   */
  public java.lang.String removeSeqAt(final int index)
  {
    java.lang.Object obj = this._seqList.remove(index);
    return (java.lang.String) obj;
  }

  /**
   * Sets the value of field 'colour'.
   * 
   * @param colour
   *          the value of field 'colour'.
   */
  public void setColour(final java.lang.String colour)
  {
    this._colour = colour;
  }

  /**
   * Sets the value of field 'colourText'.
   * 
   * @param colourText
   *          the value of field 'colourText'.
   */
  public void setColourText(final boolean colourText)
  {
    this._colourText = colourText;
    this._has_colourText = true;
  }

  /**
   * Sets the value of field 'consThreshold'.
   * 
   * @param consThreshold
   *          the value of field 'consThreshold'.
   */
  public void setConsThreshold(final int consThreshold)
  {
    this._consThreshold = consThreshold;
    this._has_consThreshold = true;
  }

  /**
   * Sets the value of field 'displayBoxes'.
   * 
   * @param displayBoxes
   *          the value of field 'displayBoxes'.
   */
  public void setDisplayBoxes(final boolean displayBoxes)
  {
    this._displayBoxes = displayBoxes;
    this._has_displayBoxes = true;
  }

  /**
   * Sets the value of field 'displayText'.
   * 
   * @param displayText
   *          the value of field 'displayText'.
   */
  public void setDisplayText(final boolean displayText)
  {
    this._displayText = displayText;
    this._has_displayText = true;
  }

  /**
   * Sets the value of field 'end'.
   * 
   * @param end
   *          the value of field 'end'.
   */
  public void setEnd(final int end)
  {
    this._end = end;
    this._has_end = true;
  }

  /**
   * Sets the value of field 'id'. The field 'id' has the following description:
   * Optional sequence group ID (only needs to be unique for this alignment)
   * 
   * 
   * @param id
   *          the value of field 'id'.
   */
  public void setId(final java.lang.String id)
  {
    this._id = id;
  }

  /**
   * Sets the value of field 'ignoreGapsinConsensus'.
   * 
   * @param ignoreGapsinConsensus
   *          the value of field 'ignoreGapsinConsensus'.
   */
  public void setIgnoreGapsinConsensus(final boolean ignoreGapsinConsensus)
  {
    this._ignoreGapsinConsensus = ignoreGapsinConsensus;
    this._has_ignoreGapsinConsensus = true;
  }

  /**
   * Sets the value of field 'name'.
   * 
   * @param name
   *          the value of field 'name'.
   */
  public void setName(final java.lang.String name)
  {
    this._name = name;
  }

  /**
   * Sets the value of field 'normaliseSequenceLogo'.
   * 
   * @param normaliseSequenceLogo
   *          the value of field 'normaliseSequenceLogo'.
   */
  public void setNormaliseSequenceLogo(final boolean normaliseSequenceLogo)
  {
    this._normaliseSequenceLogo = normaliseSequenceLogo;
    this._has_normaliseSequenceLogo = true;
  }

  /**
   * Sets the value of field 'outlineColour'.
   * 
   * @param outlineColour
   *          the value of field 'outlineColour'.
   */
  public void setOutlineColour(final int outlineColour)
  {
    this._outlineColour = outlineColour;
    this._has_outlineColour = true;
  }

  /**
   * Sets the value of field 'pidThreshold'.
   * 
   * @param pidThreshold
   *          the value of field 'pidThreshold'.
   */
  public void setPidThreshold(final int pidThreshold)
  {
    this._pidThreshold = pidThreshold;
    this._has_pidThreshold = true;
  }

  /**
   * 
   * 
   * @param index
   * @param vSeq
   * @throws java.lang.IndexOutOfBoundsException
   *           if the index given is outside the bounds of the collection
   */
  public void setSeq(final int index, final java.lang.String vSeq)
          throws java.lang.IndexOutOfBoundsException
  {
    // check bounds for index
    if (index < 0 || index >= this._seqList.size())
    {
      throw new IndexOutOfBoundsException("setSeq: Index value '" + index
              + "' not in range [0.." + (this._seqList.size() - 1) + "]");
    }

    this._seqList.set(index, vSeq);
  }

  /**
   * 
   * 
   * @param vSeqArray
   */
  public void setSeq(final java.lang.String[] vSeqArray)
  {
    // -- copy array
    _seqList.clear();

    for (int i = 0; i < vSeqArray.length; i++)
    {
      this._seqList.add(vSeqArray[i]);
    }
  }

  /**
   * Sets the value of field 'showConsensusHistogram'.
   * 
   * @param showConsensusHistogram
   *          the value of field 'showConsensusHistogram'.
   */
  public void setShowConsensusHistogram(final boolean showConsensusHistogram)
  {
    this._showConsensusHistogram = showConsensusHistogram;
    this._has_showConsensusHistogram = true;
  }

  /**
   * Sets the value of field 'showSequenceLogo'.
   * 
   * @param showSequenceLogo
   *          the value of field 'showSequenceLogo'
   */
  public void setShowSequenceLogo(final boolean showSequenceLogo)
  {
    this._showSequenceLogo = showSequenceLogo;
    this._has_showSequenceLogo = true;
  }

  /**
   * Sets the value of field 'showUnconserved'.
   * 
   * @param showUnconserved
   *          the value of field 'showUnconserved'.
   */
  public void setShowUnconserved(final boolean showUnconserved)
  {
    this._showUnconserved = showUnconserved;
    this._has_showUnconserved = true;
  }

  /**
   * Sets the value of field 'start'.
   * 
   * @param start
   *          the value of field 'start'.
   */
  public void setStart(final int start)
  {
    this._start = start;
    this._has_start = true;
  }

  /**
   * Sets the value of field 'textCol1'.
   * 
   * @param textCol1
   *          the value of field 'textCol1'.
   */
  public void setTextCol1(final int textCol1)
  {
    this._textCol1 = textCol1;
    this._has_textCol1 = true;
  }

  /**
   * Sets the value of field 'textCol2'.
   * 
   * @param textCol2
   *          the value of field 'textCol2'.
   */
  public void setTextCol2(final int textCol2)
  {
    this._textCol2 = textCol2;
    this._has_textCol2 = true;
  }

  /**
   * Sets the value of field 'textColThreshold'.
   * 
   * @param textColThreshold
   *          the value of field 'textColThreshold'
   */
  public void setTextColThreshold(final int textColThreshold)
  {
    this._textColThreshold = textColThreshold;
    this._has_textColThreshold = true;
  }

  /**
   * Method unmarshal.
   * 
   * @param reader
   * @throws org.exolab.castor.xml.MarshalException
   *           if object is null or if any SAXException is thrown during
   *           marshaling
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   * @return the unmarshaled jalview.schemabinding.version2.JGroup
   */
  public static jalview.schemabinding.version2.JGroup unmarshal(
          final java.io.Reader reader)
          throws org.exolab.castor.xml.MarshalException,
          org.exolab.castor.xml.ValidationException
  {
    return (jalview.schemabinding.version2.JGroup) Unmarshaller.unmarshal(
            jalview.schemabinding.version2.JGroup.class, reader);
  }

  /**
   * 
   * 
   * @throws org.exolab.castor.xml.ValidationException
   *           if this object is an invalid instance according to the schema
   */
  public void validate() throws org.exolab.castor.xml.ValidationException
  {
    org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
    validator.validate(this);
  }

}
