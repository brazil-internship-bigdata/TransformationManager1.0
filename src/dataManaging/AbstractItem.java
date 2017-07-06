package dataManaging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tools.CancelledCommandException;

public abstract class AbstractItem implements Item {

	//protected boolean jobRunning;
	protected Date lastTransformation;

	private DateFormat format = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
	protected Date defaultDate = new Date(0L); //defaultDate to replace null. This date corresponds to a transformation that never happened yet.

	private String identifier;
	
	public static final String separator = " ";
	
	
	public AbstractItem() throws CancelledCommandException{
		this.identifier = new IdentifierPane().getIdentifier();
		if(identifier == null)
			throw new CancelledCommandException();
		abstractInit();
	}
	
	public AbstractItem(String [] attributes) throws IllegalArgumentException {//String lastTransformationDate, String jobRunning) {
		abstractInit();
		
		if(attributes.length != numberOfAttributes()) {
			throw new IllegalArgumentException("the line in the savings file couldn't be analyzed correctly. No field should be empty nor contain spaces");			
		}
		
		identifier = attributes[0];
		
		int indexOfDate = attributes.length -1;
//		int indexOfJobRunning = attributes.length -1;
		
		parseLastTransformationDate(attributes[indexOfDate]);
//		this.jobRunning = attributes[indexOfJobRunning].equals("true");	
	}
	

	
	private void abstractInit() {
		
//		this.jobRunning = false;
		lastTransformation = defaultDate;
		init(); //abstract method for now
	}

	
	
	
	@Override
	public boolean isJobRunning() {
		// TODO check if the job is scheduled on the computer
//		return jobRunning;
		return false;
	}



	@Override
	public String lastTransformationDate() {
		return format.format(lastTransformation);
	}



	private void parseLastTransformationDate(String lastTransformationDate) {
		try {
			lastTransformation = format.parse(lastTransformationDate);
		} catch (ParseException e) {
			//lastTransformation = defaultDate; //No need to do that in my opinion
			System.err.println("date of last transformation could not be read. The date remain what it was. (probably defaultDate)");
			//e.printStackTrace();
		}
	}

	
	@Override
	public void setJobRunning(boolean b) {
//		jobRunning = b;
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
		String res = getIdentifier();
		res += separator + childSavingTextLine();
		res += separator + lastTransformationDate();
//		res += separator + jobRunning;
		
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
		
		File savingFile = new File(savingDirectory, getIdentifier());
		
		if( !savingFile.exists() ) {
			
			try {
				savingFile.createNewFile();
			} catch (IOException e) {
				System.err.println("the following item couldn't be saved: " + getIdentifier());
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
		return new File( savingFolder() + identifier );
	}

	@Override
	public File jobFile() {
		return new File( transformationFolder() + "rootJob.kjb" );
	}
	
	
//	@Override
	public String savingFolder() {
		return "savings/" + childFolderPath();
	}
	
	@Override
	public String transformationFolder() {
		return "transformation/jobs/" + childFolderPath() + identifier + "/";
	}

	
	/**
	 * typical hierarchy of results, savings, transformation files etc... for a specific item
	 * @return <item type>/
	 */
	protected abstract String childFolderPath();

	
	@Override
	public void generateFolders() {
		new File( savingFolder() ).mkdirs();
		new File( transformationFolder() ).mkdirs();
	}
	
	

	@Override
	public String getIdentifier() {
		return identifier;
	}
}
