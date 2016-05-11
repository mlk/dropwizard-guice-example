stage 'Checkout'
node{
  def kubectl = env.KUBE_CONTROL
  def kubeServer = env.KUBE_SERVER
  def kubeUsername = env.KUBE_USERNAME
  def kubePassword = env.KUBE_PASSWORD  

  checkout scm

  stage 'Build application'
  sh 'mvn clean package'

  stage 'Build docker image'
  sh 'mvn docker:build'

  sh "$kubectl --insecure-skip-tls-verify=true --server=$kubeServer --username=$kubeUsername --password=$kubePassword update -f src/main/kube/full-stack.yml"
}
