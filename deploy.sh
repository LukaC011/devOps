#!/bin/bash
set -euo pipefail

HEALTH_ATTEMPTS=10
HEALTH_DELAY=3

http_status() {
    curl -s -o /dev/null -w "%{http_code}" --max-time 5 "$1" 2>/dev/null || true
}

wait_for_ok() {
    local url=$1
    local attempt=1
    while [ "$attempt" -le "$HEALTH_ATTEMPTS" ]; do
        if [ "$(http_status "$url")" = "200" ]; then
            return 0
        fi
        sleep "$HEALTH_DELAY"
        attempt=$((attempt + 1))
    done
    return 1
}

if [ ! -f .env ]; then
    echo "Nema .env fajla, kreiram ga iz .env.example"
    cp .env.example .env
fi

echo "Pocinje lokalni deploy..."
docker compose up -d --build --wait --wait-timeout 600

echo "Pokrecem post-deploy health check..."
failed=0
for entry in api-gateway:8080 employee-service:8081 client-service:8082 task-service:8083 notification-service:8084; do
    name=${entry%%:*}
    port=${entry##*:}
    if wait_for_ok "http://localhost:${port}/actuator/health"; then
        echo "  ${name} je zdrav"
    else
        echo "  ${name} nije zdrav (poslednji odgovor: $(http_status "http://localhost:${port}/actuator/health"))"
        failed=1
    fi
done

if [ "$failed" -ne 0 ]; then
    echo "Deploy nije uspeo, bar jedan servis nije zdrav."
    docker compose ps
    exit 1
fi

echo "Proveravam rutiranje kroz gateway..."
if ! wait_for_ok "http://localhost:8080/api/employees"; then
    echo "Gateway ne rutira ispravno (poslednji odgovor: $(http_status http://localhost:8080/api/employees))."
    exit 1
fi

echo ""
echo "Deploy uspesan. Svih pet servisa je zdravo."
echo "  Gateway      http://localhost:8080/api"
echo "  Grafana      http://localhost:3000"
echo "  Prometheus   http://localhost:9090"
echo "  Zipkin       http://localhost:9411"
echo "  RabbitMQ     http://localhost:15672"
