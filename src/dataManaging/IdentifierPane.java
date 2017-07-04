package dataManaging;

import javax.swing.JOptionPane;

public class IdentifierPane {

	private String choice;
	
	
	public IdentifierPane() {

		String[] identifiers = Identifiers.toArray();
		
		
		choice = (String)JOptionPane.showInputDialog(
                null,
                "Select the database you want to send to the lab",
                "Identifier selector",
                JOptionPane.PLAIN_MESSAGE,
                null,
                identifiers,
                null);
	}
	
	/**
	 * get the identifier chosen by the user
	 * @return
	 */
	public String getIdentifier(){
		return choice;
	}
	
		
	public static void main(String [] args) {
		IdentifierPane ip = new IdentifierPane();
		System.out.println(ip.getIdentifier());
	}

}
