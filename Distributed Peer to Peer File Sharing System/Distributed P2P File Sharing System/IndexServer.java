import java.io.*;
import java.util.*;
import java.net.*;
import static java.lang.System.out;



public class IndexServer {



private static LinkedHashMap<String, String> filenameip = new LinkedHashMap<String, String>();

private ServerSocket serverSocket;
public IndexServer() 
{
indexServer();
}

public void indexServer() 
{
int portnumber = 4233;

   try {
       
   
       serverSocket = new ServerSocket(portnumber, 0, InetAddress.getLocalHost());
	
System.out.println(serverSocket.getInetAddress()); 
System.out.println(serverSocket.getInetAddress().getHostName() + ":" +serverSocket.getLocalPort());

while(true) 
{
Socket socket = serverSocket.accept();
new peerThread(socket);   
}}
catch (IOException e) {
            System.out.println("IO Exception:" + e);
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception:" + e);
            System.exit(1);
        }
    }
public static LinkedHashMap<String, String> getpeerInfo() {
   return filenameip;
}


public static void main(String args[])  {

new IndexServer();
}}



        
