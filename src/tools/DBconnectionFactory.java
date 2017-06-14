package tools;

import javax.swing.JOptionPane;

import dbConnectionManaging.DBconnection;
import dbConnectionManaging.DBconnectionPane;

public class DBconnectionFactory implements Factory<DBconnection> {

	@Override
	public DBconnection create(MyListView<DBconnection> listView) {
		// TODO Auto-generated method stub
		return new DBconnection(listView);
	}

	@Override
	public DBconnection create() {
		// TODO Auto-generated method stub
		return new DBconnection();
	}

	/**
	 * return the DBconnection corresponding to the users settings.
	 * If the user cancels, it returns null. This should be replaced with a custom exception. 
	 */
	@Override
	public DBconnection createWithGUI() throws CancelledCommandException {
		DBconnectionPane dbcp = new DBconnectionPane(); 
		
		int answer = dbcp.showCreationDialog();
		if(answer == JOptionPane.CANCEL_OPTION || answer == JOptionPane.CLOSED_OPTION) {
			throw new CancelledCommandException("Command cancelled by user");
		}
		return dbcp.makeDBconnection();
	}

}
