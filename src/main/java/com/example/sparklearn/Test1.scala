package com.example.sparklearn

import org.apache.spark.sql.SparkSession

object Test1 {

  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()

    session.sql("select /*+ BROADCAST(g) */ t.ccard,t.ename,t.phone,g.name from ceshi.test t  join ceshi.gongcan g on t.ccard=g.name").write.csv("/user/zgh/broad")

    session.close()
  }
}
