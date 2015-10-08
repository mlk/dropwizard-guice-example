package com.example.helloworld.tasks;

import com.example.helloworld.services.CounterService;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

public class JumpTenCounter extends Task {
    private final CounterService counterService;

    @Inject
    public JumpTenCounter(CounterService counterService) {
        super("jump-10-counter-task");
        this.counterService = counterService;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap, PrintWriter printWriter) throws Exception {
        for (int i = 0; i < 10; i++) {
            counterService.next();
        }
    }
}
