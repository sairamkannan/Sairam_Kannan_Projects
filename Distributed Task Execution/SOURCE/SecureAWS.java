

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.*;

public class SecureAWS {
	public static void main(String[] args) {
	
		AmazonEC2 ec2 = new AmazonEC2Client(new ClasspathPropertiesFileCredentialsProvider());
		Region useast2 = Region.getRegion(Regions.US_EAST_2);
		ec2.setRegion(useast2);

	   
	    	try {
	        	CreateSecurityGroupRequest securityGroupRequest = new CreateSecurityGroupRequest("Group started", "Security grap started");
	        	ec2.createSecurityGroup(securityGroupRequest);
	    	} catch (AmazonServiceException ase) {
	    		
	    		System.out.println(ase.getMessage());
	    	}

	    	   	
	    		    	
	    	ArrayList<IpPermission> ipPermissions = new ArrayList<IpPermission> ();
	    	IpPermission ipPermission = new IpPermission();
	    	ipPermission.setIpProtocol("all");
	    	
	    	ipPermissions.add(ipPermission);

	    	try {
		    	
		    	AuthorizeSecurityGroupIngressRequest ingressRequest = new AuthorizeSecurityGroupIngressRequest("GettingStartedGroup",ipPermissions);
		    	ec2.authorizeSecurityGroupIngress(ingressRequest);
	    	} catch (AmazonServiceException ase) {
	    		
	    		
	    	}
	}

}
