package com.example.helloworld.services;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class CounterService {
    private final AtomicLong counter = new AtomicLong();

    public long next() {
        return counter.incrementAndGet();
    }
}