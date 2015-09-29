package com.example.helloworld.resources;

import com.example.helloworld.HelloWorldApplication;
import com.example.helloworld.HelloWorldConfiguration;
import com.example.helloworld.core.Saying;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeThat;

public class ApiIntegrationTest {
    @Rule
    public DropwizardAppRule<HelloWorldConfiguration> RULE =
            new DropwizardAppRule<>(HelloWorldApplication.class,
                    ResourceHelpers.resourceFilePath("integration-test.yml"));

    @Test
    public void whenHelloWorldIsCalledThenWarmlyGreetTheUser() {
        Client client = ClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/api/hello-world", RULE.getLocalPort()))
                .request()
                .post(Entity.form(new Form()));

        Saying actual = response.readEntity(Saying.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(actual.getContent()).isEqualToIgnoringCase("Hello, Stranger!");
    }

    @Test
    public void whenNameIsGivenThenWarmlyGreetByName() {
        Client client = ClientBuilder.newClient();

        Response response = client.target(
                String.format("http://localhost:%d/api/hello-world", RULE.getLocalPort()))
                .request()
                .post(Entity.form(new Form().param("name", "John")));

        Saying actual = response.readEntity(Saying.class);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(actual.getContent()).isEqualToIgnoringCase("Hello, John!");
    }

    @Test
    public void whenHelloWorldIsCalledThenStoreTheGreetings() {
        Client client = ClientBuilder.newClient();
        assumeThat(client.target(String.format("http://localhost:%d/api/hello-world", RULE.getLocalPort()))
                .request()
                .post(Entity.form(new Form())).getStatus(), is(200));

        Response response = client.target(String.format("http://localhost:%d/api/hello-world", RULE.getLocalPort()))
                .request()
                .get();

        List<Saying> actual = response.readEntity(new GenericType<List<Saying>>() { });
        List<Saying> expected = Collections.singletonList(new Saying(1, "Hello, Stranger!"));

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(actual).isEqualTo(expected);

    }
}
