package tools;

import java.io.File;

public class SavingsManager {

	private File savingsFile;

	public SavingsManager(File savingsFile) {
		this.savingsFile = savingsFile;
	}
	
	public void saveAttribute(String attributeName, String value) {
		//TODO add/modif the attribute attributeName with the value indicated
	}
	
	/**
	 * erase the item's savings
	 */
	public void eraseItem() {
		savingsFile.delete();
	}
}
