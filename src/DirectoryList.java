import java.io.File;
import java.util.ArrayList;

public class DirectoryList extends ArrayList<File> {
	
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
}
