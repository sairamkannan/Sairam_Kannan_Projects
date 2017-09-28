

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    
    private Socket socket = null;
    private PeerNode peer;

    public ServerThread(Socket socket, PeerNode peer) {
		super("ServerThread");
		this.socket = socket;
		this.peer = peer;
    }
    public static String processInput(String s){
    	return s;
    }
    
    
    public static void log(String text){
  
    	try {
  	
    		PrintStream out = new PrintStream(new FileOutputStream("dhtmessage.p2plog", true)); 
 
			out.println(text);
            
            
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
    }
    
    public void run() {

		try {
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(
					    new InputStreamReader(
					    socket.getInputStream()));
	
		    String inputLine, outputLine;
	
		    log("----------------> peer "+peer.getID()+" server loging <----------------");
		    while ((inputLine = in.readLine()) != null) {
  
				log("[Received -"+" PeerID: "+peer.getID()+" Portnum: "+peer.getPort()+"] "+inputLine);
				outputLine = this.peer.Protocol(inputLine).toString();
				log("[Sent -"+" PeerID: "+peer.getID()+" Portnum: "+peer.getPort()+"]     "+outputLine);

				out.println(outputLine);
				if (outputLine.equals("Bye"))
				    break;
				System.out.println(peer.toString());
		    }
		    out.close();
		    in.close();
		    socket.close();
	
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}
