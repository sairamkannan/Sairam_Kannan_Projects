import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;

public class InstanceLaunch {
	public static void launch(int noAmazonInstances){		
		AmazonEC2Client awsClient = createClient();	
		RunInstancesRequest request = new RunInstancesRequest();
		request.withImageId("ami-4ebf1df6")
						                     .withInstanceType("t2.micro")
						                     .withMinCount(1)
						                     .withMaxCount(noAmazonInstances)
						                     .withKeyName("")
						                     .withSecurityGroups("sqs");
						  
						  RunInstancesResult runInstancesResult =  awsClient.runInstances(req);
						  					  
							
	}
	
	public static void main(String args[]){
				
		AmazonEC2Client ec2 = createClient();
		securityInfo(ec2);
		launch(17);		
		AmazonEC2Client ec2 = createClient();
		
		
            DescribeInstancesResult reserve = ec2.describeInstances();
            Set<Instance> inst = new HashSet<Instance>();
            List<Reservation> res = reserve.getReservations();
            

            for (Reservation reserve : res) {
                inst.addAll(reserve.getInstances());
            }
		
	}	

	private static AmazonEC2Client createClient() {
		
		InputStream credStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("AwsCredentials.properties");
		AWSCredentials creds = null;
		try {
			creds = new PropertiesCredentials(credStream);
			System.out.println(creds.getAWSAccessKeyId());
			System.out.println(creds.getAWSSecretKey());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
				
				
				AmazonEC2Client amazClient=   new AmazonEC2Client(creds);
						amazClient.setEndpoint("ec2.us-east-1.amazonaws.com");
					
		return amazClient;
	}

	private static void securityInfo(AmazonEC2Client amazonEC2Client) {
		
			CreateSecurityGroupRequest csgr = 
				new CreateSecurityGroupRequest();
			        	
			csgr.withGroupName("")
				.withDescription("sqs security group");		
			
			  CreateSecurityGroupResult csgRes = amazonEC2Client.createSecurityGroup(csgReq);
				  
				  
				  AuthorizeSecurityGroupIngressRequest securityGroupReq =
				  	new AuthorizeSecurityGroupIngressRequest();
				      	
				 

				  amazonEC2Client.authorizeSecurityGroupIngress(securityGroupReq);
				  
	
				CreateKeyPairRequest keyReq = 	new CreateKeyPairRequest();
				keyReq.withKeyName("HI");					
				
				CreateKeyPairResult keyRes = amazonEC2Client.createKeyPair(keyReq);

				KeyPair keyPair = new KeyPair();
				keyPair = keyRes.getKeyPair();
				String pKey = keyPair.getKeyMaterial();
				
	}

	
		}
	
}
