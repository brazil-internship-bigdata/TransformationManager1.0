package dataManaging.databaseConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.microsoft.sqlserver.jdbc.SQLServerDriver
import javax.swing.JOptionPane;

import dataManaging.Item;
import tools.CancelledCommandException;

public class DBconnection implements Item {

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
	
	
	public DBconnection() {
	}

	public DBconnection(String customName, String hostName, String dataBaseName, String userName, char[] password) {
		this.connectionName = customName;
		this.hostName = hostName;
		this.dataBaseName = dataBaseName;
		this.userName = userName;
		this.password = new String(password);		
	}

	public DBconnection(String[] parameters) throws IllegalArgumentException {
		
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
		if(answer == JOptionPane.CANCEL_OPTION || answer == JOptionPane.CLOSED_OPTION) {
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
	

	/**
	 * Check the validity of the DBconnection
	 * thank you: https://stackoverflow.com/questions/24586043/warning-connectionid1-prelogin-error-host-127-0-0-1-port-3306-unexpected-resp
	 * @return
	 */
	private boolean checkConnection() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://"+hostName+":3306/" + dataBaseName;
			Connection connection = null;
			try {
			    System.out.println("Connecting database...");
			    connection = DriverManager.getConnection(url, userName, password);
			    System.out.println("Database connected!");
			    
			} catch (SQLException e) {
			    return false;

			} finally {
			    System.out.println("Closing the connection.");
			    if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
			}
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			e1.printStackTrace();
			return false;
			//TODO check if this is normal and handle it better. (this is probably not the user's fault so tell him !
		}
		
		return true;
	}
	

}
