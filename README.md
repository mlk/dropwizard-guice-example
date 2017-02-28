dropwizard-guice-example
========================

This is an example of using Dropwizard with:

 * Google Guice (4.0)
 * Static Resources 
 * Project Lombok
 * Hibernate (using Derby as the data store)
 * Serving legacy servlets
 * Serving (Guiced up) WebSockets with Atmosphere

The primary goal of this repo is to help me to get my head round using Dropwizard with Guice. 


```
mvn clean package
java -jar target/hello-guice-*.jar server hello-world.yml
```

Atmosphere, Guice and Dropwizard
--------------------------------
Atmosphere has a Guice bundle which I attempted to use but spat out a bunch of errors. As such I ended up writing basic Guice AtmosphereObjectFactory.
However I'm not convinced this is the correct way to do this. Please do fork and update.



[![Build Status](https://travis-ci.org/mlk/dropwizard-guice-example.svg?branch=master)](https://travis-ci.org/mlk/dropwizard-guice-example)

.
