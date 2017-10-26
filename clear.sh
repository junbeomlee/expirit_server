#!/bin/sh

PID=$(sudo lsof -t  -i:9090)

if [ "" !=  "$PID" ]; then
  echo "killing $PID"
  sudo kill -9 $PID
fi

