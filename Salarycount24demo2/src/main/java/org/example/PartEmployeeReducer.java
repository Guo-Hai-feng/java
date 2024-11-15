package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class PartEmployeeReducer extends Reducer<DeptSalaryKey, Employee, IntWritable, Employee> {

	@Override
	protected void reduce(DeptSalaryKey key, Iterable<Employee> values, Context context)
			throws IOException, InterruptedException {
		IntWritable departmentKey = new IntWritable(key.getDeptno());
		for (Employee e : values) {
			context.write(departmentKey, e);
		}
	}
}

