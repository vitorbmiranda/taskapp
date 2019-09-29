#!/bin/bash

# https://ops.tips/gists/measuring-http-response-times-curl/

set -o errexit

main () {
  local cmd=$1
  local req_number=$2
  for i in `seq 1 $req_number`; do
    make_request $cmd
  done
}

make_request () {
  local cmd=$1
  local current_time=`date +%s`
  curl \
    --write-out "$current_time,%{http_code},%{time_total}\n" \
    --silent \
    --output /dev/null \
    "$cmd"
}

main "$@"
