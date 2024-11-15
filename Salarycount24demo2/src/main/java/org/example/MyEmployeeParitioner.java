package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/*
 * 建立自己的分区规则：根据员工的部门号进行分区
                                              根据Map的输出
 */
//                                                    k2    v2
public class MyEmployeeParitioner extends Partitioner<IntWritable, Employee>{

	/**
	 * numPartition参数：建立多少个分区
	 */
	@Override
	public int getPartition(IntWritable k2, Employee v2, int numPartition) {
		// 如何建立分区
		if(v2.getDeptno() == 10){
			//放入1号分区中
			return 1%numPartition;
		}else if(v2.getDeptno() == 20){
			//放入2号分区中
			return 2%numPartition;
		}else{
			//放入0号分区中
			return 3%numPartition;
		}
	}

}








