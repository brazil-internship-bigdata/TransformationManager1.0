import java.io.File;
import java.util.ArrayList;


public class DirectoryList extends ArrayList<File> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4962373861401353388L;

	private File savingsFile; //file to modify in case of put/remove/save. This file contains the list of the selected directories
	
	
	public DirectoryList(File savingsFile) {
		super();
		
		//TODO read the savings file to create the list
	}
	
	
	/**
	 * add the directory to the list, and modifies the file to save the new list of directories
	 */
	@Override
	public boolean add(File f) {
		boolean res = super.add(f);
		//TODO write in the savings file to insert the new directory
		return res;
	}

	
	@Override
	public File remove(int index) {
		File deletedElement = this.get(index);
		//TODO remove from the savings file the File deletedElement;
		return deletedElement;
	}
	
	@Override
	public File get(int index) {
		File item = super.get(index);
		
		//TODO verify on internet that the files are really immutable
		
		return item;
	}
	
	public void save() {
		//TODO rewrite the savingsFile
	}

}
