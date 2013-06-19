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
package jalview.ws;

import jalview.bin.Cache;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.Desktop;
import jalview.gui.JvSwingUtils;
import jalview.util.GroupUrlLink;
import jalview.util.GroupUrlLink.UrlStringTooLongException;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lowagie.text.html.HtmlEncoder;

/**
 * Lightweight runnable to discover dynamic 'one way' group URL services
 * 
 * @author JimP
 * 
 */
public class EnfinEnvision2OneWay extends DefaultHandler implements
        Runnable, WSMenuEntryProviderI
{
  private static EnfinEnvision2OneWay groupURLLinksGatherer = null;

  public static EnfinEnvision2OneWay getInstance()
  {
    if (groupURLLinksGatherer == null)
    {
      groupURLLinksGatherer = new EnfinEnvision2OneWay();
    }
    return groupURLLinksGatherer;
  }

  private void waitForCompletion()
  {
    if (groupURLLinksGatherer.isRunning())
    {
      // wait around and show a visual delay indicator
      Cursor oldCursor = Desktop.instance.getCursor();
      Desktop.instance.setCursor(new Cursor(Cursor.WAIT_CURSOR));
      while (groupURLLinksGatherer.isRunning())
      {
        try
        {
          Thread.sleep(100);
        } catch (InterruptedException e)
        {
        }
        ;
      }
      Desktop.instance.setCursor(oldCursor);
    }
  }

  public Vector getEnvisionServiceGroupURLS()
  {
    waitForCompletion();
    return groupURLLinks;
  }

  /**
   * indicate if
   */
  private static String BACKGROUND = "BACKGROUNDPARAM";

  /**
   * contains null strings or one of the above constants - indicate if this URL
   * is a special case.
   */
  private Vector additionalPar = new Vector();

  /**
   * the enfin service URL
   */
  private String enfinService = null;

  private String description = null;

  private String wfname;

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  public void endElement(String uri, String localName, String qName)
          throws SAXException
  {

    // System.err.println("End element: : '"+uri+" "+localName+" "+qName);
    if (qName.equalsIgnoreCase("workflow") && description != null
            && description.length() > 0)
    {
      // groupURLLinks.addElement("UNIPROT|EnVision2|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?tool=Jalview&workflow=Default&datasetName=JalviewIDs$DATASETID$&input=$SEQUENCEIDS$&inputType=0|,");
      // groupURLLinks.addElement("Seqs|EnVision2|http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?tool=Jalview&workflow=Default&datasetName=JalviewSeqs$DATASETID$&input=$SEQUENCES=/([A-Za-z]+)+/=$&inputType=1|,");
      System.err.println("Adding entry for " + wfname + " " + description);
      if (wfname.toLowerCase().indexOf("funcnet") == -1)
      {
        description = Pattern.compile("\\s+", Pattern.MULTILINE)
                .matcher(description).replaceAll(" ");
        groupURLdescr.addElement(description);
        groupURLdescr.addElement(description);
        String urlstub = wfname;
        if (wfname.indexOf(" ") > -1)
        {
          // make the name safe!
          try
          {
            urlstub = URLEncoder.encode(wfname, "utf-8");
          } catch (UnsupportedEncodingException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        groupURLLinks
                .addElement(wfname
                        + "|"
                        + "http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?tool=Jalview&workflow="
                        + urlstub
                        + "&datasetName=JalviewSeqs$DATASETID$&input=$SEQUENCEIDS$&inputType=0|,"); // #"+description+"#");
        groupURLLinks
                .addElement(wfname
                        + "|"
                        + "http://www.ebi.ac.uk/enfin-srv/envision2/pages/linkin.jsf?tool=Jalview&workflow="
                        + urlstub
                        + "&datasetName=JalviewSeqs$DATASETID$&input=$SEQUENCES=/([A-Za-z]+)+/=$&inputType=1|,"); // #"+description+"#");
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
   */
  public void characters(char[] ch, int start, int length)
          throws SAXException
  {
    if (description != null)
    {
      for (int i = start; i < start + length; i++)
      {
        description += ch[i];
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName,
          Attributes attributes) throws SAXException
  {
    if (qName.equalsIgnoreCase("workflow"))
    {
      description = null;
      wfname = attributes.getValue("name");
    }
    if (qName.equalsIgnoreCase("description"))
    {
      description = "";
    }

    // System.err.println("Start element: : '"+uri+" "+localName+" "+qName+" attributes"+attributes);
    // super.startElement(uri,localName,qname,attributes);
  }

  private boolean started = false;

  private boolean running = false;

  private Vector groupURLLinks = null;

  private Vector groupURLdescr = null;

  private static String[] allowedDb = new String[]
  { "UNIPROT", "EMBL", "PDB" };

  public EnfinEnvision2OneWay()
  {
    groupURLLinks = new Vector();
    groupURLdescr = new Vector();

    enfinService = Cache.getDefault("ENVISION2_WORKFLOWSERVICE",
            "http://www.ebi.ac.uk/enfin-srv/envision2/pages/workflows.xml");
    new Thread(this).start();
  }

  public void run()
  {
    started = true;
    running = true;
    try
    {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      SAXParser sp = spf.newSAXParser();
      sp.parse(new URL(enfinService).openStream(), this);
    } catch (Exception e)
    {
      Cache.log.warn("Exception when discovering One Way services: ", e);
    } catch (Error e)
    {
      Cache.log.warn("Error when discovering One Way services: ", e);
    }
    running = false;
    Cache.log.debug("Finished running.");
  }

  /**
   * have we finished running yet ?
   * 
   * @return false if we have been run.
   */
  public boolean isRunning()
  {

    // TODO Auto-generated method stub
    return !started || running;
  }

  public static void main(String[] args)
  {
    Cache.initLogger();
    EnfinEnvision2OneWay ow = new EnfinEnvision2OneWay();
    while (ow.isRunning())
    {
      try
      {
        Thread.sleep(50);
      } catch (Exception e)
      {
      }
      ;

    }
    for (int i = 0; i < ow.groupURLLinks.size(); i++)
    {
      System.err.println("Description" + ow.groupURLdescr.elementAt(i)
              + "Service URL: " + ow.groupURLLinks.elementAt(i));
    }
  }

  // / Copied from jalview.gui.PopupMenu
  /**
   * add a late bound URL service item to the given menu
   * 
   * @param linkMenu
   * @param label
   *          - menu label string
   * @param urlgenerator
   *          GroupURLLink used to generate URL
   * @param urlstub
   *          Object array returned from the makeUrlStubs function.
   */
  private void addshowLink(JMenu linkMenu, String label, String descr,
          String dbname, final GroupUrlLink urlgenerator,
          final Object[] urlstub)
  {
    Component[] jmi = linkMenu.getMenuComponents();
    for (int i = 0; i < jmi.length; i++)
    {
      if (jmi[i] instanceof JMenuItem
              && ((JMenuItem) jmi[i]).getText().equalsIgnoreCase(label))
      {
        // don't add this - its a repeat of an existing URL.
        return;
      }
    }
    try
    {
      descr = HtmlEncoder.encode(descr);
    } catch (Exception e)
    {
    }
    ;

    boolean seqsorids = (urlgenerator.getGroupURLType() & urlgenerator.SEQUENCEIDS) == 0;
    int i = urlgenerator.getNumberInvolved(urlstub);
    JMenuItem item = new JMenuItem(label);
    //
    if (dbname == null || dbname.trim().length() == 0)
    {
      dbname = "";
    }
    item.setToolTipText("<html>"
            + JvSwingUtils.wrapTooltip("Submit " + i + " " + dbname + " "
                    + (seqsorids ? "sequence" : "sequence id")
                    + (i > 1 ? "s" : "")

                    + " to<br/>" + descr) + "</html>");
    item.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        new Thread(new Runnable()
        {

          public void run()
          {
            try
            {
              showLink(urlgenerator.constructFrom(urlstub));
            } catch (UrlStringTooLongException ex)
            {
              Cache.log.warn("Not showing link: URL is too long!", ex);
            }
          }

        }).start();
      }
    });

    linkMenu.add(item);
  }

  /**
   * open the given link in a new browser window
   * 
   * @param url
   */
  public void showLink(String url)
  {
    try
    {
      jalview.util.BrowserLauncher.openURL(url);
    } catch (Exception ex)
    {
      JOptionPane
              .showInternalMessageDialog(
                      Desktop.desktop,
                      "Unixers: Couldn't find default web browser."
                              + "\nAdd the full path to your browser in Preferences.",
                      "Web browser not found", JOptionPane.WARNING_MESSAGE);

      ex.printStackTrace();
    }
  }

  /**
   * called by a web service menu instance when it is opened.
   * 
   * @param enfinServiceMenu
   * @param alignFrame
   */
  private void buildGroupLinkMenu(JMenu enfinServiceMenu,
          AlignFrame alignFrame)
  {
    if (running || !started)
    {
      return;
    }
    SequenceI[] seqs = alignFrame.getViewport().getSelectionAsNewSequence();
    SequenceGroup sg = alignFrame.getViewport().getSelectionGroup();
    if (sg == null)
    {
      // consider visible regions here/
    }
    enfinServiceMenu.removeAll();
    JMenu entries = buildGroupURLMenu(seqs, sg);
    if (entries != null)
    {
      for (int i = 0, iSize = entries.getMenuComponentCount(); i < iSize; i++)
      {
        // transfer - menu component is removed from entries automatically
        enfinServiceMenu.add(entries.getMenuComponent(0));
      }
      // entries.removeAll();
      enfinServiceMenu.setEnabled(true);
    }
    else
    {
      enfinServiceMenu.setEnabled(false);
    }
  }

  /**
   * construct a dynamic enfin services menu given a sequence selection
   * 
   * @param seqs
   * @param sg
   * @param groupLinks
   * @return
   */
  private JMenu buildGroupURLMenu(SequenceI[] seqs, SequenceGroup sg)
  {
    if (groupURLdescr == null || groupURLLinks == null)
      return null;
    // TODO: usability: thread off the generation of group url content so root
    // menu appears asap
    // sequence only URLs
    // ID/regex match URLs
    JMenu groupLinksMenu = new JMenu("Group Link");
    String[][] idandseqs = GroupUrlLink.formStrings(seqs);
    Hashtable commonDbrefs = new Hashtable();
    for (int sq = 0; sq < seqs.length; sq++)
    {

      int start, end;
      if (sg != null)
      {
        start = seqs[sq].findPosition(sg.getStartRes());
        end = seqs[sq].findPosition(sg.getEndRes());
      }
      else
      {
        // get total width of alignment.
        start = seqs[sq].getStart();
        end = seqs[sq].findPosition(seqs[sq].getLength());
      }
      // we skip sequences which do not have any non-gaps in the region of
      // interest
      if (start > end)
      {
        continue;
      }
      // just collect ids from dataset sequence
      // TODO: check if IDs collected from selecton group intersects with the
      // current selection, too
      SequenceI sqi = seqs[sq];
      while (sqi.getDatasetSequence() != null)
      {
        sqi = sqi.getDatasetSequence();
      }
      DBRefEntry[] dbr = sqi.getDBRef();
      if (dbr != null && dbr.length > 0)
      {
        for (int d = 0; d < dbr.length; d++)
        {
          String src = dbr[d].getSource(); // jalview.util.DBRefUtils.getCanonicalName(dbr[d].getSource()).toUpperCase();
          Object[] sarray = (Object[]) commonDbrefs.get(src);
          if (sarray == null)
          {
            sarray = new Object[2];
            sarray[0] = new int[]
            { 0 };
            sarray[1] = new String[seqs.length];

            commonDbrefs.put(src, sarray);
          }

          if (((String[]) sarray[1])[sq] == null)
          {
            if (!dbr[d].hasMap()
                    || (dbr[d].getMap().locateMappedRange(start, end) != null))
            {
              ((String[]) sarray[1])[sq] = dbr[d].getAccessionId();
              ((int[]) sarray[0])[0]++;
            }
          }
        }
      }
    }
    // now create group links for all distinct ID/sequence sets.
    Hashtable<String, JMenu[]> gurlMenus = new Hashtable<String, JMenu[]>();
    /**
     * last number of sequences where URL generation failed
     */
    int[] nsqtype = new int[]
    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    for (int i = 0; i < groupURLLinks.size(); i++)
    {
      String link = (String) groupURLLinks.elementAt(i);
      String descr = (String) groupURLdescr.elementAt(i);

      // boolean specialCase =
      // additionalPar.elementAt(i).toString().equals(BACKGROUND);
      GroupUrlLink urlLink = null;
      try
      {
        urlLink = new GroupUrlLink(link);
      } catch (Exception foo)
      {
        jalview.bin.Cache.log.error("Exception for GroupURLLink '" + link
                + "'", foo);
        continue;
      }
      ;
      if (!urlLink.isValid())
      {
        jalview.bin.Cache.log.error(urlLink.getInvalidMessage());
        continue;
      }
      final String label = urlLink.getLabel();
      // create/recover the sub menus that might be populated for this link.
      JMenu[] wflinkMenus = gurlMenus.get(label);
      if (wflinkMenus == null)
      {
        // three types of url that might be
        // created.
        wflinkMenus = new JMenu[]
        { null, new JMenu("IDS"), new JMenu("Sequences"),
            new JMenu("IDS and Sequences") };
        gurlMenus.put(label, wflinkMenus);
      }

      boolean usingNames = false;
      // Now see which parts of the group apply for this URL
      String ltarget;
      String[] seqstr, ids; // input to makeUrl
      for (int t = 0; t < allowedDb.length; t++)
      {
        ltarget = allowedDb[t]; // jalview.util.DBRefUtils.getCanonicalName(urlLink.getTarget());
        Object[] idset = (Object[]) commonDbrefs.get(ltarget.toUpperCase());
        if (idset != null)
        {
          int numinput = ((int[]) idset[0])[0];
          String[] allids = ((String[]) idset[1]);
          seqstr = new String[numinput];
          ids = new String[numinput];
          if (nsqtype[urlLink.getGroupURLType()] > 0
                  && numinput >= nsqtype[urlLink.getGroupURLType()])
          {
            continue;
          }
          for (int sq = 0, idcount = 0; sq < seqs.length; sq++)
          {
            if (allids[sq] != null)
            {
              ids[idcount] = allids[sq];
              seqstr[idcount++] = idandseqs[1][sq];
            }
          }
          try
          {
            createAndAddLinks(wflinkMenus, false, urlLink, ltarget, null,
                    descr, ids, seqstr);
          } catch (UrlStringTooLongException ex)
          {
            nsqtype[urlLink.getGroupURLType()] = numinput;
          }
        }
      }
      // also do names only.
      seqstr = idandseqs[1];
      ids = idandseqs[0];
      if (nsqtype[urlLink.getGroupURLType()] > 0
              && idandseqs[0].length >= nsqtype[urlLink.getGroupURLType()])
      {
        continue;
      }

      try
      {
        createAndAddLinks(wflinkMenus, true, urlLink, "Any", null, descr,
                ids, seqstr);
      } catch (UrlStringTooLongException ex)
      {
        nsqtype[urlLink.getGroupURLType()] = idandseqs[0].length;
      }
    }
    boolean anyadded = false; // indicates if there are any group links to give
    // to user
    for (Map.Entry<String, JMenu[]> menues : gurlMenus.entrySet())
    {
      JMenu grouplinkset = new JMenu(menues.getKey());
      JMenu[] wflinkMenus = menues.getValue();
      for (int m = 0; m < wflinkMenus.length; m++)
      {
        if (wflinkMenus[m] != null
                && wflinkMenus[m].getMenuComponentCount() > 0)
        {
          anyadded = true;
          grouplinkset.add(wflinkMenus[m]);
        }
      }
      groupLinksMenu.add(grouplinkset);
    }
    if (anyadded)
    {
      return groupLinksMenu;
    }
    return null;
  }

  private boolean createAndAddLinks(JMenu[] linkMenus, boolean usingNames,
          GroupUrlLink urlLink, String label, String ltarget, String descr,
          String[] ids, String[] seqstr) throws UrlStringTooLongException
  {
    Object[] urlset = urlLink.makeUrlStubs(ids, seqstr, "FromJalview"
            + System.currentTimeMillis(), false);

    if (urlset != null)
    {
      int type = urlLink.getGroupURLType() & 3;
      // System.out.println(urlLink.getGroupURLType()
      // +" "+((String[])urlset[3])[0]);
      // first two bits ofurlLink type bitfield are sequenceids and sequences
      // TODO: FUTURE: ensure the groupURL menu structure can be generalised
      addshowLink(
              linkMenus[type],
              label
                      + " "
                      + (ltarget == null ? (((type & 1) == 1 ? "ID"
                              : "Sequence") + (urlLink
                              .getNumberInvolved(urlset) > 1 ? "s" : ""))
                              : (usingNames ? (((type & 1) == 1) ? "(Names)"
                                      : "")
                                      : ("(" + ltarget + ")"))), descr,
              usingNames ? null : label, urlLink, urlset);
      return true;
    }
    return false;
  }

  // / end of stuff copied from popupmenu
  public void attachWSMenuEntry(final JMenu wsmenu,
          final AlignFrame alignFrame)
  {
    final JMenu enfinServiceMenu = new JMenu("Envision 2");
    wsmenu.add(enfinServiceMenu);
    enfinServiceMenu.setEnabled(false);
    wsmenu.addMenuListener(new MenuListener()
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
        if (refresh && !isRunning())
        {
          new Thread(new Runnable()
          {
            public void run()
            {
              try
              {
                buildGroupLinkMenu(enfinServiceMenu, alignFrame);
              } catch (OutOfMemoryError ex)
              {
                Cache.log
                        .error("Out of memory when calculating the Envision2 links.",
                                ex);
                enfinServiceMenu.setEnabled(false);
              }
            }
          }).start();
          refresh = false;
        }
      }
    });

  }

}
