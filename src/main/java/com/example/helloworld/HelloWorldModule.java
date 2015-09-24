package com.example.helloworld;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

import javax.inject.Named;

public class HelloWorldModule extends AbstractModule {
	private final HibernateBundle<HelloWorldConfiguration> hibernateBundle;

    public HelloWorldModule(HibernateBundle<HelloWorldConfiguration> hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }


    @Override
	protected void configure() {

	}

    @Provides
    public SessionFactory providesFactory() {
        return hibernateBundle.getSessionFactory();
    }

	@Provides
	@Named("template")
	public String provideTemplate(HelloWorldConfiguration configuration) {
		return configuration.getTemplate();
	}

	@Provides
	@Named("defaultName")
	public String provideDefaultName(HelloWorldConfiguration configuration) {
        return configuration.getDefaultName();
	}


}
