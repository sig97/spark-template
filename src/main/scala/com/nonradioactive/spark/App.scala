package com.nonradioactive.spark

import org.apache.spark.sql.functions._
import org.slf4j.LoggerFactory

final case class Person(firstName: String, lastName: String,
                        country: String, age: Int)

object App extends InitSpark {

  var log = LoggerFactory.getLogger(classOf[App])

  def main(args: Array[String]) = {

    import spark.implicits._
    val version = spark.version
    log.info("SPARK VERSION: {}", version)

    var fileName = "clients.csv"
    var path = ClassLoader.getSystemClassLoader.getResource(fileName).getPath
    log.info("Reading data file {}", path)

    val persons = reader.csv(path).as[Person]
    persons.show(10, false)

    val averageAge = persons.agg(avg("age"))
                     .first.get(0).asInstanceOf[Double]

    log.info("Average age: {}", averageAge)

    close
  }
}
