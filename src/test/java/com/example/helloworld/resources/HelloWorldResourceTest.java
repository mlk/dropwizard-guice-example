package com.example.helloworld.resources;

import com.example.helloworld.core.Saying;
import com.example.helloworld.services.SayingDao;
import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelloWorldResourceTest {

    @Test
    public void whenNameIsAbsentThenUseTheDefaultName() {
        SayingDao service = mock(SayingDao.class);
        when(service.persist(anyObject())).thenAnswer(new SetSayingIdAnswer(123));

        HelloWorldResource subject = new HelloWorldResource("template %s", "defaultName", service);

        Saying actual = subject.sayHello(Optional.<String>absent());

        Saying expected = new Saying(123, "template defaultName");

        assertThat(actual, is(expected));
    }

    @Test
    public void whenNamePresentThenUseTheGivenName() {
        SayingDao service = mock(SayingDao.class);
        when(service.persist(anyObject())).thenAnswer(new SetSayingIdAnswer(1234));

        HelloWorldResource subject = new HelloWorldResource("template %s", "defaultName", service);

        Saying actual = subject.sayHello(Optional.of("name"));

        Saying expected = new Saying(1234, "template name");

        assertThat(actual, is(expected));
    }
}