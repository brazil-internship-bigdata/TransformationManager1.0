package main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import dbConnectionManaging.DBconnection;
import dbConnectionManaging.DBconnectionsList;
import fileManaging.FileList;
import fileManaging.MyFile;
import tools.DBconnectionFactory;
import tools.MyFileFactory;
import tools.MyListView;


public class Home extends JFrame
						implements ContainerListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4611492777705564096L;

	
	public static final String SOFTWARE_NAME = "Ecureuil";

	
	private JSplitPane horizontalSplitPane;

	/*  LEFT */
	private JScrollPane filesScrollPane;//vertical scroll
	private MyListView<MyFile> filesPane;
	
	
	/*  RIGHT */
	//This part creates the "add repository" and "send manually" part of the home frame
	private JScrollPane dbConnectionsScrollPane;
	private MyListView<DBconnection> dbConnectionsPane;
	
	

	private FileList fileList; //List of the selected files to transform and send. This list must be created by reading the file savingsFile. this list must be modified through add and remove methods.
	private DBconnectionsList dbConnectionsList;

	
	public Home() {
		super(SOFTWARE_NAME);

		this.fileList = new FileList();
		this.dbConnectionsList = new DBconnectionsList();
		
		//List of selected directories (left part)
		/*filesPane = new JPanel(); TODO remove this comment block	
		filesPane.setLayout(new BoxLayout(filesPane, BoxLayout.Y_AXIS));
		*/
		filesPane = new MyListView<MyFile>(fileList, new MyFileFactory());
		filesScrollPane = new JScrollPane(filesPane);
		
		dbConnectionsPane = new MyListView<>(dbConnectionsList, new DBconnectionFactory());
		dbConnectionsScrollPane = new JScrollPane(dbConnectionsPane);
		
		/*  Creation of the horizontal split  */
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				filesScrollPane, dbConnectionsScrollPane);
		this.add(horizontalSplitPane);

		
		
		//Resizing Listener
		filesPane.addContainerListener(this);
		
	
		//print
//		this.add(desktop, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(800, 800);
		this.setVisible(true);
	}

/*
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
*/


	
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



	@Override
	public void componentAdded(ContainerEvent e) {
		horizontalSplitPane.setDividerLocation(-1);
	}


	@Override
	public void componentRemoved(ContainerEvent e) {
		horizontalSplitPane.setDividerLocation(-1);		
	}

}
