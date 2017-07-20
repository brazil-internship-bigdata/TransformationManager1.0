package dataManaging;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import tools.exceptions.CancelledCommandException;



public abstract class AbstractDataList<E extends Item> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -409369385013275466L;
	protected String savingsFileName;
	protected File savingsFile;
	
	
	
	
	public AbstractDataList() {
		super();		
		
		//Create the saving Folder if necessary.
		File savingFolder = itemSavingFolder();
		
		
		if( !savingFolder.isDirectory() ) {
			savingFolder.mkdirs();
			return;
		}
		
		initList(savingFolder);
	}
	
	public abstract String listName();
	
	/**
	 * saving folder of the concrete type of item contained in this list
	 * @return the File pointing on the folder containing savings for this type of data.
	 */
	protected abstract File itemSavingFolder();
	
	/**
	 * initializes the list with the files present in the savings directory.
	 * @param savingFolder folder containing the saved elements
	 */
	private void initList(File savingFolder) {
		File[] savingFiles = savingFolder.listFiles();
		
		
	
		if(savingFiles == null) {
			return;
		}
		
		for(int i = 0; i<savingFiles.length ; i++) {
			try {
				String savedAttributes = new String(Files.readAllBytes(savingFiles[i].toPath()));
				
				
				E item = textLine2Element(savedAttributes);
				super.add(item); //During initialization, we only want to register the data in this list. That's why we user super.add instead of add
				Identifiers.use(item);

			
			} catch (Exception e) {
				System.err.println("An existing element couldn't be read during initialisation.");
				e.printStackTrace();
			}
		}
		
	}
	
	
	

	/**
	 * creates an Element from a textLine
	 * @param textLine
	 * @return the element corresponding to the parameters contained in the textLine
	 */
	protected abstract E textLine2Element(String textLine) throws IllegalArgumentException;
	//TODO delete this comment : This map joins the list Items inside a given List and the elements of the listView
	
	@Override
	public boolean add(E e) {
		boolean res = super.add(e);
		
		//first, we "book" the selected identifier
		Identifiers.use(e);
		
		//then we save the changes
		e.generateFolders();
		e.save();
		
		return res;
	}

	
	@Override
	public E remove(int index) {
		try {
			throw new Exception("illegal method used");
		} catch (Exception e) {
			e.printStackTrace();
		}

		E deletedElement = super.remove(index);
		System.out.println("I shouldn't be doing that...");		
		return deletedElement;
	}

	
	
	@Override
	public boolean remove(Object o) {
		boolean contained = super.remove(o);

		//If the method isn't used on an item, it shouldn't remove anything.
		if( ! (o instanceof Item )) {
			return false;
		}
		
		//Here the object is an Item
		//first we free the identifier 
		Identifiers.free((Item) o);

		//then we save the changes
		File savingFile = ((Item) o).savingFile();
		savingFile.delete();

		
		return contained;
	}

	
	
	@Override
	public String toString() {
		String res = this.getClass().getName();
		for(E e : this) {
			res += "\n\t"+e.getIdentifier();
		}
		
		return res;
	}

	
	public void editItem(Item item) throws CancelledCommandException {
		item.setWithGUI();
		
		//Here the user didn't cancel the modification so we need to save the modifications
		item.save();
	}

	/*
	@Override
	public void saveAll() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(savingsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			createSavingsFile();
		}
		pw.close();
		
		for(E e : this) {
			saveOne(e);
		}
	}
	*/
	
	/**
	 * Saves the path to the given file in the savings file
	 * @param f file to be saved
	 * thank you https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
	 *//*
	protected void saveOne(E e) {

		if(!savingsFile.exists()) {
			createSavingsFile();
		}
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = e.generateSavingTextLine() +"\n";

			fw = new FileWriter(savingsFile, true);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done, " + content);

		} catch (IOException exception) {
			exception.printStackTrace();
			
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	*/

	/*	
	@Override
	public void createSavingsFile() {
		//Creation of the directory
		new File("savings").mkdir();

		//Creation of the file
		try {
			savingsFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}		
	}
*/

	/*	
	public AbstractDataList(String savingsFileName) {
		super();

		
		this.savingsFileName = savingsFileName;
		this.savingsFile = new File("savings/" + savingsFileName);
		
		//if the savings file doesn't exist, we have to create the directory and the file.
		if(!savingsFile.exists()) {
			createSavingsFile();
		}
		
		initList();
	}
*/ 


}
