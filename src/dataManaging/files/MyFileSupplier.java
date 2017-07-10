package dataManaging.files;

import tools.MySupplier;
import tools.exceptions.CancelledCommandException;

public class MyFileSupplier implements MySupplier<MyFile> {


	
	@Override
	public MyFile get() throws CancelledCommandException {
		return new MyFile();
	}
/*
 * TODO remove
	@Override
	public MyFile createWithGUI() throws CancelledCommandException {
		MyFile f = new MyFile();
		f.openFileChooser();
		return f;
	}
	*/
}
