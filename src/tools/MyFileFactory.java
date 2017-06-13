package tools;

import fileManaging.MyFile;
import fileManaging.MyListView;

public class MyFileFactory implements Factory<MyFile> {

	@Override
	public MyFile create(MyListView<MyFile> listView) {
		// TODO Auto-generated method stub
		return new MyFile(listView);
	}

	@Override
	public MyFile create() {
		// TODO Auto-generated method stub
		return new MyFile();
	}
	
}
