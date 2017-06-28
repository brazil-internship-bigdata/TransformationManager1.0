package dataManaging.databaseConnections.sqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.microsoft.sqlserver.jdbc.SQLServerDriver

import dataManaging.databaseConnections.AbstractDBconnection;
import dataManaging.databaseConnections.DBconnectionPane;

public class MySQLconnection extends AbstractDBconnection {

	protected static final String[][] fields_details =
		{
				{ "Connection Name:" , "Put any name here" },
				{ "Host Name:" , "put here the name of the host e.g. 127.0.0.1 or localhost" },
				{ "DataBase Name:" , "Put the name of  database e.g. \"world\" in the case of world.sql" },
				{ "User Name:" , "Put the name you use to connect to the database" },
				{ "Password:" , "Put your password here" }
		};


	public MySQLconnection() {
		super();
	}
	
	public MySQLconnection(DBconnectionPane dbcp) {
		super(dbcp);
	}

	public MySQLconnection(String[] parameters) throws IllegalArgumentException {
		super(parameters);
	}

	
	@Override
	public void init() {
		fields = new String[numberOfCustomFields()][3];
		for(int i = 0 ; i<numberOfCustomFields() ; i++) {
			fields[i][0] = "";
			fields[i][1] = fields_details[i][0];
			fields[i][2] = fields_details[i][1];
		}		
	}
	

	@Override
	public boolean check() {
		return checkConnection();
	}


	
	/**
	 * Check the validity of the DBconnection
	 * thank you: https://stackoverflow.com/questions/24586043/warning-connectionid1-prelogin-error-host-127-0-0-1-port-3306-unexpected-resp
	 * @return true if the connection is a success. false otherwise
	 */
	@Override
	protected boolean checkConnection() {
		
		String hostName = fields[1][0];
		String dataBaseName = fields[2][0];
		String userName = fields[3][0];
		String password = fields[4][0];
		
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



	@Override
	public int numberOfCustomFields() {
		return 5; //MYSQL
	}


	@Override
	public int numberOfArguments() {
		return 5; //MYSQL -> needs lastTime, hostName, DataBaseName, userName, Password
	}

	@Override
	public String childFolderPath() {
		return "MYSQL/";
	}



}
