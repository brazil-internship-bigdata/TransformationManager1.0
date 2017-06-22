package dataManaging.databaseConnections.sqlConnection;

import dataManaging.AbstractDataList;

public class MySQLconnectionsList extends AbstractDataList<MySQLconnection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7544403469327141020L;

	public MySQLconnectionsList() { 
		super("DBconnections_Savings.txt");
	}
	
	@Override
	protected MySQLconnection textLine2Element(String textLine) throws IllegalArgumentException {
		// TODO BE CAREFULL WITH THE SPACES
		String [] parameters = textLine.split(MySQLconnection.separator);
		return new MySQLconnection(parameters);
	}

}
