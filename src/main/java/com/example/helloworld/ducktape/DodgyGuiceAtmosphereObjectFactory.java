package com.example.helloworld.ducktape;

import com.hubspot.dropwizard.guice.GuiceBundle;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereObjectFactory;

/**
 * I don't know if this is the right way to do this.
 *
 * This class allows Guice to handle the construction of user classes, but the default for the construction of everything else.
 */
public class DodgyGuiceAtmosphereObjectFactory implements AtmosphereObjectFactory<Object> {
    private final GuiceBundle<?> guiceBundle;
    private final AtmosphereObjectFactory<?> originalFactory;
    private AtmosphereConfig config;

    public DodgyGuiceAtmosphereObjectFactory(GuiceBundle<?> guiceBundle, AtmosphereObjectFactory<?> originalFactory) {
        this.guiceBundle = guiceBundle;
        this.originalFactory = originalFactory;
    }

    @Override
    public <T, U extends T> T newClassInstance(Class<T> classType, Class<U> defaultType) throws InstantiationException, IllegalAccessException {
        boolean useGuice = false;
        for(String item : config.framework().packages()) {
            if(defaultType.getPackage().getName().startsWith(item)) {
                useGuice = true;
                break;
            }
        }
        if(useGuice) {
            return guiceBundle.getInjector().getInstance(defaultType);
        }

        return originalFactory.newClassInstance(classType, defaultType);
    }

    @Override
    public AtmosphereObjectFactory allowInjectionOf(Object o) {
        return this;
    }

    @Override
    public void configure(AtmosphereConfig config) {
        this.config = config;
        originalFactory.configure(config);
    }
}
