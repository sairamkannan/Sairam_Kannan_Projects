import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class RemoteBackendWorkers {

	public static void main(String args[]){
	  
	  String message=Client_AWS.message;
	  String Qname=Client_AWS.get(args[2]);
	   	
	   Iterator<String> i = tasks.iterator();
				while(i.hasNext()){
					String a = i.next();
					String work = a.substring(0,4);
					
					int val = Integer.parseInt(work.split("");			 			   		
					ConcurrentHashMap<String, Integer> workload =  new ConcurrentHashMap<String, Integer>();
			   		workload.put(work, val);			   			
			   		DynamoDB d= new DynamoDB();			    	
				    	
					d.addsleeptask(Qname,workload);
				}
				else{
					d.removesleeptask(Qname,workload);					
					}
				
		List<String> tasklist = new ArrayList<String>();
		while(message != null)
			{	
				String tsk = work "+" value "\n";
		    	
		    	if(!tsk.equals(""))
		    		tasklist.add(tsk);
		    	else{
		    		 if(!tasklist.isEmpty()){
		    			 SQS_service.process_queue();
		    		 }
		    		 else{
		    			 System.out.println("Task cannot be processed");
						
		    		 }
		    	}
		 
		     } 
    		    
			
       
   		}
 
	
}
