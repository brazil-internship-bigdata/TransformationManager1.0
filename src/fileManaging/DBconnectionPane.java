package fileManaging;

import javax.swing.JPanel;

public class DBconnectionPane extends JPanel {
	JTextField userid = new JTextField(10);
	JTextField password = new JTextField(10);
	JPanel panel = new JPanel(new GridLayout(2,2));
	panel.add( new JLabel( "Userid:") );
	panel.add( userid );
	panel.add( new JLabel( "Password:") );
	panel.add( password );
	JOptionPane.showConfirmDialog(null, panel);
}
