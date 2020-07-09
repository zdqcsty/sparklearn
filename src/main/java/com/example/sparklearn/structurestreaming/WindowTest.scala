package com.example.sparklearn.structurestreaming

import org.apache.spark.sql.SparkSession

object WindowTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder.master("local[*]")
      .appName("StructuredNetworkWordCount")
      .getOrCreate()

    import spark.implicits._

    val lines = spark.readStream.format("kafka")
      .option("kafka.bootstrap.servers","10.130.7.208:9092")
      .option("startingOffsets", "earliest")
      .option("subscribe","test")
      .load()

    val value = lines.selectExpr("CAST(value AS STRING)","CAST(timestamp AS TIMESTAMP)").as[(String,String)]

    //这行需要导入，不然window 窗口函数不能使用
    import org.apache.spark.sql.functions._
    val windowedCounts = value.groupBy(
      window($"timestamp", "2 minute", "1 minute"),
      $"value"
    ).count()

    val query = windowedCounts
      .writeStream
      //模式选择  complete 和update，根据自己需要进行选择
      .outputMode("complete")
      .format("console")
      //"truncate"只是为了在控制台输出时，不进行列宽度自动缩小。
      .option("truncate", "false")
      .start()

    query.awaitTermination()
  }
}
