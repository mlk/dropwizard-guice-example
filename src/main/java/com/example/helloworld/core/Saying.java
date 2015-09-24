package com.example.helloworld.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data @AllArgsConstructor @Entity @Table(name = "sayings")
public class Saying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;

    public Saying() {
    }

    public Saying(String content) {
        this.content = content;
    }
}