package com.nonradioactive.spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LocalConfiguration {

    private Properties sqlProperties;
    private String sqlUrl;

    private String elasticNode;
    private String elasticPort;
    private boolean esWanOnly;
    private boolean esAutoCreateIndex = true;

    private LocalConfiguration(String sqlUrl, Properties sqlProperties, String elasticNode, String elasticPort, boolean esWanOnly) {
        this.sqlUrl = sqlUrl;
        this.sqlProperties = sqlProperties;
        this.elasticNode = elasticNode;
        this.elasticPort = elasticPort;
        this.esWanOnly = esWanOnly;
    }

    public static LocalConfiguration DOCKER() {
        Properties sqlProperties = new Properties();
        sqlProperties.put("user", "user");
        sqlProperties.put("password", "password");
//        System.getProperties().forEach((k,v)  -> System.out.println("key = " + k + " : " + v));
//        System.getenv().forEach((k,v)  -> System.out.println("key = " + k + " : " + v));

        return new LocalConfiguration("jdbc:mysql://localhost:3306", sqlProperties, "localhost", "9200", true);
    }

    public Properties getSqlProperties() {
        return sqlProperties;
    }

    public String getJdbcUrl() {
        return sqlUrl;
    }

    public Map<String, String> getElasticSettings() {
        HashMap<String, String> connectionSettings = new HashMap<>();
        connectionSettings.put("es.nodes", elasticNode);
        connectionSettings.put("es.port", elasticPort);
        connectionSettings.put("es.nodes.wan.only", "" + esWanOnly);
        connectionSettings.put("es.index.auto.create", "" + esAutoCreateIndex);
        return connectionSettings;
    }

}
