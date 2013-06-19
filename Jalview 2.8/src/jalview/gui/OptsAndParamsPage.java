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

import jalview.ws.params.ArgumentI;
import jalview.ws.params.OptionI;
import jalview.ws.params.ParameterI;
import jalview.ws.params.ValueConstrainI;
import jalview.ws.params.ValueConstrainI.ValueType;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

/**
 * GUI generator/manager for options and parameters. Originally abstracted from
 * the WsJobParameters dialog box.
 * 
 * @author jprocter
 * 
 */
public class OptsAndParamsPage
{
  /**
   * compact or verbose style parameters
   */
  boolean compact = false;

  public class OptionBox extends JPanel implements MouseListener,
          ActionListener
  {
    JCheckBox enabled = new JCheckBox();

    final URL finfo;

    boolean hasLink = false;

    boolean initEnabled = false;

    String initVal = null;

    OptionI option;

    JLabel optlabel = new JLabel();

    JComboBox val = new JComboBox();

    public OptionBox(OptionI opt)
    {
      option = opt;
      setLayout(new BorderLayout());
      enabled.setSelected(opt.isRequired()); // TODO: lock required options
      enabled.setFont(new Font("Verdana", Font.PLAIN, 11));
      enabled.setText("");
      enabled.setText(opt.getName());
      enabled.addActionListener(this);
      finfo = option.getFurtherDetails();
      String desc = opt.getDescription();
      if (finfo != null)
      {
        hasLink = true;

        enabled.setToolTipText("<html>"
                + JvSwingUtils
                        .wrapTooltip(((desc == null) ? "see further details by right-clicking"
                                : desc)
                                + "<br><img src=\"" + linkImageURL + "\"/>")
                + "</html>");
        enabled.addMouseListener(this);
      }
      else
      {
        if (desc != null)
        {
          enabled.setToolTipText("<html>"
                  + JvSwingUtils.wrapTooltip(opt.getDescription())
                  + "</html>");
        }
      }
      add(enabled, BorderLayout.NORTH);
      for (Object str : opt.getPossibleValues())
      {
        val.addItem((String) str);
      }
      val.setSelectedItem((String) opt.getValue());
      if (opt.getPossibleValues().size() > 1)
      {
        setLayout(new GridLayout(1, 2));
        val.addActionListener(this);
        add(val, BorderLayout.SOUTH);
      }
      // TODO: add actionListeners for popup (to open further info),
      // and to update list of parameters if an option is enabled
      // that takes a value. JBPNote: is this TODO still valid ?
      setInitialValue();
    }

    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() != enabled)
      {
        enabled.setSelected(true);
      }
      checkIfModified();
    }

    private void checkIfModified()
    {
      boolean notmod = (initEnabled == enabled.isSelected());
      if (enabled.isSelected())
      {
        if (initVal != null)
        {
          notmod &= initVal.equals(val.getSelectedItem());
        }
        else
        {
          // compare against default service setting
          notmod &= option.getValue() == null
                  || option.getValue().equals(val.getSelectedItem());
        }
      }
      else
      {
        notmod &= (initVal != null) ? initVal.equals(val.getSelectedItem())
                : val.getSelectedItem() != initVal;
      }
      poparent.argSetModified(this, !notmod);
    }

    public OptionI getOptionIfEnabled()
    {
      if (!enabled.isSelected())
      {
        return null;
      }
      OptionI opt = option.copy();
      if (opt.getPossibleValues() != null
              && opt.getPossibleValues().size() == 1)
      {
        // Hack to make sure the default value for an enabled option with only
        // one value is actually returned
        opt.setValue(opt.getPossibleValues().get(0));
      }
      if (val.getSelectedItem() != null)
      {
        opt.setValue((String) val.getSelectedItem());
      }
      else
      {
        if (option.getValue() != null)
        {
          opt.setValue(option.getValue());
        }
      }
      return opt;
    }

    public void mouseClicked(MouseEvent e)
    {
      if (javax.swing.SwingUtilities.isRightMouseButton(e))
      {
        showUrlPopUp(this, finfo.toString(), e.getX(), e.getY());
      }
    }

    public void mouseEntered(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void resetToDefault()
    {
      enabled.setSelected(false);
      if (option.isRequired())
      {
        // Apply default value
        selectOption(option, option.getValue());
      }
    }

    public void setInitialValue()
    {
      initEnabled = enabled.isSelected();
      if (option.getPossibleValues() != null
              && option.getPossibleValues().size() > 1)
      {
        initVal = (String) val.getSelectedItem();
      }
      else
      {
        initVal = (initEnabled) ? (String) val.getSelectedItem() : null;
      }
    }

  }

  public class ParamBox extends JPanel implements ChangeListener,
          ActionListener, MouseListener
  {
    boolean adjusting = false;

    boolean choice = false;

    JComboBox choicebox;

    JPanel controlPanel = new JPanel();

    boolean descisvisible = false;

    JScrollPane descPanel = new JScrollPane();

    final URL finfo;

    boolean integ = false;

    Object lastVal;

    ParameterI parameter;

    final OptsParametersContainerI pmdialogbox;

    JPanel settingPanel = new JPanel();

    JButton showDesc = new JButton();

    JSlider slider = null;

    JTextArea string = new JTextArea();

    ValueConstrainI validator = null;

    JTextField valueField = null;

    public ParamBox(final OptsParametersContainerI pmlayout, ParameterI parm)
    {
      pmdialogbox = pmlayout;
      finfo = parm.getFurtherDetails();
      validator = parm.getValidValue();
      parameter = parm;
      if (validator != null)
      {
        integ = validator.getType() == ValueType.Integer;
      }
      else
      {
        if (parameter.getPossibleValues() != null)
        {
          choice = true;
        }
      }

      if (!compact)
      {
        makeExpanderParam(parm);
      }
      else
      {
        makeCompactParam(parm);

      }
    }

    private void makeCompactParam(ParameterI parm)
    {
      setLayout(new MigLayout("", "[][grow]"));

      String ttipText = null;

      controlPanel.setLayout(new BorderLayout());

      if (parm.getDescription() != null
              && parm.getDescription().trim().length() > 0)
      {
        // Only create description boxes if there actually is a description.
        ttipText = ("<html>"
                + JvSwingUtils
                        .wrapTooltip(parm.getDescription()
                                + (finfo != null ? "<br><img src=\""
                                        + linkImageURL
                                        + "\"/> Right click for further information."
                                        : "")) + "</html>");
      }

      JvSwingUtils.mgAddtoLayout(this, ttipText,
              new JLabel(parm.getName()), controlPanel, "");
      updateControls(parm);
      validate();
    }

    private void makeExpanderParam(ParameterI parm)
    {
      setPreferredSize(new Dimension(PARAM_WIDTH, PARAM_CLOSEDHEIGHT));
      setBorder(new TitledBorder(parm.getName()));
      setLayout(null);
      showDesc.setFont(new Font("Verdana", Font.PLAIN, 6));
      showDesc.setText("+");
      string.setFont(new Font("Verdana", Font.PLAIN, 11));
      string.setBackground(getBackground());

      string.setEditable(false);
      descPanel.getViewport().setView(string);

      descPanel.setVisible(false);

      JPanel firstrow = new JPanel();
      firstrow.setLayout(null);
      controlPanel.setLayout(new BorderLayout());
      controlPanel.setBounds(new Rectangle(39, 10, PARAM_WIDTH - 70,
              PARAM_CLOSEDHEIGHT - 50));
      firstrow.add(controlPanel);
      firstrow.setBounds(new Rectangle(10, 20, PARAM_WIDTH - 30,
              PARAM_CLOSEDHEIGHT - 30));

      final ParamBox me = this;

      if (parm.getDescription() != null
              && parm.getDescription().trim().length() > 0)
      {
        // Only create description boxes if there actually is a description.
        if (finfo != null)
        {
          showDesc.setToolTipText("<html>"
                  + JvSwingUtils
                          .wrapTooltip("Click to show brief description<br><img src=\""
                                  + linkImageURL
                                  + "\"/> Right click for further information.")
                  + "</html>");
          showDesc.addMouseListener(this);
        }
        else
        {
          showDesc.setToolTipText("<html>"
                  + JvSwingUtils
                          .wrapTooltip("Click to show brief description.")
                  + "</html>");
        }
        showDesc.addActionListener(new ActionListener()
        {

          public void actionPerformed(ActionEvent e)
          {
            descisvisible = !descisvisible;
            descPanel.setVisible(descisvisible);
            descPanel.getVerticalScrollBar().setValue(0);
            me.setPreferredSize(new Dimension(PARAM_WIDTH,
                    (descisvisible) ? PARAM_HEIGHT : PARAM_CLOSEDHEIGHT));
            me.validate();
            pmdialogbox.refreshParamLayout();
          }
        });
        string.setWrapStyleWord(true);
        string.setLineWrap(true);
        string.setColumns(32);
        string.setText(parm.getDescription());
        showDesc.setBounds(new Rectangle(10, 10, 16, 16));
        firstrow.add(showDesc);
      }
      add(firstrow);
      validator = parm.getValidValue();
      parameter = parm;
      if (validator != null)
      {
        integ = validator.getType() == ValueType.Integer;
      }
      else
      {
        if (parameter.getPossibleValues() != null)
        {
          choice = true;
        }
      }
      updateControls(parm);
      descPanel.setBounds(new Rectangle(10, PARAM_CLOSEDHEIGHT,
              PARAM_WIDTH - 20, PARAM_HEIGHT - PARAM_CLOSEDHEIGHT - 5));
      add(descPanel);
      validate();
    }

    public void actionPerformed(ActionEvent e)
    {
      if (adjusting)
      {
        return;
      }
      if (!choice)
      {
        updateSliderFromValueField();
      }
      checkIfModified();
    }

    private void checkIfModified()
    {
      Object cstate = updateSliderFromValueField();
      boolean notmod = false;
      if (cstate.getClass() == lastVal.getClass())
      {
        if (cstate instanceof int[])
        {
          notmod = (((int[]) cstate)[0] == ((int[]) lastVal)[0]);
        }
        else if (cstate instanceof float[])
        {
          notmod = (((float[]) cstate)[0] == ((float[]) lastVal)[0]);
        }
        else if (cstate instanceof String[])
        {
          notmod = (((String[]) cstate)[0].equals(((String[]) lastVal)[0]));
        }
      }
      pmdialogbox.argSetModified(this, !notmod);
    }

    @Override
    public int getBaseline(int width, int height)
    {
      return 0;
    }

    // from
    // http://stackoverflow.com/questions/2743177/top-alignment-for-flowlayout
    // helpful hint of using the Java 1.6 alignBaseLine property of FlowLayout
    @Override
    public Component.BaselineResizeBehavior getBaselineResizeBehavior()
    {
      return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
    }

    public int getBoxHeight()
    {
      return (descisvisible ? PARAM_HEIGHT : PARAM_CLOSEDHEIGHT);
    }

    public ParameterI getParameter()
    {
      ParameterI prm = parameter.copy();
      if (choice)
      {
        prm.setValue((String) choicebox.getSelectedItem());
      }
      else
      {
        prm.setValue(valueField.getText());
      }
      return prm;
    }

    public void init()
    {
      // reset the widget's initial value.
      lastVal = null;
    }

    public void mouseClicked(MouseEvent e)
    {
      if (javax.swing.SwingUtilities.isRightMouseButton(e))
      {
        showUrlPopUp(this, finfo.toString(), e.getX(), e.getY());
      }
    }

    public void mouseEntered(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e)
    {
      // TODO Auto-generated method stub

    }

    public void stateChanged(ChangeEvent e)
    {
      if (!adjusting)
      {
        valueField.setText(""
                + ((integ) ? ("" + (int) slider.getValue())
                        : ("" + (float) (slider.getValue() / 1000f))));
        checkIfModified();
      }

    }

    public void updateControls(ParameterI parm)
    {
      adjusting = true;
      boolean init = (choicebox == null && valueField == null);
      if (init)
      {
        if (choice)
        {
          choicebox = new JComboBox();
          choicebox.addActionListener(this);
          controlPanel.add(choicebox, BorderLayout.CENTER);
        }
        else
        {
          slider = new JSlider();
          slider.addChangeListener(this);
          valueField = new JTextField();
          valueField.addActionListener(this);
          valueField.addKeyListener(new KeyListener()
          {

            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
              if (valueField.getText().trim().length() > 0)
              {
                actionPerformed(null);
              }
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }
          });
          valueField.setPreferredSize(new Dimension(60, 25));
          controlPanel.add(slider, BorderLayout.WEST);
          controlPanel.add(valueField, BorderLayout.EAST);

        }
      }

      if (parm != null)
      {
        if (choice)
        {
          if (init)
          {
            List vals = parm.getPossibleValues();
            for (Object val : vals)
            {
              choicebox.addItem(val);
            }
          }

          if (parm.getValue() != null)
          {
            choicebox.setSelectedItem(parm.getValue());
          }
        }
        else
        {
          valueField.setText(parm.getValue());
        }
      }
      lastVal = updateSliderFromValueField();
      adjusting = false;
    }

    public Object updateSliderFromValueField()
    {
      int iVal;
      float fVal;
      if (validator != null)
      {
        if (integ)
        {
          iVal = 0;
          try
          {
            valueField.setText(valueField.getText().trim());
            iVal = Integer.valueOf(valueField.getText());
            if (validator.getMin() != null
                    && validator.getMin().intValue() > iVal)
            {
              iVal = validator.getMin().intValue();
              // TODO: provide visual indication that hard limit was reached for
              // this parameter
            }
            if (validator.getMax() != null
                    && validator.getMax().intValue() < iVal)
            {
              iVal = validator.getMax().intValue();
              // TODO: provide visual indication that hard limit was reached for
              // this parameter
            }
          } catch (Exception e)
          {
          }
          ;
          // update value field to reflect any bound checking we performed.
          valueField.setText("" + iVal);
          if (validator.getMin() != null && validator.getMax() != null)
          {
            slider.getModel().setRangeProperties(iVal, 1,
                    validator.getMin().intValue(),
                    validator.getMax().intValue(), true);
          }
          else
          {
            slider.setVisible(false);
          }
          return new int[]
          { iVal };
        }
        else
        {
          fVal = 0f;
          try
          {
            valueField.setText(valueField.getText().trim());
            fVal = Float.valueOf(valueField.getText());
            if (validator.getMin() != null
                    && validator.getMin().floatValue() > fVal)
            {
              fVal = validator.getMin().floatValue();
              // TODO: provide visual indication that hard limit was reached for
              // this parameter
              // update value field to reflect any bound checking we performed.
              valueField.setText("" + fVal);
            }
            if (validator.getMax() != null
                    && validator.getMax().floatValue() < fVal)
            {
              fVal = validator.getMax().floatValue();
              // TODO: provide visual indication that hard limit was reached for
              // this parameter
              // update value field to reflect any bound checking we performed.
              valueField.setText("" + fVal);
            }
          } catch (Exception e)
          {
          }
          ;
          if (validator.getMin() != null && validator.getMax() != null)
          {
            slider.getModel().setRangeProperties((int) fVal * 1000, 1,
                    (int) validator.getMin().floatValue() * 1000,
                    (int) validator.getMax().floatValue() * 1000, true);
          }
          else
          {
            slider.setVisible(false);
          }
          return new float[]
          { fVal };
        }
      }
      else
      {
        if (!choice)
        {
          slider.setVisible(false);
          return new String[]
          { valueField.getText().trim() };
        }
        else
        {
          return new String[]
          { (String) choicebox.getSelectedItem() };
        }
      }

    }
  }

  public static final int PARAM_WIDTH = 340;

  public static final int PARAM_HEIGHT = 150;

  public static final int PARAM_CLOSEDHEIGHT = 80;

  public OptsAndParamsPage(OptsParametersContainerI paramContainer)
  {
    this(paramContainer, false);
  }

  public OptsAndParamsPage(OptsParametersContainerI paramContainer,
          boolean compact)
  {
    poparent = paramContainer;
    this.compact = compact;
  }

  public static void showUrlPopUp(JComponent invoker, final String finfo,
          int x, int y)
  {

    JPopupMenu mnu = new JPopupMenu();
    JMenuItem mitem = new JMenuItem("View " + finfo);
    mitem.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        Desktop.showUrl(finfo);

      }
    });
    mnu.add(mitem);
    mnu.show(invoker, x, y);
  }

  URL linkImageURL = getClass().getResource("/images/link.gif");

  Map<String, OptionBox> optSet = new Hashtable<String, OptionBox>();

  Map<String, ParamBox> paramSet = new Hashtable<String, ParamBox>();

  public Map<String, OptionBox> getOptSet()
  {
    return optSet;
  }

  public void setOptSet(Map<String, OptionBox> optSet)
  {
    this.optSet = optSet;
  }

  public Map<String, ParamBox> getParamSet()
  {
    return paramSet;
  }

  public void setParamSet(Map<String, ParamBox> paramSet)
  {
    this.paramSet = paramSet;
  }

  OptsParametersContainerI poparent;

  OptionBox addOption(OptionI opt)
  {
    OptionBox cb = optSet.get(opt.getName());
    if (cb == null)
    {
      cb = new OptionBox(opt);
      optSet.put(opt.getName(), cb);
      // jobOptions.add(cb, FlowLayout.LEFT);
    }
    return cb;
  }

  ParamBox addParameter(ParameterI arg)
  {
    ParamBox pb = paramSet.get(arg.getName());
    if (pb == null)
    {
      pb = new ParamBox(poparent, arg);
      paramSet.put(arg.getName(), pb);
      // paramList.add(pb);
    }
    pb.init();
    // take the defaults from the parameter
    pb.updateControls(arg);
    return pb;
  }

  void selectOption(OptionI option, String string)
  {
    OptionBox cb = optSet.get(option.getName());
    if (cb == null)
    {
      cb = addOption(option);
    }
    cb.enabled.setSelected(string != null); // initial state for an option.
    if (string != null)
    {
      if (option.getPossibleValues().contains(string))
      {
        cb.val.setSelectedItem(string);
      }
      else
      {
        throw new Error("Invalid value " + string + " for option " + option);
      }

    }
    if (option.isRequired() && !cb.enabled.isSelected())
    {
      // TODO: indicate paramset is not valid.. option needs to be selected!
    }
    cb.setInitialValue();
  }

  void setParameter(ParameterI arg)
  {
    ParamBox pb = paramSet.get(arg.getName());
    if (pb == null)
    {
      addParameter(arg);
    }
    else
    {
      pb.updateControls(arg);
    }

  }

  /**
   * recover options and parameters from GUI
   * 
   * @return
   */
  public List<ArgumentI> getCurrentSettings()
  {
    List<ArgumentI> argSet = new ArrayList<ArgumentI>();
    for (OptionBox opts : getOptSet().values())
    {
      OptionI opt = opts.getOptionIfEnabled();
      if (opt != null)
      {
        argSet.add(opt);
      }
    }
    for (ParamBox parambox : getParamSet().values())
    {
      ParameterI parm = parambox.getParameter();
      if (parm != null)
      {
        argSet.add(parm);
      }
    }

    return argSet;
  }

}
