package com.example.sparklearn

import org.apache.spark.sql.SparkSession

object Test {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()

    val frame = session.read.csv("hdfs://10.129.18.17:8020/user/zgh/test1.csv")

    frame.show()
    frame.write.csv("/user/zgh/test")

    session.close()


  }
}
