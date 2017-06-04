package fileManaging;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MyFileListView extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1893607688456881203L;

	private FileList fileList;
	
	public MyFileListView(FileList fileList) {
		this.fileList = fileList;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	private void init() {
		for(File f : fileList) {
			this.add(new FileSelectedPane(this, f));
		}
	}
	
	public void add(File f) {
		super.add(new FileSelectedPane(this, f));
		fileList.add(f);
		this.revalidate();
		this.repaint();
	}
	
	public void remove(FileSelectedPane child) {
		super.remove(child);
		fileList.remove(child.getFile());
		this.repaint();
	}
}
