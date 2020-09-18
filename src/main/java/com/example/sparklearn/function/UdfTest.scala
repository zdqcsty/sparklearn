package com.example.sparklearn.function

import org.apache.spark.sql.{SparkSession, functions}
import org.apache.spark.sql.functions.{column, udf}

/**
  * UDF相关的注册测试
  * 参考资料:https://spark.apache.org/docs/latest/sql-ref-functions-udf-scalar.html
  */

object UdfTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder.master("local[*]")
      .appName("udftest")
      .getOrCreate()

    val appStr = (s:String) => {
      s+"aaa"
    }

    udf(appStr)

    spark.udf.register("appStr",appStr)

    val frame = spark.read.csv("file:///E:\\data.csv").toDF("id","name")

    frame.withColumn("extra",functions.callUDF("appStr",column("id"))).show();

  }

}
