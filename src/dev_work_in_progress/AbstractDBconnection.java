package dev_work_in_progress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import dataManaging.Item;
import dataManaging.databaseConnections.DBconnectionPane;
import tools.CancelledCommandException;

public abstract class AbstractDBconnection implements Item {

	protected boolean jobRunning;


	static final String separator = " ";
	
	public static final int NUMBER_OF_FIELDS = 5;
	
	private String connectionName;
	private String hostName;
	private String dataBaseName;
	private String tableSpaceData;
	private String tableSpaceIndices;
	private String userName;
	private String portName;
	private String password;
	
	
	public AbstractDBconnection() {
	}

	public AbstractDBconnection(String customName, String hostName, String dataBaseName, String userName, char[] password) {
		this.connectionName = customName;
		this.hostName = hostName;
		this.dataBaseName = dataBaseName;
		this.userName = userName;
		this.password = new String(password);		
	}

	public AbstractDBconnection(String[] parameters) throws IllegalArgumentException {
		
		if (parameters.length != NUMBER_OF_FIELDS) {
			throw new IllegalArgumentException("the line in the DBconnections savings file couldn't be analyzed correctly. No field should be empty nor contain spaces");
		}
		
		connectionName = parameters[0];
		hostName = parameters[1];
		dataBaseName = parameters[2];
		userName = parameters[3];
		password = parameters[4];
	}

	
	@Override
	public String generateSavingTextLine() {
		
		return connectionName
				+ separator + hostName
				+ separator + dataBaseName
				+ separator + userName
				+ separator + password;
	}



	@Override
	public void setWithGUI() throws CancelledCommandException {
		DBconnectionPane dbcp = new DBconnectionPane(this); 
		
		int answer = dbcp.showCreationDialog();
		if(answer != 0) { //0 represents the value of JOptionPane.OK_OPTION
			throw new CancelledCommandException("Command cancelled by user");
		}
		
		setFieldsFromGUI(dbcp);
	}

	private void setFieldsFromGUI(DBconnectionPane dbcp) {
		connectionName = dbcp.getConnectionName();
		hostName = dbcp.getHostName();
		dataBaseName = dbcp.getDatabaseName();
		userName = dbcp.getUserName();
		password = dbcp.getPassword();		
	}



	@Override
	public String editText() {
		return "Edit connection parameters";
	}

	@Override
	public String name() {
		return connectionName;
	}

	@Override
	public boolean check() {
		return checkConnection();
	}


	public String getHostName() {
		return hostName;
	}



	public String getDataBaseName() {
		return dataBaseName;
	}



	public String getUserName() {
		return userName;
	}



	public String getPassword() {
		return password;
	}
	


	@Override
	public boolean isJobRunning() {
		// TODO check if the job is scheduled by the computer
		return jobRunning;
	}
	
	/**
	 * Check the validity of the DBconnection
	 * thank you: https://stackoverflow.com/questions/24586043/warning-connectionid1-prelogin-error-host-127-0-0-1-port-3306-unexpected-resp
	 * @return true if the connection is a success. False otherwise
	 */
	protected abstract boolean checkConnection();
}
