package dataManaging.databaseConnections;



import dataManaging.AbstractItem;
import tools.CancelledCommandException;

public abstract class AbstractDBconnection extends AbstractItem {

//	protected boolean jobRunning;

	//parameters that represent the connection to the DB TODO chould be a method to force redefinition in childrens. But that's no big deal.
	protected String[][] fields; 


	
	protected AbstractDBconnection() throws CancelledCommandException {
		super();
	}


	public AbstractDBconnection(String[] attributes) throws IllegalArgumentException {
		super(attributes);
		
		//attributes[0] contains the Identifier. that's why we take from attributes[i+1]
		for(int i = 0 ; i<numberOfCustomFields(); i++) {
			fields[i][0] = attributes[i+1];
		}
	}

		


	@Override
	public void setWithGUI() throws CancelledCommandException {
		DBconnectionPane adbcp = new DBconnectionPane(this); 
		
		int answer = adbcp.showCreationDialog();
		if(answer != 0) { //0 represents the value of JOptionPane.OK_OPTION
			throw new CancelledCommandException("Command cancelled by user");
		}
		
		setFieldsFromGUI(adbcp);
	}

	
	private void setFieldsFromGUI(DBconnectionPane adbcp) {
		String[] paneFields = adbcp.paneFields();
		
		for(int i = 0; i<numberOfCustomFields() ; i++) {
			fields[i][0] = paneFields[i];
		}
	}



	@Override
	public String editText() {
		return "Edit connection parameters";
	}

/*	@Override
	public String name() {
		return fields[connectionNameIndex()][0];
	}
*/
	/**
	 * The databaseNameIndex should always be 2
	 * @return 2
	 */
	protected int databaseNameIndex() {
		return 2;
	}

	/**
	 * TODO remove
	 * the connectionNameIndex should always be 0.
	 * @return 0
	 */
	protected int connectionNameIndex() {
		return 0;
	}
	
	@Override
	public boolean check() {
		return checkConnection();
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
	
	/**
	 * The password needs a particular treatment. the password has an index in the fields array.
	 * The password should always be the last element of the array
	 * @return the index of the password in this array
	 */
	protected int passwordIndex() {
		return numberOfCustomFields()-1;
	}
	

	@Override
	public String[] commandLineArguments() {
		String [] arguments = new String[numberOfArguments()]; //the arguments take the date and every field except the connectionName
		
		//DATE: LAST TIME
		arguments[connectionNameIndex()] = lastTransformationDate();
		
		//TODO change this!!
		for(int i = 1 ; i<numberOfArguments(); i++) {
			arguments[i] = fields[i][0];
		}
		return arguments;
	}
	
	
	@Override
	protected String childSavingTextLine() {

		String res = fields[0][0]; //The textLine doesn't start with a space

		for(int i = 1; i<numberOfCustomFields() ; i++) {
			res += separator + fields[i][0];
		}

		return res;
	}
	
	
	@Override
	public int numberOfCustomFields() {
		return fields.length; 
	}

	//TODO this should be useless. Now only use 1 argument : identifier. the others are already stored in files so pentaho can read in it.
	@Override
	public int numberOfArguments() {
		return 5; //MYSQL -> needs lastTime, hostName, DataBaseName, userName, Password
	}


}
