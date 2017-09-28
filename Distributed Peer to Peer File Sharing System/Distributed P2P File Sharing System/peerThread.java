import java.io.*;
import java.net.*;
import java.util.*;

public class peerThread implements Runnable{

  private Socket socket;
  private String filename, lookupname;
  int reqport;
  private String ipaddr;
  private BufferedReader in;
  private PrintWriter out;
  private volatile boolean isRunning = true;
  

  private Thread t;

  private Map<String, String> filenameip = new LinkedHashMap<String, String>();

/* Indexserver calls the peerthread */    
  public peerThread(Socket socket) {
  
  try {

  this.socket = socket;
  this.filenameip = IndexServer.getpeerInfo();
  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  out = new PrintWriter(socket.getOutputStream(), true);

  t = new Thread(this);
  t.start();  /* Starts the run method of the thread */
  }  catch (IOException e) {

  System.out.println(e); 
} 
  }
    public void run() { 
          try {

          while (isRunning) {
            if (!in.ready())
              continue;

            
            filename = in.readLine(); /* Reads the file name from the peer that it wants to register*/
            lookupname = in.readLine(); /* Reads the filename that it wants to search */

            ipaddr = socket.getRemoteSocketAddress().toString().split(":")[1];
            
  /* Reads the portnumber of the peer that it connects to the server */           

           
            filenameip.put(filename,ipaddr); /* Adding filename and portname to the hashmap. Since the peers are running at localhost the ip is same.*/

            System.out.println(filenameip); /* Printing list of registered files with its ports from different peers*/

            

/* Matches the lookup filename with the registered file name and return its ports*/          
            for (String key1 : filenameip.keySet()) {
              if (key1.equals(lookupname)) {
                String var = filenameip.get(key1);
                reqport = Integer.parseInt(var);

                out.println ("The requested file found at localhost and can be accessed via port ");
                out.println(reqport);
              }
              
              }
        
         break;
          }

          out.close();
          in.close();
          socket.close();

          } catch (IOException e) {
          System.out.println(e);
          }
      }
}


           
          
          



