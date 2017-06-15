package dataManaging.files;

import java.io.File;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import dataManaging.Item;
import tools.CancelledCommandException;

public class MyFile implements Item {

	private File file;
/*	private MyListView<MyFile> listView;
	
	public MyFile(MyListView<MyFile> listView) {

		this.listView = listView;
		
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showOpenDialog(listView);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			listView.add(this);
		} else {
			System.out.println("Open command cancelled by user." );
		}
	}
*/	
	
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
		JFileChooser fc = new JFileChooser();
		
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

}
