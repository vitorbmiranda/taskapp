# TaskApp - SpringBoot

## Running the App

Either on the command line or within the IDE:

`mvn spring-boot:run`

## Request Examples

```
curl -X POST -v localhost:8080/task -H "Content-Type: application/json" -d '{"description":"first description","resolved":false}'
curl -X GET -v localhost:8080/task
curl -X GET -v localhost:8080/task/1
curl -X PUT -v localhost:8080/task/1 -H "Content-Type: application/json" -d '{"description":"changed description","resolved":true}'
curl -X PATCH -v localhost:8080/task/1 -H "Content-Type: application/json" -d '{"description":"patched description"}'
curl -X DELETE -v localhost:8080/task/1
```
