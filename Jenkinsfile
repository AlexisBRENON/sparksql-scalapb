pipeline {
    agent {
        dockerfile {
            filename 'Jenkins.Dockerfile'
        }
    }
    environment {
        NEXUS = credentials('NEXUS')
        NEXUS_USER = "$NEXUS_USR"
        NEXUS_PW = "$NEXUS_PSW"
        COURSIER_CACHE = "/root/.cache/coursier/v1"
        SBT_OPTS = "-Dsbt.boot.directory=/root/.sbt/boot " +
                "-Dsbt.ci=true " +
                "-Dsbt.global.base=/root/.sbt/1.0 " +
                "-Dsbt.ivy.home=/root/.ivy2"
        SBT = "sbt"
        WHOAMI = "jenkins"
    }
    stages {
        stage('Compile') {
            steps {
                sh '${SBT} compile'
            }
        }
        stage('Test') {
            steps {
                sh "${SBT} 'Test/protocGenerate'"
                sh "${SBT} 'testOnly -- -oWDU'"
            }
            post {
                always {
                    junit "**/target/test-reports/*.xml"
                }
            }
        }
        stage("Publish") {
            when { expression { env.TAG_NAME ==~ /v.+/ } }
            steps {
                sh "${SBT} publish"
            }
        }
    }
}
