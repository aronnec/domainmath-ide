/*
 * Copyright (C) 2013 Vinu K.N
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.domainmath.gui.dataview;

import org.domainmath.gui.databrowser.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.domainmath.gui.MainFrame;
import org.domainmath.gui.Util.DomainMathFileFilter;
import org.domainmath.gui.databrowser.DataBrowserTableModel;
import org.domainmath.gui.dataview.DataViewFrame;
import org.domainmath.gui.varview.*;

public class StructBrowserPanel extends JPanel {
    JTable table;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/varview/resources/varview_en"); 
        
    private final JPopupMenu popup = new JPopupMenu();
    PopupActionListener popupActionListener;
    TableMouseListener tableMouseListener;
    private final JMenuItem saveAllItem;
    private final JMenuItem viewItem;
    private final JMenuItem plotItem;
    private final JMenuItem deleteItem;
    private final JMenuItem renameItem;
    private final JMenuItem duplicateItem;
    private final JMenuItem refreshItem;
    private final DataBrowserTableModel model;
   
    private final String directory;
    private final JMenuItem saveItem;
    private final JMenuItem loadItem;
    
    private JButton loadButton;
    private JButton saveButton;
    private JButton saveAllButton;
    private JButton viewButton;
    private JButton refreshButton;
    
    private JButton deleteButton;
    private JButton addButton;
    private final JMenuItem addItem;
    private final JFrame frame;
    private final JMenuItem exportItem;
    private final JMenuItem exportAllItem;
    private final JMenuItem importItem;
    private ImageVarDialog dlg;
    
    private JMenuItem menuItem;
    private final JMenuItem clearAllVarItem;
    
    private VarTask varTask;
    private final JMenuItem openSelectionItem;
    private final JMenuItem remoteItem;
    
    
    
  
    public StructBrowserPanel(String directory,JFrame frame) {
        super(new BorderLayout());
        table = new JTable();
        this.frame =frame;
        this.directory = directory;
        popupActionListener = new PopupActionListener();
        tableMouseListener = new TableMouseListener();
        
        model = new DataBrowserTableModel(directory);
        table.setModel(model);
        model.init();

        
       // table.setAutoCreateColumnsFromModel(true);
       // table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
       
       
        table.setRowHeight(20);
       addItem = new JMenuItem(bundle.getString("addItem.name"));
       
       clearAllVarItem = new JMenuItem("Clear All");
       openSelectionItem = new JMenuItem("Open Selection");
       importItem = new JMenuItem(bundle.getString("importItem.name"));
       exportItem = new JMenuItem(bundle.getString("exportItem.name"));
       exportAllItem = new JMenuItem(bundle.getString("exportAllItem.name"));
       loadItem = new JMenuItem(bundle.getString("loadItem.name"));
       saveItem = new JMenuItem(bundle.getString("saveItem.name"));
        saveAllItem = new JMenuItem(bundle.getString("saveAllItem.name"));
        viewItem = new JMenuItem(bundle.getString("viewItem.name"));
        plotItem = new JMenuItem(bundle.getString("plotItem.name"));
        deleteItem = new JMenuItem(bundle.getString("deleteItem.name"));
        renameItem = new JMenuItem(bundle.getString("renameItem.name"));
        duplicateItem = new JMenuItem(bundle.getString("duplicateItem.name"));
        refreshItem = new JMenuItem(bundle.getString("refreshItem.name"));
        remoteItem= new JMenuItem("Remote");
        
        popup.add(viewItem);
        popup.add(openSelectionItem);
        
        popup.add(remoteItem);
        popup.add(addItem);
        popup.add(deleteItem);
        popup.add(clearAllVarItem);
        popup.add(renameItem);
        popup.add(duplicateItem);
        popup.add(plotItem);
        popup.addSeparator();
        
        popup.add(loadItem);
        popup.add(saveItem);
        popup.add(saveAllItem);
        popup.addSeparator();
        popup.add(importItem);
        popup.add(exportItem);
        popup.add(exportAllItem);
        popup.addSeparator();
        
        popup.add(refreshItem);
        
        addItem.setToolTipText(bundle.getString("addItem.tooltip"));
        
        
        clearAllVarItem.setToolTipText("Clear all variables");
        openSelectionItem.setToolTipText("View value in Variable Viewer");
        exportItem.setToolTipText(bundle.getString("exportItem.tooltip"));
        importItem.setToolTipText(bundle.getString("importItem.tooltip"));
        exportAllItem.setToolTipText(bundle.getString("exportAllItem.tooltip"));
        loadItem.setToolTipText(bundle.getString("loadItem.tooltip"));
        saveItem.setToolTipText(bundle.getString("saveItem.tooltip"));
        saveAllItem.setToolTipText(bundle.getString("saveAllItem.tooltip"));
        viewItem.setToolTipText(bundle.getString("viewItem.tooltip"));
        plotItem.setToolTipText(bundle.getString("plotItem.tooltip"));
        deleteItem.setToolTipText(bundle.getString("deleteItem.tooltip"));
        renameItem.setToolTipText(bundle.getString("renameItem.tooltip"));
        duplicateItem.setToolTipText(bundle.getString("duplicateItem.tooltip"));
        refreshItem.setToolTipText(bundle.getString("refreshItem.tooltip"));
        remoteItem.setToolTipText("Read contents of a file from internet");
        
        addItem.addActionListener(popupActionListener);
        openSelectionItem.addActionListener(popupActionListener);
        clearAllVarItem.addActionListener(popupActionListener);
        
        exportItem.addActionListener(popupActionListener);
        importItem.addActionListener(popupActionListener);
        exportAllItem.addActionListener(popupActionListener);
        loadItem.addActionListener(popupActionListener);
        saveItem.addActionListener(popupActionListener);
        saveAllItem.addActionListener(popupActionListener);
        viewItem.addActionListener(popupActionListener);
        plotItem.addActionListener(popupActionListener);
        deleteItem.addActionListener(popupActionListener);
        renameItem.addActionListener(popupActionListener);
        duplicateItem.addActionListener(popupActionListener);
        refreshItem.addActionListener(popupActionListener);
        remoteItem.addActionListener(popupActionListener);
        table.addMouseListener(tableMouseListener);
        table.add(popup);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
       
         add(scrollPane,BorderLayout.CENTER);
          
             
          
      
    }

    
    public  void open(){
        JFileChooser fc = new JFileChooser();
       
        
        fc.setAcceptAllFileFilterUsed(false);


       fc.setFileFilter(DomainMathFileFilter.ZIP_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FHDF5_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.HDF5_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FLOAT_BINARY_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.BINARY_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.IMAGES_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.DCM_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.ASCII_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.AUDIO_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FIS_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.MATLAB_FILE_FILTER);
       
       
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file;
        String name;
        String ext;
        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file = fc.getSelectedFile();
                 name =file.getName();
                 if(name.endsWith(".mat")) {
                     load(file,"-mat");
                 }else if(name.endsWith(".hdf5")) {
                     load(file,"-hdf5");
                 }else if(name.endsWith(".fhdf5")) {
                     load(file,"-float-hdf5");
                 }else if(name.endsWith(".txt")) {
                     load(file,"-ascii");
                 }else if(name.endsWith(".bin")) {
                     load(file,"-binary");
                 }else if(name.endsWith(".fbin")) {
                     load(file,"-float-binary");
                 }else if(name.endsWith(".zip")) {
                     load(file,"-zip");
                 }else if(name.endsWith(".fis")) {
                   MainFrame.octavePanel.eval("pkg load fuzzy-logic-toolkit");
                   MainFrame.octavePanel.eval("readfis('"+file.getAbsolutePath()+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 }
                
                 else if(name.endsWith(".bmp") ||
                         name.endsWith(".gif") ||
                         name.endsWith(".jpg") ||
                         name.endsWith(".jpeg") ||
                         name.endsWith(".pbm") ||
                         name.endsWith(".pcx") ||
                         name.endsWith(".pgm") ||
                         name.endsWith(".png") ||
                         name.endsWith(".pnm") ||
                         name.endsWith(".ppm") ||
                         name.endsWith(".ras") ||
                         name.endsWith(".tif") ||
                         name.endsWith(".tiff") ||
                         name.endsWith(".xwd") ) {
                     loadImage(file.getAbsolutePath());
                      MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }
                // "lin", "raw", "au", "mu", "snd", "wav","riff"
                 
                 else if(name.endsWith(".lin")) {
                     loadAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),"lin");
                 }else if(name.endsWith(".raw")) {
                      loadAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),"raw");
                 }else if(name.endsWith(".au")) {
                      loadAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),"au");
                 }else if(name.endsWith(".mu")) {
                      loadAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),"mu");
                 }
                 else if(name.endsWith(".snd")) {
                      loadAudio1(file.getAbsolutePath(),"snd");
                 }
                  else if(name.endsWith(".dcm")) {
                      loadDCM(file.getAbsolutePath());
                 }
                 else if(name.endsWith(".wav") ||
                         name.endsWith(".riff"))
                         {
                     loadAudio2(file.getAbsolutePath());
                 }

            } 
       }
    
    private void loadDCM(String path) {
        MainFrame.octavePanel.evaluate("pkg load dicom");
        MainFrame.octavePanel.evalWithOutput("im=dicomread('"+path+"');");
        MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
        reload();
    }
    private void loadAudio1(String filename,String ext) {
        String txt ="loadaudio('"+filename+"','"+ext+"',8)";
        MainFrame.octavePanel.eval(txt);
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
         reload();
    }
    
     private void loadAudio2(String filename) {
        String txt ="waveread('"+filename+"');";
        AudioVarDialog audioVarDialog  = new AudioVarDialog(frame,true,txt);
        audioVarDialog.setLocationRelativeTo(null);
        audioVarDialog.setVisible(true);
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
         reload();
    }
    private void loadImage(String filename) {
        String txt ="imread('"+filename+"');";
        ImageVarDialog ImageVarDialog  = new ImageVarDialog(frame,true,txt);
        ImageVarDialog.setLocationRelativeTo(null);
        ImageVarDialog.setVisible(true);
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
         reload();
    }
    
    private void saveImage(String filename,String var) {
        String txt ="imwrite("+var+",'"+filename+"');";
       MainFrame.octavePanel.eval(txt);
    }
    
    private void load(File file,String opt) {
         MainFrame.octavePanel.eval("load('"+opt+"','"+file.getAbsolutePath()+"');");
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
         JOptionPane.showMessageDialog(table,file.getName()+" has been loaded.","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
         reload();
         System.out.println(file.getAbsolutePath());
    }
    
     private void saveAll(File file,String opt) {
         MainFrame.octavePanel.eval("save('"+opt+"','"+file.getAbsolutePath()+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 System.out.println(file.getAbsolutePath());
    }
   
     private void save(File file,String opt,String var) {
         MainFrame.octavePanel.eval("save('"+opt+"','"+file.getAbsolutePath()+"','"+var+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 System.out.println(file.getAbsolutePath());
    }
      private void makeListItems() {
        String line;
        List data =Collections.synchronizedList(new ArrayList());
        try {
            FileInputStream fin = new FileInputStream("fuctions.ini");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            try {
                    
                    int i=0;
                    while((line=br.readLine()) != null) {
                        StringTokenizer s2 = new StringTokenizer(line,"\n");
                        
                        while(s2.hasMoreTokens()) {
                        
                           
                            data.add(s2.nextToken());
                         
                        }
                   
                }
                      
                br.close();
               
                
            } catch (IOException ex) {
            }
              
            
            
        } catch (FileNotFoundException ex) {
        }
        
        
    }
    public  void save(String var){
        JFileChooser fc = new JFileChooser();
       
        
        fc.setAcceptAllFileFilterUsed(false);


       fc.setFileFilter(DomainMathFileFilter.ZIP_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FHDF5_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.HDF5_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FLOAT_BINARY_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.IMAGES_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.BINARY_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.ASCII_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.DCM_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FIS_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.AUDIO_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.MATLAB_FILE_FILTER);
       
       
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file;
        String name;
        int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file = fc.getSelectedFile();
                 name =file.getName();
                 if(name.endsWith(".mat")) {
                     save(file,"-V7",var);
                 }else if(name.endsWith(".hdf5")) {
                     save(file,"-hdf5",var);
                 }else if(name.endsWith(".fhdf5")) {
                     save(file,"-float-hdf5",var);
                 }else if(name.endsWith(".txt")) {
                     save(file,"-ascii",var);
                 }else if(name.endsWith(".bin")) {
                     save(file,"-binary",var);
                 }else if(name.endsWith(".fbin")) {
                     save(file,"-float-binary",var);
                 }else if(name.endsWith(".zip")) {
                     save(file,"-zip",var);
                 }else if(name.endsWith(".fis")) {
                     MainFrame.octavePanel.eval("pkg load fuzzy-logic-toolkit");
                   MainFrame.octavePanel.eval("writefis('"+var+"','"+file.getAbsolutePath()+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                    System.out.println(file.getAbsolutePath());
                 }
                 
                 else if(name.endsWith(".bmp") ||
                         name.endsWith(".gif") ||
                         name.endsWith(".jpg") ||
                         name.endsWith(".jpeg") ||
                         name.endsWith(".pbm") ||
                         name.endsWith(".pcx") ||
                         name.endsWith(".pgm") ||
                         name.endsWith(".png") ||
                         name.endsWith(".pnm") ||
                         name.endsWith(".ppm") ||
                         name.endsWith(".ras") ||
                         name.endsWith(".tif") ||
                         name.endsWith(".tiff") ||
                         name.endsWith(".xwd") ) {
                     saveImage(file.getAbsolutePath(),var);
                 }
                  else if(name.endsWith(".lin")) {
                     writeAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),var,"lin");
                      MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }else if(name.endsWith(".raw")) {
                       writeAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),var,"raw");
                        MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }else if(name.endsWith(".au")) {
                       writeAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),var,"au");
                        MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }else if(name.endsWith(".mu")) {
                       writeAudio1(file.getParent()+File.separator+name.substring(0, name.indexOf(".")),var,"mu");
                        MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }
                 else if(name.endsWith(".snd")) {
                       writeAudio1(file.getParent()+File.separator+name,var,"snd");
                        MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }
                  else if(name.endsWith(".dcm")) {
                       writeDCM(file.getAbsolutePath(),var);
                        MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }
                 else if(name.endsWith(".wav") ||
                         name.endsWith(".riff"))
                         {
                     writeAudio2(file.getAbsolutePath(),var);
                      MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                 }
            }
       }
    
    private void writeDCM(String path, String var) {
        MainFrame.octavePanel.evaluate("pkg load dicom");
        MainFrame.octavePanel.evalWithOutput("dicomwrite("+var+",'"+path+"');");
        MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
        reload();
    }
    private void writeAudio1(String name,String var,String ext) {
        MainFrame.octavePanel.eval("saveaudio('"+name+"',"+var+",'"+ext+"');");
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
         reload();
    }
    private void writeAudio2(String name,String var) {
        MainFrame.octavePanel.eval("wavwrite("+var+",'"+name+"');");
         MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
         reload();
    }
    public  void saveAll(){
        JFileChooser fc = new JFileChooser();
       
        
        fc.setAcceptAllFileFilterUsed(false);


       fc.setFileFilter(DomainMathFileFilter.ZIP_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FHDF5_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.HDF5_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.FLOAT_BINARY_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.BINARY_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.ASCII_FILE_FILTER);
       
       fc.setFileFilter(DomainMathFileFilter.MATLAB_FILE_FILTER);
        //fc.setFileFilter(this.matlabFileFilter);
       // fc.setFileFilter(filter2);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file;
        String name;
        int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file = fc.getSelectedFile();
                 name =file.getName();
                 if(name.endsWith(".mat")) {
                     saveAll(file,"-mat");
                 }else if(name.endsWith(".hdf5")) {
                     saveAll(file,"-hdf5");
                 }else if(name.endsWith(".fhdf5")) {
                     saveAll(file,"-float-hdf5");
                 }else if(name.endsWith(".txt")) {
                     saveAll(file,"-ascii");
                 }else if(name.endsWith(".bin")) {
                     saveAll(file,"-binary");
                 }else if(name.endsWith(".fbin")) {
                     saveAll(file,"-float-binary");
                 }else if(name.endsWith(".zip")) {
                     saveAll(file,"-zip");
                 }

            } 
       }
    
    public  void export(String var){
        JFileChooser fc = new JFileChooser();
       
        
        fc.setAcceptAllFileFilterUsed(false);

       fc.setFileFilter(DomainMathFileFilter.XML_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.ODS_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.EXCEL_WORKBOOK_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.CSV_FILE_FILTER);
       
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file;
        String name;
        int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file = fc.getSelectedFile();
                 
                 name =file.getName();
                 if(name.endsWith(".xml")) {
                     exportXML(file,var);
                 }else if(name.endsWith(".ods")) {
                     exportODS(file,var);
                 }else if(name.endsWith(".xlsx")) {
                     exportXLSX(file,var);
                 }else if(name.endsWith(".csv")) {
                     exportCSV(file,var);
                 }

            } 
       }
    
    private void exportXML(File file,String opt) {
          MainFrame.octavePanel.eval("xmlwrite('"+file.getAbsolutePath()+"',"+opt+",'"+opt+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 System.out.println(file.getAbsolutePath());
                      MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
    }
    
    private void exportODS(File file,String opt) {
          MainFrame.octavePanel.eval("odswrite('"+file.getAbsolutePath()+"',"+opt+",'"+opt+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 System.out.println(file.getAbsolutePath());
                      MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
    }
    
    private void exportXLSX(File file,String opt) {
          MainFrame.octavePanel.eval("xlswrite('"+file.getAbsolutePath()+"',"+opt+",'"+opt+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 System.out.println(file.getAbsolutePath());
                     MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
    }
    private void exportCSV(File file,String opt) {
          MainFrame.octavePanel.eval("csvwrite('"+file.getAbsolutePath()+"',"+opt+"');");
                   MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   reload();
                 System.out.println(file.getAbsolutePath());
                  MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
    }
    public  void exportAll(){
        JFileChooser fc = new JFileChooser();
       
        
        fc.setAcceptAllFileFilterUsed(false);

       
       fc.setFileFilter(DomainMathFileFilter.ODS_FILE_FILTER);
       fc.setFileFilter(DomainMathFileFilter.EXCEL_WORKBOOK_FILE_FILTER);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        
        File file;
        String fname;
        int returnVal = fc.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             System.err.println(fc.getFileFilter().getDescription());
                 file = fc.getSelectedFile();
                 fname=file.getName();
                 System.out.println(file.getAbsolutePath());
                 if(fname.endsWith(".xlsx")) {
                  for(int i=0;i<table.getRowCount();i++) {
                      String var =table.getValueAt(i, 0).toString();
                      exportXLSX(file,var);
                        MainFrame.octavePanel.evaluate("obOctDefaultApp=javaObject("+
                          Character.toString('"')+"OctDefaultApp"+Character.toString('"')+
                          ","+Character.toString('"')+file.getAbsolutePath()+Character.toString('"')+");");
                  }
                               
                 }else if(fname.endsWith(".ods")) {
                     for(int i=0;i<table.getRowCount();i++) {
                      String var =table.getValueAt(i, 0).toString();
                      exportODS(file,var);
                  }
                 }
            } 
       }
    
    public void reload() {
        
        (varTask = new VarTask()).execute();
      
       }
    
    private void delete() {
               if(table.getSelectedRow() >= 0)  {
                     try {
                         String variable =table.getValueAt(table.getSelectedRow(), 0).toString();
                         int opt =JOptionPane.showConfirmDialog(table, "Do you want to delete variable - "+variable+ "?", "Information",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(opt == JOptionPane.YES_OPTION)  {
             
                            
                            MainFrame.octavePanel.eval("clear('"+variable+"');");
                            MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                   
                            reload();
                        }
                        } catch (Exception ex) {
                        }
               
                   
                 
            }
       }
    
    public DataBrowserTableModel getModel() {
        return model;
    }

    private void showPopup(MouseEvent e){
      ///  && table.getSelectedRow() > -1
        if( e.isPopupTrigger() ) {
            popup.show(e.getComponent(), e.getX(), e.getY());
            
        }
            
     }

   

    private void view() {
        if(table.getSelectedRow() >= 0)  {
                     try {
                            String variable =table.getValueAt(table.getSelectedRow(), 0).toString();
                           MainFrame.octavePanel.eval(variable);
                          
                        } catch (Exception ex) {
                        }
               
                   
                 }
    }
   
     private void viewIn() {
        if(table.getSelectedRow() >= 0)  {
                     try {
                            String variable =table.getValueAt(table.getSelectedRow(), 0).toString();
                           // MainFrame.octavePanel.evaluate(variable);
//                            MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
//                            MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.parent_root+"DomainMath_OctaveDataView.dat',"+variable+");");

                         
                             MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.log_root+variable+".dat',"+variable+");");
                //DataViewMain dataViewMain = new DataViewMain(MainFrame.log_root+variable+".dat");
                //dataViewMain.show();
                DataViewFrame n = new DataViewFrame(MainFrame.log_root+variable+".dat");
                //                            MainFrame.octavePanel.dataView.reload();
                //                            MainFrame.octavePanel.tab.setSelectedIndex(2);
                // reload();
                           
                            
                        } catch (Exception ex) {
                        }
               
                   
                 }
    }

    

    
    private  class ToolBarActionListener implements ActionListener {

         @Override
       public void actionPerformed(ActionEvent e) {
             JButton source = (JButton)(e.getSource());
            
             if(loadButton.equals(source) ) {
                 
                     open();
                 
                 
             }else if(saveButton.equals(source)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                            save(var);
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
                
             }else if(saveAllButton.equals(source)) {
                 
                           
                             saveAll();
                             reload();
                        
            
                 
             }else if(viewButton.equals(source)) {
                  view();
             }else if(deleteButton.equals(source)) {
               delete();
            }else if(addButton.equals(source)) {
                NewVarDialog newVarDlg = new NewVarDialog(frame,true);
                newVarDlg.setLocationRelativeTo(frame);
                newVarDlg.setVisible(true);
                MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                reload();
            }
             else if(refreshButton.equals(source)) {
                model.refresh();
                table.repaint(); 
            } 
    }
    }
  
    private class PopupActionListener implements ActionListener {
       
        @Override
        public void actionPerformed(ActionEvent e) {
             JMenuItem source = (JMenuItem)(e.getSource());
             
              if(source.equals(loadItem)) {
                 open();
             }
             else if(source.equals(exportItem)) {
                if(table.getSelectedRow() >= 0)  {
                     try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                            export(var);
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
             }
             else if(source.equals(remoteItem)) {
                 String s = JOptionPane.showInputDialog("Enter website address:");
                 if(s != null) {
                          MainFrame.octavePanel.eval("url = urlread("+Character.toString('"')+s+Character.toString('"')+");");
            
                 }
             }
             else if(source.equals(exportItem)) {
                if(table.getSelectedRow() >= 0)  {
                     try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                            export(var);
                               
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
             }
            
             else if(source.equals(exportAllItem)) {
                 exportAll();
             }
             else if(source.equals(openSelectionItem)) {
                 viewIn();
             }
             else if(source.equals(clearAllVarItem)) {
                
                   int opt =JOptionPane.showConfirmDialog(table, "Do you want to delete all variables ?", "Information",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                                        if(opt == JOptionPane.YES_OPTION)  {
                            try {
                                 for(int i=0;i<table.getRowCount();i++) {
                                     String variable =table.getValueAt(i, 0).toString();
                                     
                                    
                                        MainFrame.octavePanel.evaluate("clear('"+variable+"');");
                                        MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);"); 
                                       //reload();
                                   
                                 }
                                JOptionPane.showMessageDialog(table,"All variables have been removed.","DomainMath IDE",JOptionPane.INFORMATION_MESSAGE);
                                MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);"); 
                                reload(); 
                                
                            }catch(Exception ex) {
                                System.err.print(ex);
                            }
                           
                }      
                           
                    
                 
                 
             }
             else if(source.equals(saveAllItem)) {
                
                     saveAll();
                
                 
             }else if(source.equals(viewItem)) {
               view();
             }else if(source.equals(plotItem)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                          //  MainFrame.octavePanel.evaluate("figure; plot("+var+"); hold off;");
                            PlotDialog plot = new PlotDialog(frame,true,var);
                            plot.setLocationRelativeTo(frame);
                            plot.setVisible(true);
                            reload();
                            
                        } catch (Exception ex) {
                        }
               
                   
                 } 
             }else if(source.equals(deleteItem)) { 
                 delete();
              }else if(source.equals(renameItem)) {
                 if(table.getSelectedRow() >= 0)  {
                    try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                            String name =JOptionPane.showInputDialog("Enter new name:");
                            
                            MainFrame.octavePanel.eval(name+"="+var+";");
                             MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                
                            reload();
                        } catch (Exception ex) {
                        }
                 }
             }else if(source.equals(duplicateItem)) {
                 if(table.getSelectedRow() >= 0)  {
                    try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                            String name =JOptionPane.showInputDialog("Enter suffix:");
                            
                            MainFrame.octavePanel.eval(name+var+"="+var+";");
                           // MainFrame.octavePanel.evaluate("genvarname("+Character.toString('"')+var+Character.toString('"')+");");
                             MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                
                            reload();
                        } catch (Exception ex) {
                        }
                 }
             }
              else if(source.equals(addItem)) {
                NewVarDialog newVarDlg = new NewVarDialog(frame,true);
                newVarDlg.setLocationRelativeTo(frame);
                newVarDlg.setVisible(true);
                MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
                reload();
             }else if(source.equals(importItem)) {
                ImportSpreadSheetDataDialog importDlg = new ImportSpreadSheetDataDialog(frame,true);
                importDlg.setLocationRelativeTo(frame);
                importDlg.setVisible(true);
                MainFrame.octavePanel.evaluate("DomainMath_OctaveVariables('"+directory+"',whos);");
               
                reload();
             }
                else if(source.equals(refreshItem)) {
                    model.refresh();
                    model.fireTableStructureChanged();
                    model.fireTableDataChanged();
                    table.repaint(); 
             }
              else if(source.equals(saveItem)) {
                 if(table.getSelectedRow() >= 0)  {
                     try {
                            String var =table.getValueAt(table.getSelectedRow(), 0).toString();
                            save(var);
                            reload();
                        } catch (Exception ex) {
                        }
               
                   
                 }
                
             }
        }
 
    }
  
    private class TableMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2) {
                viewIn();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
            showPopup(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    private class VarTask extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent evt) {
                model.refresh();
                model.fireTableStructureChanged();
                model.fireTableDataChanged();
       
                table.repaint();
            }
        };
       javax.swing.Timer t= new javax.swing.Timer(delay, taskPerformer);
        t.setRepeats(false);   
       t.start();
           
           
            return null;
            
        }

        
        @Override
       protected void done() {
           try {
                 int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent evt) {
               
                model.refresh();
                model.fireTableStructureChanged();
                model.fireTableDataChanged();
       
                table.repaint();
                
            }
        };
         javax.swing.Timer t= new javax.swing.Timer(delay, taskPerformer);
         t.setRepeats(false);
           t.start();
           
              
           } catch (Exception ignore) {
               
           }
       }

      
    }
}
    
   
