package com.example.helloworld.services;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class IdService {
    private final AtomicLong counter = new AtomicLong();

    public long nextId() {
        return counter.incrementAndGet();
    }
}
