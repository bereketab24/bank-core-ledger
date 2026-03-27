pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
  # Container for Gradle and Java 25 (Runs tests and compiles code)
  - name: jdk25
    image: eclipse-temurin:25-jdk-alpine
    command:
    - cat
    tty: true
  # Container for Kaniko (Daemonless Docker builds)
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    command:
    - sleep
    args:
    - 9999999
  # Container for GitOps operations (Git cloning and Kustomize updates)
  - name: gitops
    image: alpine/git:latest
    command:
    - cat
    tty: true
'''
        }
    }

    environment {
        DOCKER_IMAGE = "bereketab24/bank-core"
        INFRA_REPO = "github.com/bereketab24/bank-infra.git"
    }
    stages {
        stage('Checkout Code') {
            steps {
                checkout scm

                container('gitops'){
                 // Extract the first 7 characters of the Git commit and TRIM the hidden newline
                    script {
                        // Fixing the access issue between containers inside the pipeline
                        sh 'git config --global --add safe.directory "*"'
                        env.GIT_SHA = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    }
                }
            }
        }

        stage('Test & Build Spring Boot App') {
            steps {
                container('jdk25'){
                    sh './gradlew clean build'
                }
            }
        }

        stage('Build & Push Docker Image') {
            when { branch 'main'}
            steps {
                container('kaniko'){
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        sh '''
                            # Create secure Docker config for Kaniko
                            mkdir -p /kaniko/.docker
                            echo "{\\"auths\\":{\\"https://index.docker.io/v1/\\":{\\"auth\\":\\"`echo -n ${DOCKER_USER}:${DOCKER_PASS} | base64`\\"}}}" > /kaniko/.docker/config.json

                            # Build and push the image using the pure Git SHA
                            /kaniko/executor --context `pwd` --dockerfile `pwd`/Dockerfile \
                                --destination ${DOCKER_IMAGE}:${GIT_SHA} \
                                --destination ${DOCKER_IMAGE}:latest
                        '''
                    }
                }
            }
        }
        stage('Update GitOps Repo (Dev Overlay)') {
            when { branch 'main'}
            steps {
                container('gitops'){
                    withCredentials([usernamePassword(credentialsId: 'github-creds', passwordVariable: 'GIT_PAT', usernameVariable: 'GIT_USER')]) {
                        sh '''
                            # 1. Download and install Kustomize into the Alpine container
                            wget https://github.com/kubernetes-sigs/kustomize/releases/download/kustomize%2Fv5.3.0/kustomize_v5.3.0_linux_amd64.tar.gz
                            tar -xzf kustomize_v5.3.0_linux_amd64.tar.gz
                            mv kustomize /usr/local/bin/

                            # 2. Clone bank-infra repo securely
                            git clone https://${GIT_USER}:${GIT_PAT}@${INFRA_REPO} infra-repo

                            # 3. Navigate straight to the DEV overlay
                            cd infra-repo/apps/bank-core-engine/overlays/dev

                            # 4. Use Kustomize to elegantly update the image tag to the new Git SHA
                            kustomize edit set image ${DOCKER_IMAGE}=${DOCKER_IMAGE}:${GIT_SHA}

                            # 5. Commit and push the new tag back to GitHub
                            git config user.name "Jenkins CI"
                            git config user.email "jenkins@bank-cluster.local"
                            git add kustomization.yaml
                            git commit -m "ci: Deploy bank-core commit ${GIT_SHA} to DEV environment"
                            git push origin main
                        '''
                    }
                }
            }
        }
    }

}