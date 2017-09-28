Readme.txt

I have attached all the screenshots of my Terasort application running in Hadoop/Spark clusters and Shared Memory which will be available in the screenshots folder. The report containing the performance evaluation will be in the prog2_report.pdf. The output files (head and tail of sorted data) will be stored in the output folder. The configuration files will be inside the configuration folder. I have included the shared memory source code, which will be available inside the shared-memory folder.

Hadoop:

1. First create a AWS spot instance of type c3.large using your Pem key file.

2. Launch the instance (For 10gb sort - create with atleast 60gb of EBS volume and for 100gb sort - create with atleast 300gb of EBS volume)

3. Run the hadoopinstall.sh script (Details of the script are given in echo commands inside the script) to install hadoop into your local system.

4. Go to your home directory and set the environmental variables which sets the path for your Hadoop home directory, Hadoop configuration, binary files and Java home directory

	1) vi .bashrc
	2) export HADOOP_CONF=/home/ubuntu/hadoop/conf
	   export HADOOP_PREFIX=/home/ubuntu/hadoop
	   export JAVA_HOME=/usr/lib/jvm/java-7-oracle
	   export PATH=$PATH:$HADOOP_PREFIX/bin
	3) Save the bashrc file using source ~/.bashrc 


5. Edit the hosts file using the command vi /etc/hosts and enter the <private ip> <public dns of namenode>

# I have made the single node cluster as two nodes (One acting as Namenode and other as Datanode) and multinode cluster as 17 nodes (One acting as Namenode and other 16 nodes acting as Datanodes)

6. Now create an image and start the instances ( 2 instances for single node and 17 instances for multinode)

7.Go to the master node instance and enter the Namenode Public IP in HADOOP_CONF/master of the master and all the Datanode(slave) IP in HADOOP_CONF/slaves

8. change the hostname of the Namenode using sudo hostname <Amazon public dns of namenode>

9. Now Execute the script hadoopcluster.sh (Details of the script are given in echo commands inside the script), so that the hadoop cluster is started and the output of my terasort program will be stored in HDFS.

# I have used the inbuilt teragen program for generating 10 gb and 100gb of data, so that copying files from local to hdfs can be avoided. It saves a lot of time.

# For single node, I will give only one slave node's Amazon public DNS in the slave configuration file of the Namenode and for Multinode, I will give the Amazon public DNS of 16 slave nodes in the slave configuration file of the Namenode. For Slave nodes, the (HADOOP_CONF/slaves) slave configuration file will have its own Amazon public dns and the master configuration file will be blank. Also each node will have its own <private_ip> <public_ip> in the /etc/hosts file, such that it can be easily able to identify to other nodes.

Since the private_ip's and public_dns are stored as as list in a text file, the parallel ssh script will take these data in creating a cluster.



Spark:

1. First create an AWS instance of type c3.large using your pem key file.
 
2. Launch the instance (For 10gb sort - create with atleast 60gb of EBS volume and for 100gb sort - create with atleast 300gb of EBS volume)

3. Run the sparkinstallation.sh to install scala and spark.

4. Set the environmental variables appropriately for directing the path to binary directory folder for both scala and spark.

5. For spark, I have used gensort for generating data. Store the gensort generating program in the instance and generate the data using the command ./gensort -a 100000000 /input-directory (10GB)

6. For single node cluster, go to the spark directory where the spark-submit shell file is there and type the command ./spark-submit <sorting code>. Inside the sorting code, specify the input directory from where data needs to be sorted and specify the output directory, from where the sorted data needs to be stored.

7. You can view the sorted output files in the output directory.

8. For multinode clusters, I have used a AMI for spark clusters (with pre-built spark installed) mentioned in the piazza forum, created a c3.large instance from it and executed the sparkcluster.sh script to automatically create 16 worker nodes, which also generated 100GB of data, moved the input file containing 100GB of data from local to HDFS and the sorted outputed is stored in the HDFS. You can view the sorted outputs in the output directory. Spark cluster is easy to create compared to Hadoop. 

Shared Memory:

1. I executed the sharedmemory.sh script to run the sharedmemory program for sorting 10GB of data.

2. 10GB of data is generated from the gensort program ./gensort -a 100000000 /input-directory (10GB). The data is sorted using my shared-memory sorting program and the sorted ouptut is stored in the output directory. 



