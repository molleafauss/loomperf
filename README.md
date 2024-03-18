# Spring+Project Loom sample
This project is a quick app to show how much more "scalability" [project loom](https://openjdk.org/projects/loom/) can provide to a vanilla spring app.

## Quick start

```bash
docker run --name mariadb-loom -e MYSQL_ROOT_PASSWORD=admin -e MARIADB_DATABASE=loom -p 3306:3306 -d mariadb:latest
docker run --name mockserver-loom -d --rm -p 1080:1080 -v $PWD/src/test/resources:/init --env MOCKSERVER_LOG_LEVEL=INFO --env MOCKSERVER_SERVER_PORT=1080 --env MOCKSERVER_INITIALIZATION_JSON_PATH=/init/mockserver.json mockserver/mockserver:5.15.0
docker run --name locust-loom -d --network="host" -v $PWD:/mnt/locust locustio/locust -f /mnt/locust/locustfile.py -H http://127.0.0.1:8080
mvn spring-boot:run -Dspring.threads.virtual.enabled=true
```
 
The application is purposefully very simple, but it tries to replicate a common scenario in (micro)services:

> an HTTP request comes in, an external service is called and a row is added to a database

This is a scenario that normally can be scaled by tweaking and increasing the thread pool size that server the 
incoming HTTP requests; on purpose, no defaults were changed in the spring application configuration, only the
flag that enables virtual threads (`spring.threads.virtual.enabled`) - to have a like for like comparison and 
also .

The external service call has been simulated via [MockServer](https://www.mock-server.com/); the external request
adds a fixed delay of 500ms to the responses, to simulate on purpose "long" pauses which are common in scenarios 
where your service has a dependency which has (long)er response times.

A [locust](https://locust.io/) script is included to cause load on the system.

### Results
[The result directory](results/results.md) contains, commented, the results of the tests run.

### Running
To run locally, you will need:
* a running MySQL or MariaDB - can be installed locally, or run via docker 
  * [MySQL](https://hub.docker.com/_/mysql)
  * [MariaDB](https://hub.docker.com/_/mariadb)
* running the local mockserver - can be run via maven with the command
  * `mvn process-test-resources org.mock-server:mockserver-maven-plugin:5.15.0:run`
* running locust - you will need a recent python (3.10+) and install locust as per its documentation
  * the locust script can be run via `locust -H http://localhost:8080`
* The application can be run via the usual `mvn spring-boot:run`
  * ensure the database connection is correct in the spring application config
  * To switch native thread vs virtual thread (loom), change the `spring.threads.virtual.enabled` config