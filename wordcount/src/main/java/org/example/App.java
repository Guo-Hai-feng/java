package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import java.io. IOException;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        Configuration conf = new Configuration();
        Job job=Job.getInstance(conf);
        job.setJarByClass(App.class);

        job.setMapperClass(wordcountmapper.class);
        job.setReducerClass(wordcountreducer.class);



        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        FileInputFormat.setInputPaths(job,new Path("E:\\note\\hadoop\\input"));

        FileOutputFormat.setOutputPath(job, new Path("E:\\note\\hadoop\\output"));

        job.setNumReduceTasks(1);
        boolean res=job.waitForCompletion(true);
        System.exit(res?0:1);

    }
}
