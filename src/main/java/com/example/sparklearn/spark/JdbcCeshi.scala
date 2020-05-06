//package com.example.sparklearn.spark
//
//import java.io.IOException
//import java.security.PrivilegedAction
//import java.sql.Connection
//import java.util.Properties
//
//import org.apache.hadoop.conf.Configuration
//import org.apache.hadoop.security.UserGroupInformation
//import org.apache.spark.sql.{DataFrame, SparkSession}
//
//object JdbcCeshi {
//
//  def main(args: Array[String]): Unit = {
//
//    val session = SparkSession
//      .builder().enableHiveSupport().getOrCreate()
//
//    var dataset=null
//
//    val configuration = new Configuration
//    configuration.set("hadoop.security.authentication", "Kerberos")
//    val url = "jdbc:hive2://10.130.7.54:10001/stress;principal=hs2/hadoop10.novalocal@BONCST.LBHY;auth=kerberos"
//    //    val mysqlData = session.read.jdbc(url, "DWA_D_USE_BASE_INFO_20200603", prop)
//    try {
//      System.setProperty("java.security.krb5.conf", "/home/hadoop/zgh/waiconf/krb5.conf")
//      val  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("hs2/hadoop10.novalocal@BONCST.LBHY", "/home/hadoop/zgh/waiconf/hs10.keytab");
//      UGI.checkTGTAndReloginFromKeytab();
//
//
//      return conn;
//    }
//
//
//    val mysqlData = session.read
//      .format("jdbc")
//      .option("driver", "org.apache.hive.jdbc.HiveDriver")
//      .option("url", url)
//      .option("dbtable", "stress.step_step_22993425175_0")
//      .option("user", "hadoop")
//      .option("password", "")
//      .load()
//
//    //    val partitons = new Array[String](2);
//    //    partitons(0) = "1=1  limit 1,2";
//    //    partitons(1) = "1=1  limit 2,3";
//    //
//        val mysqlData = session.read.option("driver", "com.mysql.jdbc.Driver").jdbc(url, "detail_operator", partitons, prop)
//
//
////    val start = System.currentTimeMillis()
//    //    mysqlData.write.parquet("hdfs:///user/zgh/ceshi/parquet")
//    mysqlData.write.csv("/user/zgh/stress")
//
//    //    val end = System.currentTimeMillis()
//
//    //    println("-----------------------------------")
//    //    val totalTime = end - start
//    //    println(totalTime)
//    //    println("-----------------------------------")
//
//    session.close();
//  }
//}
