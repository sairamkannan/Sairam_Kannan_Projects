import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.*;

public class DynamoDB {
	 AmazonDynamoDBClient dynamoDB;
	
	AWSCredentials creds = null;
    try {
       creds = new ProfileCredentialsProvider("aws").getCredentials();
    } catch (Exception e) {
        throw new AmazonClientException(
               "File not found exception" 
                e);
    }
    dynamoDB = new AmazonDynamoDBClient(creds);
    Region useast2 = Region.getRegion(Regions.US_EAST_2);
    dynamoDB.setRegion(useast2);
    
        String tab = "duplicatechecktable";

       
        if (Tables.doesTableExist(dynamoDB, tab)) {
            System.out.println("Active Table " + tab + " plz try different one");
        } else {

            HashMap<String, Condition> scn = new HashMap<String, Condition>();
        	CreateTableRequest tablerequest = new CreateTableRequest().withTableName(tab)
                    .withKeySchema(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("name").withAttributeType(ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
                    TableDescription tabDesc = dynamoDB.createTable(tablerequest).getTableDescription();
                    Tables.waitForTableToBecomeActive(dynamoDB, tab);    
                Region useast2 = Region.getRegion(Regions.US_east_2);
        dynamoDB.setRegion(useast2);

            AddItemRequest addtask = new AddItemRequest(add,RemoteBackendWorker.taskList); 
            DynamoDB.addItem(SQS_service.message);          
            DeleteItemRequest delReq = new DeleteItemRequest(tab, item);
            String tab = "duplicatechecktable";
            
            Condition cond = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(m));
            scn.put("value", cond);
            ScanRequest scnReq = new ScanRequest(tab).withScanFilter(scn);
            ScanResult scnRes = dynamoDB.scan(scnReq);
            if(message > 1)
            {
           
            dynamoDB.deleteItem(delReq);
        }
        }


        
        
        
        
        
        
        
        
        
        
        
        
       
        
      