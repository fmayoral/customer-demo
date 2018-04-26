# Customer management demo project
## Requirements:
* Maven
* Java 8

## Install it
```
mvn clean install
```

## Run it
```
mvn tomcat7:run
```

## Run tests
```
mvn test
```

## Use it
Visit http://localhost:9090/

### Review it
Java code: `/src/main/java`

Persistence config: `src/main/resources`

Tests: `/src/main/test`

Web app: `src/main/webapp`


#### Features
* View customer list (first name, last name, status)
* Sort customer list (any attribute)
* Filter customer list (client-side, any attribute)
* Filter customer results (server side, by status)
* Update customer status
* Create customer note

#### Features Missing
* Update a customer note (the feature is there just not exposed in the UI)
* Integration Tests