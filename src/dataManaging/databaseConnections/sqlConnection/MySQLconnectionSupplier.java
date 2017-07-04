package dataManaging.databaseConnections.sqlConnection;


import tools.CancelledCommandException;
import tools.MySupplier;



public class MySQLconnectionSupplier implements MySupplier<MySQLconnection> {

	@Override
	public MySQLconnection get() throws CancelledCommandException {
		return new MySQLconnection();
	}

}
