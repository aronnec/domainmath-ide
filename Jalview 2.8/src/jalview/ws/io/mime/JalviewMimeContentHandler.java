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
package jalview.ws.io.mime;

import jalview.io.packed.DataProvider;
import jalview.io.packed.JalviewDataset;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.descriptor.BodyDescriptor;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.parser.Field;

/**
 * ContentHandler for parsing mime encoded messages into Jalview objects. TODO:
 * complete implementation TODO: test implementation TODO: hook in to Jalview IO
 * and service response parser.
 * 
 * @author JimP
 * 
 */
public class JalviewMimeContentHandler implements ContentHandler
{
  /**
   * context for data parsed from multi-part mime document
   */
  JalviewDataset context;

  /**
   * create a new handler to process a Jalview mime message.
   * 
   * @param ds
   */
  public JalviewMimeContentHandler(JalviewDataset ds)
  {
    context = ds;
  }

  /**
   * type of data pack being parsed currently
   */
  String currentType;

  /**
   * name of data pack being parsed currently
   */
  String currentName;

  /**
   * sources for data to be parsed
   */
  List<DataProvider> dataItems = new ArrayList<DataProvider>();

  @Override
  public void body(BodyDescriptor arg0, InputStream arg1)
          throws MimeException, IOException
  {

    // TODO Auto-generated method stub

  }

  @Override
  public void endBodyPart() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void endHeader() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void endMessage() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void endMultipart() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void epilogue(InputStream arg0) throws MimeException, IOException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void field(Field arg0) throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void preamble(InputStream arg0) throws MimeException, IOException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void raw(InputStream arg0) throws MimeException, IOException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void startBodyPart() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void startHeader() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void startMessage() throws MimeException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void startMultipart(BodyDescriptor arg0) throws MimeException
  {
    // TODO Auto-generated method stub

  }

  /**
   * 
   * @return data providers to parse each data file extracted from the mime
   *         stream.
   */
  public Iterable<DataProvider> getJalviewDataProviders()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
