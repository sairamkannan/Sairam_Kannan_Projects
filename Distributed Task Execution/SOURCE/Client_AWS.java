import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client_AWS extends Thread
{
	public static void main(String[] args)
	{
		try
		{		
			int no_of_threads = LocalBackendWorkers.no_of_threads;		
			Thread t = new Thread(no_of_threads);   	
			String Qname=Client_AWS.get(args[2]);
	        File f = new File(Client_AWS.get(args[4]))                             
	              
	 	    BufferedReader bufferedreader = new BufferedReader(new FileReader(f));
	        
	 	    String message;
	        message = bufferedreader.readLine();
	        t.start();

	        public void run() 
	        {
	        while(message != null){
	       			        
	       			     message = buff_file.readLine();
	       			     SQS_service.process_queue();
	       			     SQS_service.insert(message);
	        } 
	    }
	        
	       
	                
		} 
		catch (Exception ex) 
		{
			System.out.println(ex);
		}
	}
}
