package org.mycompany.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SaveMode
import org.jetbrains.kotlinx.spark.api.*

fun main() {
    withSpark(appName = "Transform the avro data") {
        val input = spark.sparkContext().conf.get("spark.myapp.input", "data/in")
        val output = spark.sparkContext().conf.get("spark.myapp.output", "data/out")

        spark.read().format("avro").load("$input/*.avro").
        select("name", "avatar").
        write().format("avro").mode(SaveMode.Overwrite).save("$output/account-out")
    }
}