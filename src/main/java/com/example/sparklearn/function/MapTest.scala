package com.example.sparklearn.function

import org.apache.spark.{SparkConf, SparkContext}

object MapTest {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("partitions")
    val sc = new SparkContext(conf)

    println("1.map--------------------------------")
    val aa = sc.parallelize(1 to 9, 3)

    def doubleMap(a: Int): (Int, Int) = {
      (a, a * 2)
    }

    val aa_res = aa.map(doubleMap)
    println(aa.getNumPartitions)
    println(aa_res.collect().mkString)


    println("2.mapPartitions-------------------")
    val bb = sc.parallelize(1 to 9, 3)

    def doubleMapPartition(iter: Iterator[Int]): Iterator[(Int, Int)] = {
      var res = List[(Int, Int)]()
      while (iter.hasNext) {
        val cur = iter.next()
        res.::=(cur, cur * 2)
      }
      res.iterator
    }

    val bb_res = bb.mapPartitions(doubleMapPartition)
    println(bb_res.collect().mkString)


    println("3.mapPartitions-------------------")
    val cc = sc.makeRDD(1 to 5, 2)
    val cc_ref = cc.mapPartitions(x => {
      var result = List[Int]()
      var i = 0
      while (x.hasNext) {
        val cur = x.next()
        result.::=(cur * 2)
      }
      result.iterator
    })
    cc_ref.foreach(println)
  }
}
