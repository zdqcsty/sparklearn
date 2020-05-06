package com.example.sparklearn.spark.csv

import org.apache.spark.sql.SparkSession

object SparkCsv {

  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().master("local").enableHiveSupport().getOrCreate()

    //option("emptyValue","")  它的作用是落地的为NULL的数据不是双引号而是,,
    /**
      * 比如+---+----+---+
      * |_c0| _c1|_c2|
      * +---+----+---+
      * |  1|   2|  3|
      * |  5|null|  7|
      * +---+----+---+
      * 落地的数据就是5,,7
      *
      */
    val frame = session
      .read.csv("F:\\demo.txt")

    frame.show()

    frame.write.option("emptyValue","").csv("F:\\aaa.txt")





  }

}
