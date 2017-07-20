package dataManaging.databaseConnections;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dataManaging.AbstractItem;

public class DBconnectionPane extends JPanel 
										implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8474843657833224945L;

	private final int textLength = 15;

	
	private JTextField [] t_jfields;
	private JLabel [] t_labels;
	
	
	private JButton createButton;
	private JButton cancelButton;
	
	private JDialog dialog;
	private int user_choice = -1; //0 for createButton Button and 1 for cancelButton
	
	
	private DBconnectionPane() {
		super();
	}
	
	public DBconnectionPane(AbstractDBconnection adbc) {
		this();
		
		if(adbc == null) {
			System.err.println("i forgot something with the creation of DBconnectionPane");
			return;
		}
		
		
		this.setLayout(new GridLayout(adbc.numberOfCustomFields()+2,2)); //numberOfFields+2 rows to add the blanc and the buttons. 2 columns to add the labels and the JTextFields

		
		t_jfields = new JTextField[adbc.numberOfCustomFields()];
		t_labels = new JLabel[adbc.numberOfCustomFields()];

		
		for(int i = 0 ; i<t_jfields.length-1 ; i++) { //Password must be the last field.
			t_labels[i] = new JLabel( adbc.fields[i][1] ); //get the name of the field
			this.add(t_labels[i]);
			
			t_jfields[i] = new JTextField(textLength);
			t_jfields[i].setText( adbc.fields[i][0] );
			t_jfields[i].setToolTipText( adbc.fields[i][2] ); //get the tooltip of the field
			this.add( t_jfields[i] );
		}
		
		// We now have to take care of the password			
		int pwIndex = t_jfields.length-1;
		
		t_labels[pwIndex] = new JLabel( adbc.fields[pwIndex][1] ); //get the name of the field
		this.add(t_labels[pwIndex]);
		
		t_jfields[pwIndex] = new JPasswordField(textLength);
		t_jfields[pwIndex].setText( adbc.fields[pwIndex][0] );
		t_jfields[pwIndex].setToolTipText( adbc.fields[pwIndex][2] ); //get the tooltip of the field
		this.add( t_jfields[pwIndex] );
		
		
		//create the buttons and subscribe them to an action Listener (this)
		createButton = new JButton("Create Connection");
		cancelButton = new JButton("Cancel");
		createButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		//create a space between the fields and the buttons
		this.add(new JPanel());
		this.add(new JPanel());

		
		//add the buttons
		this.add(createButton);
		this.add(cancelButton);

	}

	/**
	 * create option pane and return the result of showOptionDialog TODO do the same as in IdentifierPane
	 * @return the result of a showOptionDialog with JOPTIONPANE.OK_CANCEL_OPTION
	 */
	public int showCreationDialog() {
		
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setTitle("Connection parameters");
		dialog.setContentPane(this);

	
		dialog.pack();
		dialog.setVisible(true);
		
		//return the optionPane answer value.
		return user_choice;
		
	}
	
	private boolean checkFields() {
		boolean all_fields_correct = true;
		
		
		//for each field, check if it is not empty nor contains spaces
		for(int i=0 ; i<t_jfields.length ; i++) {
			all_fields_correct = all_fields_correct 
					&& !t_jfields[i].getText().contains(AbstractItem.separator)
					&& !t_jfields[i].getText().equals(""); 			
		}

		return all_fields_correct;
	}

	/*
	public DBconnection makeDBconnection() {
		return new DBconnection(connectionName.getText(), hostName.getText(), databaseName.getText(), userName.getText(), password.getPassword());
	}
	*/
	

	
	public String[] paneFields() {
		String[] res = new String[t_jfields.length];
		
		for(int i = 0 ; i<t_jfields.length ; i++) {
			res[i] = t_jfields[i].getText();
		}
		
		return res;
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == createButton) {
			if( checkFields() ) {
				user_choice = 0;
				dialog.setVisible(false);
			}
			else 			            		
				JOptionPane.showMessageDialog(null,
						"One or more fields are not correctly filled.\nThere must be no empty field, and spaces aren't allowed.",
						"I can't save these parameters :'(",
						JOptionPane.ERROR_MESSAGE);
		}
		else if (e.getSource() == cancelButton) {
			dialog.setVisible(false);
		}
	}

}
