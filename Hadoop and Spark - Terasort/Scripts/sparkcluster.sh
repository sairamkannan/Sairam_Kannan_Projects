clear
sudo apt-get update
echo "change permission of pem file"
chmod 400 <YOUR_KEY>.pem
echo "Login to your instance"
ssh -i <YOUR_KEY>.pem ubuntu@<amazon-public-dns>
echo "copy your pem file to your instance"
scp -i <YOUR_KEY>.pem <YOUR_KEY>.pem ubuntu@<amazon-public-dns>
alias spark = "cd /home/ubuntu/spark/ec2"
echo "Exporting your AWS access key and secret access key"
export AWS_ACCESS_KEY_ID="<Your AWS access key>"
export AWS_SECRET_ACCESS_KEY="<Your AWS sercret access key"
echo "Launch the spark cluster of 16 worker nodes"
spark ./spark-ec2 -k <cluster-name> -i <YOUR_KEY>.pem -s 16 --instance-type=c3.large --spot-price=<your_bid-price> --region=us-east-1 --ami=ami-id --hadoop-major-version=yarn launch Spark100GB
echo "copy the generated data file to HDFS"
./gensort -a 1000000000 <input_file>
cat <input_file> | /root/ephemeral-hdfs/bin/hadoop fs -put <input_file>
echo "submit your spark application"
./spark-submit <sorting-code>
exit 1
