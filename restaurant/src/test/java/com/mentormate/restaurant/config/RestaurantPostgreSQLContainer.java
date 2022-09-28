package com.mentormate.restaurant.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class RestaurantPostgreSQLContainer extends PostgreSQLContainer<RestaurantPostgreSQLContainer> {
    
    private static final String IMAGE_VERSION = "postgres:13.6";
    private static RestaurantPostgreSQLContainer container;

    public RestaurantPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }
    
    public static RestaurantPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new RestaurantPostgreSQLContainer();
        }
        
        return  container;
    }

    @Override
    public void start() {
        super.start();

        System.out.printf(
                "DB_URL: %s%nDB_USERNAME: %s%nDB_PASSWORD: %s%n",
                container.getJdbcUrl(),
                container.getUsername (),
                container.getPassword());
        
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
