# Apache Spark

Spark Batch examples.

* DemoJob - count lines in this `README.md` file.
* AvroJob - load avro file, transform and save to avro file.

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
sdk use java 11.0.14-zulu 

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
    --packages org.apache.spark:spark-avro_2.12:3.2.0,com.google.cloud.bigdataoss:gcs-connector:hadoop3-2.2.6 \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar

spark-submit \
    --class org.mycompany.spark.AvroJobKt \
    --master local \
    --properties-file application.properties \
    --packages org.apache.spark:spark-avro_2.12:3.2.0 \
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
    --packages org.apache.spark:spark-avro_2.12:3.2.0 \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar
    
nohup spark-submit \
    --class corg.mycompany.spark.AvroJobKt \
    --master yarn \
    --queue abcd \
    --num-executors 2 \
    --executor-memory 2G \
    --properties-file application-prod.properties \
    --packages org.apache.spark:spark-avro_2.12:3.2.0 \
    build/libs/spark-demo-0.1.0-SNAPSHOT-all.jar arg1 arg2 > app.log 2>&1 &
```
 
### Google Cloud

```shell
gsutil mb gs://my-demo-bucket
gsutil cp data/in/account.avro gs://my-demo-bucket/in

gsutil cp target/word-count-1.0.jar gs://${BUCKET_NAME}/java/spark-demo-0.1.0-SNAPSHOT-all.jar
```

```shell
GCP_PROJECT=<gcp-project-id> \
REGION=<region>  \
SUBNET=<subnet>   \
GCS_STAGING_LOCATION=<gcs-staging-bucket-folder> \
HISTORY_SERVER_CLUSTER=<history-server> \

gcloud dataproc jobs submit spark \
    --cluster=${CLUSTER} \
    --class=dataproc.codelab.WordCount \
    --jars=gs://${BUCKET_NAME}/java/spark-demo-0.1.0-SNAPSHOT-all.jar \
    --region=${REGION} \
    -- gs://${BUCKET_NAME}/input/ gs://${BUCKET_NAME}/output/
```

## Reference 
- [Introducing Kotlin for Apache Spark Preview](https://blog.jetbrains.com/kotlin/2020/08/introducing-kotlin-for-apache-spark-preview/)
- [Code Examples](https://github.com/JetBrains/kotlin-spark-api/tree/main/examples/src/main/kotlin/org/jetbrains/kotlinx/spark/examples)
- [Processing AVRO data using Google Cloud DataProc](https://sourabhsjain.medium.com/processing-avro-data-using-google-cloud-dataproc-86352e70e50d)