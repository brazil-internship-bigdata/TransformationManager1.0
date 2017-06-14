package dbConnectionManaging;

import tools.AbstractDataList;

public class DBconnectionsList extends AbstractDataList<DBconnection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7544403469327141020L;

	@Override
	protected DBconnection textLine2Element(String textLine) {
		// TODO BE CAREFULL WITH THE SPACES
		String [] parameters = textLine.split(" ");
		return new DBconnection(parameters);
	}

}
