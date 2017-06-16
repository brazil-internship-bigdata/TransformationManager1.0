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
}
