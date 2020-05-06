package com.example.sparklearn

import org.apache.hadoop.conf.Configuration
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object SparkTest {

  def main(args: Array[String]): Unit = {

    val hadoopConf = new Configuration();
    val conf = new SparkConf().setAppName("SplitTest")

    val sc = new SparkContext(conf)
    sc.textFile("/user/zgh/ceshi.log")
      .map(value => {
        if (value.contains("info")){
          ("info", value)
        }else if((value.contains("debug"))) {
          ("debug", value)
        } else if (value.contains("error")) {
          ("error", value)
        }else {("null", value)}
      } )
      .partitionBy(new HashPartitioner(3))
      .saveAsHadoopFile("/user/zgh/test", classOf[String], classOf[String],
        classOf[RDDMultipleTextOutputFormat])

    sc.stop()

  }

}
