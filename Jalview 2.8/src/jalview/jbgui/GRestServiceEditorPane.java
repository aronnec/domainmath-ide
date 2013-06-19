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
package jalview.jbgui;

import jalview.gui.JvSwingUtils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class GRestServiceEditorPane extends JPanel
{

  protected JTabbedPane panels;

  protected JPanel details, inputs, paste;

  protected JTextArea urldesc, url, urlsuff, name, descr, parseRes;

  protected JComboBox action, gapChar;

  JLabel acttype;

  protected JButton okButton;

  protected JButton cancelButton;

  JPanel svcattribs;

  JPanel status;

  protected JList iprms;

  protected JList rdata;

  JScrollPane iprmVp, rdataVp, parseResVp, urlVp, descrVp, urldescVp;

  JButton rdataAdd, rdataRem, rdataNdown, rdataNup;

  JButton iprmsAdd, iprmsRem;

  protected JCheckBox hSeparable;

  protected JCheckBox vSeparable;

  protected JPanel parseWarnings;

  public GRestServiceEditorPane()
  {
    jbInit();
  }

  protected void jbInit()
  {
    details = new JPanel();
    details.setName("Details");
    details.setLayout(new MigLayout());
    inputs = new JPanel();
    inputs.setName("Input/Output");
    inputs.setLayout(new MigLayout("", "[grow 85,fill][]", ""));
    paste = new JPanel();
    paste.setName("Cut'n'Paste");
    paste.setLayout(new MigLayout("", "[grow 100, fill]",
            "[][grow 100,fill]"));

    panels = new JTabbedPane();
    panels.addTab(details.getName(), details);
    panels.addTab(inputs.getName(), inputs);
    panels.addTab(paste.getName(), paste);

    JPanel cpanel;

    // Name and URL Panel
    cpanel = details;
    name = new JTextArea(1, 12);

    JvSwingUtils.mgAddtoLayout(cpanel,
            "Short descriptive name for service", new JLabel("Name:"),
            name, "wrap");
    action = new JComboBox();
    JvSwingUtils
            .mgAddtoLayout(
                    cpanel,
                    "What kind of function the service performs (e.g. alignment, analysis, search, etc).",
                    new JLabel("Service Action:"), action, "wrap");
    descr = new JTextArea(4, 60);
    descrVp = new JScrollPane();
    descrVp.setViewportView(descr);
    JvSwingUtils.mgAddtoLayout(cpanel, "Brief description of service",
            new JLabel("Description:"), descrVp, "wrap");

    url = new JTextArea(2, 60);
    urlVp = new JScrollPane();
    urlVp.setViewportView(url);
    JvSwingUtils
            .mgAddtoLayout(
                    cpanel,
                    "URL to post data to service. Include any special parameters needed here",
                    new JLabel("POST URL:"), urlVp, "wrap");

    urlsuff = new JTextArea();
    urlsuff.setColumns(60);

    JvSwingUtils
            .mgAddtoLayout(
                    cpanel,
                    "Optional suffix added to URL when retrieving results from service",
                    new JLabel("URL Suffix:"), urlsuff, "wrap");

    // input options
    // details.add(cpanel = new JPanel(), BorderLayout.CENTER);
    // cpanel.setLayout(new FlowLayout());
    hSeparable = new JCheckBox("per Sequence");
    hSeparable
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("When checked, a job is created for every sequence in the current selection.")
                    + "<html>");
    hSeparable.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        hSeparable_actionPerformed(arg0);

      }
    });
    vSeparable = new JCheckBox("Results are vertically separable");
    vSeparable
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("When checked, a single job is created for the visible region and results"
                                    + " mapped back onto their location in the alignment. Otherwise, a job would be"
                                    + " created for every contiguous region visible in the alignment or current"
                                    + " selection (e.g. a multiple alignment).")
                    + "</html>");
    vSeparable.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        vSeparable_actionPerformed(arg0);

      }
    });
    gapChar = new JComboBox();
    JvSwingUtils.mgAddtoLayout(cpanel,
            "Which gap character does this service prefer ?", new JLabel(
                    "Gap Character:"), gapChar, "wrap");

    cpanel.add(hSeparable);
    cpanel.add(vSeparable);

    // Input and Output lists
    // Inputparams
    JPanel iprmsList = new JPanel();
    iprmsList.setBorder(new TitledBorder("Data input parameters"));
    iprmsList.setLayout(new MigLayout("", "[grow 90, fill][]"));
    iprmVp = new JScrollPane();
    iprmVp.getViewport().setView(iprms = new JList());
    iprmsList.add(iprmVp);
    iprms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    iprms.addMouseListener(new MouseListener()
    {

      @Override
      public void mouseReleased(MouseEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() > 1)
        {
          iprmListSelection_doubleClicked();
        }

      }
    });
    JPanel iprmButs = new JPanel();
    iprmButs.setLayout(new MigLayout());

    iprmsAdd = JvSwingUtils.makeButton("+", "Add input parameter",
            new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                iprmsAdd_actionPerformed(e);

              }
            });
    iprmsRem = JvSwingUtils.makeButton("-",
            "Remove selected input parameter", new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                iprmsRem_actionPerformed(e);

              }
            });

    iprmButs.add(iprmsAdd, "wrap");
    iprmButs.add(iprmsRem, "wrap");
    iprmsList.add(iprmButs, "wrap");
    inputs.add(iprmsList, "wrap");

    // Return Parameters

    rdataAdd = JvSwingUtils.makeButton("+", "Add return datatype",
            new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                rdataAdd_actionPerformed(e);

              }
            });
    rdataRem = JvSwingUtils.makeButton("-", "Remove return datatype",
            new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                rdataRem_actionPerformed(e);

              }
            });
    rdataNup = JvSwingUtils.makeButton("Move Up",
            "Move return type up order", new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                rdataNup_actionPerformed(e);

              }
            });
    rdataNdown = JvSwingUtils.makeButton("Move Down",
            "Move return type down order", new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                rdataNdown_actionPerformed(e);

              }
            });

    JPanel rparamList = new JPanel();
    rparamList.setBorder(new TitledBorder("Data returned by service"));
    rparamList.setLayout(new MigLayout("", "[grow 90, fill][]"));
    rdata = new JList();
    rdata.setToolTipText("Right click to edit currently selected parameter.");
    rdata.addMouseListener(new MouseListener()
    {

      @Override
      public void mouseReleased(MouseEvent arg0)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent arg0)
      {

      }

      @Override
      public void mouseExited(MouseEvent arg0)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent arg0)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseClicked(MouseEvent arg0)
      {
        if (arg0.getButton() == MouseEvent.BUTTON3)
        {
          rdata_rightClicked(arg0);
        }

      }
    });
    rdataVp = new JScrollPane();
    rdataVp.getViewport().setView(rdata);
    rparamList.add(rdataVp);
    JPanel rparamButs = new JPanel();
    rparamButs.setLayout(new MigLayout());
    rparamButs.add(rdataAdd, "wrap");
    rparamButs.add(rdataRem, "wrap");
    rparamButs.add(rdataNup, "wrap");
    rparamButs.add(rdataNdown, "wrap");
    rparamList.add(rparamButs, "wrap");
    inputs.add(rparamList, "wrap");

    // Parse flat-text to a service

    urldesc = new JTextArea(4, 60);
    urldesc.setEditable(true);
    urldesc.setWrapStyleWord(true);
    urldescVp = new JScrollPane();
    urldescVp.setViewportView(urldesc);
    JPanel urldescPane = new JPanel();
    urldescPane.setLayout(new MigLayout("", "[grow 100, fill]",
            "[grow 100, fill]"));
    urldescPane.setBorder(new TitledBorder("RSBS Encoded Service"));
    urldescPane.add(urldescVp, "span");
    paste.add(urldescPane, "span");
    urldescPane
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("Flat file representation of this rest service using the Really Simple Bioinformatics Service formalism"));

    parseRes = new JTextArea();
    parseResVp = new JScrollPane();
    parseResVp.setViewportView(parseRes);
    parseRes.setWrapStyleWord(true);
    parseRes.setColumns(60);
    parseWarnings = new JPanel(new MigLayout("", "[grow 100, fill]",
            "[grow 100, fill]"));
    parseWarnings.setBorder(new TitledBorder("Parsing errors"));
    parseWarnings
            .setToolTipText("<html>"
                    + JvSwingUtils
                            .wrapTooltip("Results of parsing the RSBS representation")
                    + "</html>");
    parseWarnings.add(parseResVp, "center");
    parseRes.setEditable(false);
    paste.add(parseWarnings, "span");
    setLayout(new BorderLayout());
    add(panels, BorderLayout.CENTER);
    okButton = JvSwingUtils.makeButton("OK", "", new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        ok_actionPerformed();
      }
    });
    cancelButton = JvSwingUtils.makeButton("Cancel", "",
            new ActionListener()
            {

              @Override
              public void actionPerformed(ActionEvent e)
              {
                cancel_actionPerformed();
              }
            });

  }

  protected void rdata_rightClicked(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  protected void iprmListSelection_doubleClicked()
  {
    // TODO Auto-generated method stub

  }

  protected void hSeparable_actionPerformed(ActionEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  protected void vSeparable_actionPerformed(ActionEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  protected void cancel_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void ok_actionPerformed()
  {
    // TODO Auto-generated method stub

  }

  protected void iprmsAdd_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void iprmsRem_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void rdataAdd_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void rdataRem_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void rdataNup_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void rdataNdown_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void ok_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void cancel_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

}
