
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			steps {
				sh 'chmod u+x gradlew'
				sh './gradlew test -DignoreFailures=true'
				junit testResults: '**/build/test-results/test/*.xml', healthScaleFactor: 10000
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