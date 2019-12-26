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
		stage("Remove docker image") {
			steps {
				sh "docker rmi $registry"
			}
		}
	}

}