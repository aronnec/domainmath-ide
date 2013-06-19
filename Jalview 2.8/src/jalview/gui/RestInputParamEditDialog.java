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

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

import net.miginfocom.swing.MigLayout;

import jalview.jbgui.GRestInputParamEditDialog;
import jalview.ws.params.InvalidArgumentException;
import jalview.ws.params.OptionI;
import jalview.ws.params.ParameterI;
import jalview.ws.rest.InputType;
import jalview.ws.rest.RestServiceDescription;

public class RestInputParamEditDialog extends GRestInputParamEditDialog
        implements OptsParametersContainerI
{
  Hashtable<String, Class> typeclass = new Hashtable<String, Class>();

  Hashtable<String, ArrayList<JPanel>> typeopts = new Hashtable<String, ArrayList<JPanel>>();

  Hashtable<String, OptsAndParamsPage> opanps = new Hashtable<String, OptsAndParamsPage>();

  private InputType getTypeFor(String name)
  {
    try
    {
      return (InputType) (typeclass.get(name).getConstructor()
              .newInstance(null));
    } catch (Throwable x)
    {
      System.err
              .println("Unexpected exception when instantiating rest input type.");
      x.printStackTrace();
    }
    return null;
  }

  int reply;

  JalviewDialog frame = new JalviewDialog()
  {

    @Override
    protected void raiseClosed()
    {

    }

    @Override
    protected void okPressed()
    {
      reply = JOptionPane.OK_OPTION;
    }

    @Override
    protected void cancelPressed()
    {
      reply = JOptionPane.CANCEL_OPTION;

    }
  };

  InputType old, current;

  public RestInputParamEditDialog(
          RestServiceEditorPane restServiceEditorPane,
          RestServiceDescription currentservice, InputType toedit)
  {
    initFor(restServiceEditorPane, currentservice, toedit);
    frame.waitForInput();
    // TODO: warn user if they are about to overwrite an existing parameter
    // because they have used the same name when editing a different parameter.
    // TODO: make any press of the return key cause 'OK' to be pressed
  }

  private void initFor(RestServiceEditorPane restServiceEditorPane,
          RestServiceDescription currentservice, InputType toedit)
  {
    okcancel.add(frame.cancel);
    okcancel.add(frame.ok);
    frame.initDialogFrame(dpane, true, true, "Edit parameter for service "
            + currentservice.getName(), 600, 800);

    initTypeLists();
    reply = JOptionPane.CANCEL_OPTION;
    old = toedit;
    current = null;
    if (old != null)
    {
      setStateFor(old);
    }
    updated = updated && reply == JOptionPane.OK_OPTION;
    frame.validate();
  }

  public RestInputParamEditDialog(
          RestServiceEditorPane restServiceEditorPane,
          RestServiceDescription currentservice, String string)
  {
    initFor(restServiceEditorPane, currentservice, null);
    tok.setText(string);
    frame.waitForInput();
  }

  private void setStateFor(InputType current)
  {
    tok.setText(current.token);
    OptsAndParamsPage opanp = opanps.get(current.getURLtokenPrefix());
    for (OptionI ops : current.getOptions())
    {
      if (ops instanceof ParameterI)
      {
        opanp.setParameter((ParameterI) ops);
      }
      else
      {
        if (ops.getValue() != null && ops.getValue().length() > 0)
        {
          opanp.selectOption(ops, ops.getValue());
        }
      }
    }
    typeList.setSelectedValue(current.getURLtokenPrefix(), true);
    type_SelectionChangedActionPerformed(null);
  }

  private void updateCurrentType()
  {
    if (typeList.getSelectedValue() != null)
    {
      InputType newType = getTypeFor((String) typeList.getSelectedValue());
      if (newType != null)
      {
        newType.token = tok.getText().trim();
        try
        {
          newType.configureFromArgumentI(opanps.get(
                  newType.getURLtokenPrefix()).getCurrentSettings());
          current = newType;
          updated = true;
        } catch (InvalidArgumentException ex)
        {
          System.err
                  .println("IMPLEMENTATION ERROR: Invalid argument for type : "
                          + typeList.getSelectedValue() + "\n");
          ex.printStackTrace();
        }
      }
    }

  }

  private void initTypeLists()
  {
    ArrayList<String> types = new ArrayList<String>();
    // populate type list
    for (Class type : RestServiceDescription.getInputTypes())
    {

      InputType jtype = null;
      try
      {
        JPanel inopts = new JPanel(new MigLayout());
        ArrayList<JPanel> opts = new ArrayList<JPanel>(), prms = new ArrayList<JPanel>();
        jtype = (InputType) (type.getConstructor().newInstance(null));
        typeclass.put(jtype.getURLtokenPrefix(), type);
        // and populate parameters from this type
        OptsAndParamsPage opanp = new OptsAndParamsPage(this, true);
        opanps.put(jtype.getURLtokenPrefix(), opanp);
        for (OptionI opt : jtype.getOptions())
        {

          if (opt instanceof ParameterI)
          {
            prms.add(opanp.addParameter((ParameterI) opt));
          }
          else
          {
            opts.add(opanp.addOption(opt));
          }
        }
        // then tag the params at the end of the options.
        for (JPanel pnl : prms)
        {
          opts.add(pnl);
        }
        typeopts.put(jtype.getURLtokenPrefix(), opts);
        types.add(jtype.getURLtokenPrefix());
      } catch (Throwable x)
      {
        System.err
                .println("Unexpected exception when instantiating rest input type.");
        x.printStackTrace();
      }
    }
    typeList.setListData(types.toArray());

  }

  @Override
  protected void type_SelectionChangedActionPerformed(ListSelectionEvent e)
  {
    options.removeAll();
    String typen = (String) typeList.getSelectedValue();
    if (typeopts.get(typen) != null)
    {
      for (JPanel opt : typeopts.get(typen))
      {
        opt.setOpaque(true);
        options.add(opt, "wrap");
      }
      options.invalidate();
      optionsPanel.setVisible(true);
    }
    else
    {
      optionsPanel.setVisible(false);
    }
    dpane.revalidate();
    updateCurrentType();
  }

  boolean updated = false;

  public boolean wasUpdated()
  {
    return updated;
  }

  @Override
  public void refreshParamLayout()
  {
    options.invalidate();
    dpane.revalidate();
  }

  @Override
  protected void tokChanged_actionPerformed()
  {
    if (tok.getText().trim().length() > 0)
    {
      if (current != null)
      {
        current.token = tok.getText().trim();
        updated = true;
      }
    }
  }

  @Override
  public void argSetModified(Object modifiedElement, boolean b)
  {
    updated = updated | b;
    if (updated)
    {
      updateCurrentType();
    }
  }

}
