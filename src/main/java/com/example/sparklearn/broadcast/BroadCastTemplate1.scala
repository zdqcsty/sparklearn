package com.example.sparklearn.broadcast

import org.apache.spark.sql.SparkSession

object BroadCastTemplate1 {


  def main(args: Array[String]): Unit = {

    val session = SparkSession
      .builder().enableHiveSupport().getOrCreate()
    import org.apache.spark.sql.functions.broadcast

    val gongcan = session.sql("select * from ceshi.gongcan")

    //这种方式相当于先广播然后注册成一张临时表
    val value = broadcast(gongcan).createOrReplaceTempView("gongcan")

//    session.sql("create table ceshi.iklouytg as select /*+ BROADCAST(a) */ b.* from zgh.vbapf5f29d8a8a724888a9069d7c96d6cf22 a join demo b on a.column_0=b.id")
    session.sql("select t.ccard,t.ename,t.phone,g.name from ceshi.test t  join gongcan g on t.ccard=g.name").write.csv("/user/zgh/broad")

    session.close();

  }

}
