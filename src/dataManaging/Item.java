package dataManaging;

import java.util.Date;

import tools.CancelledCommandException;

public interface Item {

	/**
	 * number of fields that define the item. this represents the number of variables stored in the saving files for example
	 * @return the number of fields necessary to represent this item
	 */
	public int numberOfFields();
	
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
	
	/**
	 * the date of the last time this item was transformed and sent to the lab. the result is formatted to fit with the transformation expectations
	 * @return the Date corresponding to the last Transformation. The result is null if there was no previous Transformation
	 */
	public String lastTransformationDate();
	

	/**
	 * set the date of the last transformation. This field allows to filter the old data from the recent one
	 * @param date to parse in order to initialize the field
	 */
	public void parseLastTransformationDate(String date);

	
	
	/**
	 * the arguments used to do the transformation in kitchen
	 * @return an array of strings conatining the necessary arguments.
	 */
	public String[] commandLineArguments();
	
}
