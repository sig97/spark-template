package com.nonradioactive.spark.integration

import com.nonradioactive.spark.LocalConfiguration
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.{Test, TestInstance}
import org.elasticsearch.spark._

import scala.collection.JavaConverters._

@IntegrationTest
@TestInstance(Lifecycle.PER_CLASS)
class ElasticTest {

  val configuration = LocalConfiguration.DOCKER()
  val spark = SparkSession.builder()
    .appName("Spark example")
    .master("local[*]")
    .getOrCreate()

  @Test
  def elasticMigration(): Unit = {
    import org.apache.spark.sql.SparkSession
    import org.elasticsearch.spark.sql._

    val dataSet = spark.sparkContext.parallelize(Seq(
        Row("1981-01-01", 20.7), Row("1981-01-02", 17.9), Row("1981-01-03", 18.8)
    ))

//    val conf = Map( "es.nodes" ->  "localhost",
//                    "es.port" ->  "9200",
//                    "es.index.auto.create" -> "true",
//                    "es.nodes.wan.only" -> "true",
//                    "index.mapper.dynamic" -> "false")
    val conf = configuration.getElasticSettings.asScala
    println("elastic settings: " + conf)

    val schema = new StructType()
      .add(StructField("date", StringType, true))
      .add(StructField("temperature", DoubleType, true))

    val df = spark.createDataFrame(dataSet, schema)
    df.show(10, false)

    df.saveToEs("weather/temperatures", conf)

  }
}
