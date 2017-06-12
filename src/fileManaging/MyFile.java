package fileManaging;

import java.io.File;
import java.net.URI;

public class MyFile extends File implements Item {

	public MyFile(File parent, String child) {
		super(parent, child);
		// TODO Auto-generated constructor stub
	}

	public MyFile(String parent, String child) {
		super(parent, child);
		// TODO Auto-generated constructor stub
	}

	public MyFile(String pathname) {
		super(pathname);
		// TODO Auto-generated constructor stub
	}

	public MyFile(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String editText() {
		// TODO Auto-generated method stub
		return "Edit file location";
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return this.getName();
	}

}
