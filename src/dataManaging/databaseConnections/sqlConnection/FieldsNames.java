package dataManaging.databaseConnections.sqlConnection;

public enum FieldsNames {
	CONNECTION_NAME (0, "Connection Name", "Put any name here"),
	HOST_NAME(1, "Host Name:", "put here the name of the host e.g. 127.0.0.1 or localhost"),
	DATABASE_NAME(2, "Database Name:", "Put the name of  database e.g. \"world\" in the case of world.sql"),
	USERNAME(3, "User Name:", "Put the name you use to connect to the database"),
	PASSWORD(4, "Password:", "Put your password here");
	//TODO complete with other connection parameters
	
	private final int index;
	private String field_name;
	private String toolTip;
	
	private FieldsNames(int index, String field_name, String toolTip) {
		this.index = index;
		this.field_name = field_name;
		this.toolTip = toolTip;
	}
	
	public int index() { return index; }
	public String field_name() { return field_name; }
	public String toolTip() { return toolTip; }
}

