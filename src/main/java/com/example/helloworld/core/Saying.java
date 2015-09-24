package com.example.helloworld.core;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value @AllArgsConstructor
public class Saying {
    private final long id;
    private final String content;
}