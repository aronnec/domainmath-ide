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
// NewickFile.java
// Tree I/O
// http://evolution.genetics.washington.edu/phylip/newick_doc.html
// TODO: Implement Basic NHX tag parsing and preservation
// TODO: http://evolution.genetics.wustl.edu/eddy/forester/NHX.html
// TODO: Extended SequenceNodeI to hold parsed NHX strings
package jalview.io;

import java.io.*;
import java.util.StringTokenizer;

import jalview.datamodel.*;

/**
 * Parse a new hanpshire style tree Caveats: NHX files are NOT supported and the
 * tree distances and topology are unreliable when they are parsed. TODO: on
 * this: NHX codes are appended in comments beginning with &&NHX. The codes are
 * given below (from http://www.phylosoft.org/forester/NHX.html): Element Type
 * Description Corresponding phyloXML element (parent element in parentheses) no
 * tag string name of this node/clade (MUST BE FIRST, IF ASSIGNED)
 * <name>(<clade>) : decimal branch length to parent node (MUST BE SECOND, IF
 * ASSIGNED) <branch_length>(<clade>) :GN= string gene name <name>(<sequence>)
 * :AC= string sequence accession <accession>(<sequence>) :ND= string node
 * identifier - if this is being used, it has to be unique within each phylogeny
 * <node_id>(<clade>) :B= decimal confidence value for parent branch
 * <confidence>(<clade>) :D= 'T', 'F', or '?' 'T' if this node represents a
 * duplication event - 'F' if this node represents a speciation event, '?' if
 * this node represents an unknown event (D= tag should be replaced by Ev= tag)
 * n/a :Ev=duplications>speciations>gene losses>event type>duplication type int
 * int int string string event (replaces the =D tag), number of duplication,
 * speciation, and gene loss events, type of event (transfer, fusion, root,
 * unknown, other, speciation_duplication_loss, unassigned) <events>(<clade>)
 * :E= string EC number at this node <annotation>(<sequence>) :Fu= string
 * function at this node <annotation>(<sequence>)
 * :DS=protein-length>from>to>support>name>from>... int int int double string
 * int ... domain structure at this node <domain_architecture>(<sequence>) :S=
 * string species name of the species/phylum at this node <taxonomy>(<clade>)
 * :T= integer taxonomy ID of the species/phylum at this node <id>(<taxonomy>)
 * :W= integer width of parent branch <width>(<clade>) :C=rrr.ggg.bbb
 * integer.integer.integer color of parent branch <color>(<clade>) :Co= 'Y' or
 * 'N' collapse this node when drawing the tree (default is not to collapse) n/a
 * :XB= string custom data associated with a branch <property>(<clade>) :XN=
 * string custom data associated with a node <property>(<clade>) :O= integer
 * orthologous to this external node n/a :SN= integer subtree neighbors n/a :SO=
 * integer super orthologous (no duplications on paths) to this external node
 * n/a
 * 
 * @author Jim Procter
 * @version $Revision$
 */
public class NewickFile extends FileParse
{
  SequenceNode root;

  private boolean HasBootstrap = false;

  private boolean HasDistances = false;

  private boolean RootHasDistance = false;

  // File IO Flags
  boolean ReplaceUnderscores = false;

  boolean printRootInfo = true;

  private com.stevesoft.pat.Regex[] NodeSafeName = new com.stevesoft.pat.Regex[]
  { new com.stevesoft.pat.Regex().perlCode("m/[\\[,:'()]/"), // test for
      // requiring
      // quotes
      new com.stevesoft.pat.Regex().perlCode("s/'/''/"), // escaping quote
      // characters
      new com.stevesoft.pat.Regex().perlCode("s/\\/w/_/") // unqoted whitespace
  // transformation
  };

  char QuoteChar = '\'';

  /**
   * Creates a new NewickFile object.
   * 
   * @param inStr
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public NewickFile(String inStr) throws IOException
  {
    super(inStr, "Paste");
  }

  /**
   * Creates a new NewickFile object.
   * 
   * @param inFile
   *          DOCUMENT ME!
   * @param type
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public NewickFile(String inFile, String type) throws IOException
  {
    super(inFile, type);
  }

  public NewickFile(FileParse source) throws IOException
  {
    super(source);
  }

  /**
   * Creates a new NewickFile object.
   * 
   * @param newtree
   *          DOCUMENT ME!
   */
  public NewickFile(SequenceNode newtree)
  {
    root = newtree;
  }

  /**
   * Creates a new NewickFile object.
   * 
   * @param newtree
   *          DOCUMENT ME!
   * @param bootstrap
   *          DOCUMENT ME!
   */
  public NewickFile(SequenceNode newtree, boolean bootstrap)
  {
    HasBootstrap = bootstrap;
    root = newtree;
  }

  /**
   * Creates a new NewickFile object.
   * 
   * @param newtree
   *          DOCUMENT ME!
   * @param bootstrap
   *          DOCUMENT ME!
   * @param distances
   *          DOCUMENT ME!
   */
  public NewickFile(SequenceNode newtree, boolean bootstrap,
          boolean distances)
  {
    root = newtree;
    HasBootstrap = bootstrap;
    HasDistances = distances;
  }

  /**
   * Creates a new NewickFile object.
   * 
   * @param newtree
   *          DOCUMENT ME!
   * @param bootstrap
   *          DOCUMENT ME!
   * @param distances
   *          DOCUMENT ME!
   * @param rootdistance
   *          DOCUMENT ME!
   */
  public NewickFile(SequenceNode newtree, boolean bootstrap,
          boolean distances, boolean rootdistance)
  {
    root = newtree;
    HasBootstrap = bootstrap;
    HasDistances = distances;
    RootHasDistance = rootdistance;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param Error
   *          DOCUMENT ME!
   * @param Er
   *          DOCUMENT ME!
   * @param r
   *          DOCUMENT ME!
   * @param p
   *          DOCUMENT ME!
   * @param s
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private String ErrorStringrange(String Error, String Er, int r, int p,
          String s)
  {
    return ((Error == null) ? "" : Error)
            + Er
            + " at position "
            + p
            + " ( "
            + s.substring(((p - r) < 0) ? 0 : (p - r),
                    ((p + r) > s.length()) ? s.length() : (p + r)) + " )\n";
  }

  // @tree annotations
  // These are set automatically by the reader
  public boolean HasBootstrap()
  {
    return HasBootstrap;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean HasDistances()
  {
    return HasDistances;
  }

  public boolean HasRootDistance()
  {
    return RootHasDistance;
  }

  /**
   * parse the filesource as a newick file (new hampshire and/or extended)
   * 
   * @throws IOException
   *           with a line number and character position for badly formatted NH
   *           strings
   */
  public void parse() throws IOException
  {
    String nf;

    { // fill nf with complete tree file

      StringBuffer file = new StringBuffer();

      while ((nf = nextLine()) != null)
      {
        file.append(nf);
      }

      nf = file.toString();
    }

    root = new SequenceNode();

    SequenceNode realroot = null;
    SequenceNode c = root;

    int d = -1;
    int cp = 0;
    // int flen = nf.length();

    String Error = null;
    String nodename = null;
    String commentString2 = null; // comments after simple node props

    float DefDistance = (float) 0.001; // @param Default distance for a node -
    // very very small
    int DefBootstrap = -1; // @param Default bootstrap for a node

    float distance = DefDistance;
    int bootstrap = DefBootstrap;

    boolean ascending = false; // flag indicating that we are leaving the
    // current node

    com.stevesoft.pat.Regex majorsyms = new com.stevesoft.pat.Regex(
            "[(\\['),;]");

    int nextcp = 0;
    int ncp = cp;
    while (majorsyms.searchFrom(nf, cp) && (Error == null))
    {
      int fcp = majorsyms.matchedFrom();
      char schar;
      switch (schar = nf.charAt(fcp))
      {
      case '(':

        // ascending should not be set
        // New Internal node
        if (ascending)
        {
          Error = ErrorStringrange(Error, "Unexpected '('", 7, fcp, nf);

          continue;
        }

        ;
        d++;

        if (c.right() == null)
        {
          c.setRight(new SequenceNode(null, c, null, DefDistance,
                  DefBootstrap, false));
          c = (SequenceNode) c.right();
        }
        else
        {
          if (c.left() != null)
          {
            // Dummy node for polytomy - keeps c.left free for new node
            SequenceNode tmpn = new SequenceNode(null, c, null, 0, 0, true);
            tmpn.SetChildren(c.left(), c.right());
            c.setRight(tmpn);
          }

          c.setLeft(new SequenceNode(null, c, null, DefDistance,
                  DefBootstrap, false));
          c = (SequenceNode) c.left();
        }

        if (realroot == null)
        {
          realroot = c;
        }

        nodename = null;
        distance = DefDistance;
        bootstrap = DefBootstrap;
        cp = fcp + 1;

        break;

      // Deal with quoted fields
      case '\'':

        com.stevesoft.pat.Regex qnodename = new com.stevesoft.pat.Regex(
                "([^']|'')+'");

        if (qnodename.searchFrom(nf, fcp))
        {
          int nl = qnodename.stringMatched().length();
          nodename = new String(qnodename.stringMatched().substring(0,
                  nl - 1));
          cp = fcp + nl + 1;
        }
        else
        {
          Error = ErrorStringrange(Error,
                  "Unterminated quotes for nodename", 7, fcp, nf);
        }

        break;

      default:
        if (schar == ';')
        {
          if (d != -1)
          {
            Error = ErrorStringrange(Error, "Wayward semicolon (depth=" + d
                    + ")", 7, fcp, nf);
          }
          // cp advanced at the end of default
        }
        if (schar == '[')
        {
          // node string contains Comment or structured/extended NH format info
          /*
           * if ((fcp-cp>1 && nf.substring(cp,fcp).trim().length()>1)) { // will
           * process in remains System.err.println("skipped text:
           * '"+nf.substring(cp,fcp)+"'"); }
           */
          // verify termination.
          com.stevesoft.pat.Regex comment = new com.stevesoft.pat.Regex("]");
          if (comment.searchFrom(nf, fcp))
          {
            // Skip the comment field
            nextcp = comment.matchedFrom() + 1;
            warningMessage = "Tree file contained comments which may confuse input algorithm.";
            break;

            // cp advanced at the end of default to nextcp, ncp is unchanged so
            // any node info can be read.
          }
          else
          {
            Error = ErrorStringrange(Error, "Unterminated comment", 3, fcp,
                    nf);
          }

          ;
        }
        // Parse simpler field strings
        String fstring = nf.substring(ncp, fcp);
        // remove any comments before we parse the node info
        // TODO: test newick file with quoted square brackets in node name (is
        // this allowed?)
        while (fstring.indexOf(']') > -1)
        {
          int cstart = fstring.indexOf('[');
          int cend = fstring.indexOf(']');
          commentString2 = fstring.substring(cstart + 1, cend);
          fstring = fstring.substring(0, cstart)
                  + fstring.substring(cend + 1);

        }
        com.stevesoft.pat.Regex uqnodename = new com.stevesoft.pat.Regex(
                "\\b([^' :;\\](),]+)");
        com.stevesoft.pat.Regex nbootstrap = new com.stevesoft.pat.Regex(
                "\\s*([0-9+]+)\\s*:");
        com.stevesoft.pat.Regex ndist = new com.stevesoft.pat.Regex(
                ":([-0-9Ee.+]+)");

        if (uqnodename.search(fstring)
                && ((uqnodename.matchedFrom(1) == 0) || (fstring
                        .charAt(uqnodename.matchedFrom(1) - 1) != ':'))) // JBPNote
        // HACK!
        {
          if (nodename == null)
          {
            if (ReplaceUnderscores)
            {
              nodename = uqnodename.stringMatched(1).replace('_', ' ');
            }
            else
            {
              nodename = uqnodename.stringMatched(1);
            }
          }
          else
          {
            Error = ErrorStringrange(Error,
                    "File has broken algorithm - overwritten nodename", 10,
                    fcp, nf);
          }
        }
        // get comment bootstraps

        if (nbootstrap.search(fstring))
        {
          if (nbootstrap.stringMatched(1).equals(
                  uqnodename.stringMatched(1)))
          {
            nodename = null; // no nodename here.
          }
          if (nodename == null
                  || nodename.length() == 0
                  || nbootstrap.matchedFrom(1) > (uqnodename.matchedFrom(1) + uqnodename
                          .stringMatched().length()))
          {
            try
            {
              bootstrap = (new Integer(nbootstrap.stringMatched(1)))
                      .intValue();
              HasBootstrap = true;
            } catch (Exception e)
            {
              Error = ErrorStringrange(Error,
                      "Can't parse bootstrap value", 4,
                      ncp + nbootstrap.matchedFrom(), nf);
            }
          }
        }

        boolean nodehasdistance = false;

        if (ndist.search(fstring))
        {
          try
          {
            distance = (new Float(ndist.stringMatched(1))).floatValue();
            HasDistances = true;
            nodehasdistance = true;
          } catch (Exception e)
          {
            Error = ErrorStringrange(Error,
                    "Can't parse node distance value", 7,
                    ncp + ndist.matchedFrom(), nf);
          }
        }

        if (ascending)
        {
          // Write node info here
          c.setName(nodename);
          // Trees without distances still need a render distance
          c.dist = (HasDistances) ? distance : DefDistance;
          // be consistent for internal bootstrap defaults too
          c.setBootstrap((HasBootstrap) ? bootstrap : DefBootstrap);
          if (c == realroot)
          {
            RootHasDistance = nodehasdistance; // JBPNote This is really
            // UGLY!!! Ensure root node gets
            // its given distance
          }
          parseNHXNodeProps(c, commentString2);
          commentString2 = null;
        }
        else
        {
          // Find a place to put the leaf
          SequenceNode newnode = new SequenceNode(null, c, nodename,
                  (HasDistances) ? distance : DefDistance,
                  (HasBootstrap) ? bootstrap : DefBootstrap, false);
          parseNHXNodeProps(c, commentString2);
          commentString2 = null;

          if (c.right() == null)
          {
            c.setRight(newnode);
          }
          else
          {
            if (c.left() == null)
            {
              c.setLeft(newnode);
            }
            else
            {
              // Insert a dummy node for polytomy
              // dummy nodes have distances
              SequenceNode newdummy = new SequenceNode(null, c, null,
                      (HasDistances ? 0 : DefDistance), 0, true);
              newdummy.SetChildren(c.left(), newnode);
              c.setLeft(newdummy);
            }
          }
        }

        if (ascending)
        {
          // move back up the tree from preceding closure
          c = c.AscendTree();

          if ((d > -1) && (c == null))
          {
            Error = ErrorStringrange(
                    Error,
                    "File broke algorithm: Lost place in tree (is there an extra ')' ?)",
                    7, fcp, nf);
          }
        }

        if (nf.charAt(fcp) == ')')
        {
          d--;
          ascending = true;
        }
        else
        {
          if (nf.charAt(fcp) == ',')
          {
            if (ascending)
            {
              ascending = false;
            }
            else
            {
              // Just advance focus, if we need to
              if ((c.left() != null) && (!c.left().isLeaf()))
              {
                c = (SequenceNode) c.left();
              }
            }
          }
        }

        // Reset new node properties to obvious fakes
        nodename = null;
        distance = DefDistance;
        bootstrap = DefBootstrap;
        commentString2 = null;
      }
      if (nextcp == 0)
      {
        ncp = cp = fcp + 1;
      }
      else
      {
        cp = nextcp;
        nextcp = 0;
      }
    }

    if (Error != null)
    {
      throw (new IOException("NewickFile: " + Error + "\n"));
    }
    if (root == null)
    {
      throw (new IOException("NewickFile: No Tree read in\n"));
    }
    // THe next line is failing for topali trees - not sure why yet. if
    // (root.right()!=null && root.isDummy())
    root = (SequenceNode) root.right().detach(); // remove the imaginary root.

    if (!RootHasDistance)
    {
      root.dist = (HasDistances) ? 0 : DefDistance;
    }
  }

  /**
   * parse NHX codes in comment strings and update NewickFile state flags for
   * distances and bootstraps, and add any additional properties onto the node.
   * 
   * @param c
   * @param commentString
   * @param commentString2
   */
  private void parseNHXNodeProps(SequenceNode c, String commentString)
  {
    // TODO: store raw comment on the sequenceNode so it can be recovered when
    // tree is output
    if (commentString != null && commentString.startsWith("&&NHX"))
    {
      StringTokenizer st = new StringTokenizer(commentString.substring(5),
              ":");
      while (st.hasMoreTokens())
      {
        String tok = st.nextToken();
        int colpos = tok.indexOf("=");

        if (colpos > -1)
        {
          String code = tok.substring(0, colpos);
          String value = tok.substring(colpos + 1);
          try
          {
            // parse out code/value pairs
            if (code.toLowerCase().equals("b"))
            {
              int v = -1;
              Float iv = new Float(value);
              v = iv.intValue(); // jalview only does integer bootstraps
              // currently
              c.setBootstrap(v);
              HasBootstrap = true;
            }
            // more codes here.
          } catch (Exception e)
          {
            System.err.println("Couldn't parse code '" + code + "' = '"
                    + value + "'");
            e.printStackTrace(System.err);
          }
        }
      }
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public SequenceNode getTree()
  {
    return root;
  }

  /**
   * Generate a newick format tree according to internal flags for bootstraps,
   * distances and root distances.
   * 
   * @return new hampshire tree in a single line
   */
  public String print()
  {
    synchronized (this)
    {
      StringBuffer tf = new StringBuffer();
      print(tf, root);

      return (tf.append(";").toString());
    }
  }

  /**
   * 
   * 
   * Generate a newick format tree according to internal flags for distances and
   * root distances and user specificied writing of bootstraps.
   * 
   * @param withbootstraps
   *          controls if bootstrap values are explicitly written.
   * 
   * @return new hampshire tree in a single line
   */
  public String print(boolean withbootstraps)
  {
    synchronized (this)
    {
      boolean boots = this.HasBootstrap;
      this.HasBootstrap = withbootstraps;

      String rv = print();
      this.HasBootstrap = boots;

      return rv;
    }
  }

  /**
   * 
   * Generate newick format tree according to internal flags for writing root
   * node distances.
   * 
   * @param withbootstraps
   *          explicitly write bootstrap values
   * @param withdists
   *          explicitly write distances
   * 
   * @return new hampshire tree in a single line
   */
  public String print(boolean withbootstraps, boolean withdists)
  {
    synchronized (this)
    {
      boolean dists = this.HasDistances;
      this.HasDistances = withdists;

      String rv = print(withbootstraps);
      this.HasDistances = dists;

      return rv;
    }
  }

  /**
   * Generate newick format tree according to user specified flags
   * 
   * @param withbootstraps
   *          explicitly write bootstrap values
   * @param withdists
   *          explicitly write distances
   * @param printRootInfo
   *          explicitly write root distance
   * 
   * @return new hampshire tree in a single line
   */
  public String print(boolean withbootstraps, boolean withdists,
          boolean printRootInfo)
  {
    synchronized (this)
    {
      boolean rootinfo = printRootInfo;
      this.printRootInfo = printRootInfo;

      String rv = print(withbootstraps, withdists);
      this.printRootInfo = rootinfo;

      return rv;
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  char getQuoteChar()
  {
    return QuoteChar;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param c
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  char setQuoteChar(char c)
  {
    char old = QuoteChar;
    QuoteChar = c;

    return old;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param name
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private String nodeName(String name)
  {
    if (NodeSafeName[0].search(name))
    {
      return QuoteChar + NodeSafeName[1].replaceAll(name) + QuoteChar;
    }
    else
    {
      return NodeSafeName[2].replaceAll(name);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param c
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private String printNodeField(SequenceNode c)
  {
    return ((c.getName() == null) ? "" : nodeName(c.getName()))
            + ((HasBootstrap) ? ((c.getBootstrap() > -1) ? ((c.getName() != null ? " "
                    : "") + c.getBootstrap())
                    : "")
                    : "") + ((HasDistances) ? (":" + c.dist) : "");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param root
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private String printRootField(SequenceNode root)
  {
    return (printRootInfo) ? (((root.getName() == null) ? ""
            : nodeName(root.getName()))
            + ((HasBootstrap) ? ((root.getBootstrap() > -1) ? ((root
                    .getName() != null ? " " : "") + +root.getBootstrap())
                    : "") : "") + ((RootHasDistance) ? (":" + root.dist)
            : "")) : "";
  }

  // Non recursive call deals with root node properties
  public void print(StringBuffer tf, SequenceNode root)
  {
    if (root != null)
    {
      if (root.isLeaf() && printRootInfo)
      {
        tf.append(printRootField(root));
      }
      else
      {
        if (root.isDummy())
        {
          _print(tf, (SequenceNode) root.right());
          _print(tf, (SequenceNode) root.left());
        }
        else
        {
          tf.append("(");
          _print(tf, (SequenceNode) root.right());

          if (root.left() != null)
          {
            tf.append(",");
          }

          _print(tf, (SequenceNode) root.left());
          tf.append(")" + printRootField(root));
        }
      }
    }
  }

  // Recursive call for non-root nodes
  public void _print(StringBuffer tf, SequenceNode c)
  {
    if (c != null)
    {
      if (c.isLeaf())
      {
        tf.append(printNodeField(c));
      }
      else
      {
        if (c.isDummy())
        {
          _print(tf, (SequenceNode) c.left());
          if (c.left() != null)
          {
            tf.append(",");
          }
          _print(tf, (SequenceNode) c.right());
        }
        else
        {
          tf.append("(");
          _print(tf, (SequenceNode) c.right());

          if (c.left() != null)
          {
            tf.append(",");
          }

          _print(tf, (SequenceNode) c.left());
          tf.append(")" + printNodeField(c));
        }
      }
    }
  }

  // Test
  public static void main(String[] args)
  {
    try
    {
      if (args == null || args.length != 1)
      {
        System.err
                .println("Takes one argument - file name of a newick tree file.");
        System.exit(0);
      }

      File fn = new File(args[0]);

      StringBuffer newickfile = new StringBuffer();
      BufferedReader treefile = new BufferedReader(new FileReader(fn));
      String l;

      while ((l = treefile.readLine()) != null)
      {
        newickfile.append(l);
      }

      treefile.close();
      System.out.println("Read file :\n");

      NewickFile trf = new NewickFile(args[0], "File");
      trf.parse();
      System.out.println("Original file :\n");

      com.stevesoft.pat.Regex nonl = new com.stevesoft.pat.Regex("\n+", "");
      System.out.println(nonl.replaceAll(newickfile.toString()) + "\n");

      System.out.println("Parsed file.\n");
      System.out.println("Default output type for original input.\n");
      System.out.println(trf.print());
      System.out.println("Without bootstraps.\n");
      System.out.println(trf.print(false));
      System.out.println("Without distances.\n");
      System.out.println(trf.print(true, false));
      System.out.println("Without bootstraps but with distanecs.\n");
      System.out.println(trf.print(false, true));
      System.out.println("Without bootstraps or distanecs.\n");
      System.out.println(trf.print(false, false));
      System.out.println("With bootstraps and with distances.\n");
      System.out.println(trf.print(true, true));
    } catch (java.io.IOException e)
    {
      System.err.println("Exception\n" + e);
      e.printStackTrace();
    }
  }
}
