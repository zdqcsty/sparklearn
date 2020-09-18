package com.example.sparklearn.function

import org.apache.spark.sql.{SparkSession, functions}
import org.apache.spark.sql.functions.{column, udf}

/**
  * UDF相关的注册测试
  * 参考资料:https://www.cnblogs.com/yyy-blog/p/10280657.html
  * https://spark.apache.org/docs/latest/sql-ref-functions-udf-scalar.html
  */

object UdfTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder.master("local[*]")
      .appName("udftest")
      .getOrCreate()

//    sqlRegistUdf(spark)
//    dataframeRegistUdfShiMing(spark)
    sqlRegistUdfNiMing(spark)
//    sqlRegistUdfShiMing(spark)
  }


  val appStr = (s:String) => {
    s+"aaa"
  }

  def appStrByShiMing (s:String) : String = {
    s+"aaa"
  }

  // spark sql 注册udf(注册匿名函数)
  def dataframeRegistUdfNiming(spark: SparkSession): Unit = {
    val df = udf(appStr);
    val frame = spark.read.csv("file:///E:\\data.csv").toDF("id", "name")
    frame.withColumn("extra", df(column("id"))).show();
  }

  // spark sql 注册udf(注册实名函数)
  def dataframeRegistUdfShiMing(spark: SparkSession): Unit = {
    val df = udf(appStrByShiMing _);  //注意这里是区别
    val frame = spark.read.csv("file:///E:\\data.csv").toDF("id", "name")
    frame.withColumn("extra", df(column("id"))).show();
  }

  // spark sql 注册udf
  def  sqlRegistUdfNiMing(spark : SparkSession): Unit ={
    spark.udf.register("sqlUdf",appStr)
    val frame = spark.read.csv("file:///E:\\data.csv").toDF("id","name").createOrReplaceTempView("ceshi")
    spark.sql("select sqlUdf(name) from ceshi").show
  }

  // spark sql 注册udf
  def  sqlRegistUdfShiMing(spark : SparkSession): Unit ={
    spark.udf.register("sqlUdf",appStrByShiMing _)
    val frame = spark.read.csv("file:///E:\\data.csv").toDF("id","name").createOrReplaceTempView("ceshi")
    spark.sql("select sqlUdf(name) from ceshi").show
  }



}
