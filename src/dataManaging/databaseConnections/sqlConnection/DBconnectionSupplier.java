package dataManaging.databaseConnections.sqlConnection;

import java.util.function.Supplier;


public class DBconnectionSupplier implements Supplier<DBconnection> {


	@Override
	public DBconnection get() {
		return new DBconnection();
	}

}
