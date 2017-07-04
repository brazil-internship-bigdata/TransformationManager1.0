package dataManaging;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tools.CancelledCommandException;
import tools.MySupplier;


public class MyListView<T extends Item> extends JPanel
											implements ActionListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6278407390981154945L;
	
	private MySupplier<T> supplier;
	
	private AbstractDataList<T> listData; //TODO delete this comment : This map joins the list Items inside a given List and the elements of the listView

	
	private JPanel buttonsPane;
	private JButton addButton;
	private JButton checkButton;
	private JButton sendButton; //probably useless
	
	
	//TODO modify this
	public MyListView(AbstractDataList<T> list, MySupplier<T> supplier) {
		super();
		
		this.supplier = supplier;

		//Creation of the buttons Pane
		buttonsPane = new JPanel();		
		
		//Creation and insertion of the buttons in the buttonsPane
		addButton = new JButton("+"); //+ icon
		buttonsPane.add(addButton, BorderLayout.LINE_START);
		
		checkButton = new JButton("v"); //check icon
		buttonsPane.add(checkButton, BorderLayout.CENTER);
		
		sendButton = new JButton("->"); //run icon
		buttonsPane.add(sendButton, BorderLayout.LINE_END);
		
		
		//Subscribe buttons
		addButton.addActionListener(this);
		checkButton.addActionListener(this);
		sendButton.addActionListener(this);
		
		//Creation of the List view
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(buttonsPane);
		init(list);	
	}
	
	
	private void init(AbstractDataList<T> list) {
		listData = list;
		for(T t : list) {
			MyItemView itemView = new MyItemView(this, t);
			this.add(itemView);
		}
	}
	
	public void add (T t) {
		MyItemView itemView = new MyItemView(this, t);
		listData.add(t);
		this.add(itemView);
		this.revalidate();
		this.repaint();
	}

	
	/**
	 * removes the FileSelectedPane from the scrollBar and removes the element from the list.
	 * @param child the pane that was deleted
	 */
	public void remove(MyItemView child) {
		super.remove(child);
		listData.remove(child.getItem());
		this.revalidate();
		this.repaint();
	}
	
	
	/**
	 * check every Item of the list and alerts the user of the invalid ones.
	 * @return true if every item is correctly configured
	 */
	public boolean checkItems() {
		boolean successfulCheck = true;

		String invalidItems = "";
		for(T t : listData) {
			if(!t.check()) {
				successfulCheck = false;
				invalidItems += "\n" + t.getIdentifier();
			}
		}

		
		if (successfulCheck) {
			//custom title, warning icon
			JOptionPane.showMessageDialog(this,
					"Every item of the list is correctly configured",
					"Success!",
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon("images/valid.png"));
		} else {
			//custom title, warning icon
			JOptionPane.showMessageDialog(this,
					"The following items aren't valid: " + invalidItems,
					"Warning!",
					JOptionPane.WARNING_MESSAGE);			
		}
		
		return successfulCheck;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			//Open new element perspective e.g. FileChooser if E is MyFile

			try {
				//We use the factory to create an item of type T.
				T t = supplier.get();
				
				//We open the GUI so the user sets the parameters
				t.setWithGUI();
				
				//Here the user approved the parameters, thus we have to add the new Item to the data list
				this.add(t);
		
			} catch (CancelledCommandException e1) {
				e1.printStackTrace();
				System.err.println("Cancelled");
			}
		}
		else if(e.getSource() == checkButton) {
			this.checkItems();
		}
		else if(e.getSource() == sendButton) {
			//TODO run the transformation thread
		}
	}
	
	public AbstractDataList<T> getDataList() {
		return listData;
	}
	

}
