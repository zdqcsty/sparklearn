package com.example.sparklearn.readandwrite

import org.apache.spark.sql.SparkSession

object SparkWrite {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder().master("local").enableHiveSupport().getOrCreate()

    val frame = spark.read.format("csv")
      .option("header", "true")
      .csv("/user/zgh/data.csv")

    frame.write.format("csv").mode("overwrite").option("header", "true").save("/user/zgh/democeshi")
  }
}
