package com.example.helloworld;

import com.example.helloworld.core.Saying;
import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.legacy.ExampleHttpServlet;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.ServletHolder;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

	public static void main(String[] args) throws Exception {
		new HelloWorldApplication().run(args);
	}


    private final HibernateBundle<HelloWorldConfiguration> hibernate = new HibernateBundle<HelloWorldConfiguration>(Saying.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    private GuiceBundle<HelloWorldConfiguration> guiceBundle = null;

	@Override
	public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));

        bootstrap.addBundle(hibernate);

        guiceBundle = GuiceBundle.<HelloWorldConfiguration>newBuilder()
				.addModule(new HelloWorldModule(hibernate))
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(HelloWorldConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
	}

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void run(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception {
        environment.healthChecks().register("template", guiceBundle.getInjector().getInstance(TemplateHealthCheck.class));
        environment.getApplicationContext().addServlet(new ServletHolder(guiceBundle.getInjector().getInstance(ExampleHttpServlet.class)), "/legacy/servlet");

    }
}
