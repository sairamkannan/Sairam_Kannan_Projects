clear
sudo apt-get update
echo "Installing Java"
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update && sudo apt-get install oracle-jdk7-installer
sudo apt-get update
echo "Installing hadoop"
wget http://apache.mirror.gtcomm.net/hadoop/common/hadoop-1.2.1/hadoop-1.2.1.tar.gzsudo rm /var/lib/dpkg/lock
tar -xzvf hadoop-1.2.1.tar.gz
mv hadoop-1.2.1 hadoop
exit 1
