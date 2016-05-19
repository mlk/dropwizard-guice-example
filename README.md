dropwizard-guice-example
========================

This branch is to play with using Jenkins with a Jenkinsfile to defined the build pipeline and deploy the stack onto a Kubernates cluster.

The Jenkinsfile assumes the following:

* Docker is installed and has the DOCKER_* enviroment variables set correctly in Jenkins.
* You have Maven installed on the Jenkins box
* You have installed the Kubernates kubectl binaries on the Jenkins box.
* You have set up the `KUBE_CONTROL` (binary location), `KUBE_SERVER`, `KUBE_USERNAME` and `KUBE_PASSWORD` enviroment variables set correctly in Jenkins. This is not production ready, and should use the kube config file pushed onto the jenkins box some how.
* You have a local nexus with docker support running on `192.168.99.100:18443`
 * you have set up  secrets for Kubernates (with a default docker install: `kubectl --namespace=live create secret docker-registry myregistrykey --docker-server=192.168.99.100:18443 --docker-username=admin --docker-password=admin123 --docker-email=DOCKER_EMAIL@example.com && kubectl --namespace=default create secret docker-registry myregistrykey --docker-server=192.168.99.100:18443 --docker-username=admin --docker-password=admin123 --docker-email=DOCKER_EMAIL@example.com`)
 * you have either
   * used a proper CA
   * Installed the CA in the right location on each of the hosts
   * Changed docker to ignore `192.168.99.100:18443` by adding `--insecure-registry=192.168.99.100:18443` to the docker deamon. Using RancherOS this is done using the following command `sudo ros c set rancher.docker.extra_args ['--insecure-registry=192.168.99.100:18443'] && sudo system-docker restart docker`
* You having logged into `192.168.99.100:18443` on docker on the jenkins box as the jenkins user.

**Gothca's**

* Kube requires the regisrty key to be set up of each name space.

**TODOs**
While this branch has surved my needs, it has left a number of questions and improvements that I am unlikely to look into:

* Not use the KUBE_* enveroment variables and instead use a `tool` with the configuration file set up properly
* Question how the docker bit works. What about with slaves, should this really need the box to be pre-logged into the nexus repo?
* What should happen to the manged full-stack.yml file?
* How should the database be handled? 
 * Not HA - What storage should be used?
 * Built via Hibernate (is that good for production?)
* The Chat page (WebSockets with Atmosphere) is not clustered. This [can be done](https://github.com/Atmosphere/atmosphere/wiki/Configuring-Atmosphere-for-the-Cloud), but was out of scope for this work.


Building & running locally
--------------------------

```
mvn clean package
java -jar target/hello-guice-*.jar server hello-world.yml
```

[![Build Status](https://travis-ci.org/mlk/dropwizard-guice-example.svg?branch=master)](https://travis-ci.org/mlk/dropwizard-guice-example)
