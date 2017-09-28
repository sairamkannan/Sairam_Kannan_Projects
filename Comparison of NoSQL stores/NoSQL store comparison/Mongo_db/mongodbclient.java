

import java.io.*;
import java.util.Properties;
import java.util.Random;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBObject;



public class mongodbclient {

	private static BufferedReader stdin; // stdin-InputReader to read all console inputs
	private static int noOfServers; // Variable which stores the no of servers in the network
	private static MongoClient[] availableMongoClients = null; // List to store all the MongoClients created
	private static DB[] availableMongoClientsDB = null; // List to store all the MongoClients DB created
	private static DBCollection[] availableMongoClientsDBCollection = null; // List to store all the MongoClients collection created
	private static String[] availablePeersIPAddr = null; // List to store all the sockets IP address created
	private static int noOfOpers;
	private static String[][] array = null; // Array to hold all the random numbers
	
	public static void main(String[] args) throws IOException {
		try {
			// Read values from property file
			FileReader readPeerDir = new FileReader("config.properties");
			Properties properties = new Properties();
			properties.load(readPeerDir);

			// It holds the number of the server in the network
			noOfServers = Integer.parseInt(properties.getProperty("noOfServers"));
			
			// It holds the number of the server in the network
			noOfOpers = Integer.parseInt(properties.getProperty("noOfOpers"));
						
			
	}

    private String NextPeerID;
	private String ID;
	private String Port;
	private String Hostname;
	private String NextPeerHostName;
	private String NextPeerPort;
	private String RedirectHostName;
	private String RedirectPort;
	private int MaxId;
	private boolean firstPeer = false;
	private Hashtable<Integer, String> hashtable;
	
	public PeerNode(){
		this.hashtable = new Hashtable<Integer, String>();
	}
	
	public String getID() {
		return ID;
	}

	
	public void setID(String iD) {
		ID = iD;
	}

	
	public String getPort() {
		return Port;
	}

	
	public void setPort(String port) {
		Port = port;
	}


	
	public int getMaxId() {
		return MaxId;
	}

	
	public void setMaxId(int maxId) {
		MaxId = maxId;
	}

	
	public String getNextPeerID() {
		return NextPeerID;
	}

	
	public void setNextPeerID(String nextPeerID) {
		NextPeerID = nextPeerID;
	}

	
	public boolean isFirstPeer() {
		return firstPeer;
	}

	
	public void setFirstPeer(boolean firstPeer) {
		this.firstPeer = firstPeer;
	}

	
	public String getHostname() {
		return Hostname;
	}

	
	public void setHostname(String hostname) {
		Hostname = hostname;
	}
	
	public String getNextPeerHostName() {
		return NextPeerHostName;
	}

	
	public void setNextPeerHostName(String NextPeerHostName) {
		this.NextPeerHostName = NextPeerHostName;
	}

	public String getNextPeerPort() {
		return NextPeerPort;
	}

	
	public void setNextPeerPort(String NextPeerPort) {
		this.NextPeerPort = NextPeerPort;
	}
	
	
	public String getRedirectHostName() {
		return RedirectHostName;
	}

	
	public void setRedirectHostName(String redirectHostName) {
		RedirectHostName = redirectHostName;
	}

	
	public String getRedirectPort() {
		return RedirectPort;
	}

	
	public void setRedirectPort(String redirectPort) {
		RedirectPort = redirectPort;
	}

	
	public Hashtable<Integer, String> getHashtable() {
		return hashtable;
	}

	
	public void setHashtable(Hashtable<Integer, String> hashtable) {
		this.hashtable = hashtable;
	}

	@Override
	public String toString(){
		return "Hostname: "+this.Hostname+" Port: "+this.Port+" ID: "+this.ID+" NextHostname: "+this.NextPeerHostName+" NextPort: "+
			this.NextPeerPort+" NextID: "+this.NextPeerID+" MaxID: "+this.MaxId+" isFirstPeer: "+this.firstPeer+" Hashtable: "+this.getHashtable().toString();
	}

	
	public Request genRequest(String operation, int numOfLines, String peerID){
		if(peerID==null){
			return new Request(operation, Settings.Version, numOfLines);
		}
		return new Request(operation, Settings.Version, numOfLines, peerID);
	}
	public Request genRequest(String message){
		String[] ArrayMessage = message.trim().split("CRLF");
		String version = null;
		String operation = null;
		int numOfLines = 0;
		String peerID = null;
		ArrayList<String> Message = new ArrayList<String>();
		for(String strValue : ArrayMessage){
			if(strValue.toUpperCase().contains(Settings.Version.toUpperCase())){
				String[] wordsInLine = strValue.trim().split("\\s+");
				operation = wordsInLine[0];
				version = wordsInLine[1];
				numOfLines = Integer.parseInt(wordsInLine[2]);
				if(wordsInLine.length==4){
					peerID = wordsInLine[3];	
				}
			}
			else{
				Message.add(strValue);
			}
		}
		if(peerID==null & Message.size()==0){
			return new Request(operation, version, numOfLines);
		}else if(peerID==null & Message.size()>0)
		{
			return new Request(operation, version, numOfLines, Message);
		}
		else
		{
			return new Request(operation, version, numOfLines, peerID, Message);
		}
	}	
	
	public Response genResponse(String message){
		
		String[] ArrayMessage = message.trim().split("CRLF");
		String version = null;
		String operation = null;
		String numOfLines = null;
		String responseCode = null;
		String responseCodestr = null;
		ArrayList<String> responseMessage = new ArrayList<String>();
		for(String strValue : ArrayMessage){
			if(strValue.contains(Settings.Version)){
				String[] wordsInLine = strValue.trim().split("\\s+");
				version = wordsInLine[0];
				operation = wordsInLine[1];
				numOfLines = wordsInLine[2];
				responseCode = wordsInLine[3];
				responseCodestr = wordsInLine[4];
			}
			else{
				responseMessage.add(strValue);
			}
		}
		
		return new Response(version, operation, numOfLines, responseCode, responseCodestr, responseMessage);
	}	
	
	public void ProcessFileInputArgs(String[] ArrayCommand)
	{
		int counter = 0;
		for(String strValue : ArrayCommand)
		{
			if(ArrayCommand.length>counter+1){
				
				if(strValue.trim().toUpperCase().equals("-I")){
					this.setID(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-H") ){
					this.setHostname(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-P")){
					this.setPort(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-M")){
					this.setMaxId(Integer.parseInt(ArrayCommand[counter+1]));
				}
				else if(strValue.trim().toUpperCase().equals("-R")){
					this.setRedirectHostName(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-S")){
					this.setRedirectPort(ArrayCommand[counter+1]);
				}
			}
			else if(strValue.trim().toUpperCase().equals("-F"))
			{
				this.setFirstPeer(true);
			}
			counter ++;
		}
		if(this.firstPeer && !this.Hostname.equals(null) && !this.Port.equals(null)){
			this.setNextPeerHostName(this.Hostname);
			this.setNextPeerPort(this.Port);
			this.NextPeerID = this.ID;
		}
	}
	
	
	public String Protocol(String message){
		if(message.contains("ID"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.IDQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.IDQueryRequestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("NEXT"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.NextQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.NEXTQueryReuqestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("PULL"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.PullQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.PullQueryRequestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("ADD"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.AddQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.AddQueryRequestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("QUERY"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.QueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.QueryRequestProcess(this.genRequest(message)).toString();
			
		}
		else if(message.contains("info")){
			 return this.toString();
		}
		return "";
	}

	
	public int getInt(String s){
		return Integer.parseInt(s);
	}
	
	
	public void redirectClient(String message){
		try {
			this.client(this.getRedirectHostName(), getInt(this.getRedirectPort()), message);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void nextClient(String message){
		try {
			this.client(this.getNextPeerHostName(), getInt(this.getNextPeerPort()), message);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Request IDQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 301:
			
			String[] msg = response.getMessage().get(0).trim().split("\\s+"); 
			this.setRedirectHostName(msg[0]);
			this.setRedirectPort(msg[1]);
			redirectClient(this.genRequest("ID", 0, this.getID()).toString());
			return this.genRequest("ID", 0, this.getID());
		case 200:
			redirectClient(this.genRequest("NEXT", 0, "0").toString());
			return this.genRequest("NEXT", 0, "0");
		default:
			redirectClient(this.genRequest("ID", 0, this.getID()).toString());
			return this.genRequest("ID", 0, this.getID());
		}
	}

	
	public Response IDQueryRequestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		if(getInt(this.getID())<getInt(this.NextPeerID) && getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) > getInt(this.getNextPeerID()))
		{
			responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort());
			return new Response(Settings.Version, "ID", "1", "301", "redirect", responseMessage);
		}
		else if(getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) < getInt(this.getNextPeerID()) || 
				getInt(this.getID())>getInt(this.NextPeerID) && getInt(request.getPeerID()) > getInt(this.getID()))
		{
			return new Response(Settings.Version, "ID", "0", "200", "ok", responseMessage);
		}
		else if(this.getID() == this.NextPeerID)
		{
			if(getInt(this.getID())>getInt(request.getPeerID())){
				return new Response(Settings.Version, "ID", "0", "200", "ok", responseMessage);
			}
			else
			{
				
				return NEXTQueryReuqestProcess(request);
			}
		}
		else if(getInt(request.getPeerID()) == getInt(this.getID())){
			return new Response(Settings.Version, "ID", "0", "400", "peerexist", responseMessage);
		}
		else if(!request.getVersion().trim().equals(Settings.Version.trim())){
			return new Response(Settings.Version, "ID", "0", "401", "versionError", responseMessage);
		}
		else
		{
			return new Response(Settings.Version, "ID", "0", "503", "UnknownCondition", responseMessage);
		}
	}
	
	
	public Response NEXTQueryReuqestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		
		if(this.getHostname().isEmpty()||this.getPort().isEmpty()){
			return new Response(Settings.Version, "NEXT", "0", "501", "NoNextExist", responseMessage);
		}
		responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort()+" "+this.getNextPeerID());
		return new Response(Settings.Version, "NEXT", "1", "200", "ok", responseMessage);
	}
	
	
	public Request NextQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 200:
			String[] msg = response.getMessage().get(0).trim().split("\\s+");
			this.setNextPeerHostName(msg[0]);
			this.setNextPeerPort(msg[1]);
			this.setNextPeerID(msg[2]);
			ArrayList<String> message = new ArrayList<String>();
			message.add(this.getHostname()+" "+this.getPort());
			redirectClient(new Request("PULL", Settings.Version, 1, this.getID(), message).toString());
			return new Request("PULL", Settings.Version, 1, this.getID(), message);
		case 501:
			redirectClient(new Request("NEXT", Settings.Version, 0).toString());
			return new Request("NEXT", Settings.Version, 0);
		}			
		return new Request();
	}
	
	
	public Response PullQueryRequestProcess(Request request){
		String[] msg = request.getMessage().get(0).trim().split("\\s+");
		this.setNextPeerHostName(msg[0]);
		this.setNextPeerPort(msg[1]);
		this.setNextPeerID(request.getPeerID());
		ArrayList<String> responseMessage = new ArrayList<String>();
		responseMessage.add("blah");
		return new Response(Settings.Version, "PULL", "1", "200", "ok", responseMessage);		
	}

	
	public Request PullQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 200:
			redirectClient(new Request("DONE", Settings.Version, 0).toString());
			return new Request("DONE", Settings.Version, 0);
		}
		return new Request();
	}
	
   
    public static int getAscii(String s){
    	int result = 0;
    	for (int i=0; i<s.length();i++)
    		result +=s.charAt(i);
    	return result;
    }
    
    
    public Response AddQueryRequestProcess(Request request){
    	ArrayList<String> responseMessage = new ArrayList<String>();
    	String msg = request.getMessage().get(0).trim();
    	int key = PeerNode.getAscii(msg) % this.getMaxId(); 
    	Hashtable<Integer, String> hashtbl = this.getHashtable();
    	if(hashtbl.containsValue(msg)){
    		return new Response(Settings.Version, "ADD", "0", "202", "duplicate", responseMessage);
    	}
    	else if(getInt(this.getID()) <= key && key<getInt(this.getNextPeerID()) || 
    			getInt(this.getID()) <= key && getInt(this.getID()) >getInt(this.getNextPeerID()) ||
				getInt(this.getNextPeerID()) > key && getInt(this.getID()) >getInt(this.getNextPeerID()))
    	{
    		hashtbl.put(key, msg);
    		return new Response(Settings.Version, "ADD", "0", "200", "ok", responseMessage);
    	}
    	else{
    		responseMessage.add(msg);
    		nextClient(new Request("ADD", Settings.Version, 0,this.getID(),responseMessage).toString());
    		return new Response(Settings.Version, "ADD", "0", "400", "NotResponsible", responseMessage);
    	}
    	
    }
    
    public Request AddQueryResponseProcess(Response response){
		return new Request("DONE", Settings.Version, 0);
    }

   
    public Response QueryRequestProcess(Request request){
    	ArrayList<String> responseMessage = new ArrayList<String>();
    	String msg = request.getMessage().get(0).trim();
    	int key = PeerNode.getAscii(msg) % this.getMaxId();
    	Hashtable<Integer, String> hashtbl = this.getHashtable();
    	if(hashtbl.containsValue(msg)){
    		return new Response(Settings.Version, "QUERY", "0", "200", "OK", responseMessage);
    	}
    	else if(getInt(this.getID()) <= key && key<getInt(this.getNextPeerID()) || 
    			getInt(this.getID()) <= key && getInt(this.getID()) >getInt(this.getNextPeerID()) ||
				getInt(this.getNextPeerID()) > key && getInt(this.getID()) >getInt(this.getNextPeerID()))
    	{
    		return new Response(Settings.Version, "ADD", "0", "201", "NotPresent", responseMessage);
    	}
    	else{
    		responseMessage.add(msg);
    		return new Response(Settings.Version, "ADD", "0", "400", "NotResponsible", responseMessage);
    	}
    	
    }
    
    public Request QueryResponseProcess(Response response){
		return new Request("DONE", Settings.Version, 0);
    }
    
    
    public void client(String hostname, int port, String command) throws IOException {

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
			System.setProperty("java.net.preferIPv4Stack", "true");
            kkSocket = new Socket(hostname, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;

        out.println(command);
        if ((fromServer = in.readLine()) != null) {
	    	
	    	this.Protocol(fromServer);
            System.out.println(this.toString());
        }
        
    }


	/**
	 * The selectActionDHT() function accepts no parameters. It allows user to
	 * select the operations(PUT, GET, DELETE).
	 * 
	 * @param No parameters
	 * @return Nothing
	 * @exception NO
	 * Exceptions thrown
	 * 
	 */

	public static void selectFunction() {

		try {
			String action = "";
			while (true) {
				System.out.println("Hello !! Please select an option\n:");
				System.out.println("1. Put operation Benchmark\n2. Get operation Benchmark\n3. Delete operation Benchmark\n:");
				action = stdin.readLine();
				if ("1".equals(action) || "2".equals(action) || "3".equals(action)) {
					break;
				} else {
					System.out.println("Invalid Input. Please re-enter your choice.");
				}
			}
			switch (action) {

			case "1":
				
				String keyToBeSaved;
				String msgToBeSaved;
				int countOfPut=0;
				long startTimePut=System.nanoTime();			
				while(countOfPut< noOfOpers)
				{
					keyToBeSaved=array[countOfPut][0];
					msgToBeSaved=array[countOfPut][1];
					int indexOfServerPut = generateHashKey(keyToBeSaved);
					BasicDBObject dbObjectPut = new BasicDBObject("_id",keyToBeSaved).append(keyToBeSaved, msgToBeSaved);
					availableMongoClientsDBCollection[indexOfServerPut].insert(dbObjectPut);				
					dbObjectPut=null;
					countOfPut++;
				}				
				
				long endTimePut=System.nanoTime();
				long elapsedTimePut=(endTimePut-startTimePut);
				System.out.println("Elapsed Time for "+noOfOpers+" PUT in ns->"+elapsedTimePut);
				selectActionDHT();
				break;
			case "2":
				
				String keyToBeRetrieved;
				int countOfGet=0;
				long startTimeGet=System.nanoTime();
				
				while(countOfGet< noOfOpers)
				{	
					keyToBeRetrieved=array[countOfGet][0];					
					int indexOfServerGet = generateHashKey(keyToBeRetrieved);					
					BasicDBObject field = new BasicDBObject();
					field.put("_id", keyToBeRetrieved);
					DBCursor cursor = availableMongoClientsDBCollection[indexOfServerGet].find(field);
					while (cursor.hasNext()) {
					    BasicDBObject obj = (BasicDBObject) cursor.next();
					    //System.out.println(obj.getString(keyToBeRetrieved));						    
					}
					field=null;
					countOfGet++;
				}
				long endTimeGet=System.nanoTime();
				long elapsedTimeGet=(endTimeGet-startTimeGet);
				System.out.println("Elapsed Time for "+noOfOpers+" GET in ns->"+elapsedTimeGet);				
				selectActionDHT();
				break;
			case "3":
				
				String keyToBeDeleted;
				int countOfDel=0;
				long startTimeDel=System.nanoTime();
				while(countOfDel< noOfOpers)
				{	
					keyToBeDeleted=array[countOfDel][0];
					int indexOfServerDel = generateHashKey(keyToBeDeleted);
					BasicDBObject field = new BasicDBObject();
					field.put("_id", keyToBeDeleted);
					availableMongoClientsDBCollection[indexOfServerDel].remove(field);
					field=null;
					countOfDel++;
				}
				long endTimeDel=System.nanoTime();
				long elapsedTimeDel=(endTimeDel-startTimeDel);
				System.out.println("Elapsed Time for "+noOfOpers+" DELETE in ns->"+elapsedTimeDel);				
				selectActionDHT();
				break;
			default:
				System.out.println("Incorrect command received.");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Not a valid input");
			selectActionDHT();
		}
	}

	/**
	 * The generateHashKey() function accepts key name as a parameter. It
	 * generates hash value for each key entered. And then multipied by prime
	 * number. Modulus of the obtained hash value with total no of server is
	 * done to identify the server to add the key.
	 * 
	 * @param String,
	 *            Key name to be stored in server.
	 * @return integer, server number
	 */
	public static int generateHashKey(String keyEntered) {

		int hashValueOfServer = 0;		
		hashValueOfServer = keyEntered.hashCode()*11;
		hashValueOfServer %= noOfServers;		
		if(hashValueOfServer<0){
			hashValueOfServer += noOfServers;
		}
		return hashValueOfServer;
	}
}
