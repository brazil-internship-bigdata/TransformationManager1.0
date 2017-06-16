package dataManaging.databaseConnections;

import javax.swing.JOptionPane;

import dataManaging.Factory;
import dataManaging.MyListView;
import tools.CancelledCommandException;

public class DBconnectionFactory implements Factory<DBconnection> {


	@Override
	public DBconnection create() {
		return new DBconnection();
	}

	/**
	 * return the DBconnection corresponding to the users settings.
	 * If the user cancels, it returns null. This should be replaced with a custom exception. 
	 */
/*	@Override
	public DBconnection createWithGUI() throws CancelledCommandException {
		DBconnectionPane dbcp = new DBconnectionPane(); 
		
		int answer = dbcp.showCreationDialog();
		if(answer == JOptionPane.CANCEL_OPTION || answer == JOptionPane.CLOSED_OPTION) {
			throw new CancelledCommandException("Command cancelled by user");
		}
		return dbcp.makeDBconnection();
	}
*/
}
