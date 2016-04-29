docker.image('cloudbees/java-build-tools:0.0.6').inside {
	checkout scm
	stage 'Build Web App'
	sh 'mvn clean package'
}
