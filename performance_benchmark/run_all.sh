#!/bin/bash

set -o errexit

if [ "x$1" = "x" ] ; then
	echo "Parameters : 
  <base_url> <number of repeats> <req_count> <interval> 
eg
http://localhost:4567/ 10  100 1 
would make 10 sets of 100 requests with a 1 sec pause between sets
"
exit
fi

run() {
  # input parameters
  local base_url=$1
  local repeat=$2
  local req_count=$3
  local interval=$4
  
  # file/output references
  local test_output_dir="/tmp/taskapp_test"
  local ref=`date +"%Y%m%d_%H%M%S"`
  local output_file="$test_output_dir/$ref.out"
  local script_dir=`dirname $0`

  echo $output_file

  # API
  local taskapi_url="$base_url/task"
  local payload="'{\"description\":\"whatever description\",\"resolved\":false}'"
  local content_type="Content-Type: application/json"
  
  mkdir -p $test_output_dir

  for i in `seq 1 $repeat`; do

    $script_dir/performance_test.sh \
      $taskapi_url $req_count \
      "-X POST -H \"$content_type\" \
      -d $payload" >> "$output_file"

    sleep $interval
  done

  local total_avg=`awk -F "," '{ total += $3; count++ } END { print total/count }' < $output_file`
  echo "Total Average: $total_avg"

}

run "$@"
