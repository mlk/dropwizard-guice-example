package com.example.helloworld;

import com.example.helloworld.core.Saying;
import com.example.helloworld.ducktape.DodgyGuiceAtmosphereObjectFactory;
import com.example.helloworld.legacy.ExampleHttpServlet;
import com.example.helloworld.resources.HelloWorldChatResource;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletRegistration;

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
        // Static assets bundle.
		bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));

        // Hibernate bundle
        bootstrap.addBundle(hibernate);

        // Guice
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
        // Legacy style servlets.
        environment.getApplicationContext().addServlet(new ServletHolder(guiceBundle.getInjector().getInstance(ExampleHttpServlet.class)), "/legacy/servlet");

        // Guiced Atmosphere (WebSockets)
        AtmosphereServlet servlet = new AtmosphereServlet();
        servlet.framework().addInitParameter(ApplicationConfig.ANNOTATION_PACKAGE, HelloWorldChatResource.class.getPackage().getName());
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");
        servlet.framework().objectFactory(new DodgyGuiceAtmosphereObjectFactory(guiceBundle, servlet.framework().objectFactory()));

        ServletRegistration.Dynamic registration = environment.servlets().addServlet("atmosphere", servlet);
        registration.addMapping("/chat/*");

    }
}
