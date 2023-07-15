#!/bin/sh

(cd deployment && docker compose down)

git pull
(cd deployment \
  && docker compose pull \
  && docker compose up -d)
