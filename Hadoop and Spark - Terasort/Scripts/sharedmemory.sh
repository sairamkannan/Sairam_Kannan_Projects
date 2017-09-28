clear
sudo apt-get update
echo "Installing Java"
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update && sudo apt-get install oracle-jdk7-installer
sudo apt-get update
echo "compiling sharedmemory code"
javac sharedmemory.java
echo "Running sharedmemory code"
java sharedmemory
exit 1