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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * this is an implementation of an abstract Jalview GUI class that provides a
 * dialog/menu which allows the user to select/deselect specific views from a
 * list of associated views.
 * 
 * Includes patches related to JAL-641
 * 
 * @author JimP
 * 
 */
public class ViewSelectionMenu extends JMenu
{
  public interface ViewSetProvider
  {
    public AlignmentPanel[] getAllAlignmentPanels();
  }

  private ViewSetProvider _allviews;

  private List<AlignmentPanel> _selectedviews;

  private ItemListener _handler;

  @Override
  protected void finalize() throws Throwable
  {
    _selectedviews = null;
    _handler = null;
    _allviews = null;
    super.finalize();
  }

  /**
   * create a new view selection menu. This menu has some standard entries
   * (select all, invert selection), and a checkbox for every view. Mousing over
   * a view entry will cause it to be raised/selected in the Desktop, allowing
   * the user to easily identify which view is being referred to.
   * 
   * @param title
   *          Name of menu
   * @param allviews
   *          all the views that might be selected
   * @param selectedviews
   *          the list of selected views which will be updated when
   *          selection/deselections occur
   * @param handler
   *          a handler called for each selection/deselection - use this to
   *          update any gui elements which need to reflect current
   *          selection/deselection state
   */
  public ViewSelectionMenu(String title, final ViewSetProvider allviews,
          final List<AlignmentPanel> selectedviews,
          final ItemListener handler)
  {
    super(title);
    this._allviews = allviews;
    this._selectedviews = selectedviews;
    this._handler = handler;
    addMenuListener(new MenuListener()
    {

      @Override
      public void menuSelected(MenuEvent e)
      {
        rebuild();

      }

      @Override
      public void menuDeselected(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void menuCanceled(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }
    });
  }

  /**
   * view selection modifier flag - indicates if an action key is pressed when
   * menu selection event occurred.
   */
  private boolean append = false;

  /**
   * flag indicating if the itemStateChanged listener for view associated menu
   * items is currently enabled
   */
  private boolean enabled = true;

  private JMenuItem selectAll, invertSel;

  private JCheckBoxMenuItem toggleview = null;

  private void rebuild()
  {
    removeAll();
    AlignmentPanel[] allviews = _allviews.getAllAlignmentPanels();
    if (allviews == null)
    {
      setVisible(false);
      return;
    }
    if (allviews.length >= 2)
    {
      // ensure we update menu state to reflect external selection list state
      append = append || _selectedviews.size() > 1;
      toggleview = new JCheckBoxMenuItem("Select many views", append);
      toggleview
              .setToolTipText("When enabled, allows many views to be selected.");
      toggleview.addItemListener(new ItemListener()
      {

        @Override
        public void itemStateChanged(ItemEvent arg0)
        {
          if (enabled)
          {
            append = !append;
            selectAll.setEnabled(append);
            invertSel.setEnabled(append);
          }

        }

      });
      add(toggleview);
      add(selectAll = new JMenuItem("Select all views"));
      selectAll.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          for (Component c : getMenuComponents())
          {
            boolean t = append;
            append = true;
            if (c instanceof JCheckBoxMenuItem)
            {
              if (toggleview != c && !((JCheckBoxMenuItem) c).isSelected())
              {
                ((JCheckBoxMenuItem) c).doClick();
              }
            }
            append = t;
          }
        }
      });
      add(invertSel = new JMenuItem("Invert selection"));
      invertSel.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          boolean t = append;
          append = true;
          for (Component c : getMenuComponents())
          {
            if (toggleview != c && c instanceof JCheckBoxMenuItem)
            {
              ((JCheckBoxMenuItem) c).doClick();
            }
          }
          append = t;
        }
      });
      invertSel.setEnabled(append);
      selectAll.setEnabled(append);
    }
    for (AlignmentPanel ap : allviews)
    {
      String nm = ((ap.getViewName() == null || ap.getViewName().length() == 0) ? ""
              : ap.getViewName() + " for ")
              + ap.alignFrame.getTitle();
      final JCheckBoxMenuItem a = new JCheckBoxMenuItem(nm,
              _selectedviews.contains(ap));
      final AlignmentPanel p = ap;
      a.addItemListener(new ItemListener()
      {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
          if (enabled)
          {
            if (append)
            {
              enabled = false;
              // toggle the inclusion state
              if (_selectedviews.indexOf(p) == -1)
              {
                _selectedviews.add(p);
                a.setSelected(true);
              }
              else
              {
                _selectedviews.remove(p);
                a.setSelected(false);
              }
              enabled = true;
              _handler.itemStateChanged(e);
            }
            else
            {
              // Deselect everything and select this item only
              _selectedviews.clear();
              _selectedviews.add(p);
              enabled = false;
              for (Component c : getMenuComponents())
              {
                if (c instanceof JCheckBoxMenuItem)
                {
                  ((JCheckBoxMenuItem) c).setSelected(a == c);
                }
              }
              enabled = true;
              // only fire event if we weren't selected before
              _handler.itemStateChanged(e);
            }
          }
        }
      });
      a.addMouseListener(new MouseListener()
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
          try
          {
            p.setSelected(false);
          } catch (Exception ex)
          {
          }
          ;
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

          try
          {
            p.setSelected(true);
          } catch (Exception ex)
          {
          }
          ;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
        }
      });
      add(a);
    }
  }

}
