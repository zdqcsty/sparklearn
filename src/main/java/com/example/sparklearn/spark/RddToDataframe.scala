package com.example.sparklearn.spark

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object RddToDataframe {

  def main(args: Array[String]): Unit = {

    val session = SparkSession.builder().master("local[*]").getOrCreate()


    val structSchema: StructType = StructType(
      List(
        StructField("id", IntegerType, true),
        StructField("name", StringType, true)
      )
    )

    val value = session.sparkContext.textFile("file:///E:\\study_workSpace\\sparklearn\\src\\main\\resources\\ceshi.txt")
      .map(x => x.split(",")).map(x => (x(0).toInt, x(1)))

    //在这里也就更加清楚了这个row的含义   存储数据和schemal
    val value1 = value.map(x => Row(x._1, x._2))

    val frame = session.createDataFrame(value1, structSchema)


    frame.printSchema()


    frame.show()

  }

}
