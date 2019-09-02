package com.nonradioactive.spark

import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrameReader, SQLContext, SparkSession}

trait InitSpark {
  val spark: SparkSession = SparkSession.builder()
                            .appName("Spark Template")
                            .master("local[*]")
                            .getOrCreate()

  var sc: SparkContext = spark.sparkContext
  var sqlContext: SQLContext = spark.sqlContext

  def reader: DataFrameReader = spark.read
               .option("header", true)
               .option("inferSchema", true)
               .option("mode", "DROPMALFORMED")

  private def init = {
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.WARN)
    Logger.getLogger("akka").setLevel(Level.WARN)
    LogManager.getRootLogger.setLevel(Level.INFO)
  }

  def close(): Unit = {
    spark.close()
  }

  init
}
