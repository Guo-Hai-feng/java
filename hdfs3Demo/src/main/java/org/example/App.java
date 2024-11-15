package org.example;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        //创建目录
        URI uri=new URI("hdfs://hadp103:8020");
        Configuration conf=new Configuration();
        FileSystem fs=FileSystem.get(uri,conf,"root");
//        fs.copyFromLocalFile(new Path("d:/test.txt"), new Path("/"));
        fs.copyToLocalFile(new Path("E:/a.txt"), new Path("d:/test-down.txt"));
        fs.close();




    }
}
