import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleworkflow.flow.annotations.Wait;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;


public class SQS_service {
	public static String QueueLength = null;    


       
        AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
        try {                       
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(Queuename);
	        queueurl = sqs.createQueue(createQueueRequest).getQueueUrl();
	        QueueLength = queueurl;
            sqs.sendMessage(new SendMessageRequest(queueurl, data.toString()));       
            GetQueueAttributesRequest request = new GetQueueAttributesRequest();
            String tasks = process_queue();
	        request = request.withAttributeNames("Running tasks");
	        Map<String, String> attrs = sqs.getQueueAttributes(request).getAttributes();	      
	         request = request.withQueueUrl(queueurl);                        
                     
            } 
            catch(Exception e)
            {

            }
		   
	public static String process_queue() {
		Queue createQueue = new Queue();

        System.out.println("Elements inserted into Queue");
        ConcurrentHashMap<String, Integer> message = new ConcurrentHashMap<String, Integer>();

       
        createQueue.insert(message);
                
        createQueue.display();
                   
       
        demo.processElement();


	  AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
	  CreateQueueRequest createQueueRequest = new CreateQueueRequest("sqsqueue");
	  String  queueurl = sqs.createQueue(createQueueRequest).getQueueUrl();
	  String sqsqueue = null;		 
      List<Message> msgs = sqs.receiveMessage(
	  new ReceiveMessageRequest(queueurl).withMaxNumberOfMessages(1)).getMessages();
	  sqs = this.push(msgs, "sqsqueue");
			  System.out.println(msgs.size());
			  
			   Message message = msgs.get(0);			   
			   sqsqueue = message.getBody();
			   if(sqsqueue>QueueLength)
			   {
			   	return sqsqueue;
			   }
			    sqs.deleteMessage(new DeleteMessageRequest(queueurl, message.getReceiptHandle()));
		CreateQueueRequest createQueueRequest1 = new CreateQueueRequest("clientacknowledgementQueue");	 
		sqs = this.push(msgs, "clientacknowledgementQueue")
		String  queueurl = sqs.createQueue(createQueueRequest).getQueueUrl();
		List<Message> msgs = sqs.receiveMessage(
			     new ReceiveMessageRequest(queueurl)).getMessages();
			  System.out.println("Result Queue Size "+msgs.size());
			  int size = msgs.size();  
			  if(size < 0) 
			  {				
				System.out.println("Message empty")				 
			  } 
			  clientacknowledgementQueue=message.getBody(); 
			  if(clientacknowledgementQueue>QueueLength)
			  {
			  	return clientacknowledgementQueue;
			  }
		  
			   		  	
	}		
	return sqs; 
	}

	