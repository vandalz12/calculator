pipeline {

	agent any
	triggers {
		pollSCM('* * * * *')
	}
	stages {
		stage("Initialize") {
			script {
				def dockerHome = tool "docker-jenkins"
				def mavenHome = tool "M3"
				env.PATH = "${dockerHome}/bin:${env.PATH}"
				env.PATH = "${mavenHome}/bin:${env.PATH}"
			}
		}
		stage("Compile") {
			steps {
				sh "mvn compile"
			}
		}
		stage("Unit Test") {
			steps {
				sh "mvn test"
			}
		}
		stage("Code Coverage") {
			steps {
				sh "mvn test jacoco:report@jacoco-report"
				publishHTML (target: [
					reportDir: "target/site/jacoco",
					reportFiles: "index.html",
					reportName: "JaCoCo Report"
				])
				sh "mvn jacoco:check@jacoco-check"
			}
		}
		stage("Package") {
			steps {
				sh "mvn package -Dmaven.test.skip=true"
			}
		}
		stage("Docker build") {
			steps {
				sh "docker build -t vandalz12/calculator ."
			}
		}
		stage("Docker push") {
			steps {
				sh "docker push vandalz12/calculator"
			}
		}
	}

}