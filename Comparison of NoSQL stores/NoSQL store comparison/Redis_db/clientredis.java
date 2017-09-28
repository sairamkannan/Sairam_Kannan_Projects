


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;

import redis.clients.jedis.Jedis;



public class clientredis {

	
	 
	public static String choice;
	public static String[] servers = new String[8];
	private static Properties prop;
	private static InputStream inpu = null;
	// private static Socket sock, sock1;
	private static String key, value;
	// private static InputStreamReader stdin,in;
	private static BufferedReader stdin;
	private static Socket sockets[] = new Socket[8];
	private static boolean success;
	private static int flag = 0;
	/*static Session[] dbSession = new Session[8];
	static Database[] db = new Database[8];*/
	private static Jedis[] jedis =new Jedis [8];
	private static int noOfOpers = 100000;
	private static String[][] array = new String[noOfOpers][2]; // Array to hold
																// all the
																// random
																// numbers

	public static void main(String[] args) {
		// new peer1server();
		String dbName = "redisdb";
		stdin = new BufferedReader(new InputStreamReader(System.in));
		prop = new Properties();
		File f = new File("Config.properties");
		try {
			inpu = new FileInputStream(f);
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
		try {
			prop.load(inpu);
			int i = 0;
			while (i <= 7) {
				servers[i] = prop.getProperty("peer" + (i + 1));
				//sockets[i] = null;
				i++;
			}
			// populate array to generate random key

			for (i = 0; i < noOfOpers; i++) {
				for (int j = 0; j < 2; j++) {
					char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
					StringBuilder sb = new StringBuilder();
					Random random = new Random();
					if (j == 0) {
						for (int z = 0; z < 10; z++) {
							char k = chars[random.nextInt(chars.length)];
							sb.append(k);
						}
						String key = sb.toString();
						array[i][j] = key;
					}
					if (j == 1)
						for (int z = 0; z < 90; z++) {
							char k = chars[random.nextInt(chars.length)];
							sb.append(k);
						}
					String value = sb.toString();
					array[i][j] = value;
				}
			}

			// }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
		
				
						success = put();

						break;
					
						val = get();

						break;
					
						success = del();

						break;
						/*int Totalservers = Integer.parseInt(prop.getProperty("Number_Servers"));
						for (int num = 0; num < Totalservers; num++) {
							//dbSession[num].deleteDatabase(dbName);
						}*/
						System.exit(0);
					default:
						System.out.println("Default");
						break;
					}
			
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (inpu != null) {
					try {/*int Totalservers = Integer.parseInt(prop.getProperty("Number_Servers"));
					for (int num = 0; num < Totalservers; num++) {
						dbSession[num].deleteDatabase(dbName);
						
					}*/

						
						inpu.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public static int hashfunction(String key) {
		int len = key.length();
		long sum = 0;
		while (len > 0) {
			sum += (int) key.charAt((len - 1));
			len--;
		}
		// 19 is prime number
		sum = (sum * 19);
		int server = (int) (sum % 8);
		return 0;
	}

	public static boolean put() {
		try {
			long startTime = System.currentTimeMillis();
			for(int i=0;i< noOfOpers;i++)
			{
				int num=hashfunction(array[i][0]);
				/*Document doc = new Document();
				doc.setId(array[i][0]);
				//doc.put("Key", key);
				doc.put("Value", array[i][1]);
				db[num].saveDocument(doc);*/
				jedis[num].set(array[i][0],array[i][1]);
				
			}			
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken to put " + noOfOpers + " operations on Redis Server :"
					+ (endTime - startTime) + " milliseconds\n");
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String get() {
		try {
			long startTime = System.currentTimeMillis();
			for(int i=0;i< noOfOpers;i++)
			{
				int num=hashfunction(array[i][0]);
				//Document d = db[num].getDocument(array[i][0]);
				if ( jedis[num].exists(array[i][0])){
					jedis[num].get(array[i][0]);
					}
				
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken to GET " + noOfOpers + " operations on Redis Server :"
					+ (endTime - startTime) + " milliseconds\n");
			String value = "";

			
			//System.out.println(d);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean del() {
		try {
			long startTime = System.currentTimeMillis();
			for(int i=0;i< noOfOpers;i++)
			{
				int num=hashfunction(array[i][0]);
				//db[num].deleteDocument(db[num].getDocument(array[i][0]));
				if ( jedis[num].exists(array[i][0])){
					jedis[num].del(array[i][0]);
				
					}
				
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Total time taken to put " + noOfOpers + " operations on Redis Server :"
					+ (endTime - startTime) + " milliseconds\n");
			//Document d = db[num].getDocument(key);

			// System.out.println("Document 1: " + d);
			
			
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
