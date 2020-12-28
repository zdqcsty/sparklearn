package com.example.sparklearn.github

import org.apache.spark.sql.SparkSession

class DepartmentDao(session: SparkSession) {

  import session.implicits._

  def sumBudgets(): Long =
    session
      .sql("SELECT SUM(budget) FROM departments")
      .map(row => row.getLong(0)).first()

  def numberOfEmployees() =
    session
      .sql("SELECT department, COUNT(*) FROM employees GROUP BY department")
      .map(row => (row.getInt(0), row.getLong(1)))

}
