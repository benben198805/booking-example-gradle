pipeline {
    agent {
        kubernetes {
            label 'tw-leasing-demo-slave'
            yamlFile 'jenkinsSlavePod.yaml'
            defaultContainer 'jnlp'
        }
    }

    environment {
        IMAG_REPOSITORY = "registry.cn-hangzhou.aliyuncs.com/tw/tw-leasing-demo"
        IMAG_TAG = "${env.Build_TIMESTAMP}-${env.BUILD_NUMBER}"
        SONAR_URL = "${SONAR_URL}"
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }

    stages{
        stage('Package'){
            steps{
                lock(resource: 'gradle_build_tw_leasing_demo', inversePrecedence: true) {
                    sh "./gradlew clean build -Dorg.gradle.daemon=false -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_TOKEN}"
                }
            }
            post {
                always {
                   publishHTML target: [
                            allowMissing         : true,
                            alwaysLinkToLastBuild: true,
                            keepAll              : true,
                            reportDir            : 'build/reports/jacoco/test/html',
                            reportFiles          : 'index.html',
                            reportName           : 'Jacoco Report'
                    ]

                    publishHTML target: [
                            allowMissing         : true,
                            alwaysLinkToLastBuild: true,
                            keepAll              : true,
                            reportDir            : 'build/reports/tests/test',
                            reportFiles          : 'index.html',
                            reportName           : 'Unit Test Report'
                    ]

                    publishHTML target: [
                            allowMissing         : true,
                            alwaysLinkToLastBuild: true,
                            keepAll              : true,
                            reportDir            : 'build/reports/tests/integrationTest',
                            reportFiles          : 'index.html',
                            reportName           : 'Integration Test Report'
                    ]
                }
            }
        }

        stage('Push Image') {
            steps {
                container('docker') {
                    withCredentials([usernamePassword(
                        credentialsId: 'DOCKER_CREDENTIALS',
                        passwordVariable: 'DOCKER_CREDENTIALS_PSW',
                        usernameVariable: 'DOCKER_CREDENTIALS_USR')]) {
                        sh """
                            docker login registry.cn-hangzhou.aliyuncs.com -u ${DOCKER_CREDENTIALS_USR} -p ${DOCKER_CREDENTIALS_PSW}
                            docker build -t ${IMAG_REPOSITORY}:${IMAG_TAG} .
                            docker push ${IMAG_REPOSITORY}:${IMAG_TAG}
                            docker images --filter=reference=\"${IMAG_REPOSITORY}:*\" -q | xargs docker rmi --force || true
                         """
                    }
                }
            }
        }

        stage('Deploy To DEV') {
            environment {
                GIT_CREDS = credentials('GITLAB')
            }

            steps {
                container('kustomize') {
                  sh "git clone --depth=1 http://$GIT_CREDS_USR:$GIT_CREDS_PSW@gitlab.wiskind.cn/gits/scc/scc-argocd-apps.git  scc-argocd-apps-dev"
                  sh "git config --global user.email 'qlyan@thoughtworks.com'"

                  dir("scc-argocd-apps-dev") {
                    sh "cd ./tw-leasing-demo/overlay/dev && kustomize edit set image ${IMAG_REPOSITORY}=:${IMAG_TAG}"
                    sh "git commit -am '[CD] publish new version to dev env' && git pull -r && git push || echo 'no changes'"
                  }
                }
            }
        }

        stage('Deploy To ST') {
            environment {
                GIT_CREDS = credentials('GITLAB')
            }

            steps {
                container('kustomize') {
                  sh "git clone --depth=1 http://$GIT_CREDS_USR:$GIT_CREDS_PSW@gitlab.wiskind.cn/gits/scc/scc-argocd-apps.git  scc-argocd-apps-st"
                  sh "git config --global user.email 'qlyan@thoughtworks.com'"

                  dir("scc-argocd-apps-st") {
                    sh "cd ./tw-leasing-demo/overlay/st && kustomize edit set image ${IMAG_REPOSITORY}=:${IMAG_TAG}"
                    sh "git commit -am '[CD] publish new version to st env' && git pull -r && git push || echo 'no changes'"
                  }
                }
            }
        }

    }
}
