package com.example.sparklearn.structurestreaming

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.spark.sql.{SparkSession, functions}
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.DataType

object StreamingDeduplication {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder.master("local[*]")
      .appName("StructuredNetworkWordCount")
      .getOrCreate()


    spark.udf.register("_stringToTs", _stringToTs _)

    import spark.implicits._

    val lines = spark.readStream.format("kafka")
      .option("kafka.bootstrap.servers", "10.130.7.208:9092")
      .option("startingOffsets", "earliest")
      .option("subscribe", "test")
      .load()

    val value = lines.selectExpr("CAST(value AS STRING)")

    val frame = value.withColumn("timestamp", functions.callUDF("_stringToTs", value("value").substr(13, 20)))

    //使用Watermark这种方式可以使时间戳在这个范围以外的数据进不来，这样可以少维护数据的状态了。如果不带Watermark，相当于得维护所有数据的状态，也相当于给所有数据去重
    val result = value.withWatermark("timestamp", "10 seconds").dropDuplicates("value", "timestamp")

    val query = result
      .selectExpr("CAST(value AS STRING)", "CAST(timestamp AS TIMESTAMP)")
      .writeStream
      .outputMode("update")
      .format("console")
      .option("truncate", "false")
      .start()


    query.awaitTermination()
  }

  //转换时间戳的udf
  def _stringToTs(s: String): Timestamp = {
    val format = new SimpleDateFormat("yyyyMMddHHmmss")
    val time = format.parse(s).getTime
    new Timestamp(time)
  }

}

