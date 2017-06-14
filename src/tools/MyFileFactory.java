package tools;

import fileManaging.MyFile;

public class MyFileFactory implements Factory<MyFile> {

	@Override
	public MyFile create(MyListView<MyFile> listView) {
		// TODO Auto-generated method stub
		//return new MyFile(listView);
		return null;
	}

	@Override
	public MyFile create() {
		// TODO Auto-generated method stub
		return new MyFile();
	}

	@Override
	public MyFile createWithGUI() throws CancelledCommandException {
		MyFile f = new MyFile();
		f.openFileChooser();
		return f;
	}
	
}
