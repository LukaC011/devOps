package com.uris.crmhrm.api_gateway;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.server.mvc.config.FilterProperties;
import org.springframework.cloud.gateway.server.mvc.config.GatewayMvcProperties;
import org.springframework.cloud.gateway.server.mvc.config.PredicateProperties;
import org.springframework.cloud.gateway.server.mvc.config.RouteProperties;

@SpringBootTest
class GatewayRoutesTest {

    @Autowired
    private GatewayMvcProperties gatewayMvcProperties;

    @Test
    void exposesEveryBusinessServiceUnderTheApiPrefix() {
        List<RouteProperties> routes = gatewayMvcProperties.getRoutes();

        assertThat(routes)
                .extracting(RouteProperties::getId)
                .containsExactlyInAnyOrder("employee-service", "client-service", "task-service",
                        "notification-service");
    }

    @Test
    void stripsTheApiPrefixBeforeForwarding() {
        assertThat(gatewayMvcProperties.getRoutes()).allSatisfy(route -> {
            assertThat(route.getPredicates())
                    .extracting(PredicateProperties::getName)
                    .containsExactly("Path");
            assertThat(route.getFilters())
                    .extracting(FilterProperties::getName)
                    .containsExactly("StripPrefix");
        });
    }
}
