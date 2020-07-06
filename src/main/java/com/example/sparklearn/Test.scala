package com.example.sparklearn

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapred.TextOutputFormat
import org.apache.spark.sql.{Encoder, SparkSession}


object Test {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()

    val frame = session.read.csv("/user/zgh/ceshi/*")
//    val frame = session.read.csv("F:\\data.csv")

    val value = frame.rdd.map(x=>(x,1)).groupByKey().map(w => (w._1, w._2.sum))
//    val value = frame.rdd.map(x=>(x,1)).reduceByKey((_+_))

//    value.saveAsTextFile("F:\\result")
    value.saveAsHadoopFile("/user/zgh/result",classOf[Text],classOf[IntWritable],classOf[TextOutputFormat[Text,IntWritable]])

//    frame.write.csv("/user/zgh/result")
    session.close()

/*    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()

    session.sql("select * from "+args(0)).write.csv(args(1))

    session.close()*/

  }
}
