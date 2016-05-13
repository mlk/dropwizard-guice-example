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

  stage 'Build docker image'
  sh "mvn docker:build -DlocalTag=${localTag}"
  stage 'Deploy to TEST'
  sh "$kubectl --insecure-skip-tls-verify=true --server=$kubeServer --username=$kubeUsername --password=$kubePassword apply -f src/main/kube/full-stack.yml"

}
