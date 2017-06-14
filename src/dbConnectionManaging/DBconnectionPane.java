package dbConnectionManaging;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DBconnectionPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8474843657833224945L;

	private final int textLength = 15;
	
	private JTextField yourConnectionName;
	private JTextField hostName;
	private JTextField databaseName;
	private JTextField userName;
	private JPasswordField password;
	
	
//	private JPanel panel;
	
	
	public DBconnectionPane() {
		super();
		yourConnectionName = new JTextField(textLength);		
		hostName = new JTextField(textLength);
		databaseName = new JTextField(textLength);
		userName = new JTextField(textLength);		
		password = new JPasswordField(textLength);
		
		this.setLayout(new GridLayout(5,2));

		JLabel label = new JLabel( "Connection Name:");
		this.add(label);
		this.add( yourConnectionName );
		String text = "Put any name here";
		yourConnectionName.setToolTipText(text);
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
		text = "Put the name of your database e.g. \"world\" in the case of world.sql";
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
	}
	
	public DBconnectionPane(DBconnection dbc) {
		this();
		
		if(dbc == null) {
			return;
		}
		
		yourConnectionName.setText(dbc.name());
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
		return JOptionPane.showOptionDialog(null, this, "Connection parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
	}

	
	public DBconnection makeDBconnection() {
		return new DBconnection(yourConnectionName.getText(), hostName.getText(), databaseName.getText(), userName.getText(), password.getPassword());
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(new DBconnectionPane().showCreationDialog());
	}
}
