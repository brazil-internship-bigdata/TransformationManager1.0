package dataManaging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.print.Printer;

public abstract class AbstractItem implements Item {

	protected boolean jobRunning;
	protected Date lastTransformation;

	private DateFormat format = new SimpleDateFormat("dd.MM.yyyy-hh:mm:ss");
	
	public static final String separator = " ";
	
	
	public AbstractItem() {
		abstractInit();
	}
	
	public AbstractItem(String [] attributes) throws IllegalArgumentException {//String lastTransformationDate, String jobRunning) {
		this();
		
		if(attributes.length != numberOfAttributes()) {
			throw new IllegalArgumentException("the line in the savings file couldn't be analyzed correctly. No field should be empty nor contain spaces");			
		}
		
		int indexOfDate = attributes.length -2;
		int indexOfJobRunning = attributes.length -1;
		
		parseLastTransformationDate(attributes[indexOfDate]);
		this.jobRunning = attributes[indexOfJobRunning].equals("true");

	
	}
	

	
	private void abstractInit() {
		this.jobRunning = false;
		lastTransformation = null;
		init(); //abstract method for now
	}

	
	
	
	@Override
	public boolean isJobRunning() {
		// TODO check if the job is scheduled on the computer
		return jobRunning;
	}



	@Override
	public String lastTransformationDate() {
		if(lastTransformation == null) {
			return null;
		}
		else
			return format.format(lastTransformation);
	}



	private void parseLastTransformationDate(String lastTransformationDate) {
		try {
			lastTransformation = format.parse(lastTransformationDate);
		} catch (ParseException e) {
			lastTransformation = null;
			System.err.println("date of last transformation could not be read. The date is now null");
			//e.printStackTrace();
		}
	}

	
	@Override
	public void setJobRunning(boolean b) {
		jobRunning = b;
	}
	
	@Override
	public void setLastTransformationDate(Date date) {
		lastTransformation = date;
	}
	
	@Override
	public int numberOfAttributes() {
		//last transformation date and jobRunning boolean
		return 2 + numberOfCustomFields();
	}
	

	@Override
	public String generateSavingTextLine() {
		String res = childSavingTextLine();
		res += separator+lastTransformationDate();
		res += separator+jobRunning;
		
		return res;
	}

	/**
	 * generates the saving text line of the item. This textline must contain the necessary attributes to represent an instance of this item. This doesn't take into account the attributes from superior classes
	 * @return the saving text line corresponding to this particular item.
	 */
	protected abstract String childSavingTextLine();
	

	
	
	
	@Override
	public void save()  {

		//Create the directory if it's necessary
		
		File savingDirectory = new File(savingFolder());
		
		if( !savingDirectory.isDirectory() ) {
			savingDirectory.mkdirs();
		}
		
		
		//Create the file if it's necessary. The file is based on the name of the item. two items in the same directory shouldn't have the same name.
		
		File savingFile = new File(savingDirectory, name());
		
		if( !savingFile.exists() ) {
			
			try {
				savingFile.createNewFile();
			} catch (IOException e) {
				System.err.println("the following item couldn't be saved: " + name());
				e.printStackTrace();
			}
		
		}

		
		//generate the savingTextLine and print it in the savingFile. the previous content is erased when the PrintWriter is created.
		
		String text = generateSavingTextLine();

		try {
			PrintWriter printer = new PrintWriter(savingFile);
			printer.write(text);			
			printer.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			save();
		} 
	}
	
	
	@Override
	public File savingFile() {
		return new File( savingFolder() + name() );
	}
	
}
