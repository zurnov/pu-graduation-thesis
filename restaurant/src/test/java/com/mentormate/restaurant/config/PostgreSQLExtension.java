package com.mentormate.restaurant.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLExtension implements BeforeAllCallback {
    
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        PostgreSQLContainer<RestaurantPostgreSQLContainer> container = RestaurantPostgreSQLContainer.getInstance();
        container.start();
    }
}
