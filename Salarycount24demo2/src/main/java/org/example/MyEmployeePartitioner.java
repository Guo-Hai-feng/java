package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyEmployeePartitioner extends Partitioner<DeptSalaryKey, Employee> {
    @Override
    public int getPartition(DeptSalaryKey key, Employee value, int numPartitions) {
        // 按部门号分区，使相同部门号的数据进入相同的 Reducer
        return (key.getDeptno() & Integer.MAX_VALUE) % numPartitions;
    }
}

