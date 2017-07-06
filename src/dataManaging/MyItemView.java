package dataManaging;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tools.CancelledCommandException;
import tools.CommandExecutor;

public class MyItemView extends JPanel
										implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 377495803506335663L;

	private Item item;
	private MyListView<?> parent;
	
	private JTextArea itemName;
	private JButton deleteButton;
	private JButton editButton;
	private JButton jobButton;
	//TODO file browser, check if file exists and print red text if it doesn't > advising to relocate or delete

	
	public MyItemView(MyListView<?> parent, Item item) {
		super();
		
		this.parent = parent;
		this.item = item;
		
		itemName = new JTextArea(item.getIdentifier());

		
		deleteButton = new JButton("X");
		deleteButton.setToolTipText("delete this item");
		deleteButton.addActionListener(this);
		
		editButton = new JButton(item.editText()); //we can change the button's text with item.editText
		editButton.addActionListener(this);
		
		jobButton = new JButton("->");
		jobButton.setToolTipText("send the data to the Lab!");
		jobButton.addActionListener(this);
		
		this.add(itemName);
		this.add(deleteButton);
		this.add(editButton);
		this.add(jobButton);
	}

	
	public Item getItem() {
		return item;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == deleteButton) {
			
			parent.remove(this);
			System.out.println("item: " + item.toString() + " was deleted");
		} 
		else if (e.getSource() == editButton) {
			try {
				//If the user clicks on the edit Button, we modify the item through the corresponding GUI.
				parent.getDataList().editItem(item); 

				//Here, the user didn't cancel the item editing => we have to upload the view
				itemName.setText(item.getIdentifier());
//				parent.revalidate();
//				parent.repaint();
			} catch (CancelledCommandException e1) {
				//TODO
			}
		}
		else if (e.getSource() == jobButton) {
			//TODO get transform put!
			
			//Just in case of problem, we generate the folders necessary to receive the job.
			item.generateFolders();
			
			//TODO GET HERE.
			//TODO change this function so it's a get and not a copy anymore.

			
			File destination = new File( item.transformationFolder() );			
			//First we delete the previous jobs.
			File[] old_jobs = destination.listFiles();
			
			
			for(int i = 0 ; i< old_jobs.length ; i++) {
				old_jobs[i].delete();
			}
			
			
			File sources = new File("transformation/jobs/source/" + item.getIdentifier());			
			File[] jobs =  sources.listFiles();

			
			//then we put the new jobs
			for(int i = 0; i<jobs.length ; i++) {
				
				try {
					Path p = Paths.get(destination.getAbsolutePath(), jobs[i].getName());
					Files.copy(jobs[i].toPath(), p, StandardCopyOption.COPY_ATTRIBUTES);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			//Now we execute the rootJob
			try {
				CommandExecutor.execute(item);
				item.setJobRunning(true);
			} catch (InterruptedException | IOException e1) {
				item.setJobRunning(false);
				e1.printStackTrace();
			} finally {
				item.save();
			}
			
			
			//TODO PUT HERE (or maybe through pentaho)
			
		}
		
	}
}
