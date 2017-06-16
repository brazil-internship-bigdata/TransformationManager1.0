package dataManaging.databaseConnections;

import javax.swing.JOptionPane;

import dataManaging.Item;
import tools.CancelledCommandException;

public class DBconnection implements Item {

	static final String separator = " ";
	
	private String connectionName;
	private String hostName;
	private String dataBaseName;
	private String tableSpaceData;
	private String tableSpaceIndices;
	private String userName;
	private String portName;
	private String password;
	
	
	public DBconnection() {
	}

	public DBconnection(String customName, String hostName, String dataBaseName, String userName, char[] password) {
		this.connectionName = customName;
		this.hostName = hostName;
		this.dataBaseName = dataBaseName;
		this.userName = userName;
		this.password = new String(password);		
	}

	public DBconnection(String[] parameters) {
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
		if(answer == JOptionPane.CANCEL_OPTION || answer == JOptionPane.CLOSED_OPTION) {
			throw new CancelledCommandException("Command cancelled by user");
		}
		
		setFieldsFromGUI(dbcp);
	}

	private void setFieldsFromGUI(DBconnectionPane dbcp) {
		connectionName = dbcp.getConnectionName();
		dataBaseName = dbcp.getHostName();
		hostName = dbcp.getDatabaseName();
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
		// TODO check the connection
		return true;
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

}
