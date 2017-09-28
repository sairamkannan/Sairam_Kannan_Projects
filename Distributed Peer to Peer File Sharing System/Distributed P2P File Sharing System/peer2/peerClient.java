import java.io.*;
import java.net.*;
import java.util.*;

public class peerClient {

private String serverAddress;
StringBuilder sb = new StringBuilder();
private Socket socket;
private BufferedReader in;
private PrintWriter out;
private int port = 4233;
String t="";
public peerClient() throws IOException
{
connectServer();
fileclient();


}
public void fileserver() throws IOException {

ServerSocket servsock = new ServerSocket(4661);
    File myFile = new File("/Assignment3/peer2/c.txt");
    while (true) 
{
      Socket sock = servsock.accept();
      byte[] mybytearray = new byte[(int) myFile.length()];
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
      bis.read(mybytearray, 0, mybytearray.length);
      OutputStream os = sock.getOutputStream();
      os.write(mybytearray, 0, mybytearray.length);
      os.flush();
      sock.close();
      System.exit(0);
      
}}



public void fileclient() throws IOException {
long t1 = System.currentTimeMillis();
 Socket sock = new Socket("127.0.0.1", 6019);
    byte[] mybytearray = new byte[1024];
    InputStream is = sock.getInputStream();
    FileOutputStream fos = new FileOutputStream("/Assignment3/peer2/a-downloaded.txt");
    
    
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
    
    
    System.out.println("Files Received from the peer");
     long t3 = System.currentTimeMillis() - t1 ;
     
    
    bos.write(mybytearray, 0, bytesRead);
  
    System.out.println("Time taken to download " +  t3 + " milliseconds" );
     System.out.println("You can view the downloaded file at /javapro1/peer2 location");
    
    bos.close();

    sock.close();
    fileserver();
   

}
public void regfile()
{
ArrayList results = new ArrayList<String>();

File[] files = new File("/Assignment3/peer2").listFiles();
for (File file : files) {
    if (file.isFile()) {
        results.add(file.getName());
        
    }
   
   
}
System.out.println(results); 
}


public void connectServer()
{
try
   {
serverAddress = "127.0.1.1";
System.out.println(" Trying to connect with server. " + serverAddress);

socket = new Socket(serverAddress, port);
in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
out = new PrintWriter(socket.getOutputStream(), true);
 
regfile();
 System.out.println("Enter the  filename to be registered from the above list");
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  String filename = br.readLine();

  

System.out.println("Enter the filename to be searched");
  
  String lookupname = br.readLine();
  
  
  out.println(filename);
  out.println(lookupname);
  while((t=in.readLine())!=null)
 {
  sb.append(t);
  
  }
  System.out.println(sb);
  
}
catch (IOException e)
         {
            System.out.println("IO Exception:\n" + e);
            
            return;
         }
}

public static void main(String args[]) throws IOException {
 new peerClient();
}
}










