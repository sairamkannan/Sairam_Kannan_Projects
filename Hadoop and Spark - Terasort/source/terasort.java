import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class terasort{
        public static class SortingCustomMapper extends Mapper<Object, Text, Text, Text>{
                public void map (Object key, Text value, Context context)throws IOException, InterruptedException{
                        String firstTenKey = (value.toString()).substring(1,10);
                        String nextNintyKey  = (value.toString()).substring(11);
                        context.write(new Text(firstTenKey),new Text(nextNintyKey));
                }
        }
        public static class SortingCustomReducer extends Reducer<Text, Text, Text, Text>{
                public void reduce (Text key, Text values, Context context)throws IOException, InterruptedException{
                        context.write(key,new Text(values));
                }
        }

        public static void main(String[] args) throws Exception{
                Configuration conf = new Configuration();
                Job job = Job.getInstance(conf, "sort");
                job.setJarByClass(HadoopSortSingleNode.class);
                job.setMapperClass(SortingCustomMapper.class);
                job.setCombinerClass(SortingCustomReducer.class);
                job.setReducerClass(SortingCustomReducer.class);
                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(Text.class);
                FileInputFormat.addInputPath(job, new Path(args[0]));
                FileOutputFormat.setOutputPath(job, new Path(args[1]));
                System.exit(job.waitForCompletion(true) ? 0 : 1);
        }

}