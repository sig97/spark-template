package com.nonradioactive.spark.integration

import com.github.javafaker.Faker
import com.nonradioactive.spark.LocalConfiguration
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.{BeforeAll, Test, TestInstance}

case class ClientType(id: Int, firstName: String, lastName: String, age: Int)

@IntegrationTest
@TestInstance(Lifecycle.PER_CLASS)
class LocalTest {
  val spark: SparkSession = SparkSession.builder()
    .appName("Spark example")
    .master("local[*]")
    .getOrCreate()

  val configuration = LocalConfiguration.DOCKER()
  var jdbcUrl = configuration.getJdbcUrl
  val connectionProperties = configuration.getSqlProperties
  var clientTableName = "db.client"

  @BeforeAll
  def setUp(): Unit = {
    import spark.implicits._

    val faker = new Faker()
    // Generate test table data
    val data = (1 to 40).map(it => new ClientType(it, faker.name().firstName(), faker.name.lastName(), faker.number().numberBetween(17, 88)))
    val rdd = spark.sparkContext.parallelize(data)
    //    val df = spark.createDataFrame(rdd).toDF("id", "firstName", "lastName", "age")
    val df = rdd.toDF()

    println("Generated test data:")
    df.printSchema()
    df.show(20, false)

    // Write test data to a table
    df.write.option("createTableColumnTypes", "id BIGINT, firstName VARCHAR(128), lastName VARCHAR(128), age INT")
      .mode(SaveMode.Overwrite)
      .jdbc(jdbcUrl, clientTableName, connectionProperties)
  }


  @Test
  def sqlMigration() =  {
    import spark.implicits._
    import org.apache.spark.sql.functions._

    val df = spark.read
      .jdbc(url = jdbcUrl,
        table = clientTableName,
        properties = connectionProperties)

    val incrementId: (Int => Int) = (arg: Int) => { arg + 1000}
    val idUdf = udf(incrementId)

    val mappedDf = df.withColumn("id", idUdf($"id"))

    mappedDf.union(df).show(10, false)

    // Write both old and mapped values to a new table
    mappedDf.write
      .option("createTableColumnTypes", "id BIGINT, firstName VARCHAR(128), lastName VARCHAR(128), age INT")
      .mode(SaveMode.Overwrite)
      .jdbc(jdbcUrl, clientTableName + "_map", connectionProperties)
  }
}



