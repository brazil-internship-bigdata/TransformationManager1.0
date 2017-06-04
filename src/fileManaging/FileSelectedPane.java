package fileManaging;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FileSelectedPane extends JPanel
										implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377495803506335663L;

	private File file;
	private int index;
	private FileList fileList;
	private MyFileListView parent;
	
	private JTextArea filePath;
	private JButton deleteButton;
	private JButton relocateButton;
	
	//TODO file browser, check if file exists and print red text if it doesn't > advising to relocate or delete

	
	
	/**
	 * Constructs a pane containing the item of the dirList (at the position index)
	 * @param fileList List of selected directories
	 * @param index position of the item in the list
	 */
	public FileSelectedPane(FileList fileList, int index) {
		super();
		
		this.fileList = fileList;
		this.file = fileList.get(index);
		filePath = new JTextArea(file.getAbsolutePath());
		filePath.append("\n File Name: " + file.getName());
		
		deleteButton = new JButton("X");
		deleteButton.addActionListener(this);
		
		relocateButton = new JButton("relocate File");
		relocateButton.addActionListener(this);
		
		this.add(filePath);
		this.add(deleteButton);
		
		this.setSize(300, 10);
	}

	public FileSelectedPane(MyFileListView parent, File file) {
		super();
		
		this.parent = parent;
		this.file = file;
		
		filePath = new JTextArea(file.getAbsolutePath());
		filePath.append("\n File Name: " + file.getName());
		
		deleteButton = new JButton("X");
		deleteButton.addActionListener(this);
		
		relocateButton = new JButton("relocate File");
		relocateButton.addActionListener(this);
		
		this.add(filePath);
		this.add(deleteButton);
	}

	public void remove() {
		parent.remove(this);
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == deleteButton) {
			
			//fileList.remove(index);
			parent.remove(this);
			System.out.println("item: " + file.getName() + " was deleted");
		} 
		else if (e.getSource() == relocateButton) {
			//TODO createFileChooser with fileList
		}
		
	}
}
