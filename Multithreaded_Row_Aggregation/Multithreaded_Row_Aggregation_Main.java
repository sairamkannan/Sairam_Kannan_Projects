package Multithreaded_Row_Aggregation;
import java.sql.Connection;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import DBConnection.MySQL_Singleton;
import Logic.Query_Logic;
public class Multithreaded_Row_Aggregation_Main   {
	private static Connection con;
	public static void main(String[] args) throws Exception
	{
	Multithreaded_Row_Aggregation_Main mra = new Multithreaded_Row_Aggregation_Main();	
	String host =  args[0];     // Host is localhost as the Database is located in the same machine
	String username = args[1];  // root - Username of the Database account
	String password = args[2];  // Password of the Database account
	String database = "mra"; // Database name - I have created a Database named mra
	ExecutorService executor = Executors.newFixedThreadPool(200);    // Created a ThreadPool of 200 threads.  Advantage of using ThreadPool is that, same threads can be re-used once its task is complete
	con = mra.getConnection(host,username,password,database);  
	while(Query_Logic.counter%100 == 0)  // Each Thread operates on 100 records.
	{
	Runnable worker = new Query_Logic(con); 
	executor.execute(worker);      // Enables the execution of thread to perform the required function
	}
	executor.shutdown();	 // Once all the Threads finishes its task, ThreadPool is closed	
	}		
	private Connection getConnection(String localhost, String username, String password, String database) {
		// TODO Auto-generated method stub
		return MySQL_Singleton.getInstance().MySQL_Connect(localhost, username, password, database);		// Calls the Singleton class for establishing Database connection
	}
}