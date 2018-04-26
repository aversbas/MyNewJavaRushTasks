package com.javarush.task.task37.task3709.connectors;

import com.javarush.task.task37.task3709.security.SecurityChecker;

public class SecurityProxyConnector implements Connector {
    private String resourceString;
    private SimpleConnector simpleConnector;
    private SecurityChecker securityChecker;

    public SecurityProxyConnector(String resourceString) {
        this.resourceString = resourceString;
        this.simpleConnector = new SimpleConnector(resourceString);
    }

    @Override
    public void connect() {
       if (securityChecker.performSecurityCheck())
           simpleConnector.connect();
    }
}
