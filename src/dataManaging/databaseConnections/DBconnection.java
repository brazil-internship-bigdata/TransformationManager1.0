package dataManaging.databaseConnections;

import dataManaging.Item;
import dataManaging.MyListView;

public class DBconnection implements Item {

	private String connectionName;
	private String hostName;
	private String dataBaseName;
	private String tableSpaceData;
	private String tableSpaceIndices;
	private String userName;
	private String portName;
	private String password;
	
	private MyListView<DBconnection> listView;
	
	public DBconnection(MyListView<DBconnection> listView) {
		this.listView = listView;
		//TODO
	}

	
	
	public DBconnection() {
		// TODO Auto-generated constructor stub
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



	@Override
	public String generateSavingTextLine() {
		
		return connectionName
				+" " + hostName
				+" " + dataBaseName
				+" " + userName
				+" " + password;
	}

}
