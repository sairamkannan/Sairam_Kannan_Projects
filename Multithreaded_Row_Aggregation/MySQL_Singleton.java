package Multithreaded_Row_Aggregation;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class MySQL_Singleton {
	Connection con = null;
	private MySQL_Singleton()
	{
	}
	// The private inner static class that contains the instance of the singleton class. 
	// When the singleton class is loaded, SingletonHelper class is not loaded into memory and only when someone calls the getInstance method,
	// this class gets loaded and creates the Singleton class instance.
	// It is Thread-safe and does not required synchronization.
	private static class SingletonHelper {
		private static final MySQL_Singleton instance = new MySQL_Singleton();
	}
	public static MySQL_Singleton getInstance() {
		return SingletonHelper.instance;
	}
	public Connection MySQL_Connect(String host, String username, String password, String database)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");    // Loads the required MySQL driver
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager
			.getConnection("jdbc:mysql://"+host+":3306/"+database,username,password);  // Gets connection access to the required MySQL database using the credentials

		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
		}
		return con;
	}
}
