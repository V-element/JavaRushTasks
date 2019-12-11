package com.javarush.task.task37.task3709.connectors;

import com.javarush.task.task37.task3709.security.SecurityChecker;

public class SecurityProxyConnector  implements Connector{
    SecurityChecker securityChecker;
    SimpleConnector simpleConnector;

    public SecurityProxyConnector(String resourceString) {
        simpleConnector = new SimpleConnector(resourceString);
    }

    @Override
    public void connect() {
        boolean b = securityChecker.performSecurityCheck();
        if (b) {
            simpleConnector.connect();
        }
    }
}
