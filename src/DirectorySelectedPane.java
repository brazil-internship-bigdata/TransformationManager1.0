import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DirectorySelectedPane extends JPanel
										implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377495803506335663L;

	private File directory;
	private int index;
	private DirectoryList dirList;
	
	private JTextArea directoryPath;
	private JButton deleteButton;
	
	//TODO everything -> delete button, directory path, directory browser??
	/**
	 * Constructs a pane containing the item of the dirList (at the position index)
	 * @param dirList List of selected directories
	 * @param index position of the item in the list
	 */
	public DirectorySelectedPane(DirectoryList dirList, int index) {
		super();
		
		this.directory = dirList.get(index);
		directoryPath = new JTextArea(directory.getAbsolutePath());
		
		deleteButton = new JButton("X");
		deleteButton.addActionListener(this);
		
		this.add(directoryPath);
		this.add(deleteButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == deleteButton) {
			dirList.remove(index);
			System.out.println("item: " + directory.getName() + " was deleted");
		}
	}
}
