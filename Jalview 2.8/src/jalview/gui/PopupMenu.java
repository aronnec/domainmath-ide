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

import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jalview.analysis.*;
import jalview.commands.*;
import jalview.datamodel.*;
import jalview.io.*;
import jalview.schemes.*;
import jalview.util.GroupUrlLink;
import jalview.util.GroupUrlLink.UrlStringTooLongException;
import jalview.util.UrlLink;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision: 1.118 $
 */
public class PopupMenu extends JPopupMenu
{
  JMenu groupMenu = new JMenu();

  JMenuItem groupName = new JMenuItem();

  protected JRadioButtonMenuItem clustalColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem zappoColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem taylorColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem hydrophobicityColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem helixColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem strandColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem turnColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem buriedColour = new JRadioButtonMenuItem();

  protected JCheckBoxMenuItem abovePIDColour = new JCheckBoxMenuItem();

  protected JRadioButtonMenuItem userDefinedColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem PIDColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem BLOSUM62Colour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem purinePyrimidineColour = new JRadioButtonMenuItem();

  // protected JRadioButtonMenuItem covariationColour = new
  // JRadioButtonMenuItem();

  JRadioButtonMenuItem noColourmenuItem = new JRadioButtonMenuItem();

  protected JCheckBoxMenuItem conservationMenuItem = new JCheckBoxMenuItem();

  AlignmentPanel ap;

  JMenu sequenceMenu = new JMenu();

  JMenuItem sequenceName = new JMenuItem();

  JMenuItem sequenceDetails = new JMenuItem();

  JMenuItem sequenceSelDetails = new JMenuItem();

  SequenceI sequence;

  JMenuItem unGroupMenuItem = new JMenuItem();

  JMenuItem outline = new JMenuItem();

  JRadioButtonMenuItem nucleotideMenuItem = new JRadioButtonMenuItem();

  JMenu colourMenu = new JMenu();

  JCheckBoxMenuItem showBoxes = new JCheckBoxMenuItem();

  JCheckBoxMenuItem showText = new JCheckBoxMenuItem();

  JCheckBoxMenuItem showColourText = new JCheckBoxMenuItem();

  JCheckBoxMenuItem displayNonconserved = new JCheckBoxMenuItem();

  JMenu editMenu = new JMenu();

  JMenuItem cut = new JMenuItem();

  JMenuItem copy = new JMenuItem();

  JMenuItem upperCase = new JMenuItem();

  JMenuItem lowerCase = new JMenuItem();

  JMenuItem toggle = new JMenuItem();

  JMenu pdbMenu = new JMenu();

  JMenuItem pdbFromFile = new JMenuItem();

  JMenuItem enterPDB = new JMenuItem();

  JMenuItem discoverPDB = new JMenuItem();

  JMenu outputMenu = new JMenu();

  JMenuItem sequenceFeature = new JMenuItem();

  JMenuItem textColour = new JMenuItem();

  JMenu jMenu1 = new JMenu();

  JMenu structureMenu = new JMenu();

  JMenu viewStructureMenu = new JMenu();

  // JMenu colStructureMenu = new JMenu();
  JMenuItem editSequence = new JMenuItem();

  // JMenuItem annotationMenuItem = new JMenuItem();

  JMenu groupLinksMenu;

  /**
   * Creates a new PopupMenu object.
   * 
   * @param ap
   *          DOCUMENT ME!
   * @param seq
   *          DOCUMENT ME!
   */
  public PopupMenu(final AlignmentPanel ap, Sequence seq, Vector links)
  {
    this(ap, seq, links, null);
  }

  /**
   * 
   * @param ap
   * @param seq
   * @param links
   * @param groupLinks
   */
  public PopupMenu(final AlignmentPanel ap, final SequenceI seq,
          Vector links, Vector groupLinks)
  {
    // /////////////////////////////////////////////////////////
    // If this is activated from the sequence panel, the user may want to
    // edit or annotate a particular residue. Therefore display the residue menu
    //
    // If from the IDPanel, we must display the sequence menu
    // ////////////////////////////////////////////////////////
    this.ap = ap;
    sequence = seq;

    ButtonGroup colours = new ButtonGroup();
    colours.add(noColourmenuItem);
    colours.add(clustalColour);
    colours.add(zappoColour);
    colours.add(taylorColour);
    colours.add(hydrophobicityColour);
    colours.add(helixColour);
    colours.add(strandColour);
    colours.add(turnColour);
    colours.add(buriedColour);
    colours.add(abovePIDColour);
    colours.add(userDefinedColour);
    colours.add(PIDColour);
    colours.add(BLOSUM62Colour);
    colours.add(purinePyrimidineColour);
    // colours.add(covariationColour);

    for (int i = 0; i < jalview.io.FormatAdapter.WRITEABLE_FORMATS.length; i++)
    {
      JMenuItem item = new JMenuItem(
              jalview.io.FormatAdapter.WRITEABLE_FORMATS[i]);

      item.addActionListener(new java.awt.event.ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          outputText_actionPerformed(e);
        }
      });

      outputMenu.add(item);
    }

    try
    {
      jbInit();
    } catch (Exception e)
    {
      e.printStackTrace();
    }

    JMenuItem menuItem;
    if (seq != null)
    {
      sequenceMenu.setText(sequence.getName());

      if (seq.getDatasetSequence().getPDBId() != null
              && seq.getDatasetSequence().getPDBId().size() > 0)
      {
        java.util.Enumeration e = seq.getDatasetSequence().getPDBId()
                .elements();

        while (e.hasMoreElements())
        {
          final PDBEntry pdb = (PDBEntry) e.nextElement();

          menuItem = new JMenuItem();
          menuItem.setText(pdb.getId());
          menuItem.addActionListener(new java.awt.event.ActionListener()
          {
              @Override
            public void actionPerformed(ActionEvent e)
            {
              // TODO re JAL-860: optionally open dialog or provide a menu entry
              // allowing user to open just one structure per sequence
              new AppJmol(pdb, ap.av.collateForPDB(new PDBEntry[]
              { pdb })[0], null, ap);
              // new PDBViewer(pdb, seqs2, null, ap, AppletFormatAdapter.FILE);
            }

          });
          viewStructureMenu.add(menuItem);

          /*
           * menuItem = new JMenuItem(); menuItem.setText(pdb.getId());
           * menuItem.addActionListener(new java.awt.event.ActionListener() {
           * public void actionPerformed(ActionEvent e) {
           * colourByStructure(pdb.getId()); } });
           * colStructureMenu.add(menuItem);
           */
        }
      }
      else
      {
        if (ap.av.getAlignment().isNucleotide() == false)
        {
          structureMenu.remove(viewStructureMenu);
        }
        // structureMenu.remove(colStructureMenu);
      }

      if (ap.av.getAlignment().isNucleotide() == true)
      {
        AlignmentAnnotation[] aa = ap.av.getAlignment()
                .getAlignmentAnnotation();
        for (int i = 0; i < aa.length; i++)
        {
          if (aa[i].getRNAStruc() != null)
          {
            final String rnastruc = aa[i].getRNAStruc();
            final String structureLine = aa[i].label;
            menuItem = new JMenuItem();
            menuItem.setText("2D RNA " + structureLine);
            menuItem.addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                new AppVarna(structureLine, seq, seq.getSequenceAsString(),
                        rnastruc, seq.getName(), ap);
              }
            });
            viewStructureMenu.add(menuItem);
          }
        }

        // SequenceFeatures[] test = seq.getSequenceFeatures();

        if (seq.getAnnotation() != null)
        {
          AlignmentAnnotation seqAnno[] = seq.getAnnotation();
          for (int i = 0; i < seqAnno.length; i++)
          {
            if (seqAnno[i].getRNAStruc() != null)
            {
              final String rnastruc = seqAnno[i].getRNAStruc();

              // TODO: make rnastrucF a bit more nice
              menuItem = new JMenuItem();
              menuItem.setText("2D RNA - " + seq.getName());
              menuItem.addActionListener(new java.awt.event.ActionListener()
              {
                  @Override
                public void actionPerformed(ActionEvent e)
                {
                  // TODO: VARNA does'nt print gaps in the sequence
                  new AppVarna(seq.getName() + " structure", seq, seq
                          .getSequenceAsString(), rnastruc, seq.getName(),
                          ap);
                }
              });
              viewStructureMenu.add(menuItem);
            }
          }
        }

      }

      menuItem = new JMenuItem("Hide Sequences");
      menuItem.addActionListener(new java.awt.event.ActionListener()
      {
          @Override
        public void actionPerformed(ActionEvent e)
        {
          hideSequences(false);
        }
      });
      add(menuItem);

      if (ap.av.getSelectionGroup() != null
              && ap.av.getSelectionGroup().getSize() > 1)
      {
        menuItem = new JMenuItem("Represent Group with " + seq.getName());
        menuItem.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
          public void actionPerformed(ActionEvent e)
          {
            hideSequences(true);
          }
        });
        sequenceMenu.add(menuItem);
      }

      if (ap.av.hasHiddenRows())
      {
        final int index = ap.av.getAlignment().findIndex(seq);

        if (ap.av.adjustForHiddenSeqs(index)
                - ap.av.adjustForHiddenSeqs(index - 1) > 1)
        {
          menuItem = new JMenuItem("Reveal Sequences");
          menuItem.addActionListener(new ActionListener()
          {
              @Override
            public void actionPerformed(ActionEvent e)
            {
              ap.av.showSequence(index);
              if (ap.overviewPanel != null)
              {
                ap.overviewPanel.updateOverviewImage();
              }
            }
          });
          add(menuItem);
        }
      }
    }
    // for the case when no sequences are even visible
    if (ap.av.hasHiddenRows())
    {
      {
        menuItem = new JMenuItem("Reveal All");
        menuItem.addActionListener(new ActionListener()
        {
            @Override
          public void actionPerformed(ActionEvent e)
          {
            ap.av.showAllHiddenSeqs();
            if (ap.overviewPanel != null)
            {
              ap.overviewPanel.updateOverviewImage();
            }
          }
        });

        add(menuItem);
      }

    }

    SequenceGroup sg = ap.av.getSelectionGroup();

    if (sg != null && sg.getSize() > 0)
    {
      groupName.setText("Name: " + sg.getName());
      groupName.setText("Edit name and description of current group.");

      if (sg.cs instanceof ZappoColourScheme)
      {
        zappoColour.setSelected(true);
      }
      else if (sg.cs instanceof TaylorColourScheme)
      {
        taylorColour.setSelected(true);
      }
      else if (sg.cs instanceof PIDColourScheme)
      {
        PIDColour.setSelected(true);
      }
      else if (sg.cs instanceof Blosum62ColourScheme)
      {
        BLOSUM62Colour.setSelected(true);
      }
      else if (sg.cs instanceof UserColourScheme)
      {
        userDefinedColour.setSelected(true);
      }
      else if (sg.cs instanceof HydrophobicColourScheme)
      {
        hydrophobicityColour.setSelected(true);
      }
      else if (sg.cs instanceof HelixColourScheme)
      {
        helixColour.setSelected(true);
      }
      else if (sg.cs instanceof StrandColourScheme)
      {
        strandColour.setSelected(true);
      }
      else if (sg.cs instanceof TurnColourScheme)
      {
        turnColour.setSelected(true);
      }
      else if (sg.cs instanceof BuriedColourScheme)
      {
        buriedColour.setSelected(true);
      }
      else if (sg.cs instanceof ClustalxColourScheme)
      {
        clustalColour.setSelected(true);
      }
      else if (sg.cs instanceof PurinePyrimidineColourScheme)
      {
        purinePyrimidineColour.setSelected(true);
      }
      /*
       * else if (sg.cs instanceof CovariationColourScheme) {
       * covariationColour.setSelected(true); }
       */
      else
      {
        noColourmenuItem.setSelected(true);
      }

      if (sg.cs != null && sg.cs.conservationApplied())
      {
        conservationMenuItem.setSelected(true);
      }
      displayNonconserved.setSelected(sg.getShowNonconserved());
      showText.setSelected(sg.getDisplayText());
      showColourText.setSelected(sg.getColourText());
      showBoxes.setSelected(sg.getDisplayBoxes());
      // add any groupURLs to the groupURL submenu and make it visible
      if (groupLinks != null && groupLinks.size() > 0)
      {
        buildGroupURLMenu(sg, groupLinks);
      }
      // Add a 'show all structures' for the current selection
      Hashtable<String, PDBEntry> pdbe = new Hashtable<String, PDBEntry>();
      SequenceI sqass = null;
      for (SequenceI sq : ap.av.getSequenceSelection())
      {
        Vector<PDBEntry> pes = (Vector<PDBEntry>) sq.getDatasetSequence()
                .getPDBId();
        if (pes != null)
        {
          for (PDBEntry pe : pes)
          {
            pdbe.put(pe.getId(), pe);
            if (sqass == null)
            {
              sqass = sq;
            }
          }
        }
      }
      if (pdbe.size() > 0)
      {
        final PDBEntry[] pe = pdbe.values().toArray(
                new PDBEntry[pdbe.size()]);
        final JMenuItem gpdbview;
        if (pdbe.size() == 1)
        {
          structureMenu.add(gpdbview = new JMenuItem("View structure for "
                  + sqass.getDisplayId(false)));
        }
        else
        {
          structureMenu.add(gpdbview = new JMenuItem("View all "
                  + pdbe.size() + " structures."));
        }
        gpdbview.setToolTipText("Open a new Jmol view with all structures associated with the current selection and superimpose them using the alignment.");
        gpdbview.addActionListener(new ActionListener()
        {

          @Override
          public void actionPerformed(ActionEvent e)
          {
            new AppJmol(ap, pe, ap.av.collateForPDB(pe));
          }
        });
      }
    }
    else
    {
      groupMenu.setVisible(false);
      editMenu.setVisible(false);
    }

    if (!ap.av.getAlignment().getGroups().contains(sg))
    {
      unGroupMenuItem.setVisible(false);
    }

    if (seq == null)
    {
      sequenceMenu.setVisible(false);
      structureMenu.setVisible(false);
    }

    if (links != null && links.size() > 0)
    {

      JMenu linkMenu = new JMenu("Link");
      Vector linkset = new Vector();
      for (int i = 0; i < links.size(); i++)
      {
        String link = links.elementAt(i).toString();
        UrlLink urlLink = null;
        try
        {
          urlLink = new UrlLink(link);
        } catch (Exception foo)
        {
          jalview.bin.Cache.log.error("Exception for URLLink '" + link
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
        if (seq != null && urlLink.isDynamic())
        {

          // collect matching db-refs
          DBRefEntry[] dbr = jalview.util.DBRefUtils.selectRefs(
                  seq.getDBRef(), new String[]
                  { urlLink.getTarget() });
          // collect id string too
          String id = seq.getName();
          String descr = seq.getDescription();
          if (descr != null && descr.length() < 1)
          {
            descr = null;
          }

          if (dbr != null)
          {
            for (int r = 0; r < dbr.length; r++)
            {
              if (id != null && dbr[r].getAccessionId().equals(id))
              {
                // suppress duplicate link creation for the bare sequence ID
                // string with this link
                id = null;
              }
              // create Bare ID link for this RUL
              String[] urls = urlLink.makeUrls(dbr[r].getAccessionId(),
                      true);
              if (urls != null)
              {
                for (int u = 0; u < urls.length; u += 2)
                {
                  if (!linkset.contains(urls[u] + "|" + urls[u + 1]))
                  {
                    linkset.addElement(urls[u] + "|" + urls[u + 1]);
                    addshowLink(linkMenu, label + "|" + urls[u],
                            urls[u + 1]);
                  }
                }
              }
            }
          }
          if (id != null)
          {
            // create Bare ID link for this RUL
            String[] urls = urlLink.makeUrls(id, true);
            if (urls != null)
            {
              for (int u = 0; u < urls.length; u += 2)
              {
                if (!linkset.contains(urls[u] + "|" + urls[u + 1]))
                {
                  linkset.addElement(urls[u] + "|" + urls[u + 1]);
                  addshowLink(linkMenu, label, urls[u + 1]);
                }
              }
            }
          }
          // Create urls from description but only for URL links which are regex
          // links
          if (descr != null && urlLink.getRegexReplace() != null)
          {
            // create link for this URL from description where regex matches
            String[] urls = urlLink.makeUrls(descr, true);
            if (urls != null)
            {
              for (int u = 0; u < urls.length; u += 2)
              {
                if (!linkset.contains(urls[u] + "|" + urls[u + 1]))
                {
                  linkset.addElement(urls[u] + "|" + urls[u + 1]);
                  addshowLink(linkMenu, label, urls[u + 1]);
                }
              }
            }
          }
        }
        else
        {
          if (!linkset.contains(label + "|" + urlLink.getUrl_prefix()))
          {
            linkset.addElement(label + "|" + urlLink.getUrl_prefix());
            // Add a non-dynamic link
            addshowLink(linkMenu, label, urlLink.getUrl_prefix());
          }
        }
      }
      if (sequence != null)
      {
        sequenceMenu.add(linkMenu);
      }
      else
      {
        add(linkMenu);
      }
    }
  }

  private void buildGroupURLMenu(SequenceGroup sg, Vector groupLinks)
  {

    // TODO: usability: thread off the generation of group url content so root
    // menu appears asap
    // sequence only URLs
    // ID/regex match URLs
    groupLinksMenu = new JMenu("Group Link");
    JMenu[] linkMenus = new JMenu[]
    { null, new JMenu("IDS"), new JMenu("Sequences"),
        new JMenu("IDS and Sequences") }; // three types of url that might be
                                          // created.
    SequenceI[] seqs = ap.av.getSelectionAsNewSequence();
    String[][] idandseqs = GroupUrlLink.formStrings(seqs);
    Hashtable commonDbrefs = new Hashtable();
    for (int sq = 0; sq < seqs.length; sq++)
    {

      int start = seqs[sq].findPosition(sg.getStartRes()), end = seqs[sq]
              .findPosition(sg.getEndRes());
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
    boolean addMenu = false; // indicates if there are any group links to give
                             // to user
    for (int i = 0; i < groupLinks.size(); i++)
    {
      String link = groupLinks.elementAt(i).toString();
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
      boolean usingNames = false;
      // Now see which parts of the group apply for this URL
      String ltarget = urlLink.getTarget(); // jalview.util.DBRefUtils.getCanonicalName(urlLink.getTarget());
      Object[] idset = (Object[]) commonDbrefs.get(ltarget.toUpperCase());
      String[] seqstr, ids; // input to makeUrl
      if (idset != null)
      {
        int numinput = ((int[]) idset[0])[0];
        String[] allids = ((String[]) idset[1]);
        seqstr = new String[numinput];
        ids = new String[numinput];
        for (int sq = 0, idcount = 0; sq < seqs.length; sq++)
        {
          if (allids[sq] != null)
          {
            ids[idcount] = allids[sq];
            seqstr[idcount++] = idandseqs[1][sq];
          }
        }
      }
      else
      {
        // just use the id/seq set
        seqstr = idandseqs[1];
        ids = idandseqs[0];
        usingNames = true;
      }
      // and try and make the groupURL!

      Object[] urlset = null;
      try
      {
        urlset = urlLink.makeUrlStubs(ids, seqstr,
                "FromJalview" + System.currentTimeMillis(), false);
      } catch (UrlStringTooLongException e)
      {
      }
      if (urlset != null)
      {
        int type = urlLink.getGroupURLType() & 3;
        // System.out.println(urlLink.getGroupURLType()
        // +" "+((String[])urlset[3])[0]);
        // first two bits ofurlLink type bitfield are sequenceids and sequences
        // TODO: FUTURE: ensure the groupURL menu structure can be generalised
        addshowLink(linkMenus[type], label
                + (((type & 1) == 1) ? ("("
                        + (usingNames ? "Names" : ltarget) + ")") : ""),
                urlLink, urlset);
        addMenu = true;
      }
    }
    if (addMenu)
    {
      groupLinksMenu = new JMenu("Group Links");
      for (int m = 0; m < linkMenus.length; m++)
      {
        if (linkMenus[m] != null
                && linkMenus[m].getMenuComponentCount() > 0)
        {
          groupLinksMenu.add(linkMenus[m]);
        }
      }

      groupMenu.add(groupLinksMenu);
    }
  }

  /**
   * add a show URL menu item to the given linkMenu
   * 
   * @param linkMenu
   * @param label
   *          - menu label string
   * @param url
   *          - url to open
   */
  private void addshowLink(JMenu linkMenu, String label, final String url)
  {
    JMenuItem item = new JMenuItem(label);
    item.setToolTipText("open URL: " + url);
    item.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        new Thread(new Runnable()
        {

          public void run()
          {
            showLink(url);
          }

        }).start();
      }
    });

    linkMenu.add(item);
  }

  /**
   * add a late bound groupURL item to the given linkMenu
   * 
   * @param linkMenu
   * @param label
   *          - menu label string
   * @param urlgenerator
   *          GroupURLLink used to generate URL
   * @param urlstub
   *          Object array returned from the makeUrlStubs function.
   */
  private void addshowLink(JMenu linkMenu, String label,
          final GroupUrlLink urlgenerator, final Object[] urlstub)
  {
    JMenuItem item = new JMenuItem(label);
    item.setToolTipText("open URL (" + urlgenerator.getUrl_prefix()
            + "..) (" + urlgenerator.getNumberInvolved(urlstub) + " seqs)"); // TODO:
                                                                             // put
                                                                             // in
                                                                             // info
                                                                             // about
                                                                             // what
                                                                             // is
                                                                             // being
                                                                             // sent.
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
            } catch (UrlStringTooLongException e)
            {
            }
          }

        }).start();
      }
    });

    linkMenu.add(item);
  }

  /**
   * DOCUMENT ME!
   * 
   * @throws Exception
   *           DOCUMENT ME!
   */
  private void jbInit() throws Exception
  {
    groupMenu.setText("Group");
    groupMenu.setText("Selection");
    groupName.setText("Name");
    groupName.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        groupName_actionPerformed();
      }
    });
    sequenceMenu.setText("Sequence");
    sequenceName.setText("Edit Name/Description");
    sequenceName.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        sequenceName_actionPerformed();
      }
    });
    sequenceDetails.setText("Sequence Details ...");
    sequenceDetails.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        sequenceDetails_actionPerformed();
      }
    });
    sequenceSelDetails.setText("Sequence Details ...");
    sequenceSelDetails
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                sequenceSelectionDetails_actionPerformed();
              }
            });
    PIDColour.setFocusPainted(false);
    unGroupMenuItem.setText("Remove Group");
    unGroupMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        unGroupMenuItem_actionPerformed();
      }
    });

    outline.setText("Border colour");
    outline.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        outline_actionPerformed();
      }
    });
    nucleotideMenuItem.setText("Nucleotide");
    nucleotideMenuItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nucleotideMenuItem_actionPerformed();
      }
    });
    colourMenu.setText("Group Colour");
    showBoxes.setText("Boxes");
    showBoxes.setState(true);
    showBoxes.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showBoxes_actionPerformed();
      }
    });
    showText.setText("Text");
    showText.setState(true);
    showText.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showText_actionPerformed();
      }
    });
    showColourText.setText("Colour Text");
    showColourText.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showColourText_actionPerformed();
      }
    });
    displayNonconserved.setText("Show Nonconserved");
    displayNonconserved.setState(true);
    displayNonconserved.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showNonconserved_actionPerformed();
      }
    });
    editMenu.setText("Edit");
    cut.setText("Cut");
    cut.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cut_actionPerformed();
      }
    });
    upperCase.setText("To Upper Case");
    upperCase.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        changeCase(e);
      }
    });
    copy.setText("Copy");
    copy.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        copy_actionPerformed();
      }
    });
    lowerCase.setText("To Lower Case");
    lowerCase.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        changeCase(e);
      }
    });
    toggle.setText("Toggle Case");
    toggle.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        changeCase(e);
      }
    });
    pdbMenu.setText("Associate Structure with Sequence");
    pdbFromFile.setText("From File");
    pdbFromFile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        pdbFromFile_actionPerformed();
      }
    });
    enterPDB.setText("Enter PDB Id");
    enterPDB.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        enterPDB_actionPerformed();
      }
    });
    discoverPDB.setText("Discover PDB ids");
    discoverPDB.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        discoverPDB_actionPerformed();
      }
    });
    outputMenu.setText("Output to Textbox...");
    sequenceFeature.setText("Create Sequence Feature");
    sequenceFeature.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        sequenceFeature_actionPerformed();
      }
    });
    textColour.setText("Text Colour");
    textColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        textColour_actionPerformed();
      }
    });
    jMenu1.setText("Group");
    structureMenu.setText("Structure");
    viewStructureMenu.setText("View Structure");
    // colStructureMenu.setText("Colour By Structure");
    editSequence.setText("Edit Sequence...");
    editSequence.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        editSequence_actionPerformed(actionEvent);
      }
    });

    /*
     * annotationMenuItem.setText("By Annotation");
     * annotationMenuItem.addActionListener(new ActionListener() { public void
     * actionPerformed(ActionEvent actionEvent) {
     * annotationMenuItem_actionPerformed(actionEvent); } });
     */
    groupMenu.add(sequenceSelDetails);
    add(groupMenu);
    add(sequenceMenu);
    this.add(structureMenu);
    groupMenu.add(editMenu);
    groupMenu.add(outputMenu);
    groupMenu.add(sequenceFeature);
    groupMenu.add(jMenu1);
    sequenceMenu.add(sequenceName);
    sequenceMenu.add(sequenceDetails);
    colourMenu.add(textColour);
    colourMenu.add(noColourmenuItem);
    colourMenu.add(clustalColour);
    colourMenu.add(BLOSUM62Colour);
    colourMenu.add(PIDColour);
    colourMenu.add(zappoColour);
    colourMenu.add(taylorColour);
    colourMenu.add(hydrophobicityColour);
    colourMenu.add(helixColour);
    colourMenu.add(strandColour);
    colourMenu.add(turnColour);
    colourMenu.add(buriedColour);
    colourMenu.add(nucleotideMenuItem);
    if (ap.getAlignment().isNucleotide())
    {
      colourMenu.add(purinePyrimidineColour);
    }
    // colourMenu.add(covariationColour);
    colourMenu.add(userDefinedColour);

    if (jalview.gui.UserDefinedColours.getUserColourSchemes() != null)
    {
      java.util.Enumeration userColours = jalview.gui.UserDefinedColours
              .getUserColourSchemes().keys();

      while (userColours.hasMoreElements())
      {
        JMenuItem item = new JMenuItem(userColours.nextElement().toString());
        item.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt)
          {
            userDefinedColour_actionPerformed(evt);
          }
        });
        colourMenu.add(item);
      }
    }

    colourMenu.addSeparator();
    colourMenu.add(abovePIDColour);
    colourMenu.add(conservationMenuItem);
    // colourMenu.add(annotationMenuItem);
    editMenu.add(copy);
    editMenu.add(cut);
    editMenu.add(editSequence);
    editMenu.add(upperCase);
    editMenu.add(lowerCase);
    editMenu.add(toggle);
    pdbMenu.add(pdbFromFile);
    pdbMenu.add(enterPDB);
    pdbMenu.add(discoverPDB);
    jMenu1.add(groupName);
    jMenu1.add(unGroupMenuItem);
    jMenu1.add(colourMenu);
    jMenu1.add(showBoxes);
    jMenu1.add(showText);
    jMenu1.add(showColourText);
    jMenu1.add(outline);
    jMenu1.add(displayNonconserved);
    structureMenu.add(pdbMenu);
    structureMenu.add(viewStructureMenu);
    // structureMenu.add(colStructureMenu);
    noColourmenuItem.setText("None");
    noColourmenuItem.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        noColourmenuItem_actionPerformed();
      }
    });

    clustalColour.setText("Clustalx colours");
    clustalColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        clustalColour_actionPerformed();
      }
    });
    zappoColour.setText("Zappo");
    zappoColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        zappoColour_actionPerformed();
      }
    });
    taylorColour.setText("Taylor");
    taylorColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        taylorColour_actionPerformed();
      }
    });
    hydrophobicityColour.setText("Hydrophobicity");
    hydrophobicityColour
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                hydrophobicityColour_actionPerformed();
              }
            });
    helixColour.setText("Helix propensity");
    helixColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        helixColour_actionPerformed();
      }
    });
    strandColour.setText("Strand propensity");
    strandColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        strandColour_actionPerformed();
      }
    });
    turnColour.setText("Turn propensity");
    turnColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        turnColour_actionPerformed();
      }
    });
    buriedColour.setText("Buried Index");
    buriedColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buriedColour_actionPerformed();
      }
    });
    abovePIDColour.setText("Above % Identity");
    abovePIDColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        abovePIDColour_actionPerformed();
      }
    });
    userDefinedColour.setText("User Defined...");
    userDefinedColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        userDefinedColour_actionPerformed(e);
      }
    });
    PIDColour.setText("Percentage Identity");
    PIDColour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        PIDColour_actionPerformed();
      }
    });
    BLOSUM62Colour.setText("BLOSUM62");
    BLOSUM62Colour.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        BLOSUM62Colour_actionPerformed();
      }
    });
    purinePyrimidineColour.setText("Purine/Pyrimidine");
    purinePyrimidineColour
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                purinePyrimidineColour_actionPerformed();
              }
            });
    /*
     * covariationColour.addActionListener(new java.awt.event.ActionListener() {
     * public void actionPerformed(ActionEvent e) {
     * covariationColour_actionPerformed(); } });
     */

    conservationMenuItem.setText("Conservation");
    conservationMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                conservationMenuItem_actionPerformed();
              }
            });
  }

  protected void sequenceSelectionDetails_actionPerformed()
  {
    createSequenceDetailsReport(ap.av.getSequenceSelection());
  }

  protected void sequenceDetails_actionPerformed()
  {
    createSequenceDetailsReport(new SequenceI[]
    { sequence });
  }

  public void createSequenceDetailsReport(SequenceI[] sequences)
  {
    CutAndPasteHtmlTransfer cap = new CutAndPasteHtmlTransfer();
    StringBuffer contents = new StringBuffer();
    for (SequenceI seq : sequences)
    {
      contents.append("<p><h2>Annotation for " + seq.getDisplayId(true)
              + "</h2></p><p>");
      new SequenceAnnotationReport(null)
              .createSequenceAnnotationReport(
                      contents,
                      seq,
                      true,
                      true,
                      false,
                      (ap.seqPanel.seqCanvas.fr != null) ? ap.seqPanel.seqCanvas.fr.minmax
                              : null);
      contents.append("</p>");
    }
    cap.setText("<html>" + contents.toString() + "</html>");

    Desktop.instance.addInternalFrame(cap, "Sequence Details for "
            + (sequences.length == 1 ? sequences[0].getDisplayId(true)
                    : "Selection"), 500, 400);

  }

  protected void showNonconserved_actionPerformed()
  {
    getGroup().setShowNonconserved(displayNonconserved.isSelected());
    refresh();
  }

  /**
   * call to refresh view after settings change
   */
  void refresh()
  {
    ap.updateAnnotation();
    ap.paintAlignment(true);

    PaintRefresher.Refresh(this, ap.av.getSequenceSetId());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void clustalColour_actionPerformed()
  {
    SequenceGroup sg = getGroup();
    sg.cs = new ClustalxColourScheme(sg, ap.av.getHiddenRepSequences());
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void zappoColour_actionPerformed()
  {
    getGroup().cs = new ZappoColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void taylorColour_actionPerformed()
  {
    getGroup().cs = new TaylorColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void hydrophobicityColour_actionPerformed()
  {
    getGroup().cs = new HydrophobicColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void helixColour_actionPerformed()
  {
    getGroup().cs = new HelixColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void strandColour_actionPerformed()
  {
    getGroup().cs = new StrandColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void turnColour_actionPerformed()
  {
    getGroup().cs = new TurnColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void buriedColour_actionPerformed()
  {
    getGroup().cs = new BuriedColourScheme();
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void nucleotideMenuItem_actionPerformed()
  {
    getGroup().cs = new NucleotideColourScheme();
    refresh();
  }

  protected void purinePyrimidineColour_actionPerformed()
  {
    getGroup().cs = new PurinePyrimidineColourScheme();
    refresh();
  }

  /*
   * protected void covariationColour_actionPerformed() { getGroup().cs = new
   * CovariationColourScheme(sequence.getAnnotation()[0]); refresh(); }
   */
  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void abovePIDColour_actionPerformed()
  {
    SequenceGroup sg = getGroup();
    if (sg.cs == null)
    {
      return;
    }

    if (abovePIDColour.isSelected())
    {
      sg.cs.setConsensus(AAFrequency.calculate(
              sg.getSequences(ap.av.getHiddenRepSequences()),
              sg.getStartRes(), sg.getEndRes() + 1));

      int threshold = SliderPanel.setPIDSliderSource(ap, sg.cs, getGroup()
              .getName());

      sg.cs.setThreshold(threshold, ap.av.getIgnoreGapsConsensus());

      SliderPanel.showPIDSlider();
    }
    else
    // remove PIDColouring
    {
      sg.cs.setThreshold(0, ap.av.getIgnoreGapsConsensus());
    }

    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void userDefinedColour_actionPerformed(ActionEvent e)
  {
    SequenceGroup sg = getGroup();

    if (e.getActionCommand().equals("User Defined..."))
    {
      new UserDefinedColours(ap, sg);
    }
    else
    {
      UserColourScheme udc = (UserColourScheme) UserDefinedColours
              .getUserColourSchemes().get(e.getActionCommand());

      sg.cs = udc;
    }
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void PIDColour_actionPerformed()
  {
    SequenceGroup sg = getGroup();
    sg.cs = new PIDColourScheme();
    sg.cs.setConsensus(AAFrequency.calculate(
            sg.getSequences(ap.av.getHiddenRepSequences()),
            sg.getStartRes(), sg.getEndRes() + 1));
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void BLOSUM62Colour_actionPerformed()
  {
    SequenceGroup sg = getGroup();

    sg.cs = new Blosum62ColourScheme();

    sg.cs.setConsensus(AAFrequency.calculate(
            sg.getSequences(ap.av.getHiddenRepSequences()),
            sg.getStartRes(), sg.getEndRes() + 1));

    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void noColourmenuItem_actionPerformed()
  {
    getGroup().cs = null;
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void conservationMenuItem_actionPerformed()
  {
    SequenceGroup sg = getGroup();
    if (sg.cs == null)
    {
      return;
    }

    if (conservationMenuItem.isSelected())
    {
      Conservation c = new Conservation("Group",
              ResidueProperties.propHash, 3, sg.getSequences(ap.av
                      .getHiddenRepSequences()), sg.getStartRes(),
              sg.getEndRes() + 1);

      c.calculate();
      c.verdict(false, ap.av.getConsPercGaps());

      sg.cs.setConservation(c);

      SliderPanel.setConservationSlider(ap, sg.cs, sg.getName());
      SliderPanel.showConservationSlider();
    }
    else
    // remove ConservationColouring
    {
      sg.cs.setConservation(null);
    }

    refresh();
  }

  public void annotationMenuItem_actionPerformed(ActionEvent actionEvent)
  {
    SequenceGroup sg = getGroup();
    if (sg == null)
    {
      return;
    }

    AnnotationColourGradient acg = new AnnotationColourGradient(
            sequence.getAnnotation()[0], null,
            AnnotationColourGradient.NO_THRESHOLD);

    acg.predefinedColours = true;
    sg.cs = acg;

    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void groupName_actionPerformed()
  {

    SequenceGroup sg = getGroup();
    EditNameDialog dialog = new EditNameDialog(sg.getName(),
            sg.getDescription(), "       Group Name ",
            "Group Description ", "Edit Group Name/Description",
            ap.alignFrame);

    if (!dialog.accept)
    {
      return;
    }

    sg.setName(dialog.getName());
    sg.setDescription(dialog.getDescription());
    refresh();
  }

  /**
   * Get selection group - adding it to the alignment if necessary.
   * 
   * @return sequence group to operate on
   */
  SequenceGroup getGroup()
  {
    SequenceGroup sg = ap.av.getSelectionGroup();
    // this method won't add a new group if it already exists
    if (sg != null)
    {
      ap.av.getAlignment().addGroup(sg);
    }

    return sg;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  void sequenceName_actionPerformed()
  {
    EditNameDialog dialog = new EditNameDialog(sequence.getName(),
            sequence.getDescription(), "       Sequence Name ",
            "Sequence Description ", "Edit Sequence Name/Description",
            ap.alignFrame);

    if (!dialog.accept)
    {
      return;
    }

    if (dialog.getName() != null)
    {
      if (dialog.getName().indexOf(" ") > -1)
      {
        JOptionPane.showMessageDialog(ap,
                "Spaces have been converted to \"_\"",
                "No spaces allowed in Sequence Name",
                JOptionPane.WARNING_MESSAGE);
      }

      sequence.setName(dialog.getName().replace(' ', '_'));
      ap.paintAlignment(false);
    }

    sequence.setDescription(dialog.getDescription());

    ap.av.firePropertyChange("alignment", null, ap.av.getAlignment()
            .getSequences());

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  void unGroupMenuItem_actionPerformed()
  {
    SequenceGroup sg = ap.av.getSelectionGroup();
    ap.av.getAlignment().deleteGroup(sg);
    ap.av.setSelectionGroup(null);
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void outline_actionPerformed()
  {
    SequenceGroup sg = getGroup();
    Color col = JColorChooser.showDialog(this, "Select Outline Colour",
            Color.BLUE);

    if (col != null)
    {
      sg.setOutlineColour(col);
    }

    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void showBoxes_actionPerformed()
  {
    getGroup().setDisplayBoxes(showBoxes.isSelected());
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void showText_actionPerformed()
  {
    getGroup().setDisplayText(showText.isSelected());
    refresh();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void showColourText_actionPerformed()
  {
    getGroup().setColourText(showColourText.isSelected());
    refresh();
  }

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

  void hideSequences(boolean representGroup)
  {
    SequenceGroup sg = ap.av.getSelectionGroup();
    if (sg == null || sg.getSize() < 1)
    {
      ap.av.hideSequence(new SequenceI[]
      { sequence });
      return;
    }

    ap.av.setSelectionGroup(null);

    if (representGroup)
    {
      ap.av.hideRepSequences(sequence, sg);

      return;
    }

    int gsize = sg.getSize();
    SequenceI[] hseqs;

    hseqs = new SequenceI[gsize];

    int index = 0;
    for (int i = 0; i < gsize; i++)
    {
      hseqs[index++] = sg.getSequenceAt(i);
    }

    ap.av.hideSequence(hseqs);
    // refresh(); TODO: ? needed ?
    ap.av.sendSelection();
  }

  public void copy_actionPerformed()
  {
    ap.alignFrame.copy_actionPerformed(null);
  }

  public void cut_actionPerformed()
  {
    ap.alignFrame.cut_actionPerformed(null);
  }

  void changeCase(ActionEvent e)
  {
    Object source = e.getSource();
    SequenceGroup sg = ap.av.getSelectionGroup();

    if (sg != null)
    {
      int[][] startEnd = ap.av.getVisibleRegionBoundaries(sg.getStartRes(),
              sg.getEndRes() + 1);

      String description;
      int caseChange;

      if (source == toggle)
      {
        description = "Toggle Case";
        caseChange = ChangeCaseCommand.TOGGLE_CASE;
      }
      else if (source == upperCase)
      {
        description = "To Upper Case";
        caseChange = ChangeCaseCommand.TO_UPPER;
      }
      else
      {
        description = "To Lower Case";
        caseChange = ChangeCaseCommand.TO_LOWER;
      }

      ChangeCaseCommand caseCommand = new ChangeCaseCommand(description,
              sg.getSequencesAsArray(ap.av.getHiddenRepSequences()),
              startEnd, caseChange);

      ap.alignFrame.addHistoryItem(caseCommand);

      ap.av.firePropertyChange("alignment", null, ap.av.getAlignment()
              .getSequences());

    }
  }

  public void outputText_actionPerformed(ActionEvent e)
  {
    CutAndPasteTransfer cap = new CutAndPasteTransfer();
    cap.setForInput(null);
    Desktop.addInternalFrame(cap,
            "Alignment output - " + e.getActionCommand(), 600, 500);

    String[] omitHidden = null;

    System.out.println("PROMPT USER HERE"); // TODO: decide if a prompt happens
    // or we simply trust the user wants
    // wysiwig behaviour
    SequenceGroup sg = ap.av.getSelectionGroup();
    ColumnSelection csel = new ColumnSelection(ap.av.getColumnSelection());
    omitHidden = ap.av.getViewAsString(true);
    Alignment oal = new Alignment(ap.av.getSequenceSelection());
    AlignmentAnnotation[] nala = ap.av.getAlignment()
            .getAlignmentAnnotation();
    if (nala != null)
    {
      for (int i = 0; i < nala.length; i++)
      {
        AlignmentAnnotation na = nala[i];
        oal.addAnnotation(na);
      }
    }
    cap.setText(new FormatAdapter().formatSequences(e.getActionCommand(),
            oal, omitHidden, csel, sg));
    oal = null;
  }

  public void pdbFromFile_actionPerformed()
  {
    jalview.io.JalviewFileChooser chooser = new jalview.io.JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"));
    chooser.setFileView(new jalview.io.JalviewFileView());
    chooser.setDialogTitle("Select a PDB file for "
            + sequence.getDisplayId(false));
    chooser.setToolTipText("Load a PDB file and associate it with sequence '"
            + sequence.getDisplayId(false) + "'");

    int value = chooser.showOpenDialog(null);

    if (value == jalview.io.JalviewFileChooser.APPROVE_OPTION)
    {
      String choice = chooser.getSelectedFile().getPath();
      jalview.bin.Cache.setProperty("LAST_DIRECTORY", choice);
      new AssociatePdbFileWithSeq().associatePdbWithSeq(choice,
              jalview.io.AppletFormatAdapter.FILE, sequence, true);
    }

  }

  public void enterPDB_actionPerformed()
  {
    String id = JOptionPane.showInternalInputDialog(Desktop.desktop,
            "Enter PDB Id", "Enter PDB Id", JOptionPane.QUESTION_MESSAGE);

    if (id != null && id.length() > 0)
    {
      PDBEntry entry = new PDBEntry();
      entry.setId(id.toUpperCase());
      sequence.getDatasetSequence().addPDBId(entry);
    }
  }

  public void discoverPDB_actionPerformed()
  {

    final SequenceI[] sequences = ((ap.av.getSelectionGroup() == null) ? new SequenceI[]
    { sequence }
            : ap.av.getSequenceSelection());
    Thread discpdb = new Thread(new Runnable()
    {
      public void run()
      {

        new jalview.ws.DBRefFetcher(sequences, ap.alignFrame)
                .fetchDBRefs(false);
      }

    });
    discpdb.start();
  }

  public void sequenceFeature_actionPerformed()
  {
    SequenceGroup sg = ap.av.getSelectionGroup();
    if (sg == null)
    {
      return;
    }

    int rsize = 0, gSize = sg.getSize();
    SequenceI[] rseqs, seqs = new SequenceI[gSize];
    SequenceFeature[] tfeatures, features = new SequenceFeature[gSize];

    for (int i = 0; i < gSize; i++)
    {
      int start = sg.getSequenceAt(i).findPosition(sg.getStartRes());
      int end = sg.findEndRes(sg.getSequenceAt(i));
      if (start <= end)
      {
        seqs[rsize] = sg.getSequenceAt(i).getDatasetSequence();
        features[rsize] = new SequenceFeature(null, null, null, start, end,
                "Jalview");
        rsize++;
      }
    }
    rseqs = new SequenceI[rsize];
    tfeatures = new SequenceFeature[rsize];
    System.arraycopy(seqs, 0, rseqs, 0, rsize);
    System.arraycopy(features, 0, tfeatures, 0, rsize);
    features = tfeatures;
    seqs = rseqs;
    if (ap.seqPanel.seqCanvas.getFeatureRenderer().amendFeatures(seqs,
            features, true, ap))
    {
      ap.alignFrame.setShowSeqFeatures(true);
      ap.highlightSearchResults(null);
    }
  }

  public void textColour_actionPerformed()
  {
    SequenceGroup sg = getGroup();
    if (sg != null)
    {
      new TextColourChooser().chooseColour(ap, sg);
    }
  }

  public void colourByStructure(String pdbid)
  {
    Annotation[] anots = ap.av.getStructureSelectionManager()
            .colourSequenceFromStructure(sequence, pdbid);

    AlignmentAnnotation an = new AlignmentAnnotation("Structure",
            "Coloured by " + pdbid, anots);

    ap.av.getAlignment().addAnnotation(an);
    an.createSequenceMapping(sequence, 0, true);
    // an.adjustForAlignment();
    ap.av.getAlignment().setAnnotationIndex(an, 0);

    ap.adjustAnnotationHeight();

    sequence.addAlignmentAnnotation(an);

  }

  public void editSequence_actionPerformed(ActionEvent actionEvent)
  {
    SequenceGroup sg = ap.av.getSelectionGroup();
    
    if (sg != null)
    {
      if (sequence == null) {
            sequence = (Sequence) sg.getSequenceAt(0);
        }

      EditNameDialog dialog = new EditNameDialog(
              sequence.getSequenceAsString(sg.getStartRes(),
                      sg.getEndRes() + 1), null, "Edit Sequence ", null,
              "Edit Sequence", ap.alignFrame);
     
      if (dialog.accept)
      {
        EditCommand editCommand = new EditCommand("Edit Sequences",
                EditCommand.REPLACE, dialog.getName().replace(' ',
                        ap.av.getGapCharacter()),
                sg.getSequencesAsArray(ap.av.getHiddenRepSequences()),
                sg.getStartRes(), sg.getEndRes() + 1, ap.av.getAlignment());

        ap.alignFrame.addHistoryItem(editCommand);

        ap.av.firePropertyChange("alignment", null, ap.av.getAlignment()
                .getSequences());
      }
    }
  }

}
