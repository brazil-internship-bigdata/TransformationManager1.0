package dataManaging.files;


import dataManaging.AbstractDataList;


public class FileList extends AbstractDataList<MyFile> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4962373861401353388L;

	public FileList(){
		super("Files_Savings.txt");
	}
	
	


	@Override
	protected MyFile textLine2Element(String textLine) {
		String [] attributes = textLine.split(MyFile.separator);
		return new MyFile(attributes);
	}

	
	
	@Override
	public String toString() {
		String res = "FileList: ";
		for(MyFile f : this) {
			res += "\n\t"+f.name();
		}
		
		return res;
	}
}



