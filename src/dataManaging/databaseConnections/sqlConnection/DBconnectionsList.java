package dev_work_in_progress;

import dataManaging.AbstractDataList;

public class DBconnectionsList extends AbstractDataList<DBconnection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7544403469327141020L;

	public DBconnectionsList() { 
		super("DBconnections_Savings.txt");
	}
	
	@Override
	protected DBconnection textLine2Element(String textLine) throws IllegalArgumentException {
		// TODO BE CAREFULL WITH THE SPACES
		String [] parameters = textLine.split(DBconnection.separator);
		return new DBconnection(parameters);
	}

}
