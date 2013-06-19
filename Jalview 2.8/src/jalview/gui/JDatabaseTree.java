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
import jalview.ws.seqfetcher.DbSourceProxy;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class JDatabaseTree extends JalviewDialog implements KeyListener
{
  boolean allowMultiSelections = false;

  JButton getDatabaseSelectorButton()
  {
    final JButton viewdbs = new JButton("--- Select Database ---");
    viewdbs.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        showDialog(null);
      }
    });
    return viewdbs;
  }

  JScrollPane svp;

  JTree dbviews;

  private jalview.ws.SequenceFetcher sfetcher;

  private JLabel dbstatus, dbstatex;

  public JDatabaseTree(jalview.ws.SequenceFetcher sfetch)
  {
    initDialogFrame(this, true, false, "Select Database Retrieval Source",
            650, 490);
    /*
     * Dynamically generated database list will need a translation function from
     * internal source to externally distinct names. UNIPROT and UP_NAME are
     * identical DB sources, and should be collapsed.
     */
    DefaultMutableTreeNode tn = null, root = new DefaultMutableTreeNode();
    Hashtable<String, DefaultMutableTreeNode> source = new Hashtable<String, DefaultMutableTreeNode>();
    sfetcher = sfetch;
    String dbs[] = sfetch.getSupportedDb();
    Hashtable<String, String> ht = new Hashtable<String, String>();
    for (int i = 0; i < dbs.length; i++)
    {
      tn = source.get(dbs[i]);
      List<DbSourceProxy> srcs = sfetch.getSourceProxy(dbs[i]);
      if (tn == null)
      {
        source.put(dbs[i], tn = new DefaultMutableTreeNode(dbs[i], true));
      }
      for (DbSourceProxy dbp : srcs)
      {
        if (ht.get(dbp.getDbName()) == null)
        {
          tn.add(new DefaultMutableTreeNode(dbp, false));
          ht.put(dbp.getDbName(), dbp.getDbName());
        }
        else
        {
          System.err.println("dupe ig for : " + dbs[i] + " \t"
                  + dbp.getDbName() + " (" + dbp.getDbSource() + ")");
          source.remove(tn);
        }
      }
    }
    for (int i = 0; i < dbs.length; i++)
    {
      tn = source.get(dbs[i]);
      if (tn == null)
      {
        continue;
      }
      if (tn.getChildCount() == 1)
      {
        DefaultMutableTreeNode ttn = (DefaultMutableTreeNode) tn
                .getChildAt(0);
        // remove nodes with only one child
        tn.setUserObject(ttn.getUserObject());
        tn.removeAllChildren();
        source.put(dbs[i], tn);
        tn.setAllowsChildren(false);
      }
      root.add(tn);
    }
    // and sort the tree
    sortTreeNodes(root);
    svp = new JScrollPane();
    // svp.setAutoscrolls(true);
    dbviews = new JTree(new DefaultTreeModel(root, false));
    dbviews.setCellRenderer(new DbTreeRenderer(this));

    dbviews.getSelectionModel().setSelectionMode(
            TreeSelectionModel.SINGLE_TREE_SELECTION);
    svp.getViewport().setView(dbviews);
    // svp.getViewport().setMinimumSize(new Dimension(300,200));
    // svp.setSize(300,250);
    // JPanel panel=new JPanel();
    // panel.setSize(new Dimension(350,220));
    // panel.add(svp);
    dbviews.addTreeSelectionListener(new TreeSelectionListener()
    {

      @Override
      public void valueChanged(TreeSelectionEvent arg0)
      {
        _setSelectionState();
      }
    });
    JPanel jc = new JPanel(new BorderLayout()), j = new JPanel(
            new FlowLayout());
    jc.add(svp, BorderLayout.CENTER);

    java.awt.Font f;
    // TODO: make the panel stay a fixed size for longest dbname+example set.
    JPanel dbstat = new JPanel(new GridLayout(2, 1));
    dbstatus = new JLabel(" "); // set the height correctly for layout
    dbstatus.setFont(f = JvSwingUtils.getLabelFont(false, true));
    dbstatus.setSize(new Dimension(290, 50));
    dbstatex = new JLabel(" ");
    dbstatex.setFont(f);
    dbstatex.setSize(new Dimension(290, 50));
    dbstat.add(dbstatus);
    dbstat.add(dbstatex);
    jc.add(dbstat, BorderLayout.SOUTH);
    jc.validate();
    // j.setPreferredSize(new Dimension(300,50));
    add(jc, BorderLayout.CENTER);
    j.add(ok);
    j.add(cancel);
    add(j, BorderLayout.SOUTH);
    dbviews.addKeyListener(this);
    validate();
  }

  private void sortTreeNodes(DefaultMutableTreeNode root)
  {
    if (root.getChildCount() == 0)
    {
      return;
    }
    int count = root.getChildCount();
    String[] names = new String[count];
    DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[count];
    for (int i = 0; i < count; i++)
    {
      TreeNode node = root.getChildAt(i);
      if (node instanceof DefaultMutableTreeNode)
      {
        DefaultMutableTreeNode child = (DefaultMutableTreeNode) node;
        nodes[i] = child;
        if (child.getUserObject() instanceof DbSourceProxy)
        {
          names[i] = ((DbSourceProxy) child.getUserObject()).getDbName()
                  .toLowerCase();
        }
        else
        {
          names[i] = ((String) child.getUserObject()).toLowerCase();
          sortTreeNodes(child);
        }
      }
      else
      {
        throw new Error(
                "Implementation Error: Can't reorder this tree. Not DefaultMutableTreeNode.");
      }
    }
    jalview.util.QuickSort.sort(names, nodes);
    root.removeAllChildren();
    for (int i = count - 1; i >= 0; i--)
    {
      root.add(nodes[i]);
    }
  }

  private class DbTreeRenderer extends DefaultTreeCellRenderer implements
          TreeCellRenderer
  {
    JDatabaseTree us;

    public DbTreeRenderer(JDatabaseTree me)
    {
      us = me;
    }

    private Component returnLabel(String txt)
    {
      JLabel jl = new JLabel(txt);
      jl.setFont(JvSwingUtils.getLabelFont());
      return jl;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus)
    {
      String val = "";
      if (value != null && value instanceof DefaultMutableTreeNode)
      {
        DefaultMutableTreeNode vl = (DefaultMutableTreeNode) value;
        value = vl.getUserObject();
        if (value instanceof DbSourceProxy)
        {
          val = (((DbSourceProxy) value).getDbName());
        }
        else
        {
          if (value instanceof String)
          {
            val = ((String) value);
          }
        }
      }
      if (value == null)
      {
        val = ("");
      }
      return super.getTreeCellRendererComponent(tree, val, selected,
              expanded, leaf, row, hasFocus);

    }
  }

  List<DbSourceProxy> oldselection, selection = null;

  TreePath[] tsel = null, oldtsel = null;

  @Override
  protected void raiseClosed()
  {
    for (ActionListener al : lstners)
    {
      al.actionPerformed(null);
    }
  }

  @Override
  protected void okPressed()
  {
    _setSelectionState();
    closeDialog();
  }

  @Override
  protected void cancelPressed()
  {
    selection = oldselection;
    tsel = oldtsel;
    _revertSelectionState();
    closeDialog();
  }

  private void showDialog(Container parent)
  {
    oldselection = selection;
    oldtsel = tsel;
    validate();
    waitForInput();
  }

  public boolean hasSelection()
  {
    return selection == null ? false : selection.size() == 0 ? false : true;
  }

  public List<DbSourceProxy> getSelectedSources()
  {
    return selection;
  }

  /**
   * disable or enable selection handler
   */
  boolean handleSelections = true;

  private void _setSelectionState()
  {
    if (!handleSelections)
    {
      return;
    }
    if (dbviews.getSelectionCount() == 0)
    {
      selection = null;
    }
    tsel = dbviews.getSelectionPaths();
    boolean forcedFirstChild = false;
    List<DbSourceProxy> srcs = new ArrayList<DbSourceProxy>();
    if (tsel != null)
    {
      for (TreePath tp : tsel)
      {
        DefaultMutableTreeNode admt, dmt = (DefaultMutableTreeNode) tp
                .getLastPathComponent();
        if (dmt.getUserObject() != null)
        {
          if (dmt.getUserObject() instanceof DbSourceProxy)
          {
            srcs.add((DbSourceProxy) dmt.getUserObject());
          }
          else
          {
            if (allowMultiSelections)
            {
              srcs.addAll(sfetcher.getSourceProxy((String) dmt
                      .getUserObject()));
            }
            else
            {
              if ((admt = dmt.getFirstLeaf()) != null
                      && admt.getUserObject() != null)
              {
                // modify db selection to just first leaf.
                if (admt.getUserObject() instanceof DbSourceProxy)
                {
                  srcs.add((DbSourceProxy) admt.getUserObject());
                }
                else
                {
                  srcs.add(sfetcher.getSourceProxy(
                          (String) admt.getUserObject()).get(0));
                }
                forcedFirstChild = true;
              }
            }
          }
        }
      }
    }
    updateDbStatus(srcs, forcedFirstChild);
    selection = srcs;
  }

  private void _revertSelectionState()
  {
    handleSelections = false;
    if (selection == null || selection.size() == 0)
    {
      dbviews.clearSelection();
    }
    else
    {
      dbviews.setSelectionPaths(tsel);
    }
    handleSelections = true;
  }

  private void updateDbStatus(List<DbSourceProxy> srcs,
          boolean forcedFirstChild)
  {
    int x = 0;
    String nm = "", qr = "";
    for (DbSourceProxy dbs : srcs)
    {
      String tq = dbs.getTestQuery();
      nm = dbs.getDbName();
      if (tq != null && tq.trim().length() > 0 && dbs.isValidReference(tq))
      {
        qr = tq;
        x++;
      }
    }

    if (allowMultiSelections)
    {
      dbstatus.setText("Selected "
              + srcs.size()
              + " database"
              + (srcs.size() == 1 ? "" : "s")
              + " to fetch from"
              + (srcs.size() > 0 ? " with " + x + " test quer"
                      + (x == 1 ? "y" : "ies") : "."));
      dbstatex.setText(" ");
    }
    else
    {
      if (nm.length() > 0)
      {
        dbstatus.setText("Database: " + nm);
        if (qr.length() > 0)
        {
          dbstatex.setText("Example: " + qr);
        }
        else
        {
          dbstatex.setText(" ");
        }
      }
      else
      {
        dbstatus.setText(" ");
      }
    }
    dbstatus.invalidate();
    dbstatex.invalidate();
  }

  public String getSelectedItem()
  {
    if (hasSelection())
    {
      return getSelectedSources().get(0).getDbName();
    }
    return null;
  }

  public String getExampleQueries()
  {
    if (!hasSelection())
    {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    HashSet<String> hs = new HashSet<String>();
    for (DbSourceProxy dbs : getSelectedSources())
    {
      String tq = dbs.getTestQuery();
      ;
      if (hs.add(tq))
      {
        if (sb.length() > 0)
        {
          sb.append(";");
        }
        sb.append(tq);
      }
    }
    return sb.toString();
  }

  List<ActionListener> lstners = new Vector<ActionListener>();

  public void addActionListener(ActionListener actionListener)
  {
    lstners.add(actionListener);
  }

  public void removeActionListener(ActionListener actionListener)
  {
    lstners.remove(actionListener);
  }

  public static void main(String args[])
  {
    Cache.getDasSourceRegistry();
    JDatabaseTree jdt = new JDatabaseTree(new jalview.ws.SequenceFetcher());
    JFrame foo = new JFrame();
    foo.setLayout(new BorderLayout());
    foo.add(jdt.getDatabaseSelectorButton(), BorderLayout.CENTER);
    foo.pack();
    foo.setVisible(true);
    int nultimes = 5;
    final Thread us = Thread.currentThread();
    jdt.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        us.interrupt();
      }
    });
    do
    {
      try
      {
        Thread.sleep(50);
      } catch (InterruptedException x)
      {
        nultimes--;
        if (!jdt.hasSelection())
        {
          System.out.println("No Selection");
        }
        else
        {
          System.out.println("Selection: " + jdt.getSelectedItem());
          int s = 1;
          for (DbSourceProxy pr : jdt.getSelectedSources())
          {
            System.out.println("Source " + s++ + ": " + pr.getDbName()
                    + " (" + pr.getDbSource() + ") Version "
                    + pr.getDbVersion() + ". Test:\t" + pr.getTestQuery());
          }
          System.out.println("Test queries: " + jdt.getExampleQueries());
        }
      }
    } while (nultimes > 0 && foo.isVisible());
    foo.setVisible(false);
  }

  @Override
  public void keyPressed(KeyEvent arg0)
  {
    if (!arg0.isConsumed() && arg0.getKeyCode() == KeyEvent.VK_ENTER)
    {
      okPressed();
    }
    if (!arg0.isConsumed() && arg0.getKeyChar() == KeyEvent.VK_ESCAPE)
    {
      cancelPressed();
    }
  }

  @Override
  public void keyReleased(KeyEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyTyped(KeyEvent arg0)
  {
    // TODO Auto-generated method stub

  }
}
