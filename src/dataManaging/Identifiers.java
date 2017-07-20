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
	 * array of the remaining identifiers.
	 * @return an array containing the available identifiers
	 */	
	public static String[] toArray() {
		String [] t_id = new String[remainingIDs.size()];
		return (String[]) remainingIDs.toArray(t_id);
	}
	
	


	/**
	 * this method must be used when an identifier is selected. This way, this identifier isn't proposed anymore.
	 * nb: the method free(String) must be used when an item is removed, so it's identifier can be proposed again.
	 * @param item newly created.
	 */
	protected static void use(Item item) {
		remainingIDs.remove(item.getIdentifier());
	}
	
	
	/**
	 * This method must be used when an item is removed. This way, the identifier can be used by other items.
	 * @param item being deleted.
	 */
	protected static void free(Item item) {
		remainingIDs.add(item.getIdentifier());
	}

}
