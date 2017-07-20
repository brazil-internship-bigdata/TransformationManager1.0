package dataManaging.files;

import java.io.File;

import dataManaging.AbstractDataList;

public class FileList extends AbstractDataList<MyFile> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4962373861401353388L;

	public FileList(){
		super();
	}
	


	@Override
	protected MyFile textLine2Element(String textLine) {
		String [] attributes = textLine.split(MyFile.separator);
		return new MyFile(attributes);
	}

	
	//this is now useless. I need to delete this and place textLine2Element in the super class => GENERIC
	@Override
	public String toString() {
		String res = "FileList: ";
		for(MyFile f : this) {
			res += "\n\t"+f.getIdentifier();
		}
		
		return res;
	}



	@Override
	protected File itemSavingFolder() {
		return new File("savings/files");
	}



	@Override
	public String listName() {
		// TODO Auto-generated method stub
		return "List of files";
	}
}



