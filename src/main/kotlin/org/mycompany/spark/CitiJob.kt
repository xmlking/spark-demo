package org.mycompany.spark

import org.jetbrains.kotlinx.spark.api.*

fun main() {

    data class Coordinate(val lon: Double, val lat: Double)
    data class City(val name: String, val coordinate: Coordinate)
    data class CityPopulation(val city: String, val population: Long)

    withSpark(appName = "Find biggest cities to visit") {
        val citiesWithCoordinates = dsOf(
            City("Moscow", Coordinate(37.6155600, 55.7522200)),
            // ...
        )

        val populations = dsOf(
            CityPopulation("Moscow", 11_503_501L),
            // ...
        )
        citiesWithCoordinates.rightJoin(populations, citiesWithCoordinates.col("name") `===` populations.col("city"))
            .filter { (_, citiesPopulation) ->
                citiesPopulation.population > 15_000_000L
            }
            .map { (city, _) ->
                // A city may potentially be null in this right join!!!
                city?.coordinate
            }
            .filterNotNull()
            .show()
    }
}