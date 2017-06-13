package fileManaging;

public class DBconnection implements Item {

	private String customName;
	private String hostName;
	private String dataBaseName;
	private String tableSpaceData;
	private String tableSpaceIndices;
	private String userName;
	private String portName;
	private String password;
	
	private MyListView<DBconnection> listView;
	
	public DBconnection(MyListView<DBconnection> listView) {
		this.listView = listView;
		//TODO
	}
	
	public DBconnection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String editText() {
		// TODO Auto-generated method stub
		return "Edit connection parameters";
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return customName;
	}

	@Override
	public boolean check() {
		// TODO check the connection
		return true;
	}

}
