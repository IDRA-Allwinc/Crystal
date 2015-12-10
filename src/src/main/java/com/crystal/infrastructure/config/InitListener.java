package com.crystal.infrastructure.config;

import javax.servlet.ServletContextEvent;

public class InitListener implements javax.servlet.ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0) {
    }
    public void contextInitialized(ServletContextEvent arg0) {
        HibernatePersistenceProviderResolver.register();
    }
}