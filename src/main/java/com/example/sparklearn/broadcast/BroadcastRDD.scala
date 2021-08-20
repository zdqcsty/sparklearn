package com.example.sparklearn.broadcast

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object BroadcastRDD {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("broadcast")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3), ("c", 3)
    ), 4)
    //
    val map = mutable.Map("b" -> 3, "c" -> 5, "d" -> 7)
    val value1 = sc.broadcast(map)
    val broadRDD = rdd.map {
      case (key, value) => {
        if (value1.value.contains(key)) {
          (key, value + 1)
        } else {
          (key, 1000)
        }
      }
    }
    broadRDD.foreach(println(_))

    sc.stop()
  }
}