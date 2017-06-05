package fileManaging;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MySplitPane extends JSplitPane implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974607541601382658L;
	
	

	private FileList fileList;
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		

	}

}
