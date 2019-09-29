# TaskApp - Spark Java

## Running the App

Run the `App.java` class.

## Request Examples

```
curl -X POST -v localhost:4567/task -H "Content-Type: application/json" -d '{"description":"first description","resolved":false}'
curl -X GET -v localhost:4567/task
curl -X GET -v localhost:4567/task/1
curl -X PUT -v localhost:4567/task/1 -H "Content-Type: application/json" -d '{"description":"changed description","resolved":true}'
curl -X PATCH -v localhost:4567/task/1 -H "Content-Type: application/json" -d '{"description":"patched description"}'
curl -X DELETE -v localhost:4567/task/1
```
