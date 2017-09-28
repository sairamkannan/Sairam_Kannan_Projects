clear
sudo apt-get update
echo "installing pssh"
sudo apt-get install pssh
echo "Parallely starting instances"
pssh -l ubuntu -h Amazonprivateiplist.txt -x -oStrictHostKeyChecking=no -i <YOUR_KEY>.pem uptime
pssh -l ubuntu -i <YOUR_KEY>.pem -h Amazonpublicdnslist.txt sudo hostname <Amazonpublicdnslist.txt>
echo "Adding private ip / public dns pair to the hosts file"
pssh -l ubuntu -i <YOUR_KEY>.pem -h Amazonpublicdnslist.txt sudo vim /etc/hosts <Amazonprivateiplist.txt> <Amazonpublicdnslist.txt> -c :wq
echo "copying pem keys to every nodes"
parallel-scp -i <YOUR_KEY>.pem -l ubuntu -h Amazonpublicdnslist.txt
echo "changing key authorization modes and agents for handling ssh-keys"
pssh -l ubuntu -i <YOUR_KEY>.pem -h Amazonpublicdnslist.txt chmod 644 authorized_keys 
pssh -l ubuntu -i <YOUR_KEY>.pem -h Amazonpublicdnslist.txt chmod 400 <YOUR_KEY>.pem
pssh -l ubuntu -i <YOUR_KEY>.pem -h Amazonpublicdnslist.txt eval `ssh-agent`
echo "Adding pem file to the ssh group"
pssh -l ubuntu -i <YOUR_KEY>.pem -h Amazonpublicdnslist.txt ssh-add hadoopec2cluster.pem
CONF_PATH="/home/ubuntu/hadoop/conf"
echo "copying the configuration files"
parallel-scp -i <YOUR_KEY>.pem -l ubuntu -h Amazonpublicdnslist.txt $CONF_PATH/ hadoop-env.sh core-site.xml hdfs-site.xml mapred-site.xml ubuntu@<Amazonpublicdnslist.txt>:/home/ubuntu/hadoop/conf
echo "copy slave ip's from the list to each of the slaves"
parallel-scp -i <YOUR_KEY>.pem -l ubuntu -h Amazonpublicdnslist.txt $CONF_PATH/ slaves ubuntu@<Amazonpublicdnslist.txt>:/home/ubuntu/hadoop/conf
echo "formatting namenode"
hadoop namenode -format
echo "starting all the services"
start-all.sh
echo "The following services are active"
jps
echo "generating 10GB of data / similarly generate date for 100GB"
hadoop jar sairam-hadoop-terasort.jar teragen 100000000 /tmp/terasort-hadoop-10-input
echo "sorting using my terasort application"
hadoop jar sairam-hadoop-terasort.jar terasort /tmp/terasort-hadoop-10gb-input /tmp/terasort-hadoop-10gb-output
echo "validating the sorted data"
hadoop jar sairam-hadoop-terasort.jar teravalidate /tmp/terasort-hadoop-10gb-output /tmp/terasort-hadoop-10gb-validate
echo "stopping all the services"
stop-all.sh
exit 1






