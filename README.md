# _Spark-Template_
A sample Apache Spark project using Scala and / or Java, built using gradle. Also included: Zeppelin notebook, DB server and sample scripts.  


## Prerequisites
- [Java](https://java.com/en/download/)
- [Gradle](https://gradle.org/)
- [Scala](https://www.scala-lang.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose Tools](https://docs.docker.com/compose/install/)

If running Docker from Ubuntu on Windows, make sure to update base mount point in wsl.conf (https://nickjanetakis.com/blog/setting-up-docker-for-windows-and-wsl-to-work-flawlessly#ensure-volume-mounts-work)  

## Running sample Spark application

### Build
`./gradlew clean build`
### Run main sample application
`./gradlew run`
### Launch docker-compose services and run integration tests
`./gradlew dockerTest`
### Launch all docker services (DB and Zeppelin notebook)
`docker-compose up --build`
### Run integration tests using pre-running docker DB
`./gradlew integrationTest`


## Running Zeppelin notebook
`docker-compose up --build`

The notebook is located at http://localhost:9080/#/notebook/

## Useful Links
- [Spark Docs - Root Page](http://spark.apache.org/docs/latest/)
- [Spark Programming Guide](http://spark.apache.org/docs/latest/programming-guide.html)
- [Spark Latest API docs](http://spark.apache.org/docs/latest/api/)
- [Scala API Docs](http://www.scala-lang.org/api/2.12.1/scala/)
 
