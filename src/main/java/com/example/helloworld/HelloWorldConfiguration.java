package com.example.helloworld;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Setter;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;


@Getter @Setter
public class HelloWorldConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String template;

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";
}
