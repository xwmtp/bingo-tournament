#!/bin/sh

git pull
(cd deployment && \
  sudo docker-compose down \
  && sudo docker-compose pull \
  && sudo docker-compose up -d)
