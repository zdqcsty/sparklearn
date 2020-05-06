package com.example.sparklearn.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

object CsvParquet {

  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder().master("local[8]").enableHiveSupport().getOrCreate()


    val csvData = session.read.csv("hdfs://beh/611b5cf1cb534cbf8cac69f01c12ba44/dataSet/vbap34e6f2736a3d41e39b2df367b393bd3f/data/").persist(StorageLevel.DISK_ONLY)
    csvData.write.parquet("hdfs:///user/zgh/ceshi/parquet")

    session.close()

  }

}
