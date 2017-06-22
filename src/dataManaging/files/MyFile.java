package dataManaging.files;

import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;

import dataManaging.AbstractItem;
import dataManaging.Item;
import tools.CancelledCommandException;

public class MyFile extends AbstractItem {

	private File file;

	
	public MyFile() {
		init();
	}
	
	@Override
	public void init() { 
		file = null;
	}



	public MyFile(String [] attributes) {
		super(attributes);
		file = new File(attributes[0]);
	}



	@Override
	public String editText() {
		return "Edit file location";
	}

	@Override
	public String name() {
		return file.getName();
	}

	@Override
	public boolean check() {
		return file.exists();
	}



	public int openFileChooser() throws CancelledCommandException{
		JFileChooser fc = new JFileChooser(this.file);
		
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else {
			throw new CancelledCommandException("Command cancelled by user");
		}		

		return returnVal;
	}



	public String getAbsolutePath() {
		// TODO Auto-generated method stub
		return file.getAbsolutePath();
	}



	@Override
	protected String childSavingTextLine() {
		return this.getAbsolutePath();
	}



	@Override
	public void setWithGUI() throws CancelledCommandException {
		openFileChooser();
	}



	@Override
	public boolean isJobRunning() {
		// TODO check if the job is scheduled on the computer
		return jobRunning;
	}




	@Override
	public String[] commandLineArguments() {
		String [] arguments = new String[numberOfParameters()];
		
		
		//LastTime
		arguments[0] = lastTransformationDate();
		
		//file path
		arguments[1] = getAbsolutePath();
		
		return arguments;
	}



	@Override
	public int numberOfCustomFields() {
		return 1;
	}



	@Override
	public int numberOfParameters() {
		return 2;
	}




}
