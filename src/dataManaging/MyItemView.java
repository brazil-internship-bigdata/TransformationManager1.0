package dataManaging;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tools.CancelledCommandException;

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
	
	//TODO file browser, check if file exists and print red text if it doesn't > advising to relocate or delete

	
	public MyItemView(MyListView<?> parent, Item item) {
		super();
		
		this.parent = parent;
		this.item = item;
		
		itemName = new JTextArea(item.name());

		
		deleteButton = new JButton("X");
		deleteButton.addActionListener(this);
		
		editButton = new JButton(item.editText()); //we can change the button's text with item.editText
		editButton.addActionListener(this);
		
		this.add(itemName);
		this.add(deleteButton);
		this.add(editButton);
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
				//If the user clics on the edit Button, we modify the item through the corresponding GUI.
				item.setWithGUI();
				
				//Here the user didn't cancel the modification so we need to save the modifications
				parent.getDataList().saveAll();
				
				itemName.setText(item.name());
//				parent.revalidate();
//				parent.repaint();
			} catch (CancelledCommandException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
