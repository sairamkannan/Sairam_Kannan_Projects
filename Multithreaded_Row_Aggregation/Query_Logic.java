package Multithreaded_Row_Aggregation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
public class Query_Logic implements Runnable{
	public static volatile int counter = 0;  // Used a counter which is volatile, so that each thread operates on the subset of records based on the latest counter value
	private static Connection con;
	// ConcurrentHashMap is ThreadSafe and also I used AtomicInteger data-type for storing the count, so that each increment is an atomic operation and ThreadSafe 
	ConcurrentHashMap <Double, AtomicInteger> hm = new ConcurrentHashMap<Double, AtomicInteger>();     
	public Query_Logic() {}
	public Query_Logic(Connection con)
	{		
		this.con = con;
	}		
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		// TODO Auto-generated method stub
		String query = "select price from rowaggregateexercise";  // Optimized SQL query for getting the price for each record from the rowaggregateexercise table
	    Statement st;                                                                   // For the purpose of this assignment, Querying on other fields is unnecessary
	    ResultSet rs;
		try
		{
		st = con.createStatement();
        rs = st.executeQuery(query);          // Execute the query
		while (rs.next())
	      {			
			counter++;                                 // Increment the counter, when each record is accessed from the given table
	        Double price = rs.getDouble("price");   // Gets the price for each order
	  	   if(hm.containsKey(price))   // If the price is found before, safely increment the count for that price
			{
	  		   AtomicInteger value = hm.get(price);
	  		   value.incrementAndGet();                           
			   hm.putIfAbsent(price, value);   // Adds the incremented count value to the required order price
			}
			else
				hm.putIfAbsent(price, new AtomicInteger(1));     // If the price is found for the first time, Count of "1" is added to the required price
	  	   		}
	  	    Set<Entry<Double, AtomicInteger>> entrySet = hm.entrySet();              // Iterates over the ConcurrentHashMap to print the order price and its count in the standard output
			Iterator<Map.Entry<Double, AtomicInteger>> itr = entrySet.iterator(); 
			System.out.println("Price    Count");
			while (itr.hasNext()) { 
				Entry<Double, AtomicInteger> entry = itr.next(); 
				Double key = entry.getKey(); 
				AtomicInteger value = entry.getValue(); 
				System.out.println(key +"             "+ value); 
				}	
	  	   	    }
		catch(Exception e)
		{
		e.printStackTrace();
		}			
		 }	
}