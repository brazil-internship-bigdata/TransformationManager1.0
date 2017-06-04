package fileManaging;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
 
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
/*
 * DirectoryChooser.java uses these files:
 *   images/Open16.gif
 */
public class FileChooser extends JPanel
                             implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8418475192604430955L;
	static private final String newline = "\n";
    JButton openButton, okButton;
    JTextArea log;
    JFileChooser fc;
    private File fileSelected;    
    private MyFileListView fileListView;
    
    
    
    public FileChooser(MyFileListView fileListView) {
       	super(new BorderLayout());
 
       	this.fileListView = fileListView; 
        
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,50);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
 
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        
        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open a File...",
                                 new ImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);
        
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButton.setVisible(false);
 /*
        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Save a File...",
                                 createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this);
 */
        //For layout purposes, put the buttons in a separate panel
        JPanel openButtonPanel = new JPanel(); //use FlowLayout
        openButtonPanel.add(openButton);
//        buttonPanel.add(saveButton);
 
        JPanel okButtonPanel = new JPanel();
        okButtonPanel.add(okButton);

        
        
        //Add the buttons and the log to this panel.
        add(openButtonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
        add(okButtonPanel, BorderLayout.PAGE_END);
        
       //fileList = new LinkedList<File>();

       
    }
 
    public synchronized void actionPerformed(ActionEvent e) {

    	if (e.getSource() == okButton) {
        	fileListView.add(fileSelected);
        	SwingUtilities.getWindowAncestor(this).dispose();
        	System.out.println("ok pressed");
        	
        	//repaint
    	}
    	else if (e.getSource() == openButton) {
            //Handle open button action.
            int returnVal = fc.showOpenDialog(FileChooser.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fileSelected = fc.getSelectedFile();
                //fileList.add(fileSelected);
                String path = fileSelected.getAbsolutePath();
                //This is where a real application would open the file.
                log.setText("Opening: " + fileSelected.getName() + newline + "is at: " + path + newline);
                
                okButton.setVisible(true);
            } else {
                System.out.println("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }
  
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(MyFileListView fileList) {
    	//Create and set up the window.
        JFrame frame = new JFrame("Data directory");
 
        //Add content to the window.
        frame.add(new FileChooser(fileList));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    public static void createFileChooser(MyFileListView fileList) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI(fileList); //TODO penser au format... savings.xml ?
            }
        });            	
    }

	public static void main(String[] args) {
//    	FileChooserDemo fc1 = new FileChooserDemo();
    	File f = new File("");

    	//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI(new MyFileListView(new FileList())); //TODO penser au format... savings.xml ?
            }
        });        
    }


}
