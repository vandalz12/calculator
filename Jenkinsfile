pipeline {

	agent any
	triggers {
		pollSCM('* * * * *')
	}
	environment {
		registry = "vandalz12/calculator"
		registryCredentials = "dockerhub"
		dockerImage = ""
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
			steps {
				script {
					dockerImage = docker.build(registry)
				}
			}
		}
		stage("Docker push") {
			steps {
				script {
					docker.withRegistry("", registryCredentials) {
						dockerImage.push()
					}
				}
			}
		}
		stage("Deploy to staging") {
			steps {
				sh "docker run -d --rm -p 8765:8080 --name calculator $registry"
			}
		}
		stage("Acceptance test") {
			steps {
				sleep 60
				sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"
			}
		}
	}
	
	post {
		always {
			sh "docker stop calculator"
		}
	}

}