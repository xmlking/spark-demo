import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.time.LocalDateTime


plugins {
    kotlin("jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

val sparkVersion: String by project
val scalaVersion: String by project
val kotlinSparkApiVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    //  Kotlin Spark API dependency
    implementation("org.jetbrains.kotlinx.spark:kotlin-spark-api-3.2:$kotlinSparkApiVersion")
    // Spark SQL subsumes Spark Core
    implementation("org.apache.spark:spark-sql_$scalaVersion:$sparkVersion")
    // Spark Avro support
    implementation("org.apache.spark:spark-avro_$scalaVersion:$sparkVersion")

    // Spark test dependencies
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<ShadowJar> {
    manifest {
        attributes(mapOf(
            "Main-Class" to "org.mycompany.spark.AvroJobKt",
            "Implementation-Version" to archiveVersion,
            "Build-Time" to LocalDateTime.now().toString()
        ))
    }

    dependencies {
        exclude { it.moduleGroup == "org.apache.spark" || it.moduleGroup == "org.scala-lang" }
    }
}

application {
    mainClass.set("org.mycompany.spark.AvroJobKt")
}