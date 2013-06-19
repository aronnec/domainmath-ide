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

import jalview.gui.OptsAndParamsPage.OptionBox;
import jalview.gui.OptsAndParamsPage.ParamBox;
import jalview.ws.jws2.JabaParamStore;
import jalview.ws.jws2.JabaPreset;
import jalview.ws.jws2.Jws2Discoverer;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.ArgumentI;
import jalview.ws.params.OptionI;
import jalview.ws.params.ParamDatastoreI;
import jalview.ws.params.ParameterI;
import jalview.ws.params.WsParamSetI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import compbio.metadata.Argument;
import compbio.metadata.Option;
import compbio.metadata.Parameter;
import compbio.metadata.Preset;
import compbio.metadata.PresetManager;
import compbio.metadata.RunnerConfig;

/**
 * job parameter editing/browsing dialog box. User can browse existing settings
 * (user + presets + Defaults), and any changes to parameters creates a modified
 * user parameter set. LOGIC: If the parameter set is modified, and its name is
 * a valid, non-existant user parameter set, then a save button is shown. If the
 * parameter set is modified and its name is a valid, extant user parameter set,
 * then an update button is shown. If user parameter set's name is edited, and
 * old name exists as a writable user parameter set, then rename button is
 * shown. If current parameter set is associated with a user defined parameter
 * set, then : if set is modifed, a 'revert' button is shown. if set is not
 * modified, a 'delete' button is shown.
 * 
 * @author JimP
 * 
 */
public class WsJobParameters extends JPanel implements ItemListener,
        ActionListener, DocumentListener, OptsParametersContainerI
{
  URL linkImageURL = getClass().getResource("/images/link.gif");

  private static final String SVC_DEF = "Defaults"; // this is the null
                                                    // parameter set as shown to
                                                    // user

  /**
   * manager for options and parameters.
   */
  OptsAndParamsPage opanp = new OptsAndParamsPage(this);

  /**
   * panel containing job options
   */
  JPanel jobOptions = new JPanel();

  /**
   * panel containing job parameters
   */
  JPanel paramList = new JPanel();

  JPanel SetNamePanel = new JPanel();

  JPanel setDetails = new JPanel();

  JSplitPane settingsPanel = new JSplitPane();

  JPanel jobPanel = new JPanel();

  JScrollPane jobOptionsPane = new JScrollPane();

  JButton createpref = new JButton();

  JButton deletepref = new JButton();

  JButton revertpref = new JButton();

  JButton updatepref = new JButton();

  JButton startjob = new JButton();

  JButton canceljob = new JButton();

  JComboBox setName = new JComboBox();

  JTextArea setDescr = new JTextArea();

  JScrollPane paramPane = new JScrollPane();

  // ScrollablePanel optsAndparams = new ScrollablePanel();
  JPanel optsAndparams = new JPanel();

  RunnerConfig serviceOptions;

  ParamDatastoreI paramStore;

  private int MAX_OPTWIDTH = 200;

  WsJobParameters(Jws2Instance service)
  {
    this(service, null);
  }

  public WsJobParameters(Jws2Instance service, WsParamSetI preset)
  {
    this(null, service, preset, null);
  }

  /**
   * 
   * @param desktop
   *          - if null, create new JFrame outside of desktop
   * @param service
   * @param preset
   */
  public WsJobParameters(JFrame parent, Jws2Instance service,
          WsParamSetI preset, List<Argument> jobArgset)
  {
    this(parent, null, service, preset, jobArgset);
  }

  /**
   * 
   * @param parent
   * @param paramStorei
   * @param service
   * @param preset
   * @param jobArgset
   */
  public WsJobParameters(JFrame parent, ParamDatastoreI paramStorei,
          Jws2Instance service, WsParamSetI preset, List<Argument> jobArgset)
  {
    super();
    jbInit();
    this.paramStore = paramStorei;
    if (paramStore == null)
    {
      paramStore = service.getParamStore();
    }
    this.service = service;
    // argSetModified(false);
    // populate parameter table
    initForService(service, preset, jobArgset);
    // display in new JFrame attached to parent.
    validate();
  }

  int response = -1;

  JDialog frame = null;

  /**
   * shows a modal dialog containing the parameters.
   * 
   * @return
   */
  public boolean showRunDialog()
  {

    frame = new JDialog(Desktop.instance, true);

    frame.setTitle("Edit parameters for " + service.getActionText());
    Rectangle deskr = Desktop.instance.getBounds();
    Dimension pref = this.getPreferredSize();
    frame.setBounds(new Rectangle(
            (int) (deskr.getCenterX() - pref.width / 2), (int) (deskr
                    .getCenterY() - pref.height / 2), pref.width,
            pref.height));
    frame.setContentPane(this);

    // should perhaps recover defaults from user prefs.

    frame.validate();
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        // jobPanel.setDividerLocation(0.25);

      }
    });
    frame.setVisible(true);

    if (response > 0)
    {
      return true;
    }
    return false;
  }

  private void jbInit()
  {
    this.addHierarchyBoundsListener(new HierarchyBoundsListener()
    {

      @Override
      public void ancestorResized(HierarchyEvent arg0)
      {
        refreshParamLayout();
      }

      @Override
      public void ancestorMoved(HierarchyEvent arg0)
      {
        // TODO Auto-generated method stub

      }
    });
    updatepref = JvSwingUtils.makeButton("Update",
            "Update this existing user parameter set.",
            new ActionListener()
            {

              public void actionPerformed(ActionEvent e)
              {
                update_actionPerformed(e);
              }
            });
    deletepref = JvSwingUtils.makeButton("Delete",
            "Delete the currently selected user parameter set.",
            new ActionListener()
            {

              public void actionPerformed(ActionEvent e)
              {
                delete_actionPerformed(e);
              }
            });
    createpref = JvSwingUtils.makeButton("Create",
            "Create a new parameter set with the current settings.",
            new ActionListener()
            {

              public void actionPerformed(ActionEvent e)
              {
                create_actionPerformed(e);
              }
            });
    revertpref = JvSwingUtils.makeButton("Revert",
            "Undo all changes to the current parameter set",
            new ActionListener()
            {

              public void actionPerformed(ActionEvent e)
              {
                revert_actionPerformed(e);
              }
            });
    startjob = JvSwingUtils.makeButton("Start Job",
            "Start Job with current settings.", new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                startjob_actionPerformed(e);
              }
            });
    canceljob = JvSwingUtils.makeButton("Cancel Job",
            "Close this dialog and cancel job.", new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                canceljob_actionPerformed(e);
              }
            });

    setDetails.setBorder(new TitledBorder("Details"));
    setDetails.setLayout(new BorderLayout());
    setDescr.setColumns(40);
    setDescr.setWrapStyleWord(true);
    setDescr.setLineWrap(true);
    setDescr.setBackground(getBackground());
    setDescr.setEditable(true);
    setDescr.getDocument().addDocumentListener(this);
    setDescr.setToolTipText("Click to edit the notes for this parameter set.");
    JScrollPane setDescrView = new JScrollPane();
    // setDescrView.setPreferredSize(new Dimension(350, 200));
    setDescrView.getViewport().setView(setDescr);
    setName.setEditable(true);
    setName.addItemListener(this);
    setName.getEditor().addActionListener(this);
    JPanel setNameInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    GridBagLayout gbl = new GridBagLayout();
    SetNamePanel.setLayout(gbl);

    JLabel setNameLabel = new JLabel("Current parameter set name :");
    setNameLabel.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));

    setNameInfo.add(setNameLabel);
    setNameInfo.add(setName);

    // initial button visibility
    updatepref.setVisible(false);
    deletepref.setVisible(false);
    revertpref.setVisible(false);
    createpref.setVisible(false);
    JPanel setsavebuts = new JPanel();
    setsavebuts.setLayout(new FlowLayout(FlowLayout.LEFT)); // GridLayout(1,2));
    ((FlowLayout) setsavebuts.getLayout()).setHgap(10);
    ((FlowLayout) setsavebuts.getLayout()).setVgap(0);
    JPanel spacer = new JPanel();
    spacer.setPreferredSize(new Dimension(2, 30));
    setsavebuts.add(spacer);
    setsavebuts.add(deletepref);
    setsavebuts.add(revertpref);
    setsavebuts.add(createpref);
    setsavebuts.add(updatepref);
    // setsavebuts.setSize(new Dimension(150, 30));
    JPanel buttonArea = new JPanel(new GridLayout(1, 1));
    buttonArea.add(setsavebuts);
    SetNamePanel.add(setNameInfo);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridheight = 2;
    gbl.setConstraints(setNameInfo, gbc);
    SetNamePanel.add(buttonArea);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridheight = 1;
    gbl.setConstraints(buttonArea, gbc);
    setDetails.add(setDescrView, BorderLayout.CENTER);

    // paramPane.setPreferredSize(new Dimension(360, 400));
    // paramPane.setPreferredSize(null);
    jobOptions.setBorder(new TitledBorder("Options"));
    jobOptions.setOpaque(true);
    paramList.setBorder(new TitledBorder("Parameters"));
    paramList.setOpaque(true);
    JPanel bjo = new JPanel(new BorderLayout()), bjp = new JPanel(
            new BorderLayout());
    bjo.add(jobOptions, BorderLayout.CENTER);
    bjp.add(paramList, BorderLayout.CENTER);
    bjp.setOpaque(true);
    bjo.setOpaque(true);
    // optsAndparams.setScrollableWidth(ScrollableSizeHint.FIT);
    // optsAndparams.setScrollableHeight(ScrollableSizeHint.NONE);
    // optsAndparams.setLayout(new BorderLayout());
    optsAndparams.setLayout(new BorderLayout());
    optsAndparams.add(jobOptions, BorderLayout.NORTH);
    optsAndparams.add(paramList, BorderLayout.CENTER);
    JPanel jp = new JPanel(new BorderLayout());
    jp.add(optsAndparams, BorderLayout.CENTER);
    paramPane.getViewport().setView(jp);
    paramPane.setBorder(null);
    setLayout(new BorderLayout());
    jobPanel.setPreferredSize(null);
    jobPanel.setLayout(new BorderLayout());
    jobPanel.add(setDetails, BorderLayout.NORTH);
    jobPanel.add(paramPane, BorderLayout.CENTER);
    // jobPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);

    add(SetNamePanel, BorderLayout.NORTH);
    add(jobPanel, BorderLayout.CENTER);

    JPanel dialogpanel = new JPanel();
    dialogpanel.add(startjob);
    dialogpanel.add(canceljob);
    add(dialogpanel, BorderLayout.SOUTH);
    validate();
  }

  protected void revert_actionPerformed(ActionEvent e)
  {
    reInitDialog(lastParmSet);

  }

  protected void update_actionPerformed(ActionEvent e)
  {
    if (isUserPreset)
    {
      String curname = ((String) setName.getSelectedItem()).trim();
      _updatePreset(lastParmSet, curname);
      lastParmSet = curname;
      isUserPreset = true;
      initArgSetModified();
      syncSetNamesWithStore();
    }
  }

  private void _deleteUserPreset(String lastParmSet2)
  {
    paramStore.deletePreset(lastParmSet2);
  }

  protected void delete_actionPerformed(ActionEvent e)
  {
    if (isUserPreset)
    {
      // delete current preset's saved entry
      _deleteUserPreset(lastParmSet);
    }
    reInitDialog(null); // service default
  }

  protected void create_actionPerformed(ActionEvent e)
  {
    String curname = ((String) setName.getSelectedItem()).trim();
    if (curname.length() > 0)
    {
      _storeCurrentPreset(curname);
      lastParmSet = curname;
      isUserPreset = true;
      initArgSetModified();
    }
    else
    {
      // TODO: show warning
      System.err.println("Invalid name. Not saved.");
    }
  }

  protected void canceljob_actionPerformed(ActionEvent e)
  {
    response = 0;
    if (frame != null)
    {
      frame.setVisible(false);
    }
  }

  protected void startjob_actionPerformed(ActionEvent e)
  {
    response = 1;
    if (frame != null)
    {
      frame.setVisible(false);
    }
  }

  Jws2Instance service;

  /**
   * list of service presets in the gui
   */
  Hashtable servicePresets = null;

  /**
   * set if dialog is being set - so handlers will avoid spurious events
   */
  boolean settingDialog = false;

  void initForService(Jws2Instance service, WsParamSetI jabap,
          List<Argument> jabajobArgset)
  {
    WsParamSetI p = null;
    List<ArgumentI> jobArgset = null;
    settingDialog = true;
    { // instantiate the abstract proxy for Jaba objects
      jobArgset = jabajobArgset == null ? null : JabaParamStore
              .getJwsArgsfromJaba(jabajobArgset);
      p = jabap; // (jabap != null) ? paramStore.getPreset(jabap.getName()) :
                 // null;
    }

    Hashtable exnames = new Hashtable();
    for (int i = 0, iSize = setName.getItemCount(); i < iSize; i++)
    {
      exnames.put((String) setName.getItemAt(i), setName.getItemAt(i));
    }
    servicePresets = new Hashtable();
    // Add the default entry - if not present already.
    if (!exnames.contains(SVC_DEF))
    {
      setName.addItem(SVC_DEF);
      exnames.put(SVC_DEF, SVC_DEF);
      servicePresets.put(SVC_DEF, SVC_DEF);
    }
    String curname = (p == null ? "" : p.getName());
    for (WsParamSetI pr : paramStore.getPresets())
    {
      if (!pr.isModifiable())
      {
        servicePresets.put(pr.getName(), "preset");
      }
      else
      {
      }
      if (!exnames.contains(pr.getName()))
      {
        setName.addItem(pr.getName());
      }
    }
    // TODO: if initial jobArgset matches a given user setting or preset then
    // should recover setting accordingly
    // updateTable(p, jobArgset);
    if (p != null)
    {
      reInitDialog(p.getName());
      initArgSetModified();
    }
    else
    {
      if (jobArgset != null && jobArgset.size() > 0)
      {
        curSetName = "Supplied Settings";
        isUserPreset = false;
        updateTable(p, jobArgset);
        setName.setSelectedItem(curSetName);
        updateButtonDisplay();
      }
      else
      {
        curSetName = null;
        reInitDialog(null);
      }
    }
    settingDialog = false;

  }

  @SuppressWarnings("unchecked")
  private void updateTable(WsParamSetI p, List<ArgumentI> jobArgset)
  {
    // populate table from default parameter set.
    List<ArgumentI> args = paramStore.getServiceParameters();

    // split to params and required arguments
    {
      int cw = 0;
      for (ArgumentI myarg : args)
      {
        // Ideally, Argument would implement isRequired !
        if (myarg instanceof ParameterI)
        {
          ParameterI parm = (ParameterI) myarg;
          opanp.addParameter(parm).validate();
        }
        else
        {
          if (myarg instanceof OptionI)
          {
            OptionI opt = (OptionI) myarg;
            OptionBox ob = opanp.addOption(opt);
            ob.resetToDefault();
            if (MAX_OPTWIDTH < ob.getPreferredSize().width)
            {
              MAX_OPTWIDTH = ob.getPreferredSize().width;
            }
            ob.validate();
            cw += ob.getPreferredSize().width + 5;
          }
          else
          {
            System.err.println("Ignoring unknown service argument type "
                    + myarg.getClass().getName());
          }
        }
      }
      args = null; // no more args to process.
    }
    if (p != null)
    {
      isUserPreset = false;
      // initialise setname
      setName.setSelectedItem(lastSetName = p.getName());
      setDescr.setText(lastDescrText = p.getDescription());
      // TODO - URL link
      try
      {
        args = p.getArguments();
      } catch (Exception e)
      {
        e.printStackTrace();
      }
      // TODO: check if args should be unselected prior to resetting using the
      // preset
    }
    else
    {
      if (lastParmSet == null)
      {
        isUserPreset = false;
        // first call - so create a dummy name

        setName.setSelectedItem(lastSetName = SVC_DEF);
      }
    }

    if (jobArgset != null)
    {
      argSetModified(jobArgset, true);
      args = jobArgset;
    }
    // get setargs from current object
    if (args != null)
    {
      for (ArgumentI arg : args)
      {
        if (arg instanceof ParameterI)
        {
          opanp.setParameter((ParameterI) arg);
        }
        else
        {
          if (arg instanceof OptionI)
          {
            // System.out.println("Setting option "
            // + System.identityHashCode(arg) + ":" + arg.getName()
            // + " with " + arg.getDefaultValue());
            opanp.selectOption((OptionI) arg, arg.getValue());
          }
        }

      }
    }

    refreshParamLayout();
    revalidate();
  }

  private boolean isModified()
  {
    return modifiedElements.size() > 0;
  }

  private Hashtable modifiedElements = new Hashtable();

  /**
   * reset gui and modification state settings
   */
  private void initArgSetModified()
  {
    curSetName = null;
    modifiedElements.clear();
    updateButtonDisplay();
  }

  private void updateButtonDisplay()
  {
    boolean _update = false, _create = false, _delete = false, _revert = false;
    if (modifiedElements.size() > 0)
    {
      // set modified
      _revert = true;
      _update = isUserPreset; // can only update user presets
      if (!isUserPreset || modifiedElements.containsKey(setName))
      {
        // name modified - can create new preset
        _create = true;
      }
    }
    else
    {
      // set unmodified
    }
    // can still delete a user preset
    _delete = isUserPreset;

    createpref.setVisible(_create);
    updatepref.setVisible(_update);
    deletepref.setVisible(_delete);
    revertpref.setVisible(_revert);
    validate();
  }

  public void argSetModified(Object modifiedElement, boolean b)
  {
    if (settingDialog)
    {
      return;
    }
    if (!b)
    {
      modifiedElements.remove(modifiedElement);
    }
    else
    {
      if (b && modifiedElement == setName
              && modifiedElements.contains(modifiedElement))
      {
        // HACK! prevents iteration on makeSetNameValid
        b = false;
      }
      modifiedElements.put(modifiedElement, modifiedElement);
    }
    // set mod status based on presence of elements in table
    if (b && modifiedElements.size() > 0)
    {
      makeSetNameValid(!isUserPreset);
      SetNamePanel.revalidate();
    }
    updateButtonDisplay();
  }

  private boolean isServicePreset(String selectedItem)
  {
    return selectedItem.equals(SVC_DEF)
            || servicePresets.containsKey(selectedItem);
  }

  /**
   * check if the current set name is a valid set name for saving, if not, then
   * fix it.
   */
  private void makeSetNameValid(boolean newuserset)
  {
    boolean stn = settingDialog;
    boolean renamed = false;
    settingDialog = true;
    String nm = (curSetName != null ? curSetName : (String) setName
            .getSelectedItem());
    // check if the name is reserved - if it is, rename it.
    if (isServicePreset(nm))
    {
      nm = "User " + nm;
      renamed = true;
    }
    String tnm = nm;
    if (newuserset)
    {
      int i = 0;
      while (paramStore.getPreset(tnm) != null)
      {
        tnm = nm + " (" + (++i) + ")";
        renamed = true;
      }
      if (i > 0)
      {
        nm = tnm;
      }
    }

    boolean makeupdate = false;
    // sync the gui with the preset database
    for (int i = 0, iS = setName.getItemCount(); i < iS; i++)
    {
      String snm = (String) setName.getItemAt(i);
      if (snm.equals(nm))
      {
        makeupdate = true;
        // setName.setSelectedIndex(i);
      }
    }
    if (!makeupdate)
    {
      setName.addItem(curSetName = nm);
      setName.setSelectedItem(curSetName);
    }
    if (renamed)
    {
      settingDialog = false; // we need this name change to be registered.
      argSetModified(setName, renamed);
    }
    settingDialog = stn;
  }

  public void refreshParamLayout()
  {
    // optsAndparams.setPreferredSize(null);
    FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
    int sep = fl.getVgap();
    boolean fh = true;
    int os = 0, s = jobOptions.getBorder().getBorderInsets(jobOptions).bottom
            + jobOptions.getBorder().getBorderInsets(jobOptions).top
            + 2
            * sep;
    /**
     * final height for viewport
     */
    int finalh = s;
    int panewidth = paramPane.getViewport().getSize().width - 120
            - jobOptions.getBorder().getBorderInsets(jobOptions).left
            + jobOptions.getBorder().getBorderInsets(jobOptions).right;

    int w = 2
            * fl.getHgap()
            + (MAX_OPTWIDTH > OptsAndParamsPage.PARAM_WIDTH ? MAX_OPTWIDTH
                    : OptsAndParamsPage.PARAM_WIDTH);
    int hgap = fl.getHgap(), cw = hgap;

    if (opanp.getOptSet().size() > 0)
    {

      jobOptions.setLayout(new MigLayout("", "", ""));
      jobOptions.removeAll();

      for (OptionBox pbox : opanp.getOptSet().values())
      {
        pbox.validate();
        cw += pbox.getSize().width + hgap;
        if (cw + 120 > panewidth)
        {
          jobOptions.add(pbox, "wrap");
          // System.out.println("Wrap on "+pbox.option.getName());
          cw = hgap + pbox.getSize().width;
          fh = true;
        }
        else
        {
          jobOptions.add(pbox);
        }
        if (fh)
        {
          finalh += pbox.getSize().height + fl.getVgap();
          fh = false;
        }
      }
      jobOptions.revalidate();
    }
    else
    {
      jobOptions.setVisible(false);
    }

    // Now layout the parameters assuming they occupy one column - to calculate
    // total height of options+parameters
    fl = new FlowLayout(FlowLayout.LEFT);
    // helpful hint from
    // http://stackoverflow.com/questions/2743177/top-alignment-for-flowlayout
    fl.setAlignOnBaseline(true);
    if (opanp.getParamSet().size() > 0)
    {
      paramList.removeAll();
      paramList.setLayout(new MigLayout("", "", ""));
      fh = true;
      for (ParamBox pbox : opanp.getParamSet().values())
      {
        pbox.validate();
        cw += pbox.getSize().width + hgap;
        if (cw + 160 > panewidth)
        {
          paramList.add(pbox, "wrap");
          cw = pbox.getSize().width + hgap;
          fh = true;
        }
        else
        {
          paramList.add(pbox);
        }
        if (fh)
        {
          finalh += pbox.getSize().height + fl.getVgap();
          fh = false;
        }

      }
      /*
       * s = 2 * sep; for (ParamBox pbox : opanp.getParamSet().values()) {
       * pbox.validate(); s += sep +
       * pbox.getPreferredSize().height+pbox.getBorder
       * ().getBorderInsets(pbox).bottom; }
       * 
       * // paramList.setPreferredSize(new Dimension(w, s));
       * os+=s+2*sep+paramList
       * .getBorder().getBorderInsets(paramList).bottom+paramList
       * .getBorder().getBorderInsets(paramList).top;
       */
      paramList.revalidate();
    }
    else
    {
      paramList.setVisible(false);
    }
    // TODO: waste some time trying to eliminate any unnecessary .validate calls
    // here
    // System.out.println("Size will be : "+w+","+os);
    // optsAndparams.setPreferredSize(null);
    // paramPane.getViewport().setView(optsAndparams);
    paramPane.getViewport().setAutoscrolls(true);
    paramPane.revalidate();
    revalidate();
  }

  /**
   * testing method - grab a service and parameter set and show the window
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    jalview.ws.jws2.Jws2Discoverer disc = jalview.ws.jws2.Jws2Discoverer
            .getDiscoverer();
    int p = 0;
    if (args.length > 0)
    {
      Vector<String> services = new Vector<String>();
      services.addElement(args[p++]);
      Jws2Discoverer.setServiceUrls(services);
    }
    try
    {
      disc.run();
    } catch (Exception e)
    {
      System.err.println("Aborting. Problem discovering services.");
      e.printStackTrace();
      return;
    }
    Jws2Instance lastserv = null;
    for (Jws2Instance service : disc.getServices())
    {
      lastserv = service;
      if (p >= args.length || service.serviceType.equalsIgnoreCase(args[p]))
      {
        if (lastserv != null)
        {
          List<Preset> prl = null;
          Preset pr = null;
          if (++p < args.length)
          {
            PresetManager prman = lastserv.getPresets();
            if (prman != null)
            {
              pr = prman.getPresetByName(args[p]);
              if (pr == null)
              {
                // just grab the last preset.
                prl = prman.getPresets();
              }
            }
          }
          else
          {
            PresetManager prman = lastserv.getPresets();
            if (prman != null)
            {
              prl = prman.getPresets();
            }
          }
          Iterator<Preset> en = (prl == null) ? null : prl.iterator();
          while (en != null && en.hasNext())
          {
            if (en != null)
            {
              if (!en.hasNext())
              {
                en = prl.iterator();
              }
              pr = en.next();
            }
            {
              System.out.println("Testing opts dupes for "
                      + lastserv.getUri() + " : "
                      + lastserv.getActionText() + ":" + pr.getName());
              List<Option> rg = lastserv.getRunnerConfig().getOptions();
              for (Option o : rg)
              {
                try
                {
                  Option cpy = jalview.ws.jws2.ParameterUtils.copyOption(o);
                } catch (Exception e)
                {
                  System.err.println("Failed to copy " + o.getName());
                  e.printStackTrace();
                } catch (Error e)
                {
                  System.err.println("Failed to copy " + o.getName());
                  e.printStackTrace();
                }
              }
            }
            {
              System.out.println("Testing param dupes:");
              List<Parameter> rg = lastserv.getRunnerConfig()
                      .getParameters();
              for (Parameter o : rg)
              {
                try
                {
                  Parameter cpy = jalview.ws.jws2.ParameterUtils
                          .copyParameter(o);
                } catch (Exception e)
                {
                  System.err.println("Failed to copy " + o.getName());
                  e.printStackTrace();
                } catch (Error e)
                {
                  System.err.println("Failed to copy " + o.getName());
                  e.printStackTrace();
                }
              }
            }
            {
              System.out.println("Testing param write:");
              List<String> writeparam = null, readparam = null;
              try
              {
                writeparam = jalview.ws.jws2.ParameterUtils
                        .writeParameterSet(
                                pr.getArguments(lastserv.getRunnerConfig()),
                                " ");
                System.out.println("Testing param read :");
                List<Option> pset = jalview.ws.jws2.ParameterUtils
                        .processParameters(writeparam,
                                lastserv.getRunnerConfig(), " ");
                readparam = jalview.ws.jws2.ParameterUtils
                        .writeParameterSet(pset, " ");
                Iterator<String> o = pr.getOptions().iterator(), s = writeparam
                        .iterator(), t = readparam.iterator();
                boolean failed = false;
                while (s.hasNext() && t.hasNext())
                {
                  String on = o.next(), sn = s.next(), st = t.next();
                  if (!sn.equals(st))
                  {
                    System.out.println("Original was " + on
                            + " Phase 1 wrote " + sn + "\tPhase 2 wrote "
                            + st);
                    failed = true;
                  }
                }
                if (failed)
                {
                  System.out.println("Original parameters:\n"
                          + pr.getOptions());
                  System.out.println("Wrote parameters in first set:\n"
                          + writeparam);
                  System.out.println("Wrote parameters in second set:\n"
                          + readparam);

                }
              } catch (Exception e)
              {
                e.printStackTrace();
              }
            }
            WsJobParameters pgui = new WsJobParameters(lastserv,
                    new JabaPreset(lastserv, pr));
            JFrame jf = new JFrame("Parameters for "
                    + lastserv.getActionText());
            JPanel cont = new JPanel(new BorderLayout());
            pgui.validate();
            cont.setPreferredSize(pgui.getPreferredSize());
            cont.add(pgui, BorderLayout.CENTER);
            jf.setLayout(new BorderLayout());
            jf.add(cont, BorderLayout.CENTER);
            jf.validate();
            final Thread thr = Thread.currentThread();
            jf.addWindowListener(new WindowListener()
            {

              public void windowActivated(WindowEvent e)
              {
                // TODO Auto-generated method stub

              }

              public void windowClosed(WindowEvent e)
              {
              }

              public void windowClosing(WindowEvent e)
              {
                thr.interrupt();

              }

              public void windowDeactivated(WindowEvent e)
              {
                // TODO Auto-generated method stub

              }

              public void windowDeiconified(WindowEvent e)
              {
                // TODO Auto-generated method stub

              }

              public void windowIconified(WindowEvent e)
              {
                // TODO Auto-generated method stub

              }

              public void windowOpened(WindowEvent e)
              {
                // TODO Auto-generated method stub

              }

            });
            jf.setVisible(true);
            boolean inter = false;
            while (!inter)
            {
              try
              {
                Thread.sleep(10000);
              } catch (Exception e)
              {
                inter = true;
              }
              ;
            }
            jf.dispose();
          }
        }
      }
    }
  }

  public boolean isServiceDefaults()
  {
    return (!isModified() && (lastParmSet != null && lastParmSet
            .equals(SVC_DEF)));
  }

  public List<ArgumentI> getJobParams()
  {
    return opanp.getCurrentSettings();
  }

  String lastParmSet = null;

  /*
   * Hashtable<String, Object[]> editedParams = new Hashtable<String,
   * Object[]>();
   * 
   * store the given parameters in the user parameter set database.
   * 
   * @param storeSetName - lastParmSet
   * 
   * @param descr - setDescr.getText()
   * 
   * @param jobParams - getJobParams()
   * 
   * private void _storeUserPreset(String storeSetName, String descr,
   * List<ArgumentI> jobParams) { // this is a simple hash store. Object[] pset;
   * editedParams.put(storeSetName, pset = new Object[3]); pset[0] =
   * storeSetName; pset[1] = descr; pset[2] = jobParams; // writeParam("Saving "
   * + storeSetName + ": ", jobParams); }
   * 
   * private void writeParam(String nm, List<ArgumentI> params) { for (ArgumentI
   * p : params) { System.out.println(nm + ":" + System.identityHashCode(p) +
   * " Name: " + p.getName() + " Value: " + p.getDefaultValue()); } }
   * 
   * private Object[] _getUserPreset(String setName) { Object[] pset =
   * editedParams.get(setName); // if (pset != null) // writeParam("Retrieving "
   * + setName + ": ", (List<Argument>) pset[2]); return pset; }
   * 
   * * remove the given user preset from the preset stash
   * 
   * @param setName
   * 
   * private void _deleteUserPreset(String setName) {
   * editedParams.remove(setName); }
   */

  private void syncSetNamesWithStore()
  {
    int n = 0;
    // remove any set names in the drop down menu that aren't either a reserved
    // setting, or a user defined or service preset.
    Vector items = new Vector();
    while (n < setName.getItemCount())
    {
      String item = (String) setName.getItemAt(n);
      if (!item.equals(SVC_DEF) && !paramStore.presetExists(item))
      {
        setName.removeItemAt(n);
      }
      else
      {
        items.addElement(item);
        n++;
      }
    }
    if (!items.contains(SVC_DEF))
    {
      setName.addItem(SVC_DEF);
    }
    for (WsParamSetI upn : paramStore.getPresets())
    {
      if (!items.contains(upn.getName()))
      {
        setName.addItem(upn.getName());
      }
    }
  }

  /**
   * true if lastParmSet is a user preset
   */
  boolean isUserPreset = false;

  private void reInitDialog(String nextPreset)
  {
    settingDialog = true;
    // updateTable(null,null); // first reset to defaults
    WsParamSetI pset = null;
    if (nextPreset != null && nextPreset.length() > 0)
    {
      pset = paramStore.getPreset(nextPreset);
    }
    if (pset != null)
    {
      if (pset.isModifiable())
      {
        isUserPreset = true;
        setDescr.setText(pset.getDescription());
        updateTable(null, pset.getArguments());
        lastParmSet = nextPreset;
      }
      else
      {
        isUserPreset = false;
        setDescr.setText("");
        // must be a default preset from service
        updateTable(pset, null);
        lastParmSet = nextPreset;
      }
    }
    else
    {
      isUserPreset = false;
      // Service defaults
      setDescr.setText("");
      updateTable(null, null);
      lastParmSet = SVC_DEF;
    }

    initArgSetModified();
    syncSetNamesWithStore();
    setName.setSelectedItem(lastParmSet);
    SetNamePanel.validate();
    validate();
    settingDialog = false;

  }

  String curSetName = null;

  public void itemStateChanged(ItemEvent e)
  {
    if (e.getSource() == setName && e.getStateChange() == e.SELECTED)
    {
      final String setname = (String) setName.getSelectedItem();
      System.out.println("Item state changed for " + setname
              + " (handling ? " + !settingDialog + ")");
      if (settingDialog)
      {
        // ignore event
        return;
      }
      if (setname == null)
      {
        return;
      }
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          doPreferenceComboStateChange(setname);
        }
      });
    }
  }

  private void doPreferenceComboStateChange(String setname)
  {
    // user has selected a different item from combo-box
    if (isModified())
    {
      String lsetname = (curSetName != null) ? curSetName : lastParmSet;
      if (lsetname.equals(setname))
      {
        // setname was just edited - so ignore this event.
        return;
      }
      settingDialog = true;
      System.out.println("Prompting to save " + lsetname);
      if (javax.swing.JOptionPane
              .showConfirmDialog(
                      this,
                      "Parameter set '"
                              + lsetname
                              + "' is modifed, and your changes will be lost.\nReally change preset ?",
                      "Warning: Unsaved Changes",
                      javax.swing.JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
      {
        // revert the combobox to the current item
        settingDialog = true;
        setName.setSelectedItem(lsetname);
        settingDialog = false;
        // and leave.
        return;
        // System.out.println("Saving for " + lsetname);
        // _storeCurrentPreset(lsetname);

      }
    }
    settingDialog = true;
    reInitDialog(setname);
    settingDialog = false;

  }

  private void _renameExistingPreset(String oldName, String curSetName2)
  {
    paramStore.updatePreset(oldName, curSetName2, setDescr.getText(),
            getJobParams());
  }

  /**
   * store current settings as given name. You should then reset gui.
   * 
   * @param curSetName2
   */
  private void _storeCurrentPreset(String curSetName2)
  {
    paramStore.storePreset(curSetName2, setDescr.getText(), getJobParams());
  }

  private void _updatePreset(String lastParmSet2, String curname)
  {
    paramStore.updatePreset(lastParmSet2, curname, setDescr.getText(),
            getJobParams());

  }

  /**
   * last saved name for this user preset
   */
  String lastSetName = null;

  /**
   * last saved value of the description text for this user preset
   */
  String lastDescrText = null;

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() instanceof Component)
    {
      Component src = (Component) e.getSource();
      if (src.getParent() == setName)
      {
        // rename any existing records we know about for this set.
        String newname = (String) e.getActionCommand().trim();
        String msg = null;
        if (isServicePreset(newname))
        {
          final String oldname = curSetName != null ? curSetName
                  : lastParmSet;
          final Component ourframe = this;
          settingDialog = true;
          setName.getEditor().setItem(oldname);
          settingDialog = false;
          javax.swing.SwingUtilities.invokeLater(new Runnable()
          {
            public void run()
            {
              JOptionPane.showMessageDialog(ourframe,
                      "Invalid name - preset already exists.",
                      "Invalid name", JOptionPane.WARNING_MESSAGE);
            }
          });

          return;
        }
        curSetName = newname;
        System.err.println("New name for user setting " + curSetName
                + " (was " + setName.getSelectedItem() + ")");
        if (curSetName.equals(setName.getSelectedItem()))
        {
          curSetName = null;
        }
        if (curSetName != null)
        {
          argSetModified(setName, true);
          return;
        }

      }
    }
  }

  private void checkDescrModified()
  {
    if (!settingDialog)
    {

      argSetModified(
              setDescr,
              (lastDescrText == null ? setDescr.getText().trim().length() > 0
                      : !setDescr.getText().equals(lastDescrText)));

    }
  }

  public void insertUpdate(DocumentEvent e)
  {
    checkDescrModified();
  }

  public void removeUpdate(DocumentEvent e)
  {
    checkDescrModified();
  }

  public void changedUpdate(DocumentEvent e)
  {
    checkDescrModified();
  }

  /**
   * 
   * @return null or the service preset selected by the user
   */
  public WsParamSetI getPreset()
  {
    if (isUserPreset || isModified()
            || (lastParmSet != null && lastParmSet.equals(SVC_DEF)))
    {
      return null;
    }
    else
    {
      return paramStore.getPreset(lastParmSet);
    }
  }
}
