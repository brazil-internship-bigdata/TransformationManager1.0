package dataManaging.files;

import java.io.File;
import java.util.Date;

import javax.swing.JFileChooser;

import dataManaging.AbstractItem;
import dataManaging.Item;
import tools.CancelledCommandException;

public class MyFile extends AbstractItem {

	private File file;
	private boolean jobRunning;

	
	public MyFile() {
	}
	
	

	public MyFile(String pathName) {
		file = new File(pathName);
	}



	@Override
	public String editText() {
		// TODO Auto-generated method stub
		return "Edit file location";
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
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
	public String generateSavingTextLine() {
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
		String [] arguments = new String[numberOfFields()];
		
		
		//LastTime
		arguments[0] = lastTransformationDate();
		
		//file path
		arguments[1] = getAbsolutePath();
		
		return arguments;
	}



	@Override
	public int numberOfFields() {
		// TODO Auto-generated method stub
		return 2;
	}


}
