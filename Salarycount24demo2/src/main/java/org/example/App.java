package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App {
	public static void main(String[] args) throws Exception {
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(App.class);

		job.setMapperClass(PartEmployeeMapper.class);
		job.setMapOutputKeyClass(DeptSalaryKey.class);
		job.setMapOutputValueClass(Employee.class);

		job.setPartitionerClass(MyEmployeePartitioner.class);
		job.setNumReduceTasks(3);

		job.setReducerClass(PartEmployeeReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Employee.class);

		// 设置自定义分组
		job.setGroupingComparatorClass(DeptGroupingComparator.class);

		FileInputFormat.setInputPaths(job, new Path("E:\\note\\hadoop\\input"));
		FileOutputFormat.setOutputPath(job, new Path("E:\\note\\hadoop\\output"));

		job.waitForCompletion(true);
	}
}

