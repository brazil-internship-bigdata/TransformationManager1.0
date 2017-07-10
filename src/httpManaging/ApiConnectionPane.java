package httpManaging;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;



public class ApiConnectionPane implements ActionListener {

	private JPanel connectionPane;
	private JOptionPane optionPane;
	private JDialog dialog;
	
	private JTextField userName = new JTextField(20);
	private JPasswordField password = new JPasswordField(20);
	
	private JLabel userLabel = new JLabel("User Name");	
	private JLabel passwordLabel = new JLabel("Password");
	
	private JButton connectionButton = new JButton("Connection");
	
	public ApiConnectionPane() {
		connectionPane = new JPanel(new MigLayout());
		
		String toolTip = "Put here your ids given by LaPeSD";
		userName.setToolTipText(toolTip);
		password.setToolTipText(toolTip);
		userLabel.setToolTipText(toolTip);
		passwordLabel.setToolTipText(toolTip);
		
		connectionPane.add(userLabel);
		connectionPane.add(userName,"wrap");
		
		connectionPane.add(passwordLabel);
		connectionPane.add(password,"wrap");
		
		connectionPane.add(connectionButton, "center");
	
		//subscribe button to listener
		connectionButton.addActionListener(this);
		
	}
	
	public void display() {
		optionPane = new JOptionPane(null, JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
		dialog = new JDialog();
		dialog.setModal(true);
		dialog.setTitle("Connection to the lab");
		dialog.add(connectionPane);
		
		dialog.pack();
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == connectionButton) {
			HttpManager httpMan = new HttpManager();
			
			boolean connected = httpMan.connect(userName.getText(), password.getText());
			
			if(!connected) {
				JOptionPane.showMessageDialog(null, "Connection failed");
				return;
			}
			
			
		}
	}

	public static void main(String[] args) {
		new ApiConnectionPane().display();
		System.out.println("fini!");
	}
}
