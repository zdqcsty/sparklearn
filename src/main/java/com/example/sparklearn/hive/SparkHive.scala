package com.example.sparklearn.hive

import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.SparkSession

object SparkHive {

  def main(args: Array[String]): Unit = {

    //设置hadoop用户   必须在sparksession创建前设置 不然不生效
    System.setProperty("HADOOP_USER_NAME", "hadoop")

    val session = SparkSession
      .builder().enableHiveSupport().master("local[*]").getOrCreate()

    //要显示指定core-site.xml hdfs-site.xml  hive-site.xml  或者将他放到resource根目录下
    val conf = new Configuration
    conf.addResource("hebing/core-site.xml")
    conf.addResource("hebing/hdfs-site.xml")
    conf.addResource("hebing/hive-site.xml")
    session.sparkContext.hadoopConfiguration.addResource(conf);

    session.sparkContext.hadoopConfiguration.set("dfs.client.use.datanode.hostname", "true")

    val frame = session.sql("select * from ceshi.ceshi limit 10")

    frame.repartition(2).write.option("header", true).csv("/user/zgh/oiuyty")

    session.close()


  }

}
