//package com.example.sparklearn.spark
//
//import java.util.Properties
//
//import org.apache.spark.sql.SparkSession
//
//object Ceshi {
//
//  def main(args: Array[String]): Unit = {
//
//    val session = SparkSession
//      .builder().master("local[5]").enableHiveSupport().getOrCreate()
//    val prop = new Properties();
//    prop.setProperty("user", "stage")
//    prop.setProperty("password", "stage123")
//    prop.setProperty("driverClass", "com.bonc.xcloud.jdbc.XCloudDriver")
//    val url = "jdbc:xcloud:@172.16.44.7:1905/SXLTDW"
//      val partitons = new Array[String](3)
//        partitons(0) = "1=1 limit 0,20000000";
//        partitons(1) = "1=1 limit 20000000,40000000";
//        partitons(2) = "1=1 limit 40000000,60000000";
//
//    val mysqlData = session.read.jdbc(url, "DWA_D_USE_BASE_INFO_20200603", partitons, prop)
//
//    val start = System.currentTimeMillis()
//    mysqlData.write.csv("hdfs:///user/zgh/ceshi/csv")
//
//    val end = System.currentTimeMillis()
//
//    println("-----------------------------------")
//    val totalTime = end - start
//    println(totalTime)
//    println("-----------------------------------")
//
//    session.close();
//  }
//}
