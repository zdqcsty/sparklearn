package com.example.sparklearn.github

import org.apache.spark.sql.{Row, SparkSession}

class EmployeeDao(session: SparkSession) {

  import com.example.sparklearn.github.EmployeeDao._

  import session.implicits._

  def lastNames() =
    session
      .sql("SELECT lastName FROM employees")
      .map(row => row.getString(0))

  def distinctLastNames() =
    session
      .sql("SELECT DISTINCT lastName FROM employees")
      .map(row => row.getString(0))

  def byLastName(lastNames: String*) =
    session
      .sql(s"SELECT * FROM employees WHERE lastName IN(${lastNames.mkString("'", "', '", "'")})")
      .map(toEmployee)

  def byLastNameLike(lastName: String) =
    session
      .sql(s"SELECT * FROM employees WHERE lastName LIKE '$lastName%'")
      .map(toEmployee)

  def withDepartment() = {
    val sql =
      """
        |SELECT ssn, e.name AS name_e, lastName, d.name AS name_d, budget
        | FROM employees e INNER JOIN departments d
        | ON e.department = d.code
      """.stripMargin

    session
      .sql(sql)
      .map(row => (row.getString(0), row.getString(1), row.getString(2), row.getString(3), row.getInt(4)))
  }
}

object EmployeeDao {
  private def toEmployee(row: Row): Employee =
    Employee(row.getString(0), row.getString(1), row.getString(2), row.getInt(3))
}
