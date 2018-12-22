
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			step {
				script {
					try {
						if (isWindows()) {
							bat 'gradlew test'
						} else {
							sh 'gradle test'
						}
					}
					finally {
						junit testResults: '**/build/test-reports/*.xml'
					}
				}
			}
		}
	}
}