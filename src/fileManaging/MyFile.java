package fileManaging;

import java.io.File;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

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
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else {
			System.out.println("Open command cancelled by user." );
		}		
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

}
