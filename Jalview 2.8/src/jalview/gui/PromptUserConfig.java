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
package jalview.gui;

import jalview.bin.Cache;

import java.awt.Component;

import javax.swing.*;

public class PromptUserConfig implements Runnable
{
  /**
   * Given a boolean Cache option:
   * 
   * 1. Prompt the user with the given text if the option is unset, and set the
   * option accordingly (yes/no==true/false).
   * 
   * 2. Execute the given Runnables according to the state of the config option.
   * 
   */
  /**
   * boolean property to set
   */
  String property = null;

  /**
   * can the user cancel rather than set the property ?
   */
  boolean allowCancel = false;

  /**
   * title of prompt dialog
   */
  String dialogTitle;

  /**
   * text in dialog
   */
  String dialogText;

  /**
   * runnables for all cases.
   */
  Runnable iftrue = null, iffalse = null, ifundef = null;

  private Component component;

  /**
   * if set, remove the property if the user says no rather than setting it to
   * false.
   */
  private boolean removeifunset;

  /**
   * @return the removeifunset
   */
  public boolean isRemoveifunset()
  {
    return removeifunset;
  }

  /**
   * @param removeifunset
   *          the removeifunset to set
   */
  public void setRemoveifunset(boolean removeifunset)
  {
    this.removeifunset = removeifunset;
  }

  /**
   * @param desktop
   *          - where the dialog box will be shown
   * @param property
   *          - boolean property in jalview.bin.Cache
   * @param dialogTitle
   *          - title of prompt box
   * @param dialogText
   *          - text of box
   * @param iftrue
   *          - executed if property is true
   * @param iffalse
   *          - executed if property is false
   * @param ifundef
   *          - executed if property was not set after prompting.
   * @param allowCancel
   *          - allow the user to cancel rather than set the property
   */
  public PromptUserConfig(Component desktop, String property,
          String dialogTitle, String dialogText, Runnable iftrue,
          Runnable iffalse, Runnable ifundef, boolean allowCancel)
  {
    super();
    this.component = desktop;
    this.property = property;
    this.dialogTitle = dialogTitle;
    this.dialogText = dialogText;
    this.iftrue = iftrue;
    this.iffalse = iffalse;
    this.ifundef = ifundef;
    this.allowCancel = allowCancel;
  }

  public void run()
  {
    if (property == null)
    {
      return;
    }
    // First - check to see if wee have an old questionnaire/response id pair.
    String lastq = jalview.bin.Cache.getProperty(property);

    if (lastq == null)
    {
      raiseDialog();
      Cache.log.debug("Got user response.");
    }
    lastq = jalview.bin.Cache.getProperty(property);
    String extype = "";
    Exception e = null;
    if (lastq == null)
    {
      // execute the ifundef
      try
      {
        if (ifundef != null)
        {
          ifundef.run();
        }
      } catch (Exception ex)
      {
        e = ex;
        extype = "undefined";
      }
    }
    else if (Boolean.valueOf(lastq).booleanValue())
    {
      // execute the iftrue
      try
      {
        if (iftrue != null)
        {
          iftrue.run();
        }
      } catch (Exception ex)
      {
        e = ex;
        extype = "if true";
      }
    }
    else
    {
      try
      {
        if (iffalse != null)
        {
          iffalse.run();
        }
      } catch (Exception ex)
      {
        e = ex;
        extype = "if false";
      }
    }
    // report any exceptions
    if (e != null)
    {
      Cache.log.warn("Unexpected exception when executing the " + extype
              + " runnable for property " + property, e);
    }
  }

  /**
   * raise the property dialog
   */
  private void raiseDialog()
  {
    if (jalview.bin.Cache.log.isDebugEnabled())
    {
      jalview.bin.Cache.log.debug("Prompting user for " + dialogTitle
              + " for Cache property " + property);
    }
    try
    {
      int reply = JOptionPane.showConfirmDialog(
              Desktop.desktop, // component,
              dialogText, dialogTitle,
              (allowCancel) ? JOptionPane.YES_NO_CANCEL_OPTION
                      : JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE);
      // now, ask the desktop to relayer any external windows that might have
      // been obsured
      if (Desktop.instance != null)
      {
        Desktop.instance.relayerWindows();
      }
      // and finish parsing the result
      jalview.bin.Cache.log.debug("Got response : " + reply);
      if (reply == JOptionPane.YES_OPTION)
      {
        jalview.bin.Cache.setProperty(property, "true");
      }
      else if (reply == JOptionPane.NO_OPTION)
      {
        if (removeifunset)
        {
          jalview.bin.Cache.removeProperty(property);
        }
        else
        {
          jalview.bin.Cache.setProperty(property, "false");
        }
      }
      else
      {
        jalview.bin.Cache.log.debug("User cancelled setting " + property);
        return;
      }
      // verify the property is set for debugging
      if (jalview.bin.Cache.log.isDebugEnabled())
      {
        jalview.bin.Cache.log.debug("User set property to "
                + jalview.bin.Cache.getProperty(property));
      }
    } catch (Exception e)
    {
      jalview.bin.Cache.log.warn(
              "Unexpected exception when prompting user for yes/no setting for property "
                      + property, e);
    }
  }
}
