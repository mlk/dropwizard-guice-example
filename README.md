dropwizard-guice-example
========================

This is an example of using Dropwizard with:

 * Google Guice (4.0)
 * Static Resources 
 * Project Lombok
 * Hibernate (using Derby as the data store)
 * Serving legacy servlets.


The primary goal of this repo is to help me to get my head round using Dropwizard with Guice. 


```
mvn clean package
java -jar target/hello-guice-*.jar server hello-world.yml
```
