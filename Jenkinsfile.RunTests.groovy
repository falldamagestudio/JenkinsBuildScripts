
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			steps {
				script {
					try {
						if (isWindows()) {
							bat 'gradlew test'
						} else {
							sh 'gradle test'
						}
					}
					catch (err) {
						currentBuild.result = 'FAILED'
					}
					finally {
						junit testResults: '**/build/test-reports/*.xml'
					}
				}
			}
		}
	}
}