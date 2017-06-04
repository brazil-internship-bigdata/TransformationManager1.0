package fileManaging;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;


public class FileList extends ArrayList<File> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4962373861401353388L;

	private File savingsFile; //file to modify in case of put/remove/save. This file contains the list of the selected directories
	
	
	public FileList() {
		super();
		
		this.savingsFile = new File("savings/savings.txt");
		
		//if the savings file doesn't exist, we have to create the directory and the file.
		if(!savingsFile.exists()) {
			createSavingsFile();
		}
		
		initList();
	}
	

	/**
	 * This method generates the savings File (empty).
	 */
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
	
	/**
	 * 	Initializes the List based on the savings File
	 * thank you https://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
	 */
	public void initList() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(savingsFile);
			br = new BufferedReader(fr);

			String currentLine;
			while((currentLine = br.readLine()) != null && !currentLine.equals("")) {
				File f = new File(currentLine);
				super.add(f);

				if(!f.exists()) {
					//TODO : for now I'm thinking about printing red text and asking the user to relocate the file. => TODO in FileSelectedPane
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
	}	

	/**
	 * add the directory to the list, and modifies the file to save the new list of directories
	 * thank you https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
	 */
	@Override
	public boolean add(File f) {
		boolean res = super.add(f);
		saveOne(f);
		return res;
	}

	
	@Override
	public File remove(int index) {
		File deletedElement = super.remove(index);
		saveAll();
		return deletedElement;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean contained = super.remove(o);
		saveAll();
		return contained;
	}
		
	
	
	public void saveAll() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(savingsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			createSavingsFile();
		}
		pw.close();
		
		for(File f : this) {
			saveOne(f);
		}
	}


	/**
	 * Saves the path to the given file in the savings file
	 * @param f file to be saved
	 */
	private void saveOne(File f) {

		if(!savingsFile.exists()) {
			createSavingsFile();
		}
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			String content = f.getAbsolutePath() +"\n";

			fw = new FileWriter(savingsFile, true);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done, " + content);

		} catch (IOException e) {
			e.printStackTrace();
			
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
	
	
	@Override
	public String toString() {
		String res = "FileList: ";
		for(File f : this) {
			res += "\n\t"+f.getName();
		}
		
		return res;
	}
}



