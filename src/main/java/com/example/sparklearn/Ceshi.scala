package com.example.sparklearn

import java.security.PrivilegedAction
import java.sql.Connection
import java.util
import java.util.Map

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Ceshi {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()

    System.setProperty("java.security.krb5.conf", "ceshi/krb5.conf") //.conf
    val conf=new Configuration
    conf.addResource("ceshi/core-site.xml")
    conf.addResource("ceshi/hdfs-site.xml")
    conf.addResource("ceshi/hive-site.xml")
    conf.set("hadoop.security.authentication", "Kerberos")
//    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem")
    session.sparkContext.hadoopConfiguration.addResource(conf)

    UserGroupInformation.setConfiguration(conf)


//    val UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@BONCDSC.GREAT", "ceshi/test001.keytab")

    UserGroupInformation.loginUserFromKeytab("test001@BONCDSC.GREAT", "ceshi/test001.keytab")
    val frame=session.sql("select * from lxg.vbapff93676c213741d4a71ed1fa2f0b4de8 limit 10")
//    val frame: DataFrame = UGI.doAs(new PrivilegedAction[DataFrame]() {
//      override def run: DataFrame = {
//        session.sql("select * from lxg.vbapff93676c213741d4a71ed1fa2f0b4de8 limit 10")
//      }
//    })
//
//    val frame = session
//      .sql("select * from hive.vbapf20086ecdfc3402f9660ec553ea8ddb2 limit 10")
    println("----------------------------------------------------------")
    frame.show()
    println("----------------------------------------------------------")

    session.close()
  }

  def initKerberos(): Unit ={
    System.setProperty("java.security.krb5.conf", "ceshi/krb5.conf")
    val conf=new Configuration
    conf.addResource("ceshi/core-site.xml")
    conf.addResource("ceshi/hdfs-site.xml")
    conf.addResource("ceshi/hive-site.xml")
    conf.set("hadoop.security.authentication", "Kerberos")
    conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem")
    UserGroupInformation.setConfiguration(conf)
    UserGroupInformation.loginUserFromKeytab("test001@BONCDSC.GREAT", "ceshi/test001.keytab")
    println("login user: "+UserGroupInformation.getLoginUser())
  }


  def getHeBingData(session: SparkSession):DataFrame={

    val conf=new Configuration
    conf.addResource("hebing/core-site.xml")
    conf.addResource("hebing/hdfs-site.xml")
    conf.addResource("hebing/hive-site.xml")

//    session.sparkContext.hadoopConfiguration.addResource("hebing/core-site.xml")
//    session.sparkContext.hadoopConfiguration.addResource("hebing/hdfs-site.xml")
//    session.sparkContext.hadoopConfiguration.addResource("hebing/hive-site.xml")
    session.sparkContext.hadoopConfiguration.addResource(conf);
    val frame = session
      .sql("select * from hive.vbapf20086ecdfc3402f9660ec553ea8ddb2 limit 10")
    frame
  }



  def getData(session: SparkSession): DataFrame = {

    val pro2 = new java.util.Properties
    /*    pro2.put("user","hadoop")
    //    pro2.put("password","qk@MKO0")
        pro2.put("password","")
        pro2.put("fetchsize","1000")
        pro2.put("driver","org.apache.hive.jdbc.HiveDriver")
        val df2=session.read.jdbc("jdbc:hive2://10.130.2.47:14000/tmpst","tmp_ad_click_clickurl",pro2);*/
    /*
        pro2.put("user", "hadoop")
        pro2.put("password", "")
        pro2.put("fetchsize", "1000")
        pro2.put("driver", "org.apache.hive.jdbc.HiveDriver")
        val df2 = session.read.jdbc("jdbc:hive2://10.130.2.62:10000/basedata", "test", pro2);*/


    pro2.put("user", "hadoop")
    pro2.put("password", "")
    pro2.put("fetchsize", "1000")
    pro2.put("driver", "org.apache.hive.jdbc.HiveDriver")
    val df2 = session.read.jdbc("jdbc:hive2://10.130.2.132:10001/hive", "vbapfdfa851bb7e94a9ca3e19344e54c1713", pro2);


    df2

    //    session.sparkContext.hadoopFile("hive-site.xml")
    //    session.sparkContext.hadoopFile("hive-site.xml")
    //    session.sparkContext.hadoopFile("hive-site.xml")
    //    System.setProperty("java.security.krb5.conf", "krb5.conf") //.conf
    //    System.setProperty("sun.security.krb5.debug", "true")
    //    val conf=new Configuration()
    //    conf.addResource("core-site.xml")
    //    conf.addResource("hdfs-site.xml")
    //    conf.set("hadoop.security.authentication", "Kerberos")
    //    UserGroupInformation.setConfiguration(conf)
    //    val UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@BONCDSC.GREAT", "test001.keytab")
    //    val data = UGI.doAs(new PrivilegedAction[Dataset[Row]]() {
    //      override def run: Dataset[Row] = {
    //        session.sql("select * from angie.ceshi")
    //      }
    //    })
  }
}

