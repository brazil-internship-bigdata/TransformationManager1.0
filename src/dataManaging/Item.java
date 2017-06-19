package dataManaging;

import tools.CancelledCommandException;

public interface Item {

	
	public String editText();
	public String name();
	
	/**
	 * verifies if the item is correctly configured (e.g. file location, database connection etc...)
	 * @return true if the item is valid, false otherwise
	 */
	public boolean check();
	
	public String generateSavingTextLine();
	
	public void setWithGUI() throws CancelledCommandException;
	
	/**
	 * verifies if the job is scheduled correctly. for now, a simple boolean should do the trick
	 * @return true if the job is running
	 */
	public boolean isJobRunning();
}
