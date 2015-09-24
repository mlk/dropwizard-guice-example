package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.Saying;
import com.example.helloworld.services.IdService;
import com.google.common.base.Optional;
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

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
	private final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);

    private final String template;
    private final String defaultName;
    private final IdService idService;

    @Inject
    public HelloWorldResource(@Named("template") String template, @Named("defaultName") String defaultName, IdService idService) {
    	logger.info("Creating a new HelloWorldResource!");

        this.template = template;
        this.defaultName = defaultName;
        this.idService = idService;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(idService.nextId(),
                          String.format(template, name.or(defaultName)));
    }

    @PreDestroy
    void destroy() {
    	logger.info("Destroying HelloWorldResource... :(");
    }
}