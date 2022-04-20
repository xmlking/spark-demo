package org.mycompany.spark

import org.jetbrains.kotlinx.spark.api.*

//import org.jetbrains.kotlinx.spark.api.tuples.*


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    val logFile = "README.md" // Change to your Spark Home path
    withSpark(appName = "Count Lines", /*master = "yarn", logLevel = SparkLogLevel.DEBUG*/){
        spark.read().textFile(logFile).withCached {
            val numAs = filter { it.contains("a") }.count()
            val numBs = filter { it.contains("b") }.count()
            println("Lines with a: $numAs, lines with b: $numBs")
        }
    }
}
