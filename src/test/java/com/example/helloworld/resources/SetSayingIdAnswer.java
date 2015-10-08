package com.example.helloworld.resources;

import com.example.helloworld.core.Saying;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

class SetSayingIdAnswer implements Answer<Saying> {
    private final long id;

    public SetSayingIdAnswer(long id) {
        this.id = id;
    }

    @Override
    public Saying answer(InvocationOnMock invocationOnMock) throws Throwable {
        Saying s = (Saying)invocationOnMock.getArguments()[0];
        s.setId(id);
        return s;
    }
}
