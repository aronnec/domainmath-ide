/*
 * Copyright (C) 2012 Vinu K.N
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


package org.domainmath.gui.Util;

import javax.swing.filechooser.FileNameExtensionFilter;


public final class DomainMathFileFilter {
   public static FileNameExtensionFilter      MATLAB_FILE_FILTER            = new FileNameExtensionFilter("Matlab File (*.mat)","mat");
   public static FileNameExtensionFilter      HDF5_FILE_FILTER               = new FileNameExtensionFilter("Hdf5 Format   (*.hdf5)", "hdf5");
   public static FileNameExtensionFilter      FHDF5_FILE_FILTER              = new FileNameExtensionFilter("Hdf5 Single Precision    (*.fhdf5)","fhdf5");
   public static FileNameExtensionFilter      BINARY_FILE_FILTER            = new FileNameExtensionFilter("Binary  (*.bin)","bin");
   public static FileNameExtensionFilter      FLOAT_BINARY_FILE_FILTER      = new FileNameExtensionFilter("Float Binary  (*.fbin)","fbin");
   public static FileNameExtensionFilter      FIS_FILE_FILTER               = new FileNameExtensionFilter("FIS Data   (*.fis)","fis");
   public static FileNameExtensionFilter      ZIP_FILE_FILTER               = new FileNameExtensionFilter("Zip Format (*.zip)","zip");
   public static FileNameExtensionFilter      EXCEL_WORKBOOK_FILE_FILTER    = new FileNameExtensionFilter("Excel Workbook  (*.xlsx)","xlsx");
   public static FileNameExtensionFilter      ODS_FILE_FILTER               = new FileNameExtensionFilter("Open Document Spreadsheet  (*.ods)","ods");
   public static FileNameExtensionFilter      XML_FILE_FILTER               = new FileNameExtensionFilter("XML Data   (*.xml)","xml");
   public static FileNameExtensionFilter      CSV_FILE_FILTER               = new FileNameExtensionFilter("CSV (Comma delimited)  (*.csv)","csv");
   public static FileNameExtensionFilter      ASCII_FILE_FILTER               = new FileNameExtensionFilter("ASCII Data  (*.txt)","txt");
   public static FileNameExtensionFilter      IMAGES_FILE_FILTER              = new FileNameExtensionFilter("Image Data  (*.bmp; *.gif; *.jpg; *.jpeg; *.pbm; *.pcx; *.pgm; *.png; *.pnm; *.ppm; *.ras; *.tif; *.tiff; *.xwd)","bmp", "gif", "jpg", "jpeg", "pbm", "pcx","pgm", "png", "pnm", "ppm", "ras", "tif" ,"tiff","xwd");
   public static FileNameExtensionFilter      AUDIO_FILE_FILTER              = new FileNameExtensionFilter("Audio Data  (*.lin; *.raw; *.au; *.mu; *.snd; *.wav; *.riff)","lin", "raw", "au", "mu", "snd", "wav","riff");
   public static FileNameExtensionFilter      SAVE_PLOT_FILE_FILTER              = new FileNameExtensionFilter("Octave Plot Format (*.ps; *.eps; *.jpg; *.png; *.emf; *.pdf)","ps", "eps", "jpg", "png", "emf", "pdf");
   public static FileNameExtensionFilter      DCM_FILE_FILTER              = new FileNameExtensionFilter(" DICOM (*.dcm)","dcm");
   
}
