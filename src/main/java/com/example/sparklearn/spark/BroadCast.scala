package com.example.sparklearn.spark

import org.apache.spark.sql.SparkSession

object BroadCast {


  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()
    import org.apache.spark.sql.functions.broadcast

    val gongcan = session.sql("select * from ceshi.broad")

    val value = broadcast(gongcan).createOrReplaceTempView("demo")

//    session.sql("create table ceshi.iklouytg as select /*+ BROADCAST(a) */ b.* from zgh.vbapf5f29d8a8a724888a9069d7c96d6cf22 a join demo b on a.column_0=b.id")
    session.sql("create table ceshi.iklouytg as select a.* from zgh.vbapf5f29d8a8a724888a9069d7c96d6cf22 a join demo b on a.column_0=b.id")

    session.close();

  }

}
