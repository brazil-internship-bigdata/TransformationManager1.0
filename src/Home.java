import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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
	private JScrollPane directoriesScrollPane;//vertical scroll
	private JPanel directoriesPane;
	
	
	/*  RIGHT */
	//This part creates the "add repository" and "send manually" part of the home frame
	private JPanel actionsPanel; //Panel that contains 2 panels : 1 for send button (top)  and 1 for add repo button (bottom)
	
	private JPanel addRepoPanel; //TOP
	private JButton addRepoButton;
	
	private JPanel sendButtonPanel; //BOTTOM
	private JButton sendManuallyButton;

	private DirectoryList directoryList; //List of the selected directories to transform and send. This list must be created by reading the file savingsFile. this list must be modified through add and remove methods.
	private File savingsFile;	//File that saves the list of directories to transform and send.

	public static final String SOFTWARE_NAME = "Ecureuil";

	public Home(File savingsFile) {
		super(SOFTWARE_NAME);

		this.savingsFile = savingsFile;
		this.directoryList = new DirectoryList(savingsFile);
		
		
		
		//List of selected directories (left part)
		
		directoriesScrollPane = new JScrollPane(directoriesPane);
		//TODO put the list of DirectorySelectedPane in the scrollPane
		
		/* Add button and send manually button (right part) */

		/*
		 * The actionsPanel (uses BorderLayout) is composed of two panels (using default flowLayout) which contain 1 button each. This creates a vertical flowLayout
		 * TODO improve this layout because full screen is ugly
		 */
		//Creation of both panels and buttons
		addRepoButton = new JButton("Add new repository");
		addRepoPanel = new JPanel();
		addRepoPanel.add(addRepoButton);
		
		sendManuallyButton = new JButton("Send Manually the data");
		sendButtonPanel = new JPanel();
		sendButtonPanel.add(sendManuallyButton);
		
		
		//Insertion of the buttons in the action panel (right part of the home frame)
		actionsPanel = new JPanel(new BorderLayout());
		actionsPanel.add(sendButtonPanel, BorderLayout.PAGE_START);
		actionsPanel.add(addRepoPanel, BorderLayout.PAGE_END);
			
		
		
		/*  Creation of the horizontal split  */
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				directoriesScrollPane, actionsPanel);
		this.add(horizontalSplitPane);

		
		//buttons listeners
		addRepoButton.addActionListener(this);
		sendManuallyButton.addActionListener(this);
		
		
		//print
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}


	public void createListOfDirectoryPanes() {
		if(directoriesScrollPane == null) {
			System.err.println("directoriesScrollPane not initialized");
			return;
		}
		for(int i = 0 ; i<directoryList.size() ; i++) {
			directoriesScrollPane.add(new DirectorySelectedPane(directoryList, i));
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addRepoButton) {
			DirectoryChooser.createDirectoryChoser(directoryList);
		} else if (e.getSource() == sendManuallyButton) {
			//TODO get->transformation->put
			System.out.println("send");
		}
	}


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Home(new File(""));
	}


}
