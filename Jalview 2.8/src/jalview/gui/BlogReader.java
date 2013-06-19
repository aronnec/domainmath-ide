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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.robsite.jswingreader.action.MarkChannelAsRead;
import org.robsite.jswingreader.action.MarkChannelAsUnread;
import org.robsite.jswingreader.action.MarkItemAsRead;
import org.robsite.jswingreader.action.MarkItemAsUnread;
import org.robsite.jswingreader.action.UpdatableAction;
import org.robsite.jswingreader.model.Channel;
import org.robsite.jswingreader.model.ChannelListModel;
import org.robsite.jswingreader.model.Item;
import org.robsite.jswingreader.model.SimpleRSSParser;
import org.robsite.jswingreader.ui.BlogContentPane;
import org.robsite.jswingreader.ui.ItemReadTimer;
import org.robsite.jswingreader.ui.Main;
import org.robsite.jswingreader.ui.util.ContextMenuMouseAdapter;

/**
 * Blog reading window, adapted from JSwingReader's
 * org.robsite.jswingreader.ui.MainWindow class
 */

public class BlogReader extends JPanel
{
  private JButton buttonRefresh = new JButton();

  private JToolBar toolBar = new JToolBar();

  private JLabel statusBar = new JLabel();

  private JPanel panelMain = new JPanel();

  private BorderLayout layoutMain = new BorderLayout();

  private BorderLayout borderLayout1 = new BorderLayout();

  private JPanel topPanel = new JPanel();

  private JPanel bottomPanel = new JPanel();

  private JSplitPane topBottomSplitPane = new JSplitPane();

  private JList listItems = new JList(new DefaultListModel());

  // SWITCH IN JALVIEW HTML VIEWER PANE HERE
  private BlogContentPane textDescription = new BlogContentPane();

  // ADD IN JALVIEW BANNER FOR PRETTINESS
  private BorderLayout borderLayout4 = new BorderLayout();

  private BorderLayout borderLayout5 = new BorderLayout();

  private ChannelListModel _channelModel = null;

  private JList listChannels = new JList();

  private Action exitAction = new Action()
  {

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      if (xf != null)
      {
        xf.dispose();
      }
      xf = null;
      jd = null;
      if (parent != null)
      {
        parent.showNews(false);
      }

    }

    @Override
    public void setEnabled(boolean arg0)
    {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener arg0)
    {
      // TODO Auto-generated method stub

    }

    @Override
    public void putValue(String arg0, Object arg1)
    {
      // TODO Auto-generated method stub

    }

    @Override
    public boolean isEnabled()
    {
      // TODO Auto-generated method stub
      return true;
    }

    @Override
    public Object getValue(String arg0)
    {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener arg0)
    {
      // TODO Auto-generated method stub

    }
  };

  private JFrame xf = null;

  private JalviewDialog jd = null;

  private JalviewDialog createDialog()
  {

    return jd = new JalviewDialog()
    {

      @Override
      protected void raiseClosed()
      {
        if (parent != null)
        {
          Cache.log.info("News window closed.");
          jd = null;
          parent.showNews(false);
        }
      }

      @Override
      protected void okPressed()
      {
        // TODO Auto-generated method stub

      }

      @Override
      protected void cancelPressed()
      {
        // TODO Auto-generated method stub

      }
    };
  };

  private JLabel lblChannels = new JLabel();

  private List _updatableActions = new ArrayList();

  private ItemReadTimer _itemTimer = null;

  private JPopupMenu _popupItems = null;

  private JPopupMenu _popupChannels = null;

  private String lastm = "";

  private boolean newsnew = false;

  private Desktop parent = null;

  BlogReader()
  {
    this(null);
  }

  // should we ignore fake gui events
  private boolean updating = false;

  public BlogReader(Desktop desktop)
  {
    Cache.log.info("Constructing news reader.");

    parent = desktop;
    _channelModel = new ChannelListModel();
    // Construct our jalview news channel
    Channel chan = new Channel();
    chan.setURL(jalview.bin.Cache.getDefault(
            "JALVIEW_NEWS_RSS",
            jalview.bin.Cache.getDefault("www.jalview.org",
                    "http://www.jalview.org") + "/feeds/desktop/rss"));
    loadLastM();
    _channelModel.addChannel(chan);
    updating = true;
    try
    {
      jbInit();
      postInit();
    } catch (Exception e)
    {
      e.printStackTrace();
    }

    initItems(chan);
    updating = false;
    boolean setvisible = checkForNew(chan, true);

    if (setvisible)
    {

      Cache.log.info("Will show jalview news automatically");
      showNews();
    }
    Cache.log.info("Completed construction of reader.");

  }

  /**
   * check if the news panel's container is visible
   */
  public boolean isVisible()
  {
    if (parent == null)
    {
      return xf != null && xf.isVisible();
    }
    return jd != null && jd.isVisible();
  }

  /**
   * display the container for the news panel
   */
  public void showNews()
  {
    final BlogReader me = this;
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        Rectangle bounds = new Rectangle(5, 5, 550, 350);
        if (parent == null)
        {
          xf = new JFrame();
          xf.setContentPane(me);
          xf.addWindowListener(new WindowAdapter()
          {
            public void windowClosing(WindowEvent e)
            {
              ActionEvent actionEvent = new ActionEvent(this,
                      ActionEvent.ACTION_FIRST, (String) exitAction
                              .getValue(Action.NAME));
              exitAction.actionPerformed(actionEvent);
            }

            public void windowOpened(WindowEvent e)
            {
            }
          });
          me.setSize(new Dimension(550, 350));
          xf.setVisible(true);
        }
        else
        {
          createDialog();
          bounds = new Rectangle(5, 5, 550, 350);
          jd.initDialogFrame(me, false, false, "News from www.jalview.org",
                  bounds.width, bounds.height);
          jd.frame.setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
          Cache.log.info("Displaying news.");
          jd.waitForInput();
        }
      }
    });
  }

  /**
   * update hasnew flag and mark all new messages as unread.
   */
  private boolean checkForNew(Channel chan, boolean updateItems)
  {

    if (!updating || updateItems)
    {
      newsnew = false;
    }
    java.util.Date earliest = null;
    try
    {
      earliest = new SimpleDateFormat("YYYY-MM-DD").parse(chan
              .getHTTPLastModified());
    } catch (Exception x)
    {
    }
    ;
    if (chan != null && chan.getItems() != null)
    {
      Cache.log.debug("Scanning news items: newsnew=" + newsnew
              + " and lastDate is " + lastDate);
      for (Item i : (List<Item>) chan.getItems())
      {
        boolean isread = lastDate == null ? false
                : (i.getPublishDate() != null && !lastDate.before(i
                        .getPublishDate()));

        if (!updating || updateItems)
        {
          newsnew |= !isread;
        }
        if (updateItems)
        {
          i.setRead(isread);
        }
        if (i.getPublishDate() != null && !i.isRead())
        {
          if (earliest == null || earliest.after(i.getPublishDate()))
          {
            earliest = i.getPublishDate();
          }
        }
      }
    }
    if (!updateItems && !updating && lastDate == null)
    {
      lastDate = earliest;
    }
    return newsnew;
  }

  java.util.Date lastDate = null;

  private void loadLastM()
  {
    lastDate = Cache.getDateProperty("JALVIEW_NEWS_RSS_LASTMODIFIED");
  }

  private void saveLastM(Item item)
  {
    if (item != null)
    {
      if (item.getPublishDate() != null)
      {
        if (lastDate == null || item.getPublishDate().after(lastDate))
        {
          lastDate = item.getPublishDate();
        }
      }

      if (_channelModel.getElementAt(0) != null)
      {
        checkForNew((Channel) _channelModel.getElementAt(0), false);
      }
      if (lastDate != null)
      {
        jalview.bin.Cache.setDateProperty("JALVIEW_NEWS_RSS_LASTMODIFIED",
                lastDate);
        jalview.bin.Cache.log.info("Saved last read date as "
                + jalview.bin.Cache.date_format.format(lastDate));

      }
    }
  }

  private void jbInit() throws Exception
  {
    setLayout(layoutMain);
    panelMain.setLayout(borderLayout1);
    topPanel.setLayout(borderLayout5);
    bottomPanel.setLayout(borderLayout4);
    topBottomSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    topBottomSplitPane.setDividerLocation(100);
    topBottomSplitPane.setTopComponent(topPanel);
    topBottomSplitPane.setBottomComponent(bottomPanel);
    JScrollPane spTextDescription = new JScrollPane(textDescription);
    textDescription.setText("");
    statusBar.setText(" [Status] ");
    buttonRefresh.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        refreshNews();
      }
    });
    add(statusBar, BorderLayout.SOUTH);
    toolBar.add(buttonRefresh);
    toolBar.addSeparator();
    JLabel about = new JLabel(
            "brought to you by JSwingReader (jswingreader.sourceforge.net)");
    toolBar.add(about);
    toolBar.setFloatable(false);
    add(toolBar, BorderLayout.NORTH);
    panelMain.add(topBottomSplitPane, BorderLayout.CENTER);
    add(panelMain, BorderLayout.CENTER);
    JScrollPane spListItems = new JScrollPane(listItems);
    listItems
            .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    topPanel.add(spListItems, BorderLayout.CENTER);
    bottomPanel.add(spTextDescription, BorderLayout.CENTER);
    listChannels.setModel(_channelModel);

    listItems.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        listItems_mouseClicked(e);
      }
    });
    _popupItems = _buildItemsPopupMenu();
    _popupChannels = _buildChannelsPopupMenu();
    ContextMenuMouseAdapter popupAdapter = new ContextMenuMouseAdapter(
            _popupItems);
    ContextMenuMouseAdapter popupChannelsAdapter = new ContextMenuMouseAdapter(
            _popupChannels);
    listItems.addMouseListener(popupAdapter);
    listItems.setCellRenderer(new ItemsRenderer());
    lblChannels.setText("Channels");
  }

  private void postInit()
  {
    // clear the default hyperlink listener and replace with our own.
    for (HyperlinkListener hll : textDescription.getHyperlinkListeners())
    {
      textDescription.removeHyperlinkListener(hll);
    }
    textDescription.addHyperlinkListener(new HyperlinkListener()
    {
      public void hyperlinkUpdate(HyperlinkEvent e)
      {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
          Desktop.showUrl(e.getURL().toExternalForm());
        }
      }
    });

    listItems.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        if (e.getValueIsAdjusting() == false)
        {
          _itemsValueChanged(listItems);
        }
      }
    });
    listChannels.setSelectedIndex(1);
    _updateAllActions();
    _updateToolbarButtons();

    _itemTimer = new ItemReadTimer(listChannels, listItems);
    _itemsValueChanged(listItems);
  }

  public class LaunchJvBrowserOnItem extends AbstractAction implements
          UpdatableAction
  {
    JList _listItems = null;

    public LaunchJvBrowserOnItem(JList listItems)
    {
      super("Open in Browser");
      this.putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_O));
      this.putValue(Action.LONG_DESCRIPTION, "Open in Browser");
      _listItems = listItems;
    }

    public void actionPerformed(ActionEvent e)
    {
      Object o = _listItems.getSelectedValue();
      if (o instanceof Item)
      {
        Item item = (Item) o;
        item.setRead(true);
        _listItems.repaint();

        Desktop.showUrl(item.getLink());
      }
    }

    public void update(Object o)
    {
      setEnabled(true);
      if (_listItems == null || _listItems.getModel().getSize() == 0)
      {
        setEnabled(false);
      }
      else if (_listItems.getSelectedIndex() == -1)
      {
        setEnabled(false);
      }
    }

  }

  private JPopupMenu _buildItemsPopupMenu()
  {
    JPopupMenu popup = new JPopupMenu();
    popup.add(new JMenuItem(new LaunchJvBrowserOnItem(listItems)));
    popup.addSeparator();
    popup.add(new JMenuItem(new MarkItemAsRead(listItems)));
    popup.add(new JMenuItem(new MarkItemAsUnread(listItems)));
    return popup;
  }

  private JPopupMenu _buildChannelsPopupMenu()
  {
    JPopupMenu popup = new JPopupMenu();
    popup.add(new JMenuItem(new MarkChannelAsRead(listChannels, listItems)));
    popup.add(new JMenuItem(
            new MarkChannelAsUnread(listChannels, listItems)));
    return popup;
  }

  private void initItems(Channel channel)
  {
    if (channel == null)
    {
      channel = new Channel();
    }
    if (!channel.isOpen() && channel.getURL() != null)
    {
      try
      {
        SimpleRSSParser.parse(channel);
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    DefaultListModel itemsModel = (DefaultListModel) listItems.getModel();
    itemsModel.clear();
    Iterator iter = (channel.getItems() != null) ? channel.getItems()
            .iterator() : Collections.EMPTY_LIST.iterator();
    while (iter.hasNext())
    {
      itemsModel.addElement(iter.next());
    }
    if (itemsModel.getSize() > 0)
    {
      listItems.setSelectedIndex(0);
      _itemsValueChanged(listItems);
    }
    setStatusBarText(channel.getURL());
    _updateAllActions();
  }

  private void _itemsValueChanged(JList itemList)
  {
    Item item = (Item) itemList.getSelectedValue();
    if (item == null)
    {
      if (itemList.getModel().getSize() > 0)
      {
        item = (Item) itemList.getModel().getElementAt(0);
      }
      if (item == null)
      {
        item = new Item();
      }
      else
      {
        itemList.setSelectedIndex(0);
      }
    }

    if (_itemTimer != null)
    {
      // prefer a shorter delay than 5s
      _itemTimer.setDelay(300);
      _itemTimer.start();
      _itemTimer.setLastItem(item);
      final Item lastitem = item;
      _itemTimer.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          saveLastM(lastitem);
        }
      });
    }

    setStatusBarText(item.getLink());
    textDescription.setBlogText(item);
    _updateAllActions();
  }

  public void setStatusBarText(String text)
  {
    statusBar.setText(text);
  }

  private void _updateAllActions()
  {
    Iterator iter = _updatableActions.iterator();
    while (iter.hasNext())
    {
      UpdatableAction action = (UpdatableAction) iter.next();
      action.update(this);
    }
  }

  private void _updateToolbarButtons()
  {
    Map general = (Map) Main.getPreferences().get("general");
    if (general == null)
    {
      return;
    }

    Component[] components = toolBar.getComponents();
    for (int i = 0; i < components.length; i++)
    {
      Component component = components[i];
      if (component instanceof JButton)
      {
        JButton button = (JButton) component;
        if (Boolean.toString(false).equals(general.get("useToolBarText")))
        {
          // Remove the text if preferences state no toolbar text
          button.setText("");
        }
        if (Boolean.toString(true).equals(general.get("radioTextBelow")))
        {
          button.setVerticalTextPosition(AbstractButton.BOTTOM);
          button.setHorizontalTextPosition(AbstractButton.CENTER);
        }
        else if (Boolean.toString(true).equals(
                general.get("radioTextRight")))
        {
          button.setVerticalTextPosition(AbstractButton.CENTER);
          button.setHorizontalTextPosition(AbstractButton.RIGHT);
        }
      }
    }
  }

  private void listItems_mouseClicked(MouseEvent e)
  {
    if (e.getClickCount() == 2 && e.getModifiersEx() == MouseEvent.NOBUTTON)
    {
      Item item = (Item) listItems.getSelectedValue();
      item.setRead(true);
      saveLastM(item);
      if (_itemTimer != null)
      {
        _itemTimer.stop();
      }

      Action action = new LaunchJvBrowserOnItem(listItems);
      ActionEvent event = new ActionEvent(this,
              ActionEvent.ACTION_PERFORMED, "LaunchBrowserOnItem");
      action.actionPerformed(event);
    }
  }

  /**
   * force the news panel to refresh
   */
  public void refreshNews()
  {
    try
    {
      initItems((Channel) _channelModel.getElementAt(0));

    } catch (Exception x)
    {
    }
  }

  public static void main(String args[])
  {
    // this tests the detection of new news based on the last read date stored
    // in jalview properties
    jalview.bin.Cache.loadProperties(null);
    jalview.bin.Cache.initLogger();
    // test will advance read date each time
    Calendar today = Calendar.getInstance(), lastread = Calendar
            .getInstance();
    lastread.set(1983, 01, 01);
    while (lastread.before(today))
    {
      Cache.setDateProperty("JALVIEW_NEWS_RSS_LASTMODIFIED",
              lastread.getTime());
      BlogReader me = new BlogReader();
      System.out.println("Set last date to "
              + jalview.bin.Cache.date_format.format(lastread.getTime()));
      if (me.isNewsNew())
      {
        Cache.log.info("There is news to read.");
      }
      else
      {
        Cache.log.info("There is no new news.");
        me.xf.setTitle("Testing : Last read is " + me.lastDate);
        me.showNews();
        me.xf.toFront();
      }
      Cache.log.info("Waiting for closure.");
      do
      {
        try
        {
          Thread.sleep(300);
        } catch (InterruptedException x)
        {
        }
        ;
      } while (me.isVisible());

      if (me.isNewsNew())
      {
        Cache.log.info("Still new news after reader displayed.");
      }
      if (lastread.getTime().before(me.lastDate))
      {
        Cache.log.info("The news was read.");
        lastread.setTime(me.lastDate);
      }
      else
      {
        lastread.add(Calendar.MONTH, 1);
      }

    }
  }

  boolean isNewsNew()
  {
    return newsnew;
  }
}

class ChannelsRenderer extends DefaultListCellRenderer
{
  private final static Icon _icon = new ImageIcon(
          Main.class.getResource("image/ComposeMail16.gif"));

  public Component getListCellRendererComponent(JList list, Object value,
          int index, boolean isSelected, boolean cellHasFocus)
  {
    JLabel component = (JLabel) super.getListCellRendererComponent(list,
            value, index, isSelected, cellHasFocus);
    component.setIcon(ChannelsRenderer._icon);
    if (value instanceof Channel)
    {
      Channel channel = (Channel) value;
      component.setText(channel.getTitle() + " ("
              + channel.getUnreadItemCount() + ")");
      component.setToolTipText(channel.getURL());
    }
    return component;
  }
}

class ItemsRenderer extends DefaultListCellRenderer
{
  private final static Icon _icon = new ImageIcon(
          Main.class.getResource("image/ComposeMail16.gif"));

  public Component getListCellRendererComponent(JList list, Object value,
          int index, boolean isSelected, boolean cellHasFocus)
  {
    JLabel component = (JLabel) super.getListCellRendererComponent(list,
            value, index, isSelected, cellHasFocus);
    component.setIcon(ItemsRenderer._icon);
    if (value instanceof Item)
    {
      Item item = (Item) value;
      if (item.getPublishDate() != null)
      {
        component.setText(DateFormat.getDateInstance().format(
                item.getPublishDate())
                + " " + item.getTitle());
      }
      component.setToolTipText(item.getLink());
      if (!item.isRead())
      {
        component.setFont(component.getFont().deriveFont(Font.BOLD));
      }
      else
      {
        component.setFont(component.getFont().deriveFont(Font.PLAIN));
      }
    }
    return component;
  }
}
