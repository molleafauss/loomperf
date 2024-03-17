## Spring+Project Loom sample
This project is a quick app to show how much more "scalability" [project loom](https://openjdk.org/projects/loom/) can provide to a vanilla spring app.

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
Results with images are in the [results/results.md] file.

### Running
To run locally, you will need:
* a running MySQL or MariaDB - can be installed locally, or run via docker 
  * [MySQL](https://hub.docker.com/_/mysql)
  * [MariaDB](https://hub.docker.com/_/mariadb)
* running the local mockserver - can be run via maven with the command
  * `mvn org.mock-server:mockserver-maven-plugin:5.15.0:run`
* running locust - you will need a recent python (3.10+) and install locust as per its documentation
  * the locust script can be run via `locust -H http://localhost:8080`
* The application can be run via the usual `mvn spring-boot:run`
  * ensure the database connection is correct in the spring application config
  * To switch native thread vs virtual thread (loom), change the `spring.threads.virtual.enabled` config