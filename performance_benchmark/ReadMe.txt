Best way to performance test it is with 
Apache Benchmark.   ----   https://httpd.apache.org/docs/2.4/programs/ab.html 


EG  ab -n 1000 -c 99  http://localhost:4567/task/501 
would send 1000 requests, with a concurrency of 99 to the URL.

If you want to test PUT for example 

With a file called data containing :
{"description":"whatever description","resolved":false, "id":1}

Perf test:::

ab  -n 1000  -c 2 -k  -u data -m PUT -T "application/json" http://localhost:4567/task/1234 


Would make 1000 requests at a concurrency level of 2 with keep alive requests PUT'ing the data to the specific task.
This would be a BAD test as you will have issues with optimistic locking on that resource.
the code does a get in one session and a write in another session - allowing the other request to update in the background



an example output for the GET request

Limited to 99 concurrent requests as the DB connection pool is only set to 100.


ab -n 1000 -c 99  http://localhost:4567/task/501 
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        Jetty(9.4.z-SNAPSHOT)
Server Hostname:        localhost
Server Port:            4567

Document Path:          /task/501
Document Length:        4 bytes

Concurrency Level:      99
Time taken for tests:   0.153 seconds
Complete requests:      1000
Failed requests:        0
Non-2xx responses:      1000
Total transferred:      137000 bytes
HTML transferred:       4000 bytes
Requests per second:    6556.60 [#/sec] (mean)
Time per request:       15.099 [ms] (mean)
Time per request:       0.153 [ms] (mean, across all concurrent requests)
Transfer rate:          877.20 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    5   2.3      4      13
Processing:     2    9   6.8      7      33
Waiting:        1    7   7.0      5      32
Total:          4   14   7.8     11      41

Percentage of the requests served within a certain time (ms)
  50%     11
  66%     14
  75%     16
  80%     18
  90%     25
  95%     33
  98%     38
  99%     39
 100%     41 (longest request)

