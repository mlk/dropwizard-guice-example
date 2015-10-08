package com.example.helloworld.tasks;

import com.example.helloworld.services.CounterService;
import com.google.common.collect.ImmutableMultimap;
import com.google.inject.Inject;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

public class ResetCounterTask extends Task {
    private final CounterService counterService;

    @Inject
    public ResetCounterTask(CounterService counterService) {
        super("reset-counter-task");
        this.counterService = counterService;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap, PrintWriter printWriter) throws Exception {
        counterService.reset();
    }
}
