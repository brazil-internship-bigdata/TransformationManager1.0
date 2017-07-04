package dataManaging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

/**
 * This class is a Singleton. It contains the ids of each database to be sent. (probably only one).
 * @author ferreraalexandre
 *
 */
public class Identifiers {

	//TODO possibility to add an identfier. This is a really NOT important TODO
	
	
	//The identifiersArray should always contain every existing identifier
//	private final String [] identifiersArray; 

	//remainingIDs should always contain the list of the unused identifiers.
	private static List<String> remainingIDs;
	
	private static Identifiers instance = new Identifiers();
	
	private Identifiers() {
		Path pathToIdentifiers = Paths.get("identifiers/identifiers.txt");
		
		try {
			remainingIDs = Files.readAllLines(pathToIdentifiers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//identifiersArray = (String[]) remainingIDs.toArray();		
	}
	
	
	public static Identifiers getInstance() {
		return instance;
	}
	
	/**
	 * create a comboBox based on the remaining identifiers.
	 * @return a comboBox containing the available identifiers
	public static JComboBox<String> getComboBox() {
		String [] t_id = new String[remainingIDs.size()];
		
		return new JComboBox<String>((String[]) remainingIDs.toArray(t_id));
	}
	 */
	
	/**
	 * array of the remaining identifiers.
	 * @return an array containing the available identifiers
	 */	
	public static String[] toArray() {
		String [] t_id = new String[remainingIDs.size()];
		return (String[]) remainingIDs.toArray(t_id);
	}
	
	
	public static void freeIdentifier(Item item) {
		remainingIDs.add(item.getIdentifier());
	}

}
