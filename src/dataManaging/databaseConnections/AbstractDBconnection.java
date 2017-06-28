package dataManaging.databaseConnections;



import dataManaging.AbstractItem;
import tools.CancelledCommandException;

public abstract class AbstractDBconnection extends AbstractItem {

//	protected boolean jobRunning;

	//parameters that represent the connection to the DB
	protected String[][] fields; 


	
	protected AbstractDBconnection() {
		super();
	}

	public AbstractDBconnection(DBconnectionPane adbcp) {
		this();
		setFieldsFromGUI(adbcp);
	}

	public AbstractDBconnection(String[] attributes) throws IllegalArgumentException {
		super(attributes);
		
		for(int i = 0 ; i<numberOfCustomFields(); i++) {
			fields[i][0] = attributes[i];
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

	@Override
	public String name() {
		return fields[connectionNameIndex()][0];
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
	
	/**
	 * the connectionNameIndex should always be 0.
	 * @return 0
	 */
	protected int connectionNameIndex() {
		return 0;
	}
	
	
	public String[] commandLineArguments() {
		String [] arguments = new String[numberOfArguments()]; //the arguments take the date and every field except the connectionName
		
		//DATE: LAST TIME
		arguments[connectionNameIndex()] = lastTransformationDate();
		
		
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

}
