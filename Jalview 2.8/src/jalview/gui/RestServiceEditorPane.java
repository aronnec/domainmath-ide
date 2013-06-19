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

import jalview.io.packed.DataProvider.JvDataType;
import jalview.jbgui.GRestServiceEditorPane;
import jalview.ws.rest.InputType;
import jalview.ws.rest.RestServiceDescription;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RestServiceEditorPane extends GRestServiceEditorPane
{
  /**
   * the latest version of the service definition.
   */
  jalview.ws.rest.RestServiceDescription currentservice = null;

  /**
   * original service passed to editor if we are modifying an existing service
   * definition
   */
  jalview.ws.rest.RestServiceDescription oldservice = null;

  public RestServiceEditorPane()
  {
    super();
    // begin with initial text description box enabled.
    urldesc.addKeyListener(new KeyListener()
    {
      @Override
      public void keyTyped(KeyEvent e)
      {
      }

      @Override
      public void keyReleased(KeyEvent e)
      {
        refreshCutnPaste(true);
      }

      @Override
      public void keyPressed(KeyEvent e)
      {

      }
    });
    panels.addChangeListener(new ChangeListener()
    {

      /**
       * last panel selected - used to decide whether the service or the GUI has
       * the latest info
       */
      Object lastComp;

      @Override
      public void stateChanged(ChangeEvent e)
      {
        if (lastComp != paste)
        {
          updateServiceFromGui();
          refreshCutnPaste(false);
        }
        else
        {
          refreshCutnPaste(true);
        }
        lastComp = panels.getSelectedComponent();

      }
    });
    currentservice = new RestServiceDescription("Analysis",
            "service description", "service name", "http://localhost/", "",
            null, false, false, '-');
    initGuiWith(currentservice);
    refreshCutnPaste(false);
    updateButtons();
  }

  public RestServiceEditorPane(RestServiceDescription toedit)
  {
    this();
    oldservice = toedit;
    if (oldservice != null)
    {
      currentservice = new RestServiceDescription(toedit);
    }
    else
    {
      currentservice = new RestServiceDescription("Analysis",
              "service description", "service name", "http://localhost/",
              "", null, false, false, '-');
    }
    initGuiWith(currentservice);
    refreshCutnPaste(false);
    updateButtons();
  }

  /**
   * refresh the buttons based on model state
   */
  public void updateButtons()
  {
    cancelButton.setEnabled(true);
    okButton.setEnabled(currentservice != null && currentservice.isValid());

  }

  Vector<String> _iparam = new Vector<String>();

  Vector<String> _rparam = new Vector<String>();

  /**
   * generate an editable URL service string and parameter list using the
   * service
   * 
   * @param currentservice2
   */
  private void initGuiWith(RestServiceDescription currentservice)
  {
    _iparam.clear();
    _rparam.clear();
    action.removeAllItems();
    action.addItem("Alignment");
    action.addItem("Analysis");
    gapChar.removeAllItems();
    gapChar.addItem(".");
    gapChar.addItem(" ");
    gapChar.addItem("-");
    if (currentservice == null)
    {
      name.setText("");
      descr.setText("");
      url.setText("");
      urlsuff.setText("");
      action.setSelectedItem("Analysis");
      gapChar.setSelectedItem("-");
    }
    else
    {
      name.setText(currentservice.getName());
      descr.setText(currentservice.getDescription());
      url.setText(currentservice.getPostUrl());
      urlsuff.setText(currentservice.getUrlSuffix());
      for (Map.Entry<String, InputType> inparam : currentservice
              .getInputParams().entrySet())
      {
        _iparam.add(inparam.getKey() + " "
                + inparam.getValue().getURLtokenPrefix() + ":"
                + inparam.getValue().getURLEncodedParameter().toString());
      }

      for (JvDataType oparam : currentservice.getResultDataTypes())
      {
        _rparam.add(oparam.name());
      }
      iprms.setListData(_iparam);
      rdata.setListData(_rparam);

      action.setSelectedItem(currentservice.getAction());

      gapChar.setSelectedItem("" + currentservice.getGapCharacter());
    }
    revalidate();
  }

  private String getSelectedInputToken()
  {
    if (iprms.getSelectedIndex() > -1)
    {
      String toktoedit = (String) iprms.getSelectedValue();
      toktoedit = toktoedit.substring(0, toktoedit.indexOf(" "));
      return toktoedit;
    }
    return null;
  }

  @Override
  protected void iprmListSelection_doubleClicked()
  {
    String toktoedit = getSelectedInputToken();
    if (toktoedit != null)
    {
      InputType toedit = currentservice.getInputParams().get(toktoedit);
      String oldParam = toktoedit;
      RestInputParamEditDialog dialog = new RestInputParamEditDialog(this,
              currentservice, toedit);
      if (dialog.wasUpdated())
      {
        currentservice.getInputParams().remove(oldParam);
        currentservice.getInputParams().put(dialog.current.token,
                dialog.current);
        initGuiWith(currentservice);
      }

    }
  }

  @Override
  protected void iprmsAdd_actionPerformed(ActionEvent e)
  {
    RestInputParamEditDialog dialog = new RestInputParamEditDialog(this,
            currentservice, "param"
                    + (1 + currentservice.getInputParams().size()));
    if (dialog.wasUpdated())
    {
      currentservice.getInputParams().put(dialog.current.token,
              dialog.current);
      initGuiWith(currentservice);
    }

  }

  @Override
  protected void iprmsRem_actionPerformed(ActionEvent e)
  {
    String toktoedit = getSelectedInputToken();
    if (toktoedit != null)
    {
      currentservice.getInputParams().remove(toktoedit);
      initGuiWith(currentservice);

    }
  }

  @Override
  protected void rdata_rightClicked(MouseEvent mouse)
  {
    final int rdatasel = rdata.getSelectedIndex();
    if (rdatasel > -1)
    {
      JPopupMenu popup = new JPopupMenu("Select return type");
      for (final JvDataType type : JvDataType.values())
      {
        popup.add(new JMenuItem(type.name())).addActionListener(
                new ActionListener()
                {

                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                    currentservice.getResultDataTypes().set(rdatasel, type);
                    initGuiWith(currentservice);
                    rdata.setSelectedIndex(rdatasel);
                  }
                });
      }
      popup.show(rdata, mouse.getX(), mouse.getY());
    }
  }

  @Override
  protected void rdataAdd_actionPerformed(ActionEvent e)
  {
    int p;
    if ((p = rdata.getSelectedIndex()) > -1)
    {
      currentservice.getResultDataTypes().add(p + 1, JvDataType.ANNOTATION);
    }
    else
    {
      currentservice.addResultDatatype(JvDataType.ANNOTATION);
    }
    initGuiWith(currentservice);
    rdata.setSelectedIndex(p == -1 ? currentservice.getResultDataTypes()
            .size() - 1 : p + 1);
  }

  @Override
  protected void rdataNdown_actionPerformed(ActionEvent e)
  {
    int p;
    if ((p = rdata.getSelectedIndex()) > -1 && p < _rparam.size() - 1)
    {
      List<JvDataType> rtypes = currentservice.getResultDataTypes();
      JvDataType below = rtypes.get(p + 1);
      rtypes.set(p + 1, rtypes.get(p));
      rtypes.set(p, below);
      initGuiWith(currentservice);
      rdata.setSelectedIndex(p + 1);
    }
  }

  @Override
  protected void rdataNup_actionPerformed(ActionEvent e)
  {
    int p;
    if ((p = rdata.getSelectedIndex()) > 0)
    {
      List<JvDataType> rtypes = currentservice.getResultDataTypes();
      JvDataType above = rtypes.get(p - 1);
      rtypes.set(p - 1, rtypes.get(p));
      rtypes.set(p, above);
      initGuiWith(currentservice);
      rdata.setSelectedIndex(p - 1);
    }
  }

  @Override
  protected void rdataRem_actionPerformed(ActionEvent e)
  {
    if (rdata.getSelectedIndex() > -1)
    {
      currentservice.getResultDataTypes().remove(rdata.getSelectedIndex());
      initGuiWith(currentservice);
    }
  }

  private boolean updateServiceFromGui()
  {
    Map<String, InputType> inputTypes = new HashMap<String, InputType>();
    StringBuffer warnings = new StringBuffer();
    for (String its : _iparam)
    {
      Matcher mtch = Pattern.compile("(\\S+)\\s(\\S+):\\[(.+)]").matcher(
              its);
      if (mtch.find())
      {
        if (!RestServiceDescription.parseTypeString(mtch.group(2) + ":"
                + mtch.group(3), mtch.group(1), mtch.group(2),
                mtch.group(3), inputTypes, warnings))
        {
          System.err
                  .println("IMPLEMENTATION PROBLEM: Cannot parse RestService input parameter string '"
                          + its + "'" + "\n" + warnings);
        }
      }
    }
    char gc = gapChar.getSelectedItem() == null ? ' ' : ((String) gapChar
            .getSelectedItem()).charAt(0);
    RestServiceDescription newService = new RestServiceDescription(
            (String) action.getSelectedItem(), descr.getText().trim(), name
                    .getText().trim(), url.getText().trim(), urlsuff
                    .getText().trim(), inputTypes, hSeparable.isSelected(),
            vSeparable.isSelected(), gc);

    if (newService.isValid())
    {
      for (String its : _rparam)
      {
        JvDataType dtype;
        try
        {
          dtype = JvDataType.valueOf(its);
          newService.addResultDatatype(dtype);
        } catch (Throwable x)
        {

          System.err
                  .println("IMPLEMENTATION PROBLEM: Cannot parse RestService output parameter string '"
                          + its + "'" + "\n" + warnings);
        }
      }
      currentservice = newService;
      return true;
    }
    else
    {
      System.err
              .println("IMPLEMENTATION PROBLEM: Restservice generated from GUI is invalid\n"
                      + warnings);

    }
    return false;
  }

  protected void refreshCutnPaste(boolean reparse)
  {
    if (!reparse && currentservice.isValid())
    {
      urldesc.setText(currentservice.toString());
      parseWarnings.setVisible(false);
    }
    else
    {
      if (reparse)
      {
        String txt = urldesc.getText().trim();
        if (txt.length() > 0)
        {
          RestServiceDescription rsd = null;
          try
          {
            rsd = new RestServiceDescription(txt);
            if (rsd.isValid())
            {
              parseWarnings.setVisible(false);
              parseRes.setText("");
              initGuiWith(currentservice = rsd);
            }
            else
            {
              parseRes.setText("Parsing failed. Syntax errors shown below\n"
                      + rsd.getInvalidMessage());
              parseWarnings.setVisible(true);
            }
          } catch (Throwable e)
          {
            e.printStackTrace();
            parseRes.setText("\nParsing failed. An unrecoverable exception was thrown:\n"
                    + e.toString());
            parseWarnings.setVisible(true);
          }
        }
        paste.revalidate();
      }
    }

  }

  public static void main(String[] args)
  {
    if (args.length == 0)
    {
      new Thread(new Runnable()
      {
        boolean visible = true;

        public void run()
        {
          boolean nulserv = true;
          while (visible)
          {
            final Thread runner = Thread.currentThread();
            JFrame df = new JFrame();
            df.getContentPane().setLayout(new BorderLayout());
            df.getContentPane().add(
                    (nulserv = !nulserv) ? new RestServiceEditorPane(
                            jalview.ws.rest.RestClient
                                    .makeShmmrRestClient()
                                    .getRestDescription())
                            : new RestServiceEditorPane(),
                    BorderLayout.CENTER);
            df.setBounds(100, 100, 600, 400);
            df.addComponentListener(new ComponentListener()
            {

              @Override
              public void componentShown(ComponentEvent e)
              {

              }

              @Override
              public void componentResized(ComponentEvent e)
              {

              }

              @Override
              public void componentMoved(ComponentEvent e)
              {

              }

              @Override
              public void componentHidden(ComponentEvent e)
              {
                visible = false;
                runner.interrupt();

              }
            });
            df.setVisible(true);
            while (visible)
            {
              try
              {
                Thread.sleep(10000);
              } catch (Exception x)
              {
              }
              ;
            }
            visible = true;
          }
        }
      }).start();

    }
  }

  String finalService = null;

  public void showDialog(String title)
  {
    if (oldservice != null)
    {
      finalService = oldservice.toString();
    }
    JalviewDialog jvd = new JalviewDialog()
    {

      @Override
      protected void raiseClosed()
      {
        // TODO Auto-generated method stub

      }

      @Override
      protected void okPressed()
      {
        updateServiceFromGui();
        finalService = currentservice.toString();
      }

      @Override
      protected void cancelPressed()
      {

      }
    };
    JPanel pane = new JPanel(new BorderLayout()), okcancel = new JPanel(
            new FlowLayout());
    pane.add(this, BorderLayout.CENTER);
    okcancel.add(jvd.ok);
    okcancel.add(jvd.cancel);
    pane.add(okcancel, BorderLayout.SOUTH);
    jvd.initDialogFrame(pane, true, true, title, 600, 350);
    jvd.waitForInput();
  }

  public String getEditedRestService()
  {
    return finalService;
  }
}
