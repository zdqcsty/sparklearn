package com.example.sparklearn.structurestreaming

import org.apache.spark.sql.SparkSession

object ConsumKafka {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder.master("local[2]")
      .appName("StructuredNetworkWordCount")
      .getOrCreate()

    import spark.implicits._

    val lines = spark.readStream.format("kafka")
      .option("kafka.bootstrap.servers","10.130.7.208:9092")
      //查询的起点，最开始的偏移量
      .option("startingOffsets", "earliest")
      .option("subscribe","test")
      .load()

    lines.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)]

    val query = lines
      //这行代码很重要，将key和value由二进制转到string  还可以选择别的字段
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)","topic")
      .writeStream
      .outputMode("update")
      .format("console")
      .start()

/*    val query = lines
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .writeStream
      .format("kafka")
      //将数据写入kafka的时候需要设置checkpoint，用于故障恢复，维护offsect
      .option("checkpointLocation", "E:\\check")
      .option("kafka.bootstrap.servers", "10.130.7.208:9092")
      .option("topic", "test")
      .start()*/


    query.awaitTermination()

  }

}
