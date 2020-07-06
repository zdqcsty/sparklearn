package com.example.sparklearn.broadcast

import org.apache.spark.sql.SparkSession

object BroadCastTemplate2 {

  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()

    //这种hint模式来创建广播变量
    session.sql("select /*+ BROADCAST(g) */ t.ccard,t.ename,t.phone,g.name from ceshi.test t  join ceshi.gongcan g on t.ccard=g.name").write.csv("/user/zgh/broad")

    session.close()
  }
}
