
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			steps {
				sh 'chmod u+x gradlew'
				sh './gradlew test -DignoreFailures=true'
				junit '**/build/test-results/test/*.xml'
				if (currentBuild.result == 'UNSTABLE') {
					echo "UNSTABLE!"
					currentBuild.result = 'FAILED'
				}
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