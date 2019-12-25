pipeline {

	agent any
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
	}

}