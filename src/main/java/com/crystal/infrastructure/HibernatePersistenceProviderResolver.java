package com.crystal.infrastructure;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.jpa.HibernatePersistenceProvider;

public class HibernatePersistenceProviderResolver implements PersistenceProviderResolver {
    private static final Logger LOGGER = Logger.getLogger(HibernatePersistenceProviderResolver.class.getName());

    private volatile PersistenceProvider persistenceProvider = new HibernatePersistenceProvider();

    public List<PersistenceProvider> getPersistenceProviders() {
        return Collections.singletonList(persistenceProvider);
    }

    public void clearCachedProviders() {
        persistenceProvider = new HibernatePersistenceProvider();
    }

    public static void register() {
        try {
            LOGGER.info("Registering HibernatePersistenceProviderResolver");
            HibernatePersistenceProviderResolver provider = new HibernatePersistenceProviderResolver();
            PersistenceProviderResolverHolder.setPersistenceProviderResolver(provider);
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }
}
