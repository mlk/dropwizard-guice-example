stage 'Checkout'
node {
  checkout scm
  stage 'Build application'
  docker.image('cloudbees/java-build-tools:0.0.6').inside {
	sh 'mvn clean package'
  }
  stage 'Build docker image'
  sh 'mvn docker:build'
}
