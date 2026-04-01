#!/bin/bash

echo "Pocinje lokalni deploy..."

docker pull ghcr.io/lukac011/employee-service:latest

docker-compose up -d --build

echo "Cekamo 10 sekundi da se servis pokrene..."
sleep 10

echo "Pokrecem post-deploy health check..."
STATUS_CODE=$(curl -o /dev/null -s -w "%{http_code}\n" http://localhost:8080/employees)

if [ "$STATUS_CODE" -eq 200 ]; then
    echo "Deploy uspesan! Servis je dostupan i vraca status 200 OK."
else
    echo "Deploy nije uspeo. Servis je vratio status: $STATUS_CODE"
    exit 1
fi