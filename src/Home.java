import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class Home extends JFrame
						implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4611492777705564096L;

	
	
	private JSplitPane horizontalSplitPane;

	/*  LEFT */
	private JScrollPane filesScrollPane;//vertical scroll
	private JPanel filesPane;
	
	
	/*  RIGHT */
	//This part creates the "add repository" and "send manually" part of the home frame
	private JPanel actionsPanel; //Panel that contains 2 panels : 1 for send button (top)  and 1 for add repo button (bottom)
	
	private JPanel addFilePanel; //TOP
	private JButton addFileButton;
	
	private JPanel sendButtonPanel; //BOTTOM
	private JButton sendManuallyButton;

	private FileList fileList; //List of the selected directories to transform and send. This list must be created by reading the file savingsFile. this list must be modified through add and remove methods.
//	private File savingsFile;	//File that saves the list of directories to transform and send.

	public static final String SOFTWARE_NAME = "Ecureuil";

	public Home() {
		super(SOFTWARE_NAME);

		this.fileList = new FileList();
		
		
		
		//List of selected directories (left part)
		
		filesScrollPane = new JScrollPane(filesPane);
		filesPane = new JPanel(new BoxLayout(filesPane, BoxLayout.Y_AXIS));
		//TODO put the list of DirectorySelectedPane in the scrollPane
		
		
		/* Add button and send manually button (right part) */

		/*
		 * The actionsPanel (uses BorderLayout) is composed of two panels (using default flowLayout) which contain 1 button each. This creates a vertical flowLayout
		 * TODO improve this layout because full screen is ugly
		 */
		//Creation of both panels and buttons
		addFileButton = new JButton("Add new repository");
		addFilePanel = new JPanel();
		addFilePanel.add(addFileButton);
		
		sendManuallyButton = new JButton("Send Manually the data");
		sendButtonPanel = new JPanel();
		sendButtonPanel.add(sendManuallyButton);
		
		
		//Insertion of the buttons in the action panel (right part of the home frame)
		actionsPanel = new JPanel(new BorderLayout());
		actionsPanel.add(sendButtonPanel, BorderLayout.PAGE_START);
		actionsPanel.add(addFilePanel, BorderLayout.PAGE_END);
			
		
		
		/*  Creation of the horizontal split  */
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				filesScrollPane, actionsPanel);
		this.add(horizontalSplitPane);

		
		//buttons listeners
		addFileButton.addActionListener(this);
		sendManuallyButton.addActionListener(this);
		
		
		//print
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}


	public void createListOfDirectoryPanes() {
		if(filesScrollPane == null) {
			System.err.println("directoriesScrollPane not initialized");
			return;
		}
		for(int i = 0 ; i<fileList.size() ; i++) {
			filesScrollPane.add(new FileSelectedPane(fileList, i));
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addFileButton) {
			FileChooser.createFileChoser(fileList);
		} else if (e.getSource() == sendManuallyButton) {
			//TODO get->transformation->put
			System.out.println("send");
		}
	}


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Home();
	}


}
