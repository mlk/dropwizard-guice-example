package com.example.helloworld.resources;

import com.example.helloworld.core.Saying;
import com.example.helloworld.services.IdService;
import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelloWorldResourceTest {

    @Test
    public void whenNameIsAbsentThenUseTheDefaultName() {
        IdService service = mock(IdService.class);
        when(service.nextId()).thenReturn(123L);

        HelloWorldResource subject = new HelloWorldResource("template %s", "defaultName", service);

        Saying actual = subject.sayHello(Optional.<String>absent());

        Saying expected = new Saying(123, "template defaultName");

        assertThat(actual, is(expected));
    }

    @Test
    public void whenNamePresentThenUseTheGivenName() {
        IdService service = mock(IdService.class);
        when(service.nextId()).thenReturn(1234L);

        HelloWorldResource subject = new HelloWorldResource("template %s", "defaultName", service);

        Saying actual = subject.sayHello(Optional.of("name"));

        Saying expected = new Saying(1234, "template name");

        assertThat(actual, is(expected));
    }
}