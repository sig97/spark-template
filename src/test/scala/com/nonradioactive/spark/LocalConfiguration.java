package com.nonradioactive.spark;

import java.util.Properties;

public class LocalConfiguration {

    private Properties sqlProperties;
    private String sqlUrl;

    private LocalConfiguration(String sqlUrl, Properties sqlProperties) {
        this.sqlUrl = sqlUrl;
        this.sqlProperties = sqlProperties;
    }

    public static LocalConfiguration DOCKER() {
        Properties sqlProperties = new Properties();
        sqlProperties.put("user", "user");
        sqlProperties.put("password", "password");
//        System.getProperties().forEach((k,v)  -> System.out.println("key = " + k + " : " + v));
//        System.getenv().forEach((k,v)  -> System.out.println("key = " + k + " : " + v));

        return new LocalConfiguration("jdbc:mysql://localhost:3306", sqlProperties);
    }

    public Properties getSqlProperties() {
        return sqlProperties;
    }

    public String getJdbcUrl() {
        return sqlUrl;
    }
}
