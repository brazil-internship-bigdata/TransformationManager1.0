import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class Home extends JFrame
						implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4611492777705564096L;

	
	
	private JSplitPane horizontalSplitPane;

	/*  RIGHT */
	private JScrollPane directoriesScrollPane;//vertical scroll
	private JList<DirectorySelectedPane> directoryPanes; 
	
	/*  LEFT */
	//This part creates the "add repository" and "send manually" part of the home frame
	private JSplitPane actionsSplitPane; 
	private JPanel addPanel; //TOP
	private JButton addRepoButton;
	
	private JPanel manualSendPanel; //BOTTOM
	private JButton sendManuallyButton;


	public static final String SOFTWARE_NAME = "Ecureuil";

	public Home(File savingsFile) {
		super(SOFTWARE_NAME);

		//this.setLayout(new BorderLayout());

		

		
		//List of selected directories (left part)
		directoryPanes = new JList<DirectorySelectedPane>();
		directoriesScrollPane = new JScrollPane(directoryPanes);

		horizontalSplitPane.add(directoriesScrollPane, BorderLayout.WEST);
		
		
		// Add button and send manually button (right part)
	
		//Creation of the vertical split
		actionsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		//Creation of both panels and buttons
		addPanel = new JPanel(new BorderLayout());
		addRepoButton = new JButton("Add new repository");
		addPanel.add(addRepoButton, BorderLayout.CENTER);
		
		manualSendPanel = new JPanel(new BorderLayout());
		sendManuallyButton = new JButton("Send Manually the data");
		manualSendPanel.add(addRepoButton, BorderLayout.CENTER);
		
		
		
		/*  Creation of the horizontal split  */
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				directoriesScrollPane, actionsSplitPane);
		this.add(horizontalSplitPane);
		
		//buttons listeners
		addRepoButton.addActionListener(this);
		sendManuallyButton.addActionListener(this);
		
		
		//print
		this.pack();
		this.setVisible(true);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Home(new File(""));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
