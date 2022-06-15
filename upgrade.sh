#!/bin/sh

git pull
./gradlew clean build -x check
sudo docker-compose build --pull
sudo docker-compose up -d
