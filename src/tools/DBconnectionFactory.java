package tools;

import fileManaging.DBconnection;
import fileManaging.MyListView;

public class DBconnectionFactory implements Factory<DBconnection> {

	@Override
	public DBconnection create(MyListView<DBconnection> listView) {
		// TODO Auto-generated method stub
		return new DBconnection(listView);
	}

	@Override
	public DBconnection create() {
		// TODO Auto-generated method stub
		return new DBconnection();
	}
	
}
