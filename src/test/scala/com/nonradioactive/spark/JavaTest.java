package com.nonradioactive.spark;

import org.apache.log4j.LogManager;
import org.apache.spark.api.java.function.ReduceFunction;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JavaTest {

    private static SparkSession spark;

    @BeforeAll
    public static void setUp() {
        spark = SparkSession.builder()
                .appName("Spark test")
                .master("local[*]")
                .getOrCreate();

        spark.sparkContext().setLogLevel("ERROR");
        Logger.getLogger("org").setLevel(Level.WARNING);
        Logger.getLogger("akka").setLevel(Level.WARNING);
        LogManager.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
    }

    @Test
    public void sparkTest() {
        Long sumHundred = spark.range(1, 101).reduce((ReduceFunction<Long>) (v1, v2) -> v1 + v2);

        assertEquals(5050, sumHundred.longValue());
    }


}
