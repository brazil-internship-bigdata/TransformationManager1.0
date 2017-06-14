package fileManaging;


import tools.AbstractDataList;


public class FileList extends AbstractDataList<MyFile> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4962373861401353388L;

	{this.savingsFileName = "Files_Savings.txt";}
	
	


	@Override
	protected MyFile textLine2Element(String textLine) {
		return new MyFile(textLine);
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



