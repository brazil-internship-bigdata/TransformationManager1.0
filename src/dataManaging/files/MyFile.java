package dataManaging.files;

import java.io.File;

import javax.swing.JFileChooser;

import dataManaging.AbstractItem;
import tools.exceptions.CancelledCommandException;

public class MyFile extends AbstractItem {

	private File file;

	
	public MyFile() throws CancelledCommandException {
		super();
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

/*	@Override
	public String name() {
		return file.getName();
	}
*/
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






	//TODO now should be handled by supertype
	@Override
	public String[] commandLineArguments() {
		String [] arguments = new String[numberOfArguments()];
		
		
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



	//TODO should be 1 now.
	@Override
	public int numberOfArguments() {
		return 2;
	}


	@Override
	protected String childFolderPath() {
		return "files/";
	}




}
