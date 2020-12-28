package com.example.sparklearn.github

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

object SparkSqlExample {

  private val master = "local[2]"
  private val appName = "example-spark"

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)

    val session = SparkSession
      .builder().enableHiveSupport().config(conf).getOrCreate()

    val sc = session.sparkContext

    val employeeDao = new EmployeeDao(session)
    val departmentDao = new DepartmentDao(session)

    import session.implicits._

    val employees = sc.textFile("src/main/resources/data/employees.txt")
      .map(_.split(","))
      .map(fields => Employee(fields(0), fields(1), fields(2), fields(3).trim.toInt))
      .toDF()
    employees.createTempView("employees")

    val departments = sc.textFile("src/main/resources/data/departments.txt")
      .map(_.split(","))
      .map(fields => Department(fields(0).trim.toInt, fields(1), fields(2).trim.toInt))
      .toDF()
    departments.createTempView("departments")

    employeeDao.lastNames().collect()

    employeeDao.distinctLastNames().collect()

    employeeDao.byLastName("Smith").collect().map(_.toString)

    employeeDao.byLastName("Smith", "Doe").collect().map(_.toString)

    employeeDao.byLastNameLike("S").collect().map(_.toString)

    departmentDao.numberOfEmployees().collect().map(_.toString())

    val employeesWithDepartments = employeeDao.withDepartment()
    
    employeesWithDepartments.collect().map(_.toString)
  }

}
