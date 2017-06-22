package dataManaging.databaseConnections.sqlConnection;

import java.util.function.Supplier;


public class MySQLconnectionSupplier implements Supplier<MySQLconnection> {


	@Override
	public MySQLconnection get() {
		return new MySQLconnection();
	}

}
