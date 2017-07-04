package dataManaging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import tools.CancelledCommandException;



public abstract class AbstractDataList<E extends Item> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -409369385013275466L;
	protected String savingsFileName;
	protected File savingsFile;
	
	
	
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
	
	public AbstractDataList() {
		super();
		
//		E item = supplier.get();
		
		//Create the saving Folder if necessary.
		File savingFolder = itemSavingFolder();
		
//		File savingFolder = new File( item.savingFolder() );
		
		
		if( !savingFolder.isDirectory() ) {
			savingFolder.mkdirs();
			return;
		}
		
		initList(savingFolder);
	}
	
	/**
	 * saving folder of the concrete type of item contained in this list
	 * @return the File pointing on the folder containing savings for this type of data.
	 */
	protected abstract File itemSavingFolder();
	
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

			
			} catch (Exception e) {
				System.err.println("An existing element couldn't be read during initialisation.");
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	private void initList() {
		FileReader fr = null;
		BufferedReader br = null;
		boolean some_error_occured = false;
		
		try {
			fr = new FileReader(savingsFile);
			br = new BufferedReader(fr);

			String currentLine;
			while((currentLine = br.readLine()) != null && !currentLine.equals("")) {
				
				try {
					E e = textLine2Element(currentLine);
					super.add(e);

					if(!e.check()) {
						//TODO : for now I'm thinking about printing red text and asking the user to relocate the file. => TODO in FileSelectedPane
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					some_error_occured = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
		
		if(some_error_occured) {
			JOptionPane.showMessageDialog(null,
					"One or more items couldn't be loaded because of some errors in the savings file... Maybe you used illegal characters?",
					"error while loading",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * creates an Element from a textLine
	 * @param textLine
	 * @return the element corresponding to the parameters contained in the textLine
	 */
	protected abstract E textLine2Element(String textLine) throws IllegalArgumentException;

	
	@Override
	public boolean add(E e) {
		boolean res = super.add(e);
		e.generateFolders();
		e.save();
		return res;
	}

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
	
	@Override
	public E remove(int index) {
		E deletedElement = super.remove(index);
		System.out.println("I shouldn't be doing that...");
	//	saveAll();
		return deletedElement;
	}

	
	
	@Override
	public boolean remove(Object o) {
		boolean contained = super.remove(o);
//		saveAll();
		if( o instanceof Item) {
			File savingFile = ((Item) o).savingFile();
			savingFile.delete();
		}
		return contained;
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

}
