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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.orsay.lri.varna.VARNAPanel;
import fr.orsay.lri.varna.components.ReorderableJList;
import fr.orsay.lri.varna.exceptions.ExceptionFileFormatOrSyntax;
import fr.orsay.lri.varna.exceptions.ExceptionLoadingFailed;
import fr.orsay.lri.varna.exceptions.ExceptionNonEqualLength;
import fr.orsay.lri.varna.exceptions.ExceptionUnmatchedClosingParentheses;
import fr.orsay.lri.varna.interfaces.InterfaceVARNAListener;
import fr.orsay.lri.varna.models.FullBackup;
import fr.orsay.lri.varna.models.VARNAConfig;
import fr.orsay.lri.varna.models.rna.Mapping;
import fr.orsay.lri.varna.models.rna.RNA;

public class AppVarnaBinding extends jalview.ext.varna.JalviewVarnaBinding
        implements DropTargetListener, InterfaceVARNAListener,
        MouseListener
{

  /**
	 * 
	 */
  // private static final long serialVersionUID = -790155708306987257L;

  private String DEFAULT_SEQUENCE = "CAGCACGACACUAGCAGUCAGUGUCAGACUGCAIACAGCACGACACUAGCAGUCAGUGUCAGACUGCAIACAGCACGACACUAGCAGUCAGUGUCAGACUGCAIA";

  private String DEFAULT_STRUCTURE1 = "..(((((...(((((...(((((...(((((.....)))))...))))).....(((((...(((((.....)))))...))))).....)))))...)))))..";

  private String DEFAULT_STRUCTURE2 = "..(((((...(((((...(((((........(((((...(((((.....)))))...)))))..................))))).....)))))...)))))..";

  public VARNAPanel vp;

  protected JPanel _tools = new JPanel();

  private JPanel _input = new JPanel();

  private JPanel _seqPanel = new JPanel();

  private JPanel _strPanel = new JPanel();

  private JLabel _info = new JLabel();

  private JTextField _str = new JTextField();

  private JTextField _seq = new JTextField();

  private JLabel _strLabel = new JLabel(" Str:");

  private JLabel _seqLabel = new JLabel(" Seq:");

  private JButton _createButton = new JButton("Create");

  private JButton _updateButton = new JButton("Update");

  private JButton _deleteButton = new JButton("Delete");

  private JButton _duplicateButton = new JButton("Snapshot");

  protected JPanel _listPanel = new JPanel();

  private ReorderableJList _sideList = null;

  private static String errorOpt = "error";

  @SuppressWarnings("unused")
  private boolean _error;

  private Color _backgroundColor = Color.white;

  private static int _nextID = 1;

  @SuppressWarnings("unused")
  private int _algoCode;

  private BackupHolder _rnaList;

  /*
   * public AppVarnaBinding() { //super("VARNA in Jalview");
   * //this.set_seq("ATGC"); //this.set_str(".()."); //RNAPanelDemoInit();
   * 
   * //initVarna("ATGCATGATATATATATAT","....((((...))))....");
   * initVarna(this.DEFAULT_SEQUENCE,this.DEFAULT_STRUCTURE1); }
   */

  public AppVarnaBinding(String seq, String struc)
  {
    // super("VARNA in Jalview");
    initVarna(seq, struc);
  }

  public AppVarnaBinding(ArrayList<RNA> rnaList)
  {
    // super("VARNA in Jalview");
    initVarnaEdit(rnaList);
  }

  private void initVarna(String seq, String str)
  {
    DefaultListModel dlm = new DefaultListModel();

    DefaultListSelectionModel m = new DefaultListSelectionModel();
    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    m.setLeadAnchorNotificationEnabled(false);

    _sideList = new ReorderableJList();
    _sideList.setModel(dlm);
    _sideList.addMouseListener(this);
    _sideList.setSelectionModel(m);
    _sideList.setPreferredSize(new Dimension(100, 0));
    _sideList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent arg0)
      {
        if (!_sideList.isSelectionEmpty() && !arg0.getValueIsAdjusting())
        {
          FullBackup sel = (FullBackup) _sideList.getSelectedValue();
          Mapping map = Mapping.DefaultOutermostMapping(vp.getRNA()
                  .getSize(), sel.rna.getSize());
          vp.showRNAInterpolated(sel.rna, sel.config, map);
          _seq.setText(sel.rna.getSeq());
          _str.setText(sel.rna.getStructDBN());
        }
      }
    });

    _rnaList = new BackupHolder(dlm, _sideList);
    RNA _RNA1 = new RNA("User defined 1");

    try
    {
      vp = new VARNAPanel("0", ".");
      _RNA1.setRNA(seq, str);
      _RNA1.drawRNARadiate(vp.getConfig());
    } catch (ExceptionNonEqualLength e)
    {
      vp.errorDialog(e);
    } catch (ExceptionUnmatchedClosingParentheses e2)
    {
      e2.printStackTrace();
    } catch (ExceptionFileFormatOrSyntax e3)
    {
      e3.printStackTrace();
    }
    vp.setPreferredSize(new Dimension(400, 400));
    _rnaList.add(vp.getConfig().clone(), _RNA1, generateDefaultName(), true);

    // TODO setBackground(_backgroundColor);
    vp.setBackground(_backgroundColor);

    // TODO getContentPane().setLayout(new BorderLayout());
    // TODO getContentPane().add(vp, BorderLayout.CENTER);

    // setVisible(true);
    vp.addVARNAListener(this);
  }

  private void initVarnaEdit(ArrayList<RNA> rnaInList)
  {
    DefaultListModel dlm = new DefaultListModel();

    int marginTools = 40;

    DefaultListSelectionModel m = new DefaultListSelectionModel();
    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    m.setLeadAnchorNotificationEnabled(false);

    _sideList = new ReorderableJList();
    _sideList.setModel(dlm);
    _sideList.addMouseListener(this);
    _sideList.setSelectionModel(m);
    _sideList.setPreferredSize(new Dimension(100, 0));
    _sideList.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent arg0)
      {
        // System.out.println(arg0);
        if (!_sideList.isSelectionEmpty() && !arg0.getValueIsAdjusting())
        {
          FullBackup sel = (FullBackup) _sideList.getSelectedValue();
          Mapping map = Mapping.DefaultOutermostMapping(vp.getRNA()
                  .getSize(), sel.rna.getSize());
          vp.showRNAInterpolated(sel.rna, sel.config, map);
          // _seq.setText(sel.rna.getSeq());
          _str.setText(sel.rna.getStructDBN());
        }
      }
    });
    _rnaList = new BackupHolder(dlm, _sideList);

    try
    {
      vp = new VARNAPanel("0", ".");
      for (int i = 0; i < rnaInList.size(); i++)
      {
        rnaInList.get(i).drawRNARadiate(vp.getConfig());
      }
    } catch (ExceptionNonEqualLength e)
    {
      vp.errorDialog(e);
    }
    vp.setPreferredSize(new Dimension(400, 400));
    for (int i = 0; i < rnaInList.size(); i++)
    {
      if (i < rnaInList.size() - 1)
      {
        _rnaList.add(vp.getConfig().clone(), rnaInList.get(i), rnaInList
                .get(i).getName());
      }
      else
      {
        _rnaList.add(vp.getConfig().clone(), rnaInList.get(i), rnaInList
                .get(i).getName(), true);
      }
    }

    /*
     * _rnaList.add(vp.getConfig().clone(),_RNA2,generateDefaultName());
     * _rnaList.add(vp.getConfig().clone(),_RNA1,generateDefaultName(),true);
     */

    JScrollPane listScroller = new JScrollPane(_sideList);
    listScroller.setPreferredSize(new Dimension(150, 0));

    vp.setBackground(_backgroundColor);

    Font textFieldsFont = Font.decode("MonoSpaced-PLAIN-12");

    // _seqLabel.setHorizontalTextPosition(JLabel.LEFT);
    // _seqLabel.setPreferredSize(new Dimension(marginTools, 15));
    _seq.setFont(textFieldsFont);
    _seq.setText(rnaInList.get(0).getSeq());

    _updateButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        FullBackup sel = (FullBackup) _sideList.getSelectedValue();
        sel.rna.setSequence("A");
      }
    });

    // _seqPanel.setLayout(new BorderLayout());
    // _seqPanel.add(_seqLabel, BorderLayout.WEST);
    // _seqPanel.add(_seq, BorderLayout.CENTER);

    _strLabel.setPreferredSize(new Dimension(marginTools, 15));
    _strLabel.setHorizontalTextPosition(JLabel.LEFT);
    _str.setFont(textFieldsFont);
    _strPanel.setLayout(new BorderLayout());
    _strPanel.add(_strLabel, BorderLayout.WEST);
    _strPanel.add(_str, BorderLayout.CENTER);

    _input.setLayout(new GridLayout(1, 0));
    // _input.add(_seqPanel);
    _input.add(_strPanel);

    JPanel goPanel = new JPanel();
    goPanel.setLayout(new BorderLayout());

    _tools.setLayout(new BorderLayout());
    _tools.add(_input, BorderLayout.CENTER);
    // _tools.add(_info, BorderLayout.SOUTH);
    _tools.add(goPanel, BorderLayout.EAST);

    /*
     * _deleteButton.addActionListener(new ActionListener() { public void
     * actionPerformed(ActionEvent e) { _rnaList.removeSelected(); } });
     * _duplicateButton.addActionListener(new ActionListener() { public void
     * actionPerformed(ActionEvent e) {
     * _rnaList.add((VARNAConfig)vp.getConfig().
     * clone(),vp.getRNA().clone(),vp.getRNA
     * ().getName()+"-"+DateFormat.getTimeInstance(DateFormat.LONG).format(new
     * Date()),true); }});
     */
    goPanel.add(_updateButton, BorderLayout.CENTER);

    JPanel ops = new JPanel();
    ops.setLayout(new GridLayout(1, 2));
    ops.add(_deleteButton);
    ops.add(_duplicateButton);

    JLabel j = new JLabel("Structures Manager", JLabel.CENTER);
    _listPanel.setLayout(new BorderLayout());

    // _listPanel.add(ops, BorderLayout.SOUTH);
    _listPanel.add(j, BorderLayout.NORTH);
    _listPanel.add(listScroller, BorderLayout.CENTER);

    // JSplitPane split = new
    // JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,_listPanel,vp);
    /**
     * TODO getContentPane().setLayout(new BorderLayout());
     * getContentPane().add(split, BorderLayout.CENTER);
     * getContentPane().add(_tools, BorderLayout.NORTH);
     */

    // TODO setVisible(true);
    DropTarget dt = new DropTarget(vp, this);

    vp.addVARNAListener(this);
  }

  public JPanel getTools()
  {
    return _tools;
  }

  public JPanel getListPanel()
  {
    return _listPanel;
  }

  /**
   * TODO: Is it effective to transfer the whole RNA?
   * 
   * @return Currently selected RNA
   */
  public RNA getSelectedRNA()
  {
    return _rnaList.getElementAt(_sideList.getSelectedIndex()).rna;
  }

  /**
   * Substitute currently selected RNA with the edited one
   * 
   * @param rnaEdit
   */
  public void updateSelectedRNA(RNA rnaEdit)
  {
    vp.repaint();
    vp.showRNA(rnaEdit);
  }

  /*
   * private void RNAPanelDemoInit() { DefaultListModel dlm = new
   * DefaultListModel();
   * 
   * 
   * int marginTools = 40;
   * 
   * DefaultListSelectionModel m = new DefaultListSelectionModel();
   * m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   * m.setLeadAnchorNotificationEnabled(false);
   * 
   * 
   * _sideList = new ReorderableJList(); _sideList.setModel(dlm);
   * _sideList.addMouseListener(this); _sideList.setSelectionModel(m);
   * _sideList.setPreferredSize(new Dimension(100, 0));
   * _sideList.addListSelectionListener( new ListSelectionListener(){ public
   * void valueChanged(ListSelectionEvent arg0) { //System.out.println(arg0); if
   * (!_sideList.isSelectionEmpty() && !arg0.getValueIsAdjusting()) { FullBackup
   * sel = (FullBackup) _sideList.getSelectedValue(); Mapping map =
   * Mapping.DefaultOutermostMapping(vp.getRNA().getSize(), sel.rna.getSize());
   * vp.showRNAInterpolated(sel.rna,sel.config,map);
   * _seq.setText(sel.rna.getSeq()); _str.setText(sel.rna.getStructDBN()); } }
   * });
   * 
   * _rnaList = new BackupHolder(dlm,_sideList); RNA _RNA1 = new
   * RNA("User defined 1"); RNA _RNA2 = new RNA("User defined 2"); try { vp =
   * new VARNAPanel("0","."); _RNA1.setRNA(DEFAULT_SEQUENCE,
   * DEFAULT_STRUCTURE1); _RNA1.drawRNARadiate(vp.getConfig());
   * _RNA2.setRNA(DEFAULT_SEQUENCE, DEFAULT_STRUCTURE2);
   * _RNA2.drawRNARadiate(vp.getConfig()); } catch (ExceptionNonEqualLength e) {
   * vp.errorDialog(e); } catch (ExceptionUnmatchedClosingParentheses e2) {
   * e2.printStackTrace(); } catch (ExceptionFileFormatOrSyntax e3) {
   * e3.printStackTrace(); } vp.setPreferredSize(new Dimension(400, 400));
   * _rnaList.add(vp.getConfig().clone(),_RNA2,generateDefaultName());
   * _rnaList.add(vp.getConfig().clone(),_RNA1,generateDefaultName(),true);
   * 
   * JScrollPane listScroller = new JScrollPane(_sideList);
   * listScroller.setPreferredSize(new Dimension(150, 0));
   * 
   * setBackground(_backgroundColor); vp.setBackground(_backgroundColor);
   * 
   * 
   * Font textFieldsFont = Font.decode("MonoSpaced-PLAIN-12");
   * 
   * _seqLabel.setHorizontalTextPosition(JLabel.LEFT);
   * _seqLabel.setPreferredSize(new Dimension(marginTools, 15));
   * _seq.setFont(textFieldsFont); _seq.setText(DEFAULT_SEQUENCE);
   * 
   * _createButton.addActionListener(new ActionListener() { public void
   * actionPerformed(ActionEvent e) { try { RNA nRNA = new
   * RNA(generateDefaultName()); nRNA.setRNA(_seq.getText(), _str.getText());
   * nRNA.drawRNARadiate(vp.getConfig()); _rnaList.add(new
   * VARNAConfig(),nRNA,true); } catch (ExceptionUnmatchedClosingParentheses e1)
   * { JOptionPane.showMessageDialog(vp, e1.getMessage(),"Error",
   * JOptionPane.ERROR_MESSAGE); } catch (ExceptionFileFormatOrSyntax e1) {
   * JOptionPane.showMessageDialog(vp, e1.getMessage(),"Error",
   * JOptionPane.ERROR_MESSAGE); } } });
   * 
   * 
   * _seqPanel.setLayout(new BorderLayout()); _seqPanel.add(_seqLabel,
   * BorderLayout.WEST); _seqPanel.add(_seq, BorderLayout.CENTER);
   * 
   * _strLabel.setPreferredSize(new Dimension(marginTools, 15));
   * _strLabel.setHorizontalTextPosition(JLabel.LEFT);
   * _str.setFont(textFieldsFont); _strPanel.setLayout(new BorderLayout());
   * _strPanel.add(_strLabel, BorderLayout.WEST); _strPanel.add(_str,
   * BorderLayout.CENTER);
   * 
   * _input.setLayout(new GridLayout(2, 0)); _input.add(_seqPanel);
   * _input.add(_strPanel);
   * 
   * JPanel goPanel = new JPanel(); goPanel.setLayout(new BorderLayout());
   * 
   * _tools.setLayout(new BorderLayout()); _tools.add(_input,
   * BorderLayout.CENTER); _tools.add(_info, BorderLayout.SOUTH);
   * _tools.add(goPanel, BorderLayout.EAST);
   * 
   * _deleteButton.addActionListener(new ActionListener() { public void
   * actionPerformed(ActionEvent e) { _rnaList.removeSelected(); } });
   * _duplicateButton.addActionListener(new ActionListener() { public void
   * actionPerformed(ActionEvent e) {
   * _rnaList.add((VARNAConfig)vp.getConfig().clone
   * (),vp.getRNA().clone(),vp.getRNA
   * ().getName()+"-"+DateFormat.getTimeInstance(DateFormat.LONG).format(new
   * Date()),true); }});
   * 
   * JPanel ops = new JPanel(); ops.setLayout(new GridLayout(1,2));
   * ops.add(_deleteButton); ops.add(_duplicateButton);
   * 
   * JLabel j = new JLabel("Structures Manager",JLabel.CENTER);
   * _listPanel.setLayout(new BorderLayout());
   * 
   * _listPanel.add(ops,BorderLayout.SOUTH);
   * _listPanel.add(j,BorderLayout.NORTH);
   * _listPanel.add(listScroller,BorderLayout.CENTER);
   * 
   * goPanel.add(_createButton, BorderLayout.CENTER);
   * 
   * JSplitPane split = new
   * JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,_listPanel,vp);
   * getContentPane().setLayout(new BorderLayout()); getContentPane().add(split,
   * BorderLayout.CENTER); getContentPane().add(_tools, BorderLayout.NORTH);
   * 
   * setVisible(true); DropTarget dt = new DropTarget(vp, this);
   * 
   * vp.addVARNAListener(this); }
   */
  public static String generateDefaultName()
  {
    return "User file #" + _nextID++;
  }

  public RNA getRNA()
  {
    return (RNA) _sideList.getSelectedValue();
  }

  public String[][] getParameterInfo()
  {
    String[][] info =
    {
        // Parameter Name Kind of Value Description,
        { "sequenceDBN", "String", "A raw RNA sequence" },
        { "structureDBN", "String",
            "An RNA structure in dot bracket notation (DBN)" },
        { errorOpt, "boolean", "To show errors" }, };
    return info;
  }

  public void init()
  {
    vp.setBackground(_backgroundColor);
    _error = true;
  }

  @SuppressWarnings("unused")
  private Color getSafeColor(String col, Color def)
  {
    Color result;
    try
    {
      result = Color.decode(col);
    } catch (Exception e)
    {
      try
      {
        result = Color.getColor(col, def);
      } catch (Exception e2)
      {
        return def;
      }
    }
    return result;
  }

  public VARNAPanel get_varnaPanel()
  {
    return vp;
  }

  public void set_varnaPanel(VARNAPanel surface)
  {
    vp = surface;
  }

  public String get_seq()
  {
    return _seq.getText();
  }

  public void set_seq(String _seq)
  {
    this._seq.setText(_seq);
  }

  public String get_str()
  {
    return _str.getText();
  }

  public void set_str(String _str)
  {
    this._str.setText(_str);
  }

  public JLabel get_info()
  {
    return _info;
  }

  public void set_info(JLabel _info)
  {
    this._info = _info;
  }

  /*
   * public static void main(String[] args) { AppVarnaBinding d = new
   * AppVarnaBinding(); d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   * d.pack(); d.setVisible(true); }
   */

  public void dragEnter(DropTargetDragEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void dragExit(DropTargetEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void dragOver(DropTargetDragEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void drop(DropTargetDropEvent dtde)
  {
    try
    {
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++)
      {
        if (flavors[i].isFlavorJavaFileListType())
        {
          dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
          Object ob = tr.getTransferData(flavors[i]);
          if (ob instanceof List)
          {
            List list = (List) ob;
            for (int j = 0; j < list.size(); j++)
            {
              Object o = list.get(j);

              if (dtde.getSource() instanceof DropTarget)
              {
                DropTarget dt = (DropTarget) dtde.getSource();
                Component c = dt.getComponent();
                if (c instanceof VARNAPanel)
                {
                  String path = o.toString();
                  VARNAPanel vp = (VARNAPanel) c;
                  try
                  {
                    FullBackup bck = VARNAPanel.importSession(path);
                    _rnaList.add(bck.config, bck.rna, bck.name, true);
                  } catch (ExceptionLoadingFailed e3)
                  {
                    int mn = 1;
                    Collection<RNA> mdls = fr.orsay.lri.varna.factories.RNAFactory
                            .loadSecStr(path);
                    for (RNA r : mdls)
                    {
                      r.drawRNA(vp.getConfig());
                      String name = r.getName();
                      if (name.equals(""))
                      {
                        name = path.substring(path
                                .lastIndexOf(File.separatorChar) + 1);
                      }
                      if (mdls.size() > 1)
                      {
                        name += " (Model " + mn++ + ")";
                      }
                      _rnaList.add(vp.getConfig().clone(), r, name, true);
                    }
                  }
                }
              }
            }
          }
          // If we made it this far, everything worked.
          dtde.dropComplete(true);
          return;
        }
      }
      // Hmm, the user must not have dropped a file list
      dtde.rejectDrop();
    } catch (Exception e)
    {
      e.printStackTrace();
      dtde.rejectDrop();
    }

  }

  public void dropActionChanged(DropTargetDragEvent arg0)
  {
  }

  private class BackupHolder
  {
    private DefaultListModel _rnaList;

    private ArrayList<RNA> _rnas = new ArrayList<RNA>();

    JList _l;

    public BackupHolder(DefaultListModel rnaList, JList l)
    {
      _rnaList = rnaList;
      _l = l;
    }

    public void add(VARNAConfig c, RNA r)
    {
      add(c, r, r.getName(), false);
    }

    public void add(VARNAConfig c, RNA r, boolean select)
    {
      add(c, r, r.getName(), select);
    }

    public void add(VARNAConfig c, RNA r, String name)
    {
      add(c, r, name, false);
    }

    public void add(VARNAConfig c, RNA r, String name, boolean select)
    {
      if (select)
      {
        _l.removeSelectionInterval(0, _rnaList.size());
      }
      if (name.equals(""))
      {
        name = generateDefaultName();
      }
      FullBackup bck = new FullBackup(c, r, name);
      _rnas.add(0, r);
      _rnaList.add(0, bck);
      if (select)
      {
        _l.setSelectedIndex(0);
      }
    }

    public void remove(int i)
    {
      _rnas.remove(i);
      _rnaList.remove(i);

    }

    public DefaultListModel getModel()
    {
      return _rnaList;
    }

    public boolean contains(RNA r)
    {
      return _rnas.contains(r);
    }

    /*
     * public int getSize() { return _rnaList.getSize(); }
     */
    public FullBackup getElementAt(int i)
    {
      return (FullBackup) _rnaList.getElementAt(i);
    }

    public void removeSelected()
    {
      int i = _l.getSelectedIndex();
      if (i != -1)
      {
        if (_rnaList.getSize() == 1)
        {
          RNA r = new RNA();
          try
          {
            r.setRNA(" ", ".");
          } catch (ExceptionUnmatchedClosingParentheses e1)
          {
          } catch (ExceptionFileFormatOrSyntax e1)
          {
          }
          vp.showRNA(r);
          vp.repaint();
        }
        else
        {
          int newi = i + 1;
          if (newi == _rnaList.getSize())
          {
            newi = _rnaList.getSize() - 2;
          }
          FullBackup bck = (FullBackup) _rnaList.getElementAt(newi);
          _l.setSelectedValue(bck, true);
        }
        _rnaList.remove(i);
      }

    }
  }

  public void onLayoutChanged()
  {
    // TODO Auto-generated method stub

  }

  public void onUINewStructure(VARNAConfig v, RNA r)
  {
    _rnaList.add(v, r, "", true);
  }

  public void onWarningEmitted(String s)
  {
    // TODO Auto-generated method stub

  }

  public void mouseClicked(MouseEvent e)
  {
    if (e.getClickCount() == 2)
    {
      int index = _sideList.locationToIndex(e.getPoint());
      ListModel dlm = _sideList.getModel();
      FullBackup item = (FullBackup) dlm.getElementAt(index);
      ;
      _sideList.ensureIndexIsVisible(index);
      /*
       * TODO Object newName = JOptionPane.showInputDialog( this,
       * "Specify a new name for this RNA", "Rename RNA",
       * JOptionPane.QUESTION_MESSAGE, (Icon)null, null, item.toString()); if
       * (newName!=null) { item.name = newName.toString();
       * this._sideList.repaint(); }
       */
    }
  }

  public void mouseEntered(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mouseExited(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mousePressed(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  public void mouseReleased(MouseEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public Color getColour(int atomIndex, int pdbResNum, String chain,
          String pdbId)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String[] getPdbFile()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void highlightAtom(int atomIndex, int pdbResNum, String chain,
          String pdbId)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseOverStructure(int atomIndex, String strInfo)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void releaseReferences(Object svl)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateColours(Object source)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void componentHidden(ComponentEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void componentMoved(ComponentEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void componentResized(ComponentEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void componentShown(ComponentEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void onStructureRedrawn()
  {
    // TODO Auto-generated method stub

  }
}

/*
 * public static void main(String[] args) { JTextField str = new
 * JTextField("ATGC");
 * 
 * AppVarnaBinding vab = new AppVarnaBinding(); vab.varnagui.set_seq(str);
 * vab.varnagui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * vab.varnagui.pack(); vab.varnagui.setVisible(true); } }
 */
