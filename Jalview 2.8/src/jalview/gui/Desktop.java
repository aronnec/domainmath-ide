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
import jalview.io.FileLoader;
import jalview.io.FormatAdapter;
import jalview.io.IdentifyFile;
import jalview.io.JalviewFileChooser;
import jalview.io.JalviewFileView;
import jalview.ws.params.ParamManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javax.swing.DefaultDesktopManager;
import javax.swing.DesktopManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.HyperlinkEvent.EventType;

/**
 * Jalview Desktop
 * 
 * 
 * @author $author$
 * @version $Revision: 1.155 $
 */
public class Desktop extends jalview.jbgui.GDesktop implements
        DropTargetListener, ClipboardOwner, IProgressIndicator,
        jalview.api.StructureSelectionManagerProvider
{

  private JalviewChangeSupport changeSupport = new JalviewChangeSupport();

  /**
   * news reader - null if it was never started.
   */
  private BlogReader jvnews = null;

  /**
   * @param listener
   * @see jalview.gui.JalviewChangeSupport#addJalviewPropertyChangeListener(java.beans.PropertyChangeListener)
   */
  public void addJalviewPropertyChangeListener(
          PropertyChangeListener listener)
  {
    changeSupport.addJalviewPropertyChangeListener(listener);
  }

  /**
   * @param propertyName
   * @param listener
   * @see jalview.gui.JalviewChangeSupport#addJalviewPropertyChangeListener(java.lang.String,
   *      java.beans.PropertyChangeListener)
   */
  public void addJalviewPropertyChangeListener(String propertyName,
          PropertyChangeListener listener)
  {
    changeSupport.addJalviewPropertyChangeListener(propertyName, listener);
  }

  /**
   * @param propertyName
   * @param listener
   * @see jalview.gui.JalviewChangeSupport#removeJalviewPropertyChangeListener(java.lang.String,
   *      java.beans.PropertyChangeListener)
   */
  public void removeJalviewPropertyChangeListener(String propertyName,
          PropertyChangeListener listener)
  {
    changeSupport.removeJalviewPropertyChangeListener(propertyName,
            listener);
  }

  /** Singleton Desktop instance */
  public static Desktop instance;

  public static MyDesktopPane desktop;

  static int openFrameCount = 0;

  static final int xOffset = 30;

  static final int yOffset = 30;

  public static jalview.ws.jws1.Discoverer discoverer;

  public static Object[] jalviewClipboard;

  public static boolean internalCopy = false;

  static int fileLoadingCount = 0;

  class MyDesktopManager implements DesktopManager
  {

    private DesktopManager delegate;

    public MyDesktopManager(DesktopManager delegate)
    {
      this.delegate = delegate;
    }

      @Override
    public void activateFrame(JInternalFrame f)
    {
      try
      {
        delegate.activateFrame(f);
      } catch (NullPointerException npe)
      {
        Point p = getMousePosition();
        instance.showPasteMenu(p.x, p.y);
      }
    }

      @Override
    public void beginDraggingFrame(JComponent f)
    {
      delegate.beginDraggingFrame(f);
    }

      @Override
    public void beginResizingFrame(JComponent f, int direction)
    {
      delegate.beginResizingFrame(f, direction);
    }

      @Override
    public void closeFrame(JInternalFrame f)
    {
      delegate.closeFrame(f);
    }

    public void deactivateFrame(JInternalFrame f)
    {
      delegate.deactivateFrame(f);
    }

    public void deiconifyFrame(JInternalFrame f)
    {
      delegate.deiconifyFrame(f);
    }

    public void dragFrame(JComponent f, int newX, int newY)
    {
      if (newY < 0)
      {
        newY = 0;
      }
      delegate.dragFrame(f, newX, newY);
    }

    public void endDraggingFrame(JComponent f)
    {
      delegate.endDraggingFrame(f);
    }

    public void endResizingFrame(JComponent f)
    {
      delegate.endResizingFrame(f);
    }

    public void iconifyFrame(JInternalFrame f)
    {
      delegate.iconifyFrame(f);
    }

    public void maximizeFrame(JInternalFrame f)
    {
      delegate.maximizeFrame(f);
    }

    public void minimizeFrame(JInternalFrame f)
    {
      delegate.minimizeFrame(f);
    }

    public void openFrame(JInternalFrame f)
    {
      delegate.openFrame(f);
    }

    public void resizeFrame(JComponent f, int newX, int newY, int newWidth,
            int newHeight)
    {
      Rectangle b = desktop.getBounds();
      if (newY < 0)
      {
        newY = 0;
      }
      delegate.resizeFrame(f, newX, newY, newWidth, newHeight);
    }

    public void setBoundsForFrame(JComponent f, int newX, int newY,
            int newWidth, int newHeight)
    {
      delegate.setBoundsForFrame(f, newX, newY, newWidth, newHeight);
    }

    // All other methods, simply delegate

  }

  /**
   * Creates a new Desktop object.
   */
  public Desktop()
  {
    /**
     * A note to implementors. It is ESSENTIAL that any activities that might
     * block are spawned off as threads rather than waited for during this
     * constructor.
     */
    instance = this;
    doVamsasClientCheck();
    doGroovyCheck();

    setTitle("Jalview " + jalview.bin.Cache.getProperty("VERSION"));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    boolean selmemusage = jalview.bin.Cache.getDefault("SHOW_MEMUSAGE",
            false);
    boolean showjconsole = jalview.bin.Cache.getDefault(
            "SHOW_JAVA_CONSOLE", false);
    desktop = new MyDesktopPane(selmemusage);
    showMemusage.setSelected(selmemusage);
    desktop.setBackground(Color.white);
    getContentPane().setLayout(new BorderLayout());
    // alternate config - have scrollbars - see notes in JAL-153
    // JScrollPane sp = new JScrollPane();
    // sp.getViewport().setView(desktop);
    // getContentPane().add(sp, BorderLayout.CENTER);
    getContentPane().add(desktop, BorderLayout.CENTER);
    desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    // This line prevents Windows Look&Feel resizing all new windows to maximum
    // if previous window was maximised
    desktop.setDesktopManager(new MyDesktopManager(
            new DefaultDesktopManager()));

    Rectangle dims = getLastKnownDimensions("");
    if (dims != null)
    {
      setBounds(dims);
    }
    else
    {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((int) (screenSize.width - 900) / 2,
              (int) (screenSize.height - 650) / 2, 900, 650);
    }
    jconsole = new Console(this, showjconsole);
    // add essential build information
    jconsole.setHeader("Jalview Desktop "
            + jalview.bin.Cache.getProperty("VERSION") + "\n"
            + "Build Date: "
            + jalview.bin.Cache.getDefault("BUILD_DATE", "unknown") + "\n"
            + "Java version: " + System.getProperty("java.version") + "\n"
            + System.getProperty("os.arch") + " "
            + System.getProperty("os.name") + " "
            + System.getProperty("os.version"));

    showConsole(showjconsole);

    showNews.setVisible(false);

    this.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent evt)
      {
        quit();
      }
    });

    MouseAdapter ma;
    this.addMouseListener(ma = new MouseAdapter()
    {
      public void mousePressed(MouseEvent evt)
      {
        if (SwingUtilities.isRightMouseButton(evt))
        {
          showPasteMenu(evt.getX(), evt.getY());
        }
      }
    });
    desktop.addMouseListener(ma);

    this.addFocusListener(new FocusListener()
    {

      @Override
      public void focusLost(FocusEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void focusGained(FocusEvent e)
      {
        Cache.log.debug("Relaying windows after focus gain");
        // make sure that we sort windows properly after we gain focus
        instance.relayerWindows();
      }
    });
    this.setDropTarget(new java.awt.dnd.DropTarget(desktop, this));
    // Spawn a thread that shows the splashscreen
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        new SplashScreen();
      }
    });

    // displayed.
    // Thread off a new instance of the file chooser - this reduces the time it
    // takes to open it later on.
    new Thread(new Runnable()
    {
      public void run()
      {
        Cache.log.debug("Filechooser init thread started.");
        JalviewFileChooser chooser = new JalviewFileChooser(
                jalview.bin.Cache.getProperty("LAST_DIRECTORY"),
                jalview.io.AppletFormatAdapter.READABLE_EXTENSIONS,
                jalview.io.AppletFormatAdapter.READABLE_FNAMES,
                jalview.bin.Cache.getProperty("DEFAULT_FILE_FORMAT"));
        Cache.log.debug("Filechooser init thread finished.");
      }
    }).start();
    // Add the service change listener
    changeSupport.addJalviewPropertyChangeListener("services",
            new PropertyChangeListener()
            {

              @Override
              public void propertyChange(PropertyChangeEvent evt)
              {
                Cache.log.debug("Firing service changed event for "
                        + evt.getNewValue());
                JalviewServicesChanged(evt);
              }

            });
  }

  public void checkForNews()
  {
    final Desktop me = this;
    // Thread off the news reader, in case there are connection problems.
    addDialogThread(new Runnable()
    {
      @Override
      public void run()
      {
        Cache.log.debug("Starting news thread.");

        jvnews = new BlogReader(me);
        showNews.setVisible(true);
        Cache.log.debug("Completed news thread.");
      }
    });
  }

  protected void showNews_actionPerformed(ActionEvent e)
  {
    showNews(showNews.isSelected());
  }

  void showNews(boolean visible)
  {
    {
      Cache.log.debug((visible ? "Showing" : "Hiding") + " news.");
      showNews.setSelected(visible);
      if (visible && !jvnews.isVisible())
      {
        new Thread(new Runnable()
        {
          @Override
          public void run()
          {
            long instance = System.currentTimeMillis();
            Desktop.instance.setProgressBar("Refreshing news", instance);
            jvnews.refreshNews();
            Desktop.instance.setProgressBar(null, instance);
            jvnews.showNews();
          }
        }).start();
      }
    }
  }

  /**
   * recover the last known dimensions for a jalview window
   * 
   * @param windowName
   *          - empty string is desktop, all other windows have unique prefix
   * @return null or last known dimensions scaled to current geometry (if last
   *         window geom was known)
   */
  Rectangle getLastKnownDimensions(String windowName)
  {
    // TODO: lock aspect ratio for scaling desktop Bug #0058199
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    String x = jalview.bin.Cache.getProperty(windowName + "SCREEN_X");
    String y = jalview.bin.Cache.getProperty(windowName + "SCREEN_Y");
    String width = jalview.bin.Cache.getProperty(windowName
            + "SCREEN_WIDTH");
    String height = jalview.bin.Cache.getProperty(windowName
            + "SCREEN_HEIGHT");
    if ((x != null) && (y != null) && (width != null) && (height != null))
    {
      int ix = Integer.parseInt(x), iy = Integer.parseInt(y), iw = Integer
              .parseInt(width), ih = Integer.parseInt(height);
      if (jalview.bin.Cache.getProperty("SCREENGEOMETRY_WIDTH") != null)
      {
        // attempt #1 - try to cope with change in screen geometry - this
        // version doesn't preserve original jv aspect ratio.
        // take ratio of current screen size vs original screen size.
        double sw = ((1f * screenSize.width) / (1f * Integer
                .parseInt(jalview.bin.Cache
                        .getProperty("SCREENGEOMETRY_WIDTH"))));
        double sh = ((1f * screenSize.height) / (1f * Integer
                .parseInt(jalview.bin.Cache
                        .getProperty("SCREENGEOMETRY_HEIGHT"))));
        // rescale the bounds depending upon the current screen geometry.
        ix = (int) (ix * sw);
        iw = (int) (iw * sw);
        iy = (int) (iy * sh);
        ih = (int) (ih * sh);
        while (ix >= screenSize.width)
        {
          jalview.bin.Cache.log
                  .debug("Window geometry location recall error: shifting horizontal to within screenbounds.");
          ix -= screenSize.width;
        }
        while (iy >= screenSize.height)
        {
          jalview.bin.Cache.log
                  .debug("Window geometry location recall error: shifting vertical to within screenbounds.");
          iy -= screenSize.height;
        }
        jalview.bin.Cache.log.debug("Got last known dimensions for "
                + windowName + ": x:" + ix + " y:" + iy + " width:" + iw
                + " height:" + ih);
      }
      // return dimensions for new instance
      return new Rectangle(ix, iy, iw, ih);
    }
    return null;
  }

  private void doVamsasClientCheck()
  {
    if (jalview.bin.Cache.vamsasJarsPresent())
    {
      setupVamsasDisconnectedGui();
      VamsasMenu.setVisible(true);
      final Desktop us = this;
      VamsasMenu.addMenuListener(new MenuListener()
      {
        // this listener remembers when the menu was first selected, and
        // doesn't rebuild the session list until it has been cleared and
        // reselected again.
        boolean refresh = true;

        public void menuCanceled(MenuEvent e)
        {
          refresh = true;
        }

        public void menuDeselected(MenuEvent e)
        {
          refresh = true;
        }

        public void menuSelected(MenuEvent e)
        {
          if (refresh)
          {
            us.buildVamsasStMenu();
            refresh = false;
          }
        }
      });
      vamsasStart.setVisible(true);
    }
  }

  void showPasteMenu(int x, int y)
  {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem item = new JMenuItem("Paste To New Window");
    item.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent evt)
      {
        paste();
      }
    });

    popup.add(item);
    popup.show(this, x, y);
  }

  public void paste()
  {
    try
    {
      Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable contents = c.getContents(this);

      if (contents != null)
      {
        String file = (String) contents
                .getTransferData(DataFlavor.stringFlavor);

        String format = new IdentifyFile().Identify(file,
                FormatAdapter.PASTE);

        new FileLoader().LoadFile(file, FormatAdapter.PASTE, format);

      }
    } catch (Exception ex)
    {
      System.out
              .println("Unable to paste alignment from system clipboard:\n"
                      + ex);
    }
  }

  /**
   * Adds and opens the given frame to the desktop
   * 
   * @param frame
   *          DOCUMENT ME!
   * @param title
   *          DOCUMENT ME!
   * @param w
   *          DOCUMENT ME!
   * @param h
   *          DOCUMENT ME!
   */
  public static synchronized void addInternalFrame(
          final JInternalFrame frame, String title, int w, int h)
  {
    addInternalFrame(frame, title, w, h, true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param frame
   *          DOCUMENT ME!
   * @param title
   *          DOCUMENT ME!
   * @param w
   *          DOCUMENT ME!
   * @param h
   *          DOCUMENT ME!
   * @param resizable
   *          DOCUMENT ME!
   */
  public static synchronized void addInternalFrame(
          final JInternalFrame frame, String title, int w, int h,
          boolean resizable)
  {

    // TODO: allow callers to determine X and Y position of frame (eg. via
    // bounds object).
    // TODO: consider fixing method to update entries in the window submenu with
    // the current window title

    frame.setTitle(title);
    if (frame.getWidth() < 1 || frame.getHeight() < 1)
    {
      frame.setSize(w, h);
    }
    // THIS IS A PUBLIC STATIC METHOD, SO IT MAY BE CALLED EVEN IN
    // A HEADLESS STATE WHEN NO DESKTOP EXISTS. MUST RETURN
    // IF JALVIEW IS RUNNING HEADLESS
    // ///////////////////////////////////////////////
    if (System.getProperty("java.awt.headless") != null
            && System.getProperty("java.awt.headless").equals("true"))
    {
      return;
    }

    openFrameCount++;

    frame.setVisible(true);
    frame.setClosable(true);
    frame.setResizable(resizable);
    frame.setMaximizable(resizable);
    frame.setIconifiable(resizable);
    frame.setFrameIcon(null);

    if (frame.getX() < 1 && frame.getY() < 1)
    {
      frame.setLocation(xOffset * openFrameCount, yOffset
              * ((openFrameCount - 1) % 10) + yOffset);
    }

    final JMenuItem menuItem = new JMenuItem(title);
    frame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter()
    {
      public void internalFrameActivated(
              javax.swing.event.InternalFrameEvent evt)
      {
        JInternalFrame itf = desktop.getSelectedFrame();
        if (itf != null)
        {
          itf.requestFocus();
        }

      }

      public void internalFrameClosed(
              javax.swing.event.InternalFrameEvent evt)
      {
        PaintRefresher.RemoveComponent(frame);
        openFrameCount--;
        windowMenu.remove(menuItem);
        JInternalFrame itf = desktop.getSelectedFrame();
        if (itf != null)
        {
          itf.requestFocus();
        }
        System.gc();
      };
    });

    menuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        try
        {
          frame.setSelected(true);
          frame.setIcon(false);
        } catch (java.beans.PropertyVetoException ex)
        {

        }
      }
    });
    menuItem.addMouseListener(new MouseListener()
    {

      @Override
      public void mouseReleased(MouseEvent e)
      {
      }

      @Override
      public void mousePressed(MouseEvent e)
      {
      }

      @Override
      public void mouseExited(MouseEvent e)
      {
        try
        {
          frame.setSelected(false);
        } catch (PropertyVetoException e1)
        {
        }
      }

      @Override
      public void mouseEntered(MouseEvent e)
      {
        try
        {
          frame.setSelected(true);
        } catch (PropertyVetoException e1)
        {
        }
      }

      @Override
      public void mouseClicked(MouseEvent e)
      {

      }
    });

    windowMenu.add(menuItem);

    desktop.add(frame);
    frame.toFront();
    try
    {
      frame.setSelected(true);
      frame.requestFocus();
    } catch (java.beans.PropertyVetoException ve)
    {
    } catch (java.lang.ClassCastException cex)
    {
      Cache.log
              .warn("Squashed a possible GUI implementation error. If you can recreate this, please look at http://issues.jalview.org/browse/JAL-869",
                      cex);
    }
  }

  public void lostOwnership(Clipboard clipboard, Transferable contents)
  {
    if (!internalCopy)
    {
      Desktop.jalviewClipboard = null;
    }

    internalCopy = false;
  }

  public void dragEnter(DropTargetDragEvent evt)
  {
  }

  public void dragExit(DropTargetEvent evt)
  {
  }

  public void dragOver(DropTargetDragEvent evt)
  {
  }

  public void dropActionChanged(DropTargetDragEvent evt)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  public void drop(DropTargetDropEvent evt)
  {
    Transferable t = evt.getTransferable();
    java.util.List files = null;
    java.util.List protocols = null;

    try
    {
      DataFlavor uriListFlavor = new DataFlavor(
              "text/uri-list;class=java.lang.String");
      if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
      {
        // Works on Windows and MacOSX
        evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        files = (java.util.List) t
                .getTransferData(DataFlavor.javaFileListFlavor);
      }
      else if (t.isDataFlavorSupported(uriListFlavor))
      {
        // This is used by Unix drag system
        evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        String data = (String) t.getTransferData(uriListFlavor);
        files = new java.util.ArrayList(1);
        protocols = new java.util.ArrayList(1);
        for (java.util.StringTokenizer st = new java.util.StringTokenizer(
                data, "\r\n"); st.hasMoreTokens();)
        {
          String s = st.nextToken();
          if (s.startsWith("#"))
          {
            // the line is a comment (as per the RFC 2483)
            continue;
          }
          java.net.URI uri = new java.net.URI(s);
          if (uri.getScheme().toLowerCase().startsWith("http"))
          {
            protocols.add(FormatAdapter.URL);
            files.add(uri.toString());
          }
          else
          {
            // otherwise preserve old behaviour: catch all for file objects
            java.io.File file = new java.io.File(uri);
            protocols.add(FormatAdapter.FILE);
            files.add(file.toString());
          }
        }
      }
    } catch (Exception e)
    {
    }

    if (files != null)
    {
      try
      {
        for (int i = 0; i < files.size(); i++)
        {
          String file = files.get(i).toString();
          String protocol = (protocols == null) ? FormatAdapter.FILE
                  : (String) protocols.get(i);
          String format = null;

          if (file.endsWith(".jar"))
          {
            format = "Jalview";

          }
          else
          {
            format = new IdentifyFile().Identify(file, protocol);
          }

          new FileLoader().LoadFile(file, protocol, format);

        }
      } catch (Exception ex)
      {
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void inputLocalFileMenuItem_actionPerformed(AlignViewport viewport)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"),
            jalview.io.AppletFormatAdapter.READABLE_EXTENSIONS,
            jalview.io.AppletFormatAdapter.READABLE_FNAMES,
            jalview.bin.Cache.getProperty("DEFAULT_FILE_FORMAT"));

    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Open local file");
    chooser.setToolTipText("Open");

    int value = chooser.showOpenDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      String choice = chooser.getSelectedFile().getPath();
      jalview.bin.Cache.setProperty("LAST_DIRECTORY", chooser
              .getSelectedFile().getParent());

      String format = null;
      if (chooser.getSelectedFormat().equals("Jalview"))
      {
        format = "Jalview";
      }
      else
      {
        format = new IdentifyFile().Identify(choice, FormatAdapter.FILE);
      }

      if (viewport != null)
      {
        new FileLoader().LoadFile(viewport, choice, FormatAdapter.FILE,
                format);
      }
      else
      {
        new FileLoader().LoadFile(choice, FormatAdapter.FILE, format);
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void inputURLMenuItem_actionPerformed(AlignViewport viewport)
  {
    // This construct allows us to have a wider textfield
    // for viewing
    JLabel label = new JLabel("Enter URL of Input File");
    final JComboBox history = new JComboBox();

    JPanel panel = new JPanel(new GridLayout(2, 1));
    panel.add(label);
    panel.add(history);
    history.setPreferredSize(new Dimension(400, 20));
    history.setEditable(true);
    history.addItem("http://www.");

    String historyItems = jalview.bin.Cache.getProperty("RECENT_URL");

    StringTokenizer st;

    if (historyItems != null)
    {
      st = new StringTokenizer(historyItems, "\t");

      while (st.hasMoreTokens())
      {
        history.addItem(st.nextElement());
      }
    }

    int reply = JOptionPane.showInternalConfirmDialog(desktop, panel,
            "Input Alignment From URL", JOptionPane.OK_CANCEL_OPTION);

    if (reply != JOptionPane.OK_OPTION)
    {
      return;
    }

    String url = history.getSelectedItem().toString();

    if (url.toLowerCase().endsWith(".jar"))
    {
      if (viewport != null)
      {
        new FileLoader().LoadFile(viewport, url, FormatAdapter.URL,
                "Jalview");
      }
      else
      {
        new FileLoader().LoadFile(url, FormatAdapter.URL, "Jalview");
      }
    }
    else
    {
      String format = new IdentifyFile().Identify(url, FormatAdapter.URL);

      if (format.equals("URL NOT FOUND"))
      {
        JOptionPane.showInternalMessageDialog(Desktop.desktop,
                "Couldn't locate " + url, "URL not found",
                JOptionPane.WARNING_MESSAGE);

        return;
      }

      if (viewport != null)
      {
        new FileLoader().LoadFile(viewport, url, FormatAdapter.URL, format);
      }
      else
      {
        new FileLoader().LoadFile(url, FormatAdapter.URL, format);
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void inputTextboxMenuItem_actionPerformed(AlignViewport viewport)
  {
    CutAndPasteTransfer cap = new CutAndPasteTransfer();
    cap.setForInput(viewport);
    Desktop.addInternalFrame(cap, "Cut & Paste Alignment File", 600, 500);
  }

  /*
   * Exit the program
   */
  public void quit()
  {
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    jalview.bin.Cache
            .setProperty("SCREENGEOMETRY_WIDTH", screen.width + "");
    jalview.bin.Cache.setProperty("SCREENGEOMETRY_HEIGHT", screen.height
            + "");
    storeLastKnownDimensions("", new Rectangle(getBounds().x,
            getBounds().y, getWidth(), getHeight()));

    if (jconsole != null)
    {
      storeLastKnownDimensions("JAVA_CONSOLE_", jconsole.getBounds());
      jconsole.stopConsole();
    }
    if (jvnews != null)
    {
      storeLastKnownDimensions("JALVIEW_RSS_WINDOW_", jvnews.getBounds());

    }
    if (dialogExecutor != null)
    {
      dialogExecutor.shutdownNow();
    }

    dispose();
  }

  private void storeLastKnownDimensions(String string, Rectangle jc)
  {
    jalview.bin.Cache.log.debug("Storing last known dimensions for "
            + string + ": x:" + jc.x + " y:" + jc.y + " width:" + jc.width
            + " height:" + jc.height);

    jalview.bin.Cache.setProperty(string + "SCREEN_X", jc.x + "");
    jalview.bin.Cache.setProperty(string + "SCREEN_Y", jc.y + "");
    jalview.bin.Cache.setProperty(string + "SCREEN_WIDTH", jc.width + "");
    jalview.bin.Cache.setProperty(string + "SCREEN_HEIGHT", jc.height + "");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void aboutMenuItem_actionPerformed(ActionEvent e)
  {
    // StringBuffer message = getAboutMessage(false);
    // JOptionPane.showInternalMessageDialog(Desktop.desktop,
    //
    // message.toString(), "About Jalview", JOptionPane.INFORMATION_MESSAGE);
    new Thread(new Runnable()
    {
      public void run()
      {
        new SplashScreen(true);
      }
    }).start();
  }

  public StringBuffer getAboutMessage(boolean shortv)
  {
    StringBuffer message = new StringBuffer();
    message.append("<html>");
    if (shortv)
    {
      message.append("<h1><strong>Version: "
              + jalview.bin.Cache.getProperty("VERSION")
              + "</strong></h1><br>");
      message.append("<strong>Last Updated: <em>"
              + jalview.bin.Cache.getDefault("BUILD_DATE", "unknown")
              + "</em></strong>");

    }
    else
    {

      message.append("<strong>Version "
              + jalview.bin.Cache.getProperty("VERSION")
              + "; last updated: "
              + jalview.bin.Cache.getDefault("BUILD_DATE", "unknown"));
    }

    if (jalview.bin.Cache.getDefault("LATEST_VERSION", "Checking").equals(
            "Checking"))
    {
      message.append("<br>...Checking latest version...</br>");
    }
    else if (!jalview.bin.Cache.getDefault("LATEST_VERSION", "Checking")
            .equals(jalview.bin.Cache.getProperty("VERSION")))
    {
      boolean red = false;
      if (jalview.bin.Cache.getProperty("VERSION").toLowerCase()
              .indexOf("automated build") == -1)
      {
        red = true;
        // Displayed when code version and jnlp version do not match and code
        // version is not a development build
        message.append("<div style=\"color: #FF0000;font-style: bold;\">");
      }

      message.append("<br>!! Version "
              + jalview.bin.Cache.getDefault("LATEST_VERSION",
                      "..Checking..")
              + " is available for download from "
              + jalview.bin.Cache.getDefault("www.jalview.org",
                      "http://www.jalview.org") + " !!");
      if (red)
      {
        message.append("</div>");
      }
    }
    message.append("<br>Authors:  "
            + jalview.bin.Cache
                    .getDefault(
                            "AUTHORNAMES",
                            "Jim Procter, Andrew Waterhouse, Jan Engelhardt, Lauren Lui, Michele Clamp, James Cuff, Steve Searle, David Martin & Geoff Barton")
            + "<br>Development managed by The Barton Group, University of Dundee, Scotland, UK.<br>"
            + "<br>For help, see the FAQ at <a href=\"http://www.jalview.org\">www.jalview.org</a> and/or join the jalview-discuss@jalview.org mailing list"
            + "<br>If  you use Jalview, please cite:"
            + "<br>Waterhouse, A.M., Procter, J.B., Martin, D.M.A, Clamp, M. and Barton, G. J. (2009)"
            + "<br>Jalview Version 2 - a multiple sequence alignment editor and analysis workbench"
            + "<br>Bioinformatics doi: 10.1093/bioinformatics/btp033"
            + "</html>");
    return message;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void documentationMenuItem_actionPerformed(ActionEvent e)
  {
    try
    {
      ClassLoader cl = jalview.gui.Desktop.class.getClassLoader();
      java.net.URL url = javax.help.HelpSet.findHelpSet(cl, "help/help");
      javax.help.HelpSet hs = new javax.help.HelpSet(cl, url);

      javax.help.HelpBroker hb = hs.createHelpBroker();
      hb.setCurrentID("home");
      hb.setDisplayed(true);
    } catch (Exception ex)
    {
    }
  }

  public void closeAll_actionPerformed(ActionEvent e)
  {
    JInternalFrame[] frames = desktop.getAllFrames();
    for (int i = 0; i < frames.length; i++)
    {
      try
      {
        frames[i].setClosed(true);
      } catch (java.beans.PropertyVetoException ex)
      {
      }
    }
    System.out.println("ALL CLOSED");
    if (v_client != null)
    {
      // TODO clear binding to vamsas document objects on close_all

    }
  }

  public void raiseRelated_actionPerformed(ActionEvent e)
  {
    reorderAssociatedWindows(false, false);
  }

  public void minimizeAssociated_actionPerformed(ActionEvent e)
  {
    reorderAssociatedWindows(true, false);
  }

  void closeAssociatedWindows()
  {
    reorderAssociatedWindows(false, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @seejalview.jbgui.GDesktop#garbageCollect_actionPerformed(java.awt.event.
   * ActionEvent)
   */
  protected void garbageCollect_actionPerformed(ActionEvent e)
  {
    // We simply collect the garbage
    jalview.bin.Cache.log.debug("Collecting garbage...");
    System.gc();
    jalview.bin.Cache.log.debug("Finished garbage collection.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GDesktop#showMemusage_actionPerformed(java.awt.event.ActionEvent
   * )
   */
  protected void showMemusage_actionPerformed(ActionEvent e)
  {
    desktop.showMemoryUsage(showMemusage.isSelected());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GDesktop#showConsole_actionPerformed(java.awt.event.ActionEvent
   * )
   */
  protected void showConsole_actionPerformed(ActionEvent e)
  {
    showConsole(showConsole.isSelected());
  }

  Console jconsole = null;

  /**
   * control whether the java console is visible or not
   * 
   * @param selected
   */
  void showConsole(boolean selected)
  {
    showConsole.setSelected(selected);
    // TODO: decide if we should update properties file
    Cache.setProperty("SHOW_JAVA_CONSOLE", Boolean.valueOf(selected)
            .toString());
    jconsole.setVisible(selected);
  }

  void reorderAssociatedWindows(boolean minimize, boolean close)
  {
    JInternalFrame[] frames = desktop.getAllFrames();
    if (frames == null || frames.length < 1)
    {
      return;
    }

    AlignViewport source = null, target = null;
    if (frames[0] instanceof AlignFrame)
    {
      source = ((AlignFrame) frames[0]).getCurrentView();
    }
    else if (frames[0] instanceof TreePanel)
    {
      source = ((TreePanel) frames[0]).getViewPort();
    }
    else if (frames[0] instanceof PCAPanel)
    {
      source = ((PCAPanel) frames[0]).av;
    }
    else if (frames[0].getContentPane() instanceof PairwiseAlignPanel)
    {
      source = ((PairwiseAlignPanel) frames[0].getContentPane()).av;
    }

    if (source != null)
    {
      for (int i = 0; i < frames.length; i++)
      {
        target = null;
        if (frames[i] == null)
        {
          continue;
        }
        if (frames[i] instanceof AlignFrame)
        {
          target = ((AlignFrame) frames[i]).getCurrentView();
        }
        else if (frames[i] instanceof TreePanel)
        {
          target = ((TreePanel) frames[i]).getViewPort();
        }
        else if (frames[i] instanceof PCAPanel)
        {
          target = ((PCAPanel) frames[i]).av;
        }
        else if (frames[i].getContentPane() instanceof PairwiseAlignPanel)
        {
          target = ((PairwiseAlignPanel) frames[i].getContentPane()).av;
        }

        if (source == target)
        {
          try
          {
            if (close)
            {
              frames[i].setClosed(true);
            }
            else
            {
              frames[i].setIcon(minimize);
              if (!minimize)
              {
                frames[i].toFront();
              }
            }

          } catch (java.beans.PropertyVetoException ex)
          {
          }
        }
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void preferences_actionPerformed(ActionEvent e)
  {
    new Preferences();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void saveState_actionPerformed(ActionEvent e)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"), new String[]
            { "jar" }, new String[]
            { "Jalview Project" }, "Jalview Project");

    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Save State");

    int value = chooser.showSaveDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      final Desktop me = this;
      final java.io.File choice = chooser.getSelectedFile();
      new Thread(new Runnable()
      {
        public void run()
        {

          setProgressBar("Saving jalview project " + choice.getName(),
                  choice.hashCode());
          jalview.bin.Cache.setProperty("LAST_DIRECTORY",
                  choice.getParent());
          // TODO catch and handle errors for savestate
          // TODO prevent user from messing with the Desktop whilst we're saving
          try
          {
            new Jalview2XML().SaveState(choice);
          } catch (OutOfMemoryError oom)
          {
            new OOMWarning("Whilst saving current state to "
                    + choice.getName(), oom);
          } catch (Exception ex)
          {
            Cache.log.error(
                    "Problems whilst trying to save to " + choice.getName(),
                    ex);
            JOptionPane.showMessageDialog(
                    me,
                    "Error whilst saving current state to "
                            + choice.getName(), "Couldn't save project",
                    JOptionPane.WARNING_MESSAGE);
          }
          setProgressBar(null, choice.hashCode());
        }
      }).start();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void loadState_actionPerformed(ActionEvent e)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"), new String[]
            { "jar" }, new String[]
            { "Jalview Project" }, "Jalview Project");
    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Restore state");

    int value = chooser.showOpenDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      final String choice = chooser.getSelectedFile().getAbsolutePath();
      jalview.bin.Cache.setProperty("LAST_DIRECTORY", chooser
              .getSelectedFile().getParent());
      new Thread(new Runnable()
      {
        public void run()
        {
          setProgressBar("loading jalview project " + choice,
                  choice.hashCode());
          try
          {
            new Jalview2XML().LoadJalviewAlign(choice);
          } catch (OutOfMemoryError oom)
          {
            new OOMWarning("Whilst loading project from " + choice, oom);
          } catch (Exception ex)
          {
            Cache.log.error("Problems whilst loading project from "
                    + choice, ex);
            JOptionPane.showMessageDialog(Desktop.desktop,
                    "Error whilst loading project from " + choice,
                    "Couldn't load project", JOptionPane.WARNING_MESSAGE);
          }
          setProgressBar(null, choice.hashCode());
        }
      }).start();
    }
  }

  public void inputSequence_actionPerformed(ActionEvent e)
  {
    new SequenceFetcher(this);
  }

  JPanel progressPanel;

  ArrayList<JPanel> fileLoadingPanels = new ArrayList<JPanel>();

  public void startLoading(final String fileName)
  {
    if (fileLoadingCount == 0)
    {
      fileLoadingPanels.add(addProgressPanel("Loading File: " + fileName
              + "   "));
    }
    fileLoadingCount++;
  }

  private JPanel addProgressPanel(String string)
  {
    if (progressPanel == null)
    {
      progressPanel = new JPanel(new GridLayout(1, 1));
      totalProgressCount = 0;
      instance.getContentPane().add(progressPanel, BorderLayout.SOUTH);
    }
    JPanel thisprogress = new JPanel(new BorderLayout(10, 5));
    JProgressBar progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);

    thisprogress.add(new JLabel(string), BorderLayout.WEST);

    thisprogress.add(progressBar, BorderLayout.CENTER);
    progressPanel.add(thisprogress);
    ((GridLayout) progressPanel.getLayout())
            .setRows(((GridLayout) progressPanel.getLayout()).getRows() + 1);
    ++totalProgressCount;
    instance.validate();
    return thisprogress;
  }

  int totalProgressCount = 0;

  private void removeProgressPanel(JPanel progbar)
  {
    if (progressPanel != null)
    {
      progressPanel.remove(progbar);
      GridLayout gl = (GridLayout) progressPanel.getLayout();
      gl.setRows(gl.getRows() - 1);
      if (--totalProgressCount < 1)
      {
        this.getContentPane().remove(progressPanel);
        progressPanel = null;
      }
    }
    validate();
  }

  public void stopLoading()
  {
    fileLoadingCount--;
    if (fileLoadingCount < 1)
    {
      for (JPanel flp : fileLoadingPanels)
      {
        removeProgressPanel(flp);
      }
      fileLoadingPanels.clear();
      fileLoadingCount = 0;
    }
    validate();
  }

  public static int getViewCount(String alignmentId)
  {
    AlignViewport[] aps = getViewports(alignmentId);
    return (aps == null) ? 0 : aps.length;
  }

  /**
   * 
   * @param alignmentId
   * @return all AlignmentPanels concerning the alignmentId sequence set
   */
  public static AlignmentPanel[] getAlignmentPanels(String alignmentId)
  {
    int count = 0;
    if (Desktop.desktop == null)
    {
      // no frames created and in headless mode
      // TODO: verify that frames are recoverable when in headless mode
      return null;
    }
    JInternalFrame[] frames = Desktop.desktop.getAllFrames();
    ArrayList aps = new ArrayList();
    for (int t = 0; t < frames.length; t++)
    {
      if (frames[t] instanceof AlignFrame)
      {
        AlignFrame af = (AlignFrame) frames[t];
        for (int a = 0; a < af.alignPanels.size(); a++)
        {
          if (alignmentId.equals(((AlignmentPanel) af.alignPanels
                  .elementAt(a)).av.getSequenceSetId()))
          {
            aps.add(af.alignPanels.elementAt(a));
          }
        }
      }
    }
    if (aps.size() == 0)
    {
      return null;
    }
    AlignmentPanel[] vap = new AlignmentPanel[aps.size()];
    for (int t = 0; t < vap.length; t++)
    {
      vap[t] = (AlignmentPanel) aps.get(t);
    }
    return vap;
  }

  /**
   * get all the viewports on an alignment.
   * 
   * @param sequenceSetId
   *          unique alignment id
   * @return all viewports on the alignment bound to sequenceSetId
   */
  public static AlignViewport[] getViewports(String sequenceSetId)
  {
    Vector viewp = new Vector();
    if (desktop != null)
    {
      javax.swing.JInternalFrame[] frames = instance.getAllFrames();

      for (int t = 0; t < frames.length; t++)
      {
        if (frames[t] instanceof AlignFrame)
        {
          AlignFrame afr = ((AlignFrame) frames[t]);
          if (afr.getViewport().getSequenceSetId().equals(sequenceSetId))
          {
            if (afr.alignPanels != null)
            {
              for (int a = 0; a < afr.alignPanels.size(); a++)
              {
                if (sequenceSetId.equals(((AlignmentPanel) afr.alignPanels
                        .elementAt(a)).av.getSequenceSetId()))
                {
                  viewp.addElement(((AlignmentPanel) afr.alignPanels
                          .elementAt(a)).av);
                }
              }
            }
            else
            {
              viewp.addElement(((AlignFrame) frames[t]).getViewport());
            }
          }
        }
      }
      if (viewp.size() > 0)
      {
        AlignViewport[] vp = new AlignViewport[viewp.size()];
        viewp.copyInto(vp);
        return vp;
      }
    }
    return null;
  }

  public void explodeViews(AlignFrame af)
  {
    int size = af.alignPanels.size();
    if (size < 2)
    {
      return;
    }

    for (int i = 0; i < size; i++)
    {
      AlignmentPanel ap = (AlignmentPanel) af.alignPanels.elementAt(i);
      AlignFrame newaf = new AlignFrame(ap);
      if (ap.av.explodedPosition != null
              && !ap.av.explodedPosition.equals(af.getBounds()))
      {
        newaf.setBounds(ap.av.explodedPosition);
      }

      ap.av.gatherViewsHere = false;

      addInternalFrame(newaf, af.getTitle(), AlignFrame.DEFAULT_WIDTH,
              AlignFrame.DEFAULT_HEIGHT);
    }

    af.alignPanels.clear();
    af.closeMenuItem_actionPerformed(true);

  }

  public void gatherViews(AlignFrame source)
  {
    source.viewport.gatherViewsHere = true;
    source.viewport.explodedPosition = source.getBounds();
    JInternalFrame[] frames = desktop.getAllFrames();
    String viewId = source.viewport.getSequenceSetId();

    for (int t = 0; t < frames.length; t++)
    {
      if (frames[t] instanceof AlignFrame && frames[t] != source)
      {
        AlignFrame af = (AlignFrame) frames[t];
        boolean gatherThis = false;
        for (int a = 0; a < af.alignPanels.size(); a++)
        {
          AlignmentPanel ap = (AlignmentPanel) af.alignPanels.elementAt(a);
          if (viewId.equals(ap.av.getSequenceSetId()))
          {
            gatherThis = true;
            ap.av.gatherViewsHere = false;
            ap.av.explodedPosition = af.getBounds();
            source.addAlignmentPanel(ap, false);
          }
        }

        if (gatherThis)
        {
          af.alignPanels.clear();
          af.closeMenuItem_actionPerformed(true);
        }
      }
    }

  }

  jalview.gui.VamsasApplication v_client = null;

  public void vamsasImport_actionPerformed(ActionEvent e)
  {
    if (v_client == null)
    {
      // Load and try to start a session.
      JalviewFileChooser chooser = new JalviewFileChooser(
              jalview.bin.Cache.getProperty("LAST_DIRECTORY"));

      chooser.setFileView(new JalviewFileView());
      chooser.setDialogTitle("Open a saved VAMSAS session");
      chooser.setToolTipText("select a vamsas session to be opened as a new vamsas session.");

      int value = chooser.showOpenDialog(this);

      if (value == JalviewFileChooser.APPROVE_OPTION)
      {
        String fle = chooser.getSelectedFile().toString();
        if (!vamsasImport(chooser.getSelectedFile()))
        {
          JOptionPane.showInternalMessageDialog(Desktop.desktop,
                  "Couldn't import '" + fle + "' as a new vamsas session.",
                  "Vamsas Document Import Failed",
                  JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    else
    {
      jalview.bin.Cache.log
              .error("Implementation error - load session from a running session is not supported.");
    }
  }

  /**
   * import file into a new vamsas session (uses jalview.gui.VamsasApplication)
   * 
   * @param file
   * @return true if import was a success and a session was started.
   */
  public boolean vamsasImport(URL url)
  {
    // TODO: create progress bar
    if (v_client != null)
    {

      jalview.bin.Cache.log
              .error("Implementation error - load session from a running session is not supported.");
      return false;
    }

    try
    {
      // copy the URL content to a temporary local file
      // TODO: be a bit cleverer here with nio (?!)
      File file = File.createTempFile("vdocfromurl", ".vdj");
      FileOutputStream fos = new FileOutputStream(file);
      BufferedInputStream bis = new BufferedInputStream(url.openStream());
      byte[] buffer = new byte[2048];
      int ln;
      while ((ln = bis.read(buffer)) > -1)
      {
        fos.write(buffer, 0, ln);
      }
      bis.close();
      fos.close();
      v_client = new jalview.gui.VamsasApplication(this, file,
              url.toExternalForm());
    } catch (Exception ex)
    {
      jalview.bin.Cache.log.error(
              "Failed to create new vamsas session from contents of URL "
                      + url, ex);
      return false;
    }
    setupVamsasConnectedGui();
    v_client.initial_update(); // TODO: thread ?
    return v_client.inSession();
  }

  /**
   * import file into a new vamsas session (uses jalview.gui.VamsasApplication)
   * 
   * @param file
   * @return true if import was a success and a session was started.
   */
  public boolean vamsasImport(File file)
  {
    if (v_client != null)
    {

      jalview.bin.Cache.log
              .error("Implementation error - load session from a running session is not supported.");
      return false;
    }

    setProgressBar("Importing VAMSAS session from " + file.getName(),
            file.hashCode());
    try
    {
      v_client = new jalview.gui.VamsasApplication(this, file, null);
    } catch (Exception ex)
    {
      setProgressBar("Importing VAMSAS session from " + file.getName(),
              file.hashCode());
      jalview.bin.Cache.log.error(
              "New vamsas session from existing session file failed:", ex);
      return false;
    }
    setupVamsasConnectedGui();
    v_client.initial_update(); // TODO: thread ?
    setProgressBar("Importing VAMSAS session from " + file.getName(),
            file.hashCode());
    return v_client.inSession();
  }

  public boolean joinVamsasSession(String mysesid)
  {
    if (v_client != null)
    {
      throw new Error(
              "Trying to join a vamsas session when another is already connected.");
    }
    if (mysesid == null)
    {
      throw new Error("Invalid vamsas session id.");
    }
    v_client = new VamsasApplication(this, mysesid);
    setupVamsasConnectedGui();
    v_client.initial_update();
    return (v_client.inSession());
  }

  public void vamsasStart_actionPerformed(ActionEvent e)
  {
    if (v_client == null)
    {
      // Start a session.
      // we just start a default session for moment.
      /*
       * JalviewFileChooser chooser = new JalviewFileChooser(jalview.bin.Cache.
       * getProperty("LAST_DIRECTORY"));
       * 
       * chooser.setFileView(new JalviewFileView());
       * chooser.setDialogTitle("Load Vamsas file");
       * chooser.setToolTipText("Import");
       * 
       * int value = chooser.showOpenDialog(this);
       * 
       * if (value == JalviewFileChooser.APPROVE_OPTION) { v_client = new
       * jalview.gui.VamsasApplication(this, chooser.getSelectedFile());
       */
      v_client = new VamsasApplication(this);
      setupVamsasConnectedGui();
      v_client.initial_update(); // TODO: thread ?
    }
    else
    {
      // store current data in session.
      v_client.push_update(); // TODO: thread
    }
  }

  protected void setupVamsasConnectedGui()
  {
    vamsasStart.setText("Session Update");
    vamsasSave.setVisible(true);
    vamsasStop.setVisible(true);
    vamsasImport.setVisible(false); // Document import to existing session is
    // not possible for vamsas-client-1.0.
  }

  protected void setupVamsasDisconnectedGui()
  {
    vamsasSave.setVisible(false);
    vamsasStop.setVisible(false);
    vamsasImport.setVisible(true);
    vamsasStart.setText("New Vamsas Session");
  }

  public void vamsasStop_actionPerformed(ActionEvent e)
  {
    if (v_client != null)
    {
      v_client.end_session();
      v_client = null;
      setupVamsasDisconnectedGui();
    }
  }

  protected void buildVamsasStMenu()
  {
    if (v_client == null)
    {
      String[] sess = null;
      try
      {
        sess = VamsasApplication.getSessionList();
      } catch (Exception e)
      {
        jalview.bin.Cache.log.warn(
                "Problem getting current sessions list.", e);
        sess = null;
      }
      if (sess != null)
      {
        jalview.bin.Cache.log.debug("Got current sessions list: "
                + sess.length + " entries.");
        VamsasStMenu.removeAll();
        for (int i = 0; i < sess.length; i++)
        {
          JMenuItem sessit = new JMenuItem();
          sessit.setText(sess[i]);
          sessit.setToolTipText("Connect to session " + sess[i]);
          final Desktop dsktp = this;
          final String mysesid = sess[i];
          sessit.addActionListener(new ActionListener()
          {

            public void actionPerformed(ActionEvent e)
            {
              if (dsktp.v_client == null)
              {
                Thread rthr = new Thread(new Runnable()
                {

                  public void run()
                  {
                    dsktp.v_client = new VamsasApplication(dsktp, mysesid);
                    dsktp.setupVamsasConnectedGui();
                    dsktp.v_client.initial_update();
                  }

                });
                rthr.start();
              }
            };
          });
          VamsasStMenu.add(sessit);
        }
        // don't show an empty menu.
        VamsasStMenu.setVisible(sess.length > 0);

      }
      else
      {
        jalview.bin.Cache.log.debug("No current vamsas sessions.");
        VamsasStMenu.removeAll();
        VamsasStMenu.setVisible(false);
      }
    }
    else
    {
      // Not interested in the content. Just hide ourselves.
      VamsasStMenu.setVisible(false);
    }
  }

  public void vamsasSave_actionPerformed(ActionEvent e)
  {
    if (v_client != null)
    {
      JalviewFileChooser chooser = new JalviewFileChooser(
              jalview.bin.Cache.getProperty("LAST_DIRECTORY"), new String[]
              { "vdj" }, // TODO: VAMSAS DOCUMENT EXTENSION is VDJ
              new String[]
              { "Vamsas Document" }, "Vamsas Document");

      chooser.setFileView(new JalviewFileView());
      chooser.setDialogTitle("Save Vamsas Document Archive");

      int value = chooser.showSaveDialog(this);

      if (value == JalviewFileChooser.APPROVE_OPTION)
      {
        java.io.File choice = chooser.getSelectedFile();
        JPanel progpanel = addProgressPanel("Saving VAMSAS Document to "
                + choice.getName());
        jalview.bin.Cache.setProperty("LAST_DIRECTORY", choice.getParent());
        String warnmsg = null;
        String warnttl = null;
        try
        {
          v_client.vclient.storeDocument(choice);
        } catch (Error ex)
        {
          warnttl = "Serious Problem saving Vamsas Document";
          warnmsg = ex.toString();
          jalview.bin.Cache.log.error("Error Whilst saving document to "
                  + choice, ex);

        } catch (Exception ex)
        {
          warnttl = "Problem saving Vamsas Document.";
          warnmsg = ex.toString();
          jalview.bin.Cache.log.warn("Exception Whilst saving document to "
                  + choice, ex);

        }
        removeProgressPanel(progpanel);
        if (warnmsg != null)
        {
          JOptionPane.showInternalMessageDialog(Desktop.desktop,

          warnmsg, warnttl, JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  JPanel vamUpdate = null;

  /**
   * hide vamsas user gui bits when a vamsas document event is being handled.
   * 
   * @param b
   *          true to hide gui, false to reveal gui
   */
  public void setVamsasUpdate(boolean b)
  {
    jalview.bin.Cache.log.debug("Setting gui for Vamsas update "
            + (b ? "in progress" : "finished"));

    if (vamUpdate != null)
    {
      this.removeProgressPanel(vamUpdate);
    }
    if (b)
    {
      vamUpdate = this.addProgressPanel("Updating vamsas session");
    }
    vamsasStart.setVisible(!b);
    vamsasStop.setVisible(!b);
    vamsasSave.setVisible(!b);
  }

  public JInternalFrame[] getAllFrames()
  {
    return desktop.getAllFrames();
  }

  /**
   * Checks the given url to see if it gives a response indicating that the user
   * should be informed of a new questionnaire.
   * 
   * @param url
   */
  public void checkForQuestionnaire(String url)
  {
    UserQuestionnaireCheck jvq = new UserQuestionnaireCheck(url);
    // javax.swing.SwingUtilities.invokeLater(jvq);
    new Thread(jvq).start();
  }

  /**
   * Proxy class for JDesktopPane which optionally displays the current memory
   * usage and highlights the desktop area with a red bar if free memory runs
   * low.
   * 
   * @author AMW
   */
  public class MyDesktopPane extends JDesktopPane implements Runnable
  {

    boolean showMemoryUsage = false;

    Runtime runtime;

    java.text.NumberFormat df;

    float maxMemory, allocatedMemory, freeMemory, totalFreeMemory,
            percentUsage;

    public MyDesktopPane(boolean showMemoryUsage)
    {
      showMemoryUsage(showMemoryUsage);
    }

    public void showMemoryUsage(boolean showMemoryUsage)
    {
      this.showMemoryUsage = showMemoryUsage;
      if (showMemoryUsage)
      {
        Thread worker = new Thread(this);
        worker.start();
      }
    }

    public boolean isShowMemoryUsage()
    {
      return showMemoryUsage;
    }

    public void run()
    {
      df = java.text.NumberFormat.getNumberInstance();
      df.setMaximumFractionDigits(2);
      runtime = Runtime.getRuntime();

      while (showMemoryUsage)
      {
        try
        {
          maxMemory = runtime.maxMemory() / 1048576f;
          allocatedMemory = runtime.totalMemory() / 1048576f;
          freeMemory = runtime.freeMemory() / 1048576f;
          totalFreeMemory = freeMemory + (maxMemory - allocatedMemory);

          percentUsage = (totalFreeMemory / maxMemory) * 100;

          // if (percentUsage < 20)
          {
            // border1 = BorderFactory.createMatteBorder(12, 12, 12, 12,
            // Color.red);
            // instance.set.setBorder(border1);
          }
          repaint();
          // sleep after showing usage
          Thread.sleep(3000);
        } catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
    }

    public void paintComponent(Graphics g)
    {
      if (showMemoryUsage && g != null && df != null)
      {
        if (percentUsage < 20)
          g.setColor(Color.red);
        FontMetrics fm = g.getFontMetrics();
        if (fm != null)
        {
          g.drawString(
                  "Total Free Memory: " + df.format(totalFreeMemory)
                          + "MB; Max Memory: " + df.format(maxMemory)
                          + "MB; " + df.format(percentUsage) + "%", 10,
                  getHeight() - fm.getHeight());
        }
      }
    }
  }

  /**
   * fixes stacking order after a modal dialog to ensure windows that should be
   * on top actually are
   */
  public void relayerWindows()
  {

  }

  protected JMenuItem groovyShell;

  public void doGroovyCheck()
  {
    if (jalview.bin.Cache.groovyJarsPresent())
    {
      groovyShell = new JMenuItem();
      groovyShell.setText("Groovy Console...");
      groovyShell.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          groovyShell_actionPerformed(e);
        }
      });
      toolsMenu.add(groovyShell);
      groovyShell.setVisible(true);
    }
  }

  /**
   * Accessor method to quickly get all the AlignmentFrames loaded.
   */
  public static AlignFrame[] getAlignframes()
  {
    JInternalFrame[] frames = Desktop.desktop.getAllFrames();

    if (frames == null)
    {
      return null;
    }
    Vector avp = new Vector();
    try
    {
      // REVERSE ORDER
      for (int i = frames.length - 1; i > -1; i--)
      {
        if (frames[i] instanceof AlignFrame)
        {
          AlignFrame af = (AlignFrame) frames[i];
          avp.addElement(af);
        }
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }
    if (avp.size() == 0)
    {
      return null;
    }
    AlignFrame afs[] = new AlignFrame[avp.size()];
    for (int i = 0, j = avp.size(); i < j; i++)
    {
      afs[i] = (AlignFrame) avp.elementAt(i);
    }
    avp.clear();
    return afs;
  }

  public AppJmol[] getJmols()
  {
    JInternalFrame[] frames = Desktop.desktop.getAllFrames();

    if (frames == null)
    {
      return null;
    }
    Vector avp = new Vector();
    try
    {
      // REVERSE ORDER
      for (int i = frames.length - 1; i > -1; i--)
      {
        if (frames[i] instanceof AppJmol)
        {
          AppJmol af = (AppJmol) frames[i];
          avp.addElement(af);
        }
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }
    if (avp.size() == 0)
    {
      return null;
    }
    AppJmol afs[] = new AppJmol[avp.size()];
    for (int i = 0, j = avp.size(); i < j; i++)
    {
      afs[i] = (AppJmol) avp.elementAt(i);
    }
    avp.clear();
    return afs;
  }

  /**
   * Add Groovy Support to Jalview
   */
  public void groovyShell_actionPerformed(ActionEvent e)
  {
    // use reflection to avoid creating compilation dependency.
    if (!jalview.bin.Cache.groovyJarsPresent())
    {
      throw new Error(
              "Implementation Error. Cannot create groovyShell without Groovy on the classpath!");
    }
    try
    {
      Class gcClass = Desktop.class.getClassLoader().loadClass(
              "groovy.ui.Console");
      Constructor gccons = gcClass.getConstructor(null);
      java.lang.reflect.Method setvar = gcClass.getMethod("setVariable",
              new Class[]
              { String.class, Object.class });
      java.lang.reflect.Method run = gcClass.getMethod("run", null);
      Object gc = gccons.newInstance(null);
      setvar.invoke(gc, new Object[]
      { "Jalview", this });
      run.invoke(gc, null);
    } catch (Exception ex)
    {
      jalview.bin.Cache.log.error("Groovy Shell Creation failed.", ex);
      JOptionPane
              .showInternalMessageDialog(
                      Desktop.desktop,

                      "Couldn't create the groovy Shell. Check the error log for the details of what went wrong.",
                      "Jalview Groovy Support Failed",
                      JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Progress bars managed by the IProgressIndicator method.
   */
  private Hashtable<Long, JPanel> progressBars;

  private Hashtable<Long, IProgressIndicatorHandler> progressBarHandlers;

  /*
   * (non-Javadoc)
   * 
   * @see jalview.gui.IProgressIndicator#setProgressBar(java.lang.String, long)
   */
  public void setProgressBar(String message, long id)
  {
    if (progressBars == null)
    {
      progressBars = new Hashtable<Long, JPanel>();
      progressBarHandlers = new Hashtable<Long, IProgressIndicatorHandler>();
    }

    if (progressBars.get(new Long(id)) != null)
    {
      JPanel progressPanel = progressBars.remove(new Long(id));
      if (progressBarHandlers.contains(new Long(id)))
      {
        progressBarHandlers.remove(new Long(id));
      }
      removeProgressPanel(progressPanel);
    }
    else
    {
      progressBars.put(new Long(id), addProgressPanel(message));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.gui.IProgressIndicator#registerHandler(long,
   * jalview.gui.IProgressIndicatorHandler)
   */
  public void registerHandler(final long id,
          final IProgressIndicatorHandler handler)
  {
    if (progressBarHandlers == null || !progressBars.contains(new Long(id)))
    {
      throw new Error(
              "call setProgressBar before registering the progress bar's handler.");
    }
    progressBarHandlers.put(new Long(id), handler);
    final JPanel progressPanel = (JPanel) progressBars.get(new Long(id));
    if (handler.canCancel())
    {
      JButton cancel = new JButton("Cancel");
      final IProgressIndicator us = this;
      cancel.addActionListener(new ActionListener()
      {

        public void actionPerformed(ActionEvent e)
        {
          handler.cancelActivity(id);
          us.setProgressBar(
                  "Cancelled "
                          + ((JLabel) progressPanel.getComponent(0))
                                  .getText(), id);
        }
      });
      progressPanel.add(cancel, BorderLayout.EAST);
    }
  }

  /**
   * 
   * @return true if any progress bars are still active
   */
  @Override
  public boolean operationInProgress()
  {
    if (progressBars != null && progressBars.size() > 0)
    {
      return true;
    }
    return false;
  }

  /**
   * This will return the first AlignFrame viewing AlignViewport av. It will
   * break if there are more than one AlignFrames viewing a particular av. This
   * 
   * @param av
   * @return alignFrame for av
   */
  public static AlignFrame getAlignFrameFor(AlignViewport av)
  {
    if (desktop != null)
    {
      AlignmentPanel[] aps = getAlignmentPanels(av.getSequenceSetId());
      for (int panel = 0; aps != null && panel < aps.length; panel++)
      {
        if (aps[panel] != null && aps[panel].av == av)
        {
          return aps[panel].alignFrame;
        }
      }
    }
    return null;
  }

  public VamsasApplication getVamsasApplication()
  {
    return v_client;

  }

  /**
   * flag set if jalview GUI is being operated programmatically
   */
  private boolean inBatchMode = false;

  /**
   * check if jalview GUI is being operated programmatically
   * 
   * @return inBatchMode
   */
  public boolean isInBatchMode()
  {
    return inBatchMode;
  }

  /**
   * set flag if jalview GUI is being operated programmatically
   * 
   * @param inBatchMode
   */
  public void setInBatchMode(boolean inBatchMode)
  {
    this.inBatchMode = inBatchMode;
  }

  public void startServiceDiscovery()
  {
    startServiceDiscovery(false);
  }

  public void startServiceDiscovery(boolean blocking)
  {
    boolean alive = true;
    Thread t0 = null, t1 = null, t2 = null;

    // todo: changesupport handlers need to be transferred
    if (discoverer == null)
    {
      discoverer = new jalview.ws.jws1.Discoverer();
      // register PCS handler for desktop.
      discoverer.addPropertyChangeListener(changeSupport);
    }
    // JAL-940 - disabled JWS1 service configuration - always start discoverer
    // until we phase out completely
    if (true)
    {
      (t0 = new Thread(discoverer)).start();
    }

    try
    {
      if (Cache.getDefault("SHOW_ENFIN_SERVICES", true))
      {
        // EnfinEnvision web service menu entries are rebuild every time the
        // menu is shown, so no changeSupport events are needed.
        jalview.ws.EnfinEnvision2OneWay.getInstance();
        (t1 = new Thread(jalview.ws.EnfinEnvision2OneWay.getInstance()))
                .start();
      }
    } catch (Exception e)
    {
      Cache.log
              .info("Exception when trying to launch Envision2 workflow discovery.",
                      e);
      Cache.log.info(e.getStackTrace());
    }
    if (Cache.getDefault("SHOW_JWS2_SERVICES", true))
    {
      if (jalview.ws.jws2.Jws2Discoverer.getDiscoverer().isRunning())
      {
        jalview.ws.jws2.Jws2Discoverer.getDiscoverer().setAborted(true);
      }
      t2 = jalview.ws.jws2.Jws2Discoverer.getDiscoverer().startDiscoverer(
              changeSupport);

    }
    Thread t3 = null;
    {
      // TODO: do rest service discovery
    }
    if (blocking)
    {
      while (alive)
      {
        try
        {
          Thread.sleep(15);
        } catch (Exception e)
        {
        }
        alive = (t1 != null && t1.isAlive())
                || (t2 != null && t2.isAlive())
                || (t3 != null && t3.isAlive())
                || (t0 != null && t0.isAlive());
      }
    }
  }

  /**
   * called to check if the service discovery process completed successfully.
   * 
   * @param evt
   */
  protected void JalviewServicesChanged(PropertyChangeEvent evt)
  {
    if (evt.getNewValue() == null || evt.getNewValue() instanceof Vector)
    {
      final String ermsg = jalview.ws.jws2.Jws2Discoverer.getDiscoverer()
              .getErrorMessages();
      if (ermsg != null)
      {
        if (Cache.getDefault("SHOW_WSDISCOVERY_ERRORS", true))
        {
          if (serviceChangedDialog == null)
          {
            // only run if we aren't already displaying one of these.
            addDialogThread(serviceChangedDialog = new Runnable()
            {
              public void run()
              {

                /*
                 * JalviewDialog jd =new JalviewDialog() {
                 * 
                 * @Override protected void cancelPressed() { // TODO
                 * Auto-generated method stub
                 * 
                 * }@Override protected void okPressed() { // TODO
                 * Auto-generated method stub
                 * 
                 * }@Override protected void raiseClosed() { // TODO
                 * Auto-generated method stub
                 * 
                 * } }; jd.initDialogFrame(new
                 * JLabel("<html><table width=\"450\"><tr><td>" + ermsg +
                 * "<br/>It may be that you have invalid JABA URLs in your web service preferences,"
                 * + " or mis-configured HTTP proxy settings.<br/>" +
                 * "Check the <em>Connections</em> and <em>Web services</em> tab of the"
                 * +
                 * " Tools->Preferences dialog box to change them.</td></tr></table></html>"
                 * ), true, true, "Web Service Configuration Problem", 450,
                 * 400);
                 * 
                 * jd.waitForInput();
                 */
                JOptionPane
                        .showConfirmDialog(
                                Desktop.desktop,
                                new JLabel(
                                        "<html><table width=\"450\"><tr><td>"
                                                + ermsg
                                                + "</td></tr></table>"
                                                + "<p>It may be that you have invalid JABA URLs<br/>in your web service preferences,"
                                                + " or mis-configured HTTP proxy settings.</p>"
                                                + "<p>Check the <em>Connections</em> and <em>Web services</em> tab<br/>of the"
                                                + " Tools->Preferences dialog box to change them.</p></html>"),
                                "Web Service Configuration Problem",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE);
                serviceChangedDialog = null;

              }
            });
          }
        }
        else
        {
          Cache.log
                  .error("Errors reported by JABA discovery service. Check web services preferences.\n"
                          + ermsg);
        }
      }
    }
  }

  private Runnable serviceChangedDialog = null;

  /**
   * start a thread to open a URL in the configured browser. Pops up a warning
   * dialog to the user if there is an exception when calling out to the browser
   * to open the URL.
   * 
   * @param url
   */
  public static void showUrl(final String url)
  {
    showUrl(url, Desktop.instance);
  }

  /**
   * Like showUrl but allows progress handler to be specified
   * 
   * @param url
   * @param progress
   *          (null) or object implementing IProgressIndicator
   */
  public static void showUrl(final String url,
          final IProgressIndicator progress)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          if (progress != null)
          {
            progress.setProgressBar("Opening " + url, this.hashCode());
          }
          jalview.util.BrowserLauncher.openURL(url);
        } catch (Exception ex)
        {
          JOptionPane
                  .showInternalMessageDialog(
                          Desktop.desktop,
                          "Unixers: Couldn't find default web browser."
                                  + "\nAdd the full path to your browser in Preferences.",
                          "Web browser not found",
                          JOptionPane.WARNING_MESSAGE);

          ex.printStackTrace();
        }
        if (progress != null)
        {
          progress.setProgressBar(null, this.hashCode());
        }
      }
    }).start();
  }

  public static WsParamSetManager wsparamManager = null;

  public static ParamManager getUserParameterStore()
  {
    if (wsparamManager == null)
    {
      wsparamManager = new WsParamSetManager();
    }
    return wsparamManager;
  }

  /**
   * static hyperlink handler proxy method for use by Jalview's internal windows
   * 
   * @param e
   */
  public static void hyperlinkUpdate(HyperlinkEvent e)
  {
    if (e.getEventType() == EventType.ACTIVATED)
    {
      String url = null;
      try
      {
        url = e.getURL().toString();
        Desktop.showUrl(url);
      } catch (Exception x)
      {
        if (url != null)
        {
          if (Cache.log != null)
          {
            Cache.log.error("Couldn't handle string " + url + " as a URL.");
          }
          else
          {
            System.err.println("Couldn't handle string " + url
                    + " as a URL.");
          }
        }
        // ignore any exceptions due to dud links.
      }

    }
  }

  /**
   * single thread that handles display of dialogs to user.
   */
  ExecutorService dialogExecutor = Executors.newSingleThreadExecutor();

  /**
   * flag indicating if dialogExecutor should try to acquire a permit
   */
  private volatile boolean dialogPause = true;

  /**
   * pause the queue
   */
  private java.util.concurrent.Semaphore block = new Semaphore(0);

  /**
   * add another dialog thread to the queue
   * 
   * @param prompter
   */
  public void addDialogThread(final Runnable prompter)
  {
    dialogExecutor.submit(new Runnable()
    {
      public void run()
      {
        if (dialogPause)
        {
          try
          {
            block.acquire();
          } catch (InterruptedException x)
          {
          }
          ;
        }
        if (instance == null)
        {
          return;
        }
        try
        {
          SwingUtilities.invokeAndWait(prompter);
        } catch (Exception q)
        {
          Cache.log.warn("Unexpected Exception in dialog thread.", q);
        }
      }
    });
  }

  public void startDialogQueue()
  {
    // set the flag so we don't pause waiting for another permit and semaphore
    // the current task to begin
    dialogPause = false;
    block.release();
  }
}
