package dataManaging;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
		
		itemName = new JTextArea(item.name());

		
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
				itemName.setText(item.name());
//				parent.revalidate();
//				parent.repaint();
			} catch (CancelledCommandException e1) {
				//TODO
			}
		}
		else if (e.getSource() == jobButton) {
			//TODO get transform put!
			try {
				CommandExecutor.execute(item);
				item.setJobRunning(true);
			} catch (InterruptedException | IOException e1) {
				item.setJobRunning(false);
				e1.printStackTrace();
			}
		}
		
	}
}
