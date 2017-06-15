package dataManaging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;



public abstract class AbstractDataList<E extends Item> extends ArrayList<E> implements Data<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -409369385013275466L;
	protected String savingsFileName;
	protected File savingsFile;
	
	
	
	
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

	@Override
	public void initList() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(savingsFile);
			br = new BufferedReader(fr);

			String currentLine;
			while((currentLine = br.readLine()) != null && !currentLine.equals("")) {
				E e = textLine2Element(currentLine);
				super.add(e);

				if(!e.check()) {
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
	 * creates and Element from a textLine
	 * @param textLine
	 * @return the element corresponding to the parameters contained in the textLine
	 */
	protected abstract E textLine2Element(String textLine);

	
	@Override
	public boolean add(E e) {
		boolean res = super.add(e);
		saveOne(e);
		return res;
	}

	/**
	 * Saves the path to the given file in the savings file
	 * @param f file to be saved
	 * thank you https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
	 */
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

	
	@Override
	public E remove(int index) {
		E deletedElement = super.remove(index);
		saveAll();
		return deletedElement;
	}

	
	
	@Override
	public boolean remove(Object o) {
		boolean contained = super.remove(o);
		saveAll();
		return contained;
	}

	
	
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
	
	@Override
	public String toString() {
		String res = this.getClass().getName();
		for(E e : this) {
			res += "\n\t"+e.name();
		}
		
		return res;
	}


}
