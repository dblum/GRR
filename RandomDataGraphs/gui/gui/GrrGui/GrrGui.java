/****************************************************************************************
 * Copyright (C) 2010,2011 The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering
 * Project: GRR - Generating Random RDF
 * Author:	Daniel Blum, The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering, http://www.cs.huji.ac.il/~danieb12/
 *
 * This file is part of GRR.
 * GRR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Please note that no part of this code is officially supported by HUJI
 * (The Hebrew University of Jerusalem) or any of its developers.
 *
 *  GRR is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GRR (COPYRIGHT-gpl.txt).  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************************/

package GrrGui;

import NaturalLanguageApi.NLParser;
import RdfApi.QueryOptimizationMode;
import RdfApi.RdfBasedRandomGraphAPI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The GRR GUI logic implementation
 */
public class GrrGui {

    /**
     * Class Members
     */
    // Main Panel
    private JPanel _mainPanel;
    private JTextField _log;
    private JScrollPane _scrollPane;
    private JTextPane _outputFileDisplay; 
    private JTabbedPane tabbedPane1;


    // Pane 1 - Input Files - Step 1
    private JButton _loadSamplerFunctionButton;
    private JTextField _samplerFunctionFileName;
    private JButton _loadGeneratorFileButton;
    private JTextField _generatorFileName;
    private JButton _saveFileButton1;
    private JButton _saveFileButton2;
    private JButton _showSamplerFunctionFileButton;
    private JButton _showGeneratorFileButton;
    private JButton _showTypeProperyMapFileButton;

    // Pane 2 - Input Files - Step 2
    private JButton _loadTypePropertyMapButton;
    private JTextField _typePropertyFileName;
    private JButton _saveFileButton3;

    // Pane 3 - Output Files - Step 3
    private JButton _outputPathButton;
    private JTextField _outputDirectoryName;
    private JTextField _rdfOutputFileName;
    private JTextField _rdfGeneratorFileName;
    private JButton _generateOutputButton;
    private JButton _showRDFGeneratorFileButton;
    private JButton _showTheGeneratedRDFButton;
    private JCheckBox _storeRDFIntoACheckBox;

    private String _solutionPath = System.getProperty("user.dir");

    private String _rdfGeneratorFile;
    private String _rdfOutputFile;


    /**
     * Consts
     */
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constructor
     */
    public GrrGui() {

        // ######################### Debug #########################

        _samplerFunctionFileName.setText(System.getProperty("user.dir") + "\\src\\Examples\\UniBenchmark\\ViaNl\\InputFiles\\SamplerFunctionInput.txt");
        _generatorFileName.setText(System.getProperty("user.dir") + "\\src\\Examples\\UniBenchmark\\ViaNl\\InputFiles\\Generator.txt");
        _typePropertyFileName.setText(System.getProperty("user.dir") + "\\src\\Examples\\UniBenchmark\\ViaNl\\InputFiles\\TypePropertyMappingsInput.txt");
        _outputDirectoryName.setText(System.getProperty("user.dir") + "\\src\\Examples\\UniBenchmark\\ViaNl\\OutputFiles\\");
        _rdfOutputFileName.setText("test");
        _rdfGeneratorFileName.setText("test");

        //###########################################################

        _storeRDFIntoACheckBox.setSelected(true);

        // Action for loading the Sampler Function
        _loadSamplerFunctionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                // Open dialog
                int rVal = c.showOpenDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    _samplerFunctionFileName.setText(c.getSelectedFile().getAbsolutePath());
                    //dir.setText(c.getCurrentDirectory().toString());
                    _log.setForeground(Color.GREEN);
                    _log.setText("Sampler-Function was loaded successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }

            }
        });

        // Action for saving the Sampler Function
        _saveFileButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                // Save dialog
                int rVal = c.showSaveDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String newFileName = c.getSelectedFile().getAbsolutePath();
                    _samplerFunctionFileName.setText(newFileName);
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(newFileName));
                        bw.write(_outputFileDisplay.getText());
                        bw.close();
                    } catch (IOException e1) {
                        _log.setForeground(Color.RED);
                        _log.setText("Failed to save the file at the location: " + newFileName);
                        e1.printStackTrace();  // TODO handle exception
                    }
                    _log.setForeground(Color.GREEN);
                    _log.setText("Sampler-Function was saved successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }

            }
        });


        // Action for displaying the loaded file
        _showSamplerFunctionFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String samplerFunctionFile = _samplerFunctionFileName.getText();
                if (samplerFunctionFile == null) {
                    _log.setForeground(Color.RED);
                    _log.setText("Sampler-Function file wasn't loaded!");
                } else {
                    _outputFileDisplay.setText("");
                    StyledDocument d = _outputFileDisplay.getStyledDocument();
                    BufferedReader in;
                    try {
                        in = new BufferedReader(new FileReader(samplerFunctionFile));
                        String str;
                        try {
                            while ((str = in.readLine()) != null) {
                                try {
                                    d.insertString(d.getLength(), str + "\n", null);
                                } catch (BadLocationException e1) {
                                    _log.setForeground(Color.RED);
                                    _log.setText("Failed to write into the Text-Pane the following line: " + str);
                                    e1.printStackTrace();  // TODO handle exception
                                }

                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();  // TODO handle exception
                            _log.setForeground(Color.RED);
                            _log.setText("Failed to read from the given file: " + samplerFunctionFile);
                        }
                    } catch (FileNotFoundException e1) {
                        _log.setForeground(Color.RED);
                        _log.setText("Failed to locate the given file: " + samplerFunctionFile);
                        e1.printStackTrace();  // TODO handle exception
                    }
                    _log.setForeground(Color.GREEN);
                    _log.setText("Sampler-Function was loaded to screen successfully");
                }

            }
        });

        // Action for loading the Generator File
        _loadGeneratorFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    _generatorFileName.setText(c.getSelectedFile().getAbsolutePath());
                    //dir.setText(c.getCurrentDirectory().toString());
                    _log.setForeground(Color.GREEN);
                    _log.setText("Generator File was loaded successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }
            }
        });

        // Action for saving the Generator File
        _saveFileButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String newFileName = c.getSelectedFile().getAbsolutePath();
                    _generatorFileName.setText(newFileName);
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(newFileName));
                        bw.write(_outputFileDisplay.getText());
                        bw.close();
                    } catch (IOException e1) {
                        _log.setForeground(Color.RED);
                        _log.setText("Failed to save the file at the location: " + newFileName);
                        e1.printStackTrace();  // TODO handle exception
                    }
                    _log.setForeground(Color.GREEN);
                    _log.setText("Generator File was loaded successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }

            }
        });


        // Action for displaying the loaded file
        _showGeneratorFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String generatorFile = _generatorFileName.getText();
                if (generatorFile == null) {
                    _log.setForeground(Color.RED);
                    _log.setText("Generator file wasn't loaded!");
                } else {
                    _outputFileDisplay.setText("");
                    StyledDocument d = _outputFileDisplay.getStyledDocument();
                    BufferedReader in;
                    try {
                        in = new BufferedReader(new FileReader(generatorFile));
                        String str;
                        try {
                            while ((str = in.readLine()) != null) {
                                try {
                                    d.insertString(d.getLength(), str + "\n", null);
                                } catch (BadLocationException e1) {
                                    _log.setForeground(Color.RED);
                                    _log.setText("Failed to write into the Text-Pane the following line: " + str);
                                    e1.printStackTrace();  // TODO handle exception
                                }

                            }
                        } catch (IOException e1) {
                            _log.setForeground(Color.RED);
                            _log.setText("Failed to read from the given file: " + generatorFile);
                            e1.printStackTrace();  // TODO handle exception
                        }
                    } catch (FileNotFoundException e1) {
                        _log.setForeground(Color.RED);
                        _log.setText("Failed to locate the given file: " + generatorFile);
                        e1.printStackTrace();  // TODO handle exception
                    }
                    _log.setForeground(Color.GREEN);
                    _log.setText("Generator File was loaded to screen successfully");
                }

            }
        });

        // Action for loading the Type-Propery Mapping
        _loadTypePropertyMapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    _typePropertyFileName.setText(c.getSelectedFile().getAbsolutePath());
                    //dir.setText(c.getCurrentDirectory().toString());
                    _log.setForeground(Color.GREEN);
                    _log.setText("Type-Property Map was loaded successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }

            }
        });

        // Action for saving the Type-Property Map
        _saveFileButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String newFileName = c.getSelectedFile().getAbsolutePath();
                    _typePropertyFileName.setText(newFileName);
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(newFileName));
                        bw.write(_outputFileDisplay.getText());
                        bw.close();
                    } catch (IOException e1) {
                        _log.setForeground(Color.RED);
                        _log.setText("Failed to save the file at the location: " + newFileName);
                        e1.printStackTrace();  // TODO handle exception
                    }
                    _log.setForeground(Color.GREEN);
                    _log.setText("Type-Property Map was saved successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }

            }
        });


        // Action for displaying the loaded file
        _showTypeProperyMapFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String typePropertyFileName = _typePropertyFileName.getText();
                if (typePropertyFileName == null) {
                    _log.setForeground(Color.RED);
                    _log.setText("Type-Property Map file wasn't loaded!");
                } else {
                    _outputFileDisplay.setText("");
                    StyledDocument d = _outputFileDisplay.getStyledDocument();
                    BufferedReader in;
                    try {
                        in = new BufferedReader(new FileReader(typePropertyFileName));
                        String str;
                        try {
                            while ((str = in.readLine()) != null) {
                                try {
                                    d.insertString(d.getLength(), str + "\n", null);
                                } catch (BadLocationException e1) {
                                    _log.setForeground(Color.RED);
                                    _log.setText("Failed to write into the Text-Pane the following line: " + str);
                                    e1.printStackTrace();  // TODO handle exception
                                }

                            }
                        } catch (IOException e1) {
                            _log.setForeground(Color.RED);
                            _log.setText("Failed to read from the given file: " + typePropertyFileName);
                            e1.printStackTrace();  // TODO handle exception
                        }
                    } catch (FileNotFoundException e1) {
                        _log.setForeground(Color.RED);
                        _log.setText("Failed to locate the given file: " + typePropertyFileName);
                        e1.printStackTrace();  // TODO handle exception
                    }
                    _log.setForeground(Color.GREEN);
                    _log.setText("Type-Property Map was loaded to screen successfully");
                }
            }
        });


        // Action for loading the Output folder
        _outputPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser(_solutionPath);
                c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(_mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    _outputDirectoryName.setText(c.getSelectedFile().getAbsolutePath() + "\\");
                    //dir.setText(c.getCurrentDirectory().toString());
                    _log.setForeground(Color.GREEN);
                    _log.setText("Output path was loaded successfully");
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    _log.setForeground(Color.RED);
                    _log.setText("You pressed cancel");
                }
            }
        });

        // Action for loading running the program - Generating the output
        _generateOutputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Method for verifying that all mandatory parts are filled!
                if (!verifyGuiParameters())
                    return;
                _log.setForeground(Color.GREEN);
                _log.setText("All input/output parameters are valid - Creating output files (Please wait - this might take a while)");

                QueryOptimizationMode mode = QueryOptimizationMode.SMART_CACHE;
                //
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

                //--------------------------------------------------------------------------------------------------
                // Section I : Parsing the input files
                //--------------------------------------------------------------------------------------------------
                System.out.println("NL File parsing start at: " + sdf.format(cal.getTime()));
                String nlInputFile = _generatorFileName.getText();
                int fileNamePos = nlInputFile.lastIndexOf("\\");
                String inputPath = nlInputFile.substring(0, fileNamePos + 1);
                NLParser nlParser = NLParser.create(inputPath);

                try {
                    nlParser.parseNLGeneratorFiles(_samplerFunctionFileName.getText(), _typePropertyFileName.getText(), nlInputFile);
                } catch (IOException e1) {
                    e1.printStackTrace();  // TODO handle exception
                }
                System.out.println("NL File parsing ended at: " + sdf.format(cal.getTime()));

                //--------------------------------------------------------------------------------------------------
                // Section II : Generating the RDF based generator file
                //--------------------------------------------------------------------------------------------------
                System.out.println("Generating RDF generator file start at: " + sdf.format(cal.getTime()));
                String outputPath = _outputDirectoryName.getText();
                // If given use the given filename, otherwise generate one from the output file name
                _rdfGeneratorFile = (_rdfGeneratorFileName.getText().equals("") || _rdfGeneratorFileName.getText().equals(_rdfOutputFileName.getText())) ? outputPath + _rdfOutputFileName.getText() + "-RdfGen.rdf" : outputPath + _rdfGeneratorFileName.getText() + ".rdf";
                try {
                    nlParser.generateRdfGeneratorFile(_rdfGeneratorFile);
                } catch (IOException e1) {
                    e1.printStackTrace();  // TODO handle exception
                }
                System.out.println("Generating RDF generator file ended at: " + sdf.format(cal.getTime()));

                //--------------------------------------------------------------------------------------------------
                // Section III : Running the construction commands and optionally creating an RDF output file
                //--------------------------------------------------------------------------------------------------
                //   Generate the random graph at the given destination
                System.out.println("Generating final RDF file start at: " + sdf.format(cal.getTime()));

                _rdfOutputFile = outputPath + _rdfOutputFileName.getText() + ".rdf";
                RdfBasedRandomGraphAPI rdfGrrApi = new RdfBasedRandomGraphAPI();
                boolean storeToFile = _storeRDFIntoACheckBox.isSelected();
                try {
                    rdfGrrApi.generateGraph(_rdfGeneratorFile, _rdfOutputFile, mode, null, storeToFile);
                } catch (Exception e1) {
                    e1.printStackTrace();  // TODO handle exception
                }
                System.out.println("Generating final RDF ended at: " + sdf.format(cal.getTime()));

                _log.setForeground(Color.GREEN);
                _log.setText("Output files were generated - You can view the files by clicking on the \"Show\" buttons");

            }
        });


        // Action for showing the RDF Generator File
        _showRDFGeneratorFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_rdfGeneratorFile == null) {
                    _log.setForeground(Color.RED);
                    _log.setText("RDF Generator file parameters are missing!");
                } else {
                    // Check that the file exists
                    File gFile = new File(_rdfGeneratorFile);
                    if (!gFile.exists()) {
                        _log.setForeground(Color.RED);
                        _log.setText("RDF Generator file can't be found: " + gFile.getAbsolutePath());
                    } else {
                        _outputFileDisplay.setText("");
                        StyledDocument d = _outputFileDisplay.getStyledDocument();
                        BufferedReader in;
                        try {
                            in = new BufferedReader(new FileReader(_rdfGeneratorFile));
                            String str;
                            try {
                                while ((str = in.readLine()) != null) {
                                    try {
                                        d.insertString(d.getLength(), str + "\n", null);
                                    } catch (BadLocationException e1) {
                                        _log.setForeground(Color.RED);
                                        _log.setText("Failed to write into the Text-Pane the following line: " + str);
                                        e1.printStackTrace();  // TODO handle exception
                                    }

                                }
                            } catch (IOException e1) {
                                _log.setForeground(Color.RED);
                                _log.setText("Failed to read from the given file: " + _rdfGeneratorFile);
                                e1.printStackTrace();  // TODO handle exception
                            }
                        } catch (FileNotFoundException e1) {
                            _log.setForeground(Color.RED);
                            _log.setText("Failed to locate the given file: " + _rdfGeneratorFile);
                            e1.printStackTrace();  // TODO handle exception
                        }
                        _log.setForeground(Color.GREEN);
                        _log.setText("RDF Generator File was loaded to the screen successfully");
                    }
                }
            }
        });


        // Action for showing the RDF Output File
        _showTheGeneratedRDFButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_rdfOutputFile == null) {    // check if the full path to output was set
                    _log.setForeground(Color.RED);
                    _log.setText("RDF Output file wasn't generated!!");
                } else if (!_storeRDFIntoACheckBox.isEnabled()) {
                    _log.setForeground(Color.RED);
                    _log.setText("RDF Output file wasn't generated!");
                } else {
                    // Check that the file exists
                    File gFile = new File(_rdfOutputFile);
                    if (!gFile.exists()) {
                        _log.setForeground(Color.RED);
                        _log.setText("Output file can't be found: " + gFile.getAbsolutePath());
                    } else {
                        _outputFileDisplay.setText("");
                        StyledDocument d = _outputFileDisplay.getStyledDocument();
                        BufferedReader in;
                        try {
                            in = new BufferedReader(new FileReader(_rdfOutputFile));
                            String str;
                            try {
                                while ((str = in.readLine()) != null) {
                                    try {
                                        d.insertString(d.getLength(), str + "\n", null);
                                    } catch (BadLocationException e1) {
                                        _log.setForeground(Color.RED);
                                        _log.setText("Failed to write into the Text-Pane the following line: " + str);
                                        e1.printStackTrace();  // TODO handle exception
                                    }

                                }
                            } catch (IOException e1) {
                                _log.setForeground(Color.RED);
                                _log.setText("Failed to read from the given file: " + _rdfOutputFile);
                                e1.printStackTrace();  // TODO handle exception
                            }
                        } catch (FileNotFoundException e1) {
                            _log.setForeground(Color.RED);
                            _log.setText("Failed to locate the given file: " + _rdfOutputFile);
                            e1.printStackTrace();  // TODO handle exception
                        }
                        _log.setForeground(Color.GREEN);
                        _log.setText("The random RDF output file was loaded to the screen successfully");
                    }
                }

            }
        });

        
    }


    /**
     * Private methods for verifying that all mandatory input parameters are set prior execution
     * @return - True iff all of the mandatory input parameters exists, otherwise false
     */
    private boolean verifyGuiParameters() {

        // Check sampler-function file
        if (_samplerFunctionFileName.getText().equals("")) {
            _log.setForeground(Color.RED);
            _log.setText("Sampler-Function file is missing (see Step 1 - Input Files)");
            return false;
        }

        File sfFile = new File(_samplerFunctionFileName.getText());
        if (!sfFile.exists())
        {
            _log.setForeground(Color.RED);
            _log.setText("Sampler-Function file can't be found: " + sfFile.getAbsolutePath());
            return false;
        }

        // Check Generator file
        if (_generatorFileName.getText().equals("")) {
            _log.setForeground(Color.RED);
            _log.setText("Generator file is missing (see Step 1 - Input Files)");
            return false;
        }

        File gFile = new File(_generatorFileName.getText());
        if (!gFile.exists())
        {
            _log.setForeground(Color.RED);
            _log.setText("Generator file can't be found: " + gFile.getAbsolutePath());
            return false;
        }

        // Check output directory
        if (_outputDirectoryName.getText().equals("")) {
            _log.setForeground(Color.RED);
            _log.setText("Output directory is missing (see Step 3 - Output Files)");
            return false;
        }

        File outDir = new File(_outputDirectoryName.getText());
        if (!outDir.exists())
        {
            _log.setForeground(Color.RED);
            _log.setText("Output directory can't be found: " + outDir.getAbsolutePath());
            return false;
        }

        // Check output file
        if (_rdfOutputFileName.getText().equals("")) {
            _log.setForeground(Color.RED);
            _log.setText("Output file is missing (see Step 3 - Output Files)");
            return false;
        }

        return true;
    }


    // ##############################################################################################################
    //  Main
    // ##############################################################################################################

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {

        JFrame frame = new JFrame("GRR - Generating Random RDF");

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

        frame.setContentPane(new GrrGui()._mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
