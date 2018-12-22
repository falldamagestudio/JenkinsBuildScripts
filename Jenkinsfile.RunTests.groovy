
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			steps {
				script {
					//try {
					//	if (isWindows()) {
					//		bat 'gradlew test'
					//	} else {
							sh 'gradle test'
					//	}
					//}
					//finally {
					//	junit testResults: '**/build/test-reports/*.xml'
					//}
				}
			}
		}
	}
}