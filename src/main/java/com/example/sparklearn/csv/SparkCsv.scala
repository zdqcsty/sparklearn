package com.example.sparklearn.csv

import org.apache.spark.sql.SparkSession

object SparkCsv {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().master("local").enableHiveSupport().getOrCreate()


    val frame = session.read.csv("F:\\demo.txt")

//    frame.show()

    frame.write.csv("F:\\aaa")

  }


}
