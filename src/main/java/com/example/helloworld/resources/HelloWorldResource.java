package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.Saying;
import com.example.helloworld.services.SayingDao;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);

    private final String template;
    private final String defaultName;
    private final SayingDao sayingDao;

    @Inject
    public HelloWorldResource(@Named("template") String template, @Named("defaultName") String defaultName, SayingDao sayingDao) {
    	logger.info("Creating a new HelloWorldResource!");

        this.template = template;
        this.defaultName = defaultName;
        this.sayingDao = sayingDao;
    }

    @GET
    @Timed
    @UnitOfWork
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return sayingDao.persist(new Saying(String.format(template, name.or(defaultName))));
    }

    @GET
    @Path("/all")
    @Timed
    @UnitOfWork
    public List<Saying> getAllPrevious() {
        return sayingDao.listAll();
    }

    @PreDestroy
    void destroy() {
    	logger.info("Destroying HelloWorldResource... :(");
    }
}