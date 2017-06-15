package dataManaging;

public interface Data<E extends Item> {
	

	/**
	 * This method generates the savings File (empty).
	 */
	public void createSavingsFile();
	
	/**
	 * 	Initializes the List based on the savings File
	 */
	public void initList();
	
	/**
	 * add the directory to the list, and modifies the file to save the new list of directories
	 */
	
	
	public void saveAll();

	}
