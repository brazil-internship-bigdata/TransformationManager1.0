package dataManaging;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import tools.exceptions.CancelledCommandException;

public interface Item {

	/**
	 * number of fields that define the item. this represents the number of information the user must define to create successfully the item
	 * @return the number of fields necessary to create this kind of item
	 */
	public abstract int numberOfCustomFields();
	
	/**
	 * number of the necessary parameters to launch the transformation
	 * @return the number of parameters
	 */
	public abstract int numberOfArguments();
	
	/**
	 * defines the number of attributes that are necessary to save an item. An array = number of non final elements in it
	 * @return number of non final attributes (or elements for arrays)
	 */
	public abstract int numberOfAttributes();
	
	public String editText();
	
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
	public void parseLastTransformationDate(String date);
	 */

	
	/**
	 * execute the code to initialize the item.
	 */
	public abstract void init();
	
	
	/**
	 * the arguments used to do the transformation in kitchen
	 * @return an array of strings containing the necessary arguments.
	 */
	public String[] commandLineArguments();

	/**
	 * set the boolean jobRunning.
	 * @param b new value of jobRunning
	 */
	public abstract void setJobRunning(boolean b);
	
	
	/**
	 * set the date of the last transformation
	 * @param date
	 */
	public void setLastTransformationDate(Date date);

	
	/**
	 * save the item in a file corresponding to its name. If the file already exists, the file is overwritten.
	 * @throws IOException 
	 */
	public void save();
	
	
	/**
	 * path to the directory where the saving files for this specific kind of items are stored
	 * @return path to saving directory
	 */
	public String savingFolder();
	
	
	/**
	 * file that contains the value of this item's attributes.
	 * @return the saving file of this item
	 */
	public File savingFile();
	
	/**
	 * folder that contains the transformation & job files
	 * @return the path to the transformationFolder.
	 */
	public String transformationFolder();
	
	
	/**
	 * file that contains the job to be run to transform the item to csv format.
	 * @return rootJob.kjb of this specific item.
	 */
	public File jobFile();


	/**
	 * generates the necessary folders to store the saving files, transformation files, why not result file also
	 */
	public void generateFolders();
	
	/**
	 * get the identifier related to this item.
	 * @return an identifier.
	 */
	public String getIdentifier();


}
