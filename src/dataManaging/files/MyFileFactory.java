package dataManaging.files;

import dataManaging.Factory;
import dataManaging.MyListView;
import tools.CancelledCommandException;

public class MyFileFactory implements Factory<MyFile> {


	
	@Override
	public MyFile create() {
		return new MyFile();
	}
/*
	@Override
	public MyFile createWithGUI() throws CancelledCommandException {
		MyFile f = new MyFile();
		f.openFileChooser();
		return f;
	}
	*/
}
