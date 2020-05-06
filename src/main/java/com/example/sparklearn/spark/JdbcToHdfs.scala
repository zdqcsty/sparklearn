package com.example.sparklearn.spark

import java.util.Properties

import org.apache.spark.sql.SparkSession

object JdbcToHdfs {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().master("local").getOrCreate()
    val prop = new Properties();
    prop.setProperty("user", "test001")
    prop.setProperty("password", "sqltest001")
//    prop.setProperty("driverClass", "com.bonc.xcloud.jdbc.XCloudDriver")
    val url = "jdbc:mysql://10.130.2.147:3306/bcloud_test001_str?serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8"
    ////    val url="jdbc:mysql://172.16.41.27:3306/cloud2_bonc_hebing?serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8"
    //    val mysqlData = session.read.jdbc(url,"DWA_D_USE_BASE_INFO_20200603",prop)


//        val mysqlData = session.read
//          .format("jdbc")
//          .option("driver", "com.bonc.xcloud.jdbc.XCloudDriver")
//          .option("url", url)
//          .option("dbtable", "DWA.DWA_D_USE_BASE_INFO_20200603")
//          .option("user", "stage")
//          .option("password", "stage123")
//          .load()

    val partitons=new Array[String](2);
    partitons(0)="1=1  limit 1,2";
    partitons(1)="1=1  limit 2,3";

    val mysqlData=session.read.option("driver","com.mysql.jdbc.Driver").jdbc(url, "detail_operator", partitons, prop)



    val start = System.currentTimeMillis()
    //    mysqlData.write.parquet("hdfs:///user/zgh/ceshi/parquet")
    mysqlData.write.csv("/user/zgh/yangli")

//    val end = System.currentTimeMillis()

//    println("-----------------------------------")
//    val totalTime = end - start
//    println(totalTime)
//    println("-----------------------------------")

    session.close();
  }
}
