package org.mycompany.spark

import org.jetbrains.kotlinx.spark.api.*

fun main() {
    val logFile = "data/in/account.avro"
    withSpark(appName = "Transform the avro data") {
        spark.read().format("avro").load(logFile).
        select("name", "avatar").
        write().format("avro").save("data/out/account-out")
    }
}