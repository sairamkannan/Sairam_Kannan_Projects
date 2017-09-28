
import java.util.ArrayList;
public class Request {
	private String operation;
	private String version;
	private int numOfLines;
	private String peerID;
	private ArrayList<String> message;
	
	public Request() {
		
	}
	public Request(String operation, String version, int numOfLines) {
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.message = new ArrayList<String>();
	}	
	public Request(String operation, String version, int numOfLines, String peerID){
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.peerID = peerID;
		this.message = new ArrayList<String>();
	}
	public Request(String operation, String version, int numOfLines, ArrayList<String> responseMessage){
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.message = responseMessage;
	}
	public Request(String operation, String version, int numOfLines, String peerID, ArrayList<String> responseMessage){
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.peerID = peerID;
		this.message = responseMessage;
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	public String getVersion() {
		return version;
	}

	
	public void setVersion(String version) {
		this.version = version;
	}

	
	public int getNumOfLines() {
		return numOfLines;
	}

	
	public void setNumOfLines(int numOfLines) {
		this.numOfLines = numOfLines;
	}

	public String getPeerID() {
		return peerID;
	}

	
	public void setPeerID(String peerID) {
		this.peerID = peerID;
	}

	
	public ArrayList<String> getMessage() {
		return message;
	}
	
	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}
	public String toString()
	{
		if(message.size()>0)
		{
			String msgOutput = "";
			for(String str : message){
				msgOutput +=str+"CRLF"; 
			}
			if(this.getPeerID()!=null)
				return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+" "+this.getPeerID()+"CRLF"
						+msgOutput;
			else
				return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+"CRLF"
				+msgOutput;
		}
		else if(this.getPeerID()==null)
		{
			return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+"CRLF";
		}
		return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+" "+this.getPeerID()+"CRLF";
	}
}
