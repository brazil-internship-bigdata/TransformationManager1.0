package dataManaging.files;

import java.util.function.Supplier;

public class MyFileSupplier implements Supplier<MyFile> {


	
	@Override
	public MyFile get() {
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
