
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			steps {
				sh 'chmod u+x gradlew'
				sh './gradlew test -DignoreFailures=true'
				ls 'ls -l build/test-results'
				ls 'ls -l build/test-results/test'
				junit testResults: '**/build/test-results/*.xml'
				//script {
				//	try {
				//		if (isWindows()) {
				//			bat 'gradlew test'
				//		} else {
				//			sh './gradle test'
				//		}
				//	}
				//	catch (err) {
				//		currentBuild.result = 'FAILED'
				//	}
				//	finally {
				//		junit testResults: '**/build/test-reports/*.xml'
				//	}
				//}
			}
		}
	}
}