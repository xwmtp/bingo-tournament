#!/bin/bash

echo "$2" | docker login docker.pkg.github.com -u "$1" --password-stdin

IMAGE_NAME=bingo-tournament
IMAGE_ID=docker.pkg.github.com/xwmtp/bingo-tournament/$IMAGE_NAME

docker build . --tag $IMAGE_NAME
docker tag $IMAGE_NAME $IMAGE_ID:latest
docker push $IMAGE_ID:latest

docker logout
