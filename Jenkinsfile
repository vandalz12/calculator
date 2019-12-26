pipeline {

	agent any
	triggers {
		pollSCM('* * * * *')
	}
	stages {
		stage("Compile") {
			steps {
				withMaven(maven: "M3") {
					sh "mvn compile"
				}
			}
		}
		stage("Unit Test") {
			steps {
				withMaven(maven: "M3") {
					sh "mvn test"
				}
			}
		}
		stage("Code Coverage") {
			steps {
				withMaven(maven: "M3") {
					sh "mvn test jacoco:report@jacoco-report"
				}
				publishHTML (target: [
					reportDir: "target/site/jacoco",
					reportFiles: "index.html",
					reportName: "JaCoCo Report"
				])
				withMaven(maven: "M3") {
					sh "mvn jacoco:check@jacoco-check"
				}
			}
		}
		stage("Package") {
			steps {
				withMaven(maven: "M3") {
					sh "mvn package -Dmaven.test.skip=true"
				}
			}
		}
		stage("Docker build") {
			docker.build("vandalz12/calculator")
		}
		stage("Docker push") {
			steps {
				sh "docker push vandalz12/calculator"
			}
		}
	}

}