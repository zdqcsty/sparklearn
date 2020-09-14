package com.example.sparklearn.readandwrite

import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkRead {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder().master("local").enableHiveSupport().getOrCreate()

    //读取csv  (本地测试需要使用全路径)
    val csvframe = spark.read.csv("E:\\study_workSpace\\sparklearn\\src\\main\\resources\\file\\test1.csv")
    csvframe.show()

    //读取tsv文件(制表符分隔)    (参考https://xbuba.com/questions/43508054)
    val tsvFrame = readTsv(spark,"E:\\study_workSpace\\sparklearn\\src\\main\\resources\\file\\test2.tsv")
    tsvFrame.show()

    spark.close()
  }

  //读取tsv文件(制表符分隔)(参考https://xbuba.com/questions/43508054)
  def readTsv(spark: SparkSession, path: String): DataFrame = {
    spark.read.option("header", true).format("csv").option("delimiter", "\\t")
      .load(path)
  }

}
