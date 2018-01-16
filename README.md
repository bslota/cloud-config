# About

This is a set of microservices created in order to present how to manage
configuration in a distributed environment. Project consists of following
services:
 - Discovery Server
 - Product Manager
 - Customer Manager
 - Gateway
 
# Building

In order to build all services at once, run following command in project's
root directory:

```
mvn clean install
```

If you want to build services one by one, run the same command in a proper
subdirectory.

# Running

You can run all services on your local machine by performing one of the
following commands in every subdirectory. 

_Please note that __Discovery Server__ should be run first_

```
mvn spring-boot:run
```

```
java -jar {application}-{version}.jar
```

Where _{application}_ stands for application name and _{version}_ is
current build version.

## Docker

There is also a _Dockerfile_ prepared for each service and a
_docker-compose.yml_ file in a root directory. In order to setup
whole environment, run:

```
docker-compose up
```