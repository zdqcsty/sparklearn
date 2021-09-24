package com.example.sparklearn

import java.io.{BufferedReader, BufferedWriter, File, FileReader, FileWriter, PrintWriter}

import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkFiles
import org.apache.spark.sql.{Encoder, SparkSession}


object Test {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().master("local[*]").enableHiveSupport().getOrCreate()

    val conf = new Configuration
    conf.addResource("hebing/core-site.xml")
    conf.addResource("hebing/hdfs-site.xml")
    conf.addResource("hebing/hive-site.xml")
    conf.set("dfs.client.use.datanode.hostname", "true")
    session.sparkContext.hadoopConfiguration.addResource(conf)

    val frame = session.sql("select * from test.join_test limit 5")

    frame.foreach(println(_))

    session.close()

  }




}
