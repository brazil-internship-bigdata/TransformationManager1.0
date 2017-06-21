package dev_work_in_progress;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DBconnectionPane extends JPanel 
										implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8474843657833224945L;

	private final int textLength = 15;
	
	private JTextField connectionName;
	private JTextField hostName;
	private JTextField databaseName;
	private JTextField userName;
	private JPasswordField password;
	
	private JButton createButton;
	private JButton cancelButton;
	
	private JDialog dialog;
	private int user_choice; //0 for createButton Button and 1 for cancelButton
	/*
	private JTextField [] textFields = {
			new JTextField(textLength) ,
			new JTextField(textLength) ,
			new JTextField(textLength) ,
			new JTextField(textLength) ,
			new JPasswordField(textLength)
	} ;
	
	
	private String [][] name_toolTip = {
			{ "Connection Name:" , "Put any name here" },
			{ "Host Name:" , "put here the name of the host e.g. 127.0.0.1 or localhost" },
			{ "DataBase Name:" , "Put the name of  database e.g. \"world\" in the case of world.sql" },
			{ "User Name:" , "Put the name you use to connect to the database" },
			{ "Password:" , "Put your password here" }
	};
	
	
	
	private HashMap<JTextField, String[]> fields_map; //TODO check this out. The password might be problematic
	
	*/
	
	
	public DBconnectionPane() {
		super();

		
		
		this.setLayout(new GridLayout(7,2));
//TODO try to use miglayout
		
		
		/*
		fields_map = new HashMap<JTextField, String[]>(DBconnection.NUMBER_OF_FIELDS);
		
		for(int i = 0; i < DBconnection.NUMBER_OF_FIELDS ; i++) {
		fields_map.put(textFields[i], name_toolTip[i]);
		JLabel label = new JLabel( name_toolTip[i][0]);
		this.add(label);
		this.add( textFields[i] );
		String toolTip = name_toolTip[i][1];
		connectionName.setToolTipText(toolTip);
		label.setToolTipText(toolTip);
	}
*/			
		
		connectionName = new JTextField(textLength);		
		hostName = new JTextField(textLength);
		databaseName = new JTextField(textLength);
		userName = new JTextField(textLength);		
		password = new JPasswordField(textLength);

		
		JLabel label = new JLabel( "Connection Name:");
		this.add(label);
		this.add( connectionName );
		String text = "Put any name here";
		connectionName.setToolTipText(text);
		label.setToolTipText(text);

		
		label = new JLabel( "Host Name:");
		this.add(label);
		this.add( hostName );
		text = "put here the name of the host e.g. 127.0.0.1 or localhost";
		hostName.setToolTipText(text);
		label.setToolTipText(text);

		label = new JLabel( "DataBase Name:");
		this.add(label);
		this.add( databaseName );
		text = "Put the name of  database e.g. \"world\" in the case of world.sql";
		databaseName.setToolTipText(text);
		label.setToolTipText(text);

		label = new JLabel( "User Name:");
		this.add(label);
		this.add( userName );
		text = "Put the name you use to connect to the database";
		userName.setToolTipText(text);
		label.setToolTipText(text);

		label = new JLabel( "Password:");
		this.add(label);
		this.add( password );
		text = "Put your password here";
		password.setToolTipText(text);
		label.setToolTipText(text);

		
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
	
	public DBconnectionPane(DBconnection dbc) {
		this();
		
		if(dbc == null) {
			return;
		}
		
/*		for(JTextField element : fields_map.keySet()) {
			element.setText(t);
		}
*/		
		connectionName.setText(dbc.name());
		hostName.setText(dbc.getHostName());
		databaseName.setText(dbc.getDataBaseName());
		userName.setText(dbc.getUserName());
		password.setText(dbc.getPassword());
		
	}

	/**
	 * create option pane and return the result of showOptionDialog
	 * @return the result of a showOptionDialog with JOPTIONPANE.OK_CANCEL_OPTION
	 */
	public int showCreationDialog() {
//		final JOptionPane optionPane = new JOptionPane(this, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		dialog = new JDialog();//= new JDialog((Frame) this.getTopLevelAncestor(), "Connection parameters",true);
		dialog.setModal(true);
		dialog.setTitle("Connection parameters");
		dialog.setContentPane(this);

/*		optionPane.addPropertyChangeListener(
			    new PropertyChangeListener() {
			    	@Override
			        public void propertyChange(PropertyChangeEvent e) {
			            String prop = e.getPropertyName();

			            System.out.println("je suis là");
			            if (dialog.isVisible() 
			             && (e.getSource() == optionPane)
			             && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
			            	
			            	//Here, the user clicked on the OK button => verify the fields of the optionPane and quit if they are correctly filled
			            	if( checkFields() ) 
			            		dialog.setVisible(false);
			            	else 
			            		JOptionPane.showMessageDialog(null,
			            				"One or more fields are not correctly filled.\nThere must be no empty field, and spaces aren't allowed.",
			            				"I can't save these parameters :'(",
			            				JOptionPane.ERROR_MESSAGE);
			            }
			        }

			    });
	*/	
		dialog.pack();
		dialog.setVisible(true);
		
		//return the optionPane answer value.
		return user_choice;
		
		//		return JOptionPane.showOptionDialog(null, this, "Connection parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
	}
	
	private boolean checkFields() {
		boolean all_fields_correct = true;
		
		
		//for each field, check if it is not empty nor contains spaces
		all_fields_correct = all_fields_correct && !connectionName.getText().contains(" ") && !connectionName.getText().equals(""); 
		all_fields_correct = all_fields_correct && !hostName.getText().contains(" ") && !hostName.getText().equals(""); 
		all_fields_correct = all_fields_correct && !databaseName.getText().contains(" ") && !databaseName.getText().equals(""); 
		all_fields_correct = all_fields_correct && !userName.getText().contains(" ") && !userName.getText().equals(""); 
		all_fields_correct = all_fields_correct && !new String(password.getPassword()).contains(" ") && !new String(password.getPassword()).equals(""); 
		
		return all_fields_correct;
	}

	
	public DBconnection makeDBconnection() {
		return new DBconnection(connectionName.getText(), hostName.getText(), databaseName.getText(), userName.getText(), password.getPassword());
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(new DBconnectionPane().showCreationDialog());
	}

	
	
	
	public String getConnectionName() {
		return connectionName.getText();
	}

	public String getHostName() {
		return hostName.getText();
	}

	public String getDatabaseName() {
		return databaseName.getText();
	}

	public String getUserName() {
		return userName.getText();
	}

	public String getPassword() {
		return new String(password.getPassword());
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
			user_choice = 1;
			dialog.setVisible(false);
		}
	}
}
