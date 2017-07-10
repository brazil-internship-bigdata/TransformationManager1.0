package dataManaging.databaseConnections.sqlConnection;


import tools.MySupplier;
import tools.exceptions.CancelledCommandException;



public class MySQLconnectionSupplier implements MySupplier<MySQLconnection> {

	@Override
	public MySQLconnection get() throws CancelledCommandException {
		return new MySQLconnection();
	}

}
