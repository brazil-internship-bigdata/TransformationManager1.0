package dataManaging.databaseConnections;



import dataManaging.AbstractItem;
import tools.CancelledCommandException;

public abstract class AbstractDBconnection extends AbstractItem {

	protected boolean jobRunning;

	//parameters that represent the connection to the DB
	protected String[][] fields; 

	public static final String separator = " ";
	
	
	protected AbstractDBconnection() {
//		fields = new String [numberOfFields()][3];
		init();
	}

	public AbstractDBconnection(DBconnectionPane adbcp) {
		this();
		setFieldsFromGUI(adbcp);
	}

	public AbstractDBconnection(String[] parameters) throws IllegalArgumentException {
		this();
		
		if (parameters.length != numberOfFields()) {
			throw new IllegalArgumentException("the line in the DBconnections savings file couldn't be analyzed correctly. No field should be empty nor contain spaces");
		}
		
		for(int i = 0 ; i<numberOfFields(); i++) {
			fields[i][0] = parameters[i];
		}
	}

	
	protected abstract void init();
	
	@Override
	public String generateSavingTextLine() {

		String res = fields[0][0];

		for(int i = 1 ; i<numberOfFields(); i++) {
			res += fields[i];
		}

		return res;
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
		String[] paneParameters = adbcp.paneParameters();
		
		for(int i = 0; i<numberOfFields() ; i++) {
			fields[i][0] = paneParameters[i];
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
		return numberOfFields()-1;
	}
	
	/**
	 * the connectionNameIndex should always be 0.
	 * @return 0
	 */
	protected int connectionNameIndex() {
		return 0;
	}
	
	
	public String[] commandLineArguments() {
		String [] arguments = new String[numberOfFields()]; //the arguments take the date and every field except the connectionName
		
		//DATE: LAST TIME
		arguments[connectionNameIndex()] = lastTransformationDate();
		
		
		for(int i = 1 ; i<numberOfFields(); i++) {
			arguments[i] = fields[i][0];
		}
		return arguments;
	}
}
