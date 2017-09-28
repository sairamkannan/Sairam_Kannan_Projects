import java.io.*;
import java.util.*;
public class sharedmemory extends Thread    // Thread class is extended for multithreading
{
Set set;
static int lines = 0;  // Line counter to keep track of the lines
String file = "/home/ubuntu/sharedmemory/10gbinput.txt";  //input file to be sorted
String output_file = "/home/ubuntu/sharedmemory/10gboutput.txt";  //output sorted file
BufferedWriter out = new BufferedWriter(new FileWriter(output_file));
public void run()                                                      
{
	//once the thread is started, the run method is called.
TreeMap<String,String> sort = new TreeMap<String,String>(new my_sort());  // Treemap using comparator interface for calling the my_sort function
try (BufferedReader br = new BufferedReader(new FileReader(file)))   // input data is read from the file.
{

	long size = file.length()/(1024*1024);       //Size of the input data in GB
	long total_blocks=100;                       //Size of the total chunks, so that data fits into the memory.
	static long size_of_blocks = (size / total_blocks);  // Size of the file in each chunk
	String line,key,value;
	while((line=br.readLine())!=null)
		{
          if(lines<=size_of_blocks)
             {
	  			
	  			sort.put(key,value);             // Storing the associated key value pair of each chunk
	    		lines++;                       
             }
             size_of_blocks = size_of_blocks + lines;  // Size of the block is used as a offset for each chunk
       }
       
}
catch(Exception e)
{
	System.out.println("File not Found");
}

 set = sort.entrySet();             // Get the sorted data of the entire chunk 
Iterator i = set.iterator();        // Iterate over the chunks

while(i.hasNext())
	{
		Map.Entry me = (Map.Entry)i.next();  
		out.write(me.getKey() + " ");         // Write the sorted key with its value and append to the output file
		out.write(me.getValue());
        out.write("\n");
		}
	sort.clear();         // Clearing the object.
}	
public static void main(String args[]) throws IOException
	{
		private Thread t1,t2,t3,t4,t5,t6,t7,t8;	    // 8 threads are created, you can define your own number of threads to do the sorting.
		t1 = new Thread(this);
		t2 = new Thread(this);
		t3 = new Thread(this);
		t4 = new Thread(this);
		t5 = new Thread(this);
		t6 = new Thread(this);
		t7 = new Thread(this);
		t8 = new Thread(this);
		t1.start();   // Thread is started
		t1.join();    // Threads are joined 
		t2.start();
		t2.join();
		t3.start();
		t3.join();
		t4.start();
		t4.join();
		t5.start();
		t5.join();
		t6.start();
		t6.join();
		t7.start();
		t7.join();
		t8.start();
		t8.join();
	}
	}
public class my_sort implements Comparator   //My_sorting functionality based on ascii values.
{
	public String compare(String,String)   
	{
		long startTime = System.currentTimeMillis();  // start the timer
				while((line=br.readLine())!=null)
				{
				key = line.substring(1,10);			// 	Getting the key of each line
	  			value = line.substring(11);         // Getting the value of each line
	  			 int ascii_key1 = (int)key;
	  			   while((line=br.readLine))!=null)
					{
						key1 = line.substring(1,10);
						int ascii_key2 = (int)key1
						if(ascii_key1>ascii_key2)    // comparing the ascii of the keys in the chunk
						{
							return(key);
						}
						else
						{
							return(key1);
						}	
					}
				}
	  	long endTime = System.currentTimeMillis();  //end the timer
	  	long sorting_time = endTime-startTime;
	  	System.out.println("Total Execution time:" + sorting_time + "in milliseconds");	//calculate the sorting time
	}
}