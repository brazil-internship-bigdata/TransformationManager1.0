package dataManaging.files;

import tools.CancelledCommandException;
import tools.MySupplier;

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
