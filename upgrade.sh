#!/bin/sh

git pull
(cd deployment && sudo docker-compose build --pull && sudo docker-compose up -d)
