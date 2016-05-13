stage 'Checkout'
node{
  def kubectl = env.KUBE_CONTROL
  def kubeServer = env.KUBE_SERVER
  def kubeUsername = env.KUBE_USERNAME
  def kubePassword = env.KUBE_PASSWORD  
  def localTag = "${env.BRANCH_NAME}.${env.BUILD_NUMBER}"

  checkout scm

  stage 'Build application'
  sh 'mvn clean package'
  step([$class: 'JUnitResultArchiver', testResults: '**/target/*/TEST-*.xml'])


  stage 'Build docker image'
  sh "mvn docker:build -DlocalTag=${localTag}"

  stage 'Validating image'
  echo 'Run validation here...'
  stage "Push image"
  sh "docker push michaellee/dropwizard-guice-example:${localTag}"

  stage 'Deploy to TEST'
  sh "$kubectl --insecure-skip-tls-verify=true --server=$kubeServer --username=$kubeUsername --password=$kubePassword rolling-update dropwizard-application --image michaellee/dropwizard-guice-example:${localTag}"

  sh "sed 's/IMAGE_TAG/${localTag}/g' src/main/kube/full-stack.yml > src/main/kube/full-stack.${localTag}.yml"
  sh "$kubectl --insecure-skip-tls-verify=true --server=$kubeServer --username=$kubeUsername --password=$kubePassword apply -f src/main/kube/full-stack.${localTag}.yml"

  sh "$kubectl --insecure-skip-tls-verify=true --server=$kubeServer --username=$kubeUsername --password=$kubePassword get services/my-service --output json | jq '.spec.ports[0].nodePort' > THE_PORT"

  def port = readFile 'THE_PORT'
  echo port
}
