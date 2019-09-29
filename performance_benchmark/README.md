# TaskApp

## Performance Test

Shellscript utilitary that will run multiple parallel curl requests and measure the average response time.

## Running it

`/path/to/script/run_all.sh <url> <req_count> <repeat_count> <interval>`

Will run `repeat_count` tests against the `url` for `req_count` times with an `interval` in seconds between each repetition.

E.g: `/Users/vitor/workspace/taskapp_performance/run_all.sh http://localhost:8080 10 2 0.5`