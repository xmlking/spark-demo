# Apache Spark

Spark Batch examples.

* DemoJob - count total
* LoadJob - load two cvs tables, left join and output nested json.

## Prerequisites
```shell
# spark/hadoop currently don't support Java 17
sdk install java 11.0.14-zulu
sdk use java 11.0.14-zulu 
# install `spark-shell`, `spark-submit` cli
sdk install spark
```

## Build

```shell
gradle build
# skip tests
gradle build -x test
```

## Run

### Running Locally

> In IDEs like IntelliJ, you can run `main` method directly.

```shell
gradle run 
# passing arguments for main method
gradle run --args="lorem ipsum dolor"
```

Or via spark-submit

```shell
# Submit Local
spark-submit \
    --class org.mycompany.spark.AvroJobKt \
    --master local \
    --properties-file application.properties \
     build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar
    
spark-submit \
    --class org.mycompany.spark.LoadJobKt \
    --master local \
    --properties-file application.properties \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar
```


### Launching on a Cluster

```shell
# Submit to Cluster
spark-submit \
    --class org.mycompany.spark.AvroJobKt \
    --master spark://localhost:7077 \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar

spark-submit \
    --class org.mycompany.spark.AvroJobKt \
    --master spark://localhost:7077 \
    --properties-file application-prod.properties \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar
    
nohup spark-submit \
    --class corg.mycompany.spark.AvroJobKt \
    --master yarn \
    --queue abcd \
    --num-executors 2 \
    --executor-memory 2G \
    --properties-file application-prod.properties \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar arg1 arg2 > app.log 2>&1 &
```
 

## Reference 
- [Introducing Kotlin for Apache Spark Preview](https://blog.jetbrains.com/kotlin/2020/08/introducing-kotlin-for-apache-spark-preview/)
- [Code Examples](https://github.com/JetBrains/kotlin-spark-api/tree/main/examples/src/main/kotlin/org/jetbrains/kotlinx/spark/examples)