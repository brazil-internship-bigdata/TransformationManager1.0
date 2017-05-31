package fileManaging;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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

//	private JDesktopPane desktop;
	
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
		
//		desktop = new JDesktopPane();
		
		//List of selected directories (left part)
		filesPane = new JPanel();	
		filesPane.setLayout(new BoxLayout(filesPane, BoxLayout.Y_AXIS));
		filesScrollPane = new JScrollPane(filesPane);
		createListOfFilePanes();
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
//		this.add(desktop, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(800, 800);
		this.setVisible(true);
	}


	public void createListOfFilePanes() {
		if(filesScrollPane == null || filesPane == null) {
			System.err.println("directoriesScrollPane not initialized");
			return;
		}
		for(int i = 0 ; i<fileList.size() ; i++) {
			FileSelectedPane fsPane = new FileSelectedPane(fileList, i);
			fsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
			filesPane.add(fsPane);
			System.out.println("filesPane n°"+(i+1));
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addFileButton) {
			FileChooser.createFileChooser(fileList);
			
			
/*			//Creation of internal frame, and insertion into desktop pane
			JInternalFrame internalFileChooser = new JInternalFrame("Data selector", true, true, true, true);
			desktop.add(internalFileChooser);
			internalFileChooser.setBounds(25, 25, 200, 100);

			
			//Creation of FileChooser and insertion into internal frame
			FileChooser fc = new FileChooser(fileList);
			internalFileChooser.add(fc);
			fc.setVisible(true);
*/			
		} else if (e.getSource() == sendManuallyButton) {
			//TODO get->transformation->put
			System.out.println("send");
		}
	}


	
	public static void main(String[] args) {
		Home h = new Home();
		
/*		while(true) {
			try {
				Thread.sleep(5000);
				System.out.println(h.toString());
				h.repaint();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/
	}

	
	@Override
	public String toString() {
		String res = "";
		res += "JScrollPane contains: ";
		Component [] c = this.filesScrollPane.getComponents();
		
		for (int i = 0; i<c.length ; i++)
			res += "\n\t" + c[i].toString();
		
		return res;
	}

}
