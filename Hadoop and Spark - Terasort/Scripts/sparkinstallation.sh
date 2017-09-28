clear
sudo apt-get update
echo "Installing Java"
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update && sudo apt-get install oracle-jdk7-installer
sudo apt-get update
echo "Installing scala"
sudo apt-get remove scala-library scala
wget http://www.scala-lang.org/files/archive/scala-2.11.4.deb
sudo dpkg -i scala-2.11.4.deb
sudo apt-get update
sudo apt-get install scala
echo "Installing sbt (It is similar to maven and is used as a build tool"
wget http://dl.bintray.com/sbt/debian/sbt-0.13.6.deb
sudo dpkg -i sbt-0.13.6.deb 
sudo apt-get update
sudo apt-get install sbt
echo "Installing Spark"
wget http://apache.mirrors.ionfish.org/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
tar -zxvf spark-1.6.0-bin-hadoop2.6.tgz
mv spark-1.6.0-bin-hadoop2.6 spark
exit 1
