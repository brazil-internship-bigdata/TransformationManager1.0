package main;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import dataManaging.AbstractDataList;
import dataManaging.MyListView;
import dataManaging.databaseConnections.sqlConnection.MySQLconnection;
import dataManaging.databaseConnections.sqlConnection.MySQLconnectionSupplier;
import dataManaging.databaseConnections.sqlConnection.MySQLconnectionsList;
import dataManaging.files.FileList;
import dataManaging.files.MyFile;
import dataManaging.files.MyFileSupplier;
import httpManaging.ApiConnectionPane;

public class Home extends JFrame implements ContainerListener {

	/**
	 * 
	 */
	private static final long					serialVersionUID	= -4611492777705564096L;

	public static final String					SOFTWARE_NAME		= "Ecureuil";
	public static final String					COMPANY_NAME		= "SQUIRREL";

	private JSplitPane							horizontalSplitPane;

	/* LEFT */
	private JScrollPane							filesScrollPane;							// vertical
																							// scroll
	private MyListView<MyFile>					filesPane;

	/* RIGHT */
	private JScrollPane							dbConnectionsScrollPane;
	private MyListView<MySQLconnection>			mySQLconnectionsPane;

	private AbstractDataList<MyFile>			fileList;									// List
																							// of
																							// the
																							// selected
																							// files
																							// to
																							// transform
																							// and
																							// send.
																							// This
																							// list
																							// must
																							// be
																							// created
																							// by
																							// reading
																							// the
																							// file
																							// savingsFile.
																							// this
																							// list
																							// must
																							// be
																							// modified
																							// through
																							// add
																							// and
																							// remove
																							// methods.
	private AbstractDataList<MySQLconnection>	mySQLconnectionsList;

	public Home() {
		super(SOFTWARE_NAME);

		new ApiConnectionPane().display();

		this.fileList = new FileList();
		this.mySQLconnectionsList = new MySQLconnectionsList();

		// List of selected directories (left part)
		/*
		 * filesPane = new JPanel(); TODO remove this comment block
		 * filesPane.setLayout(new BoxLayout(filesPane, BoxLayout.Y_AXIS));
		 */
		filesPane = new MyListView<MyFile>(fileList, new MyFileSupplier());
		filesScrollPane = new JScrollPane(filesPane);

		mySQLconnectionsPane = new MyListView<MySQLconnection>(mySQLconnectionsList, new MySQLconnectionSupplier());
		dbConnectionsScrollPane = new JScrollPane(mySQLconnectionsPane);

		/* Creation of the horizontal split */
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filesScrollPane, dbConnectionsScrollPane);
		this.add(horizontalSplitPane);

		// Resizing Listener
		mySQLconnectionsPane.addContainerListener(this);
		filesPane.addContainerListener(this);

		// print
		// this.add(desktop, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 800);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void componentAdded(ContainerEvent e) {
		this.revalidate();
		this.pack();
		horizontalSplitPane.setDividerLocation(-1);
	}

	@Override
	public void componentRemoved(ContainerEvent e) {
		this.revalidate();
		this.pack();
		horizontalSplitPane.setDividerLocation(-1);
	}

	public static void main(String[] args) {
		Home h = new Home();

	}

}
