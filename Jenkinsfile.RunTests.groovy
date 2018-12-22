
pipeline {

	agent { label 'master' }

	stages {
		stage('Run Tests') {
			steps {
				// Ensure gradlew is executable (Plastic does not mark it as such at checkout)
				sh 'chmod u+x gradlew'

				// Run tests; ensure that test failures do not abort the build stage
				sh './gradlew test -DignoreFailures=true'

				// Upload test results to Jenkins
				junit '**/build/test-results/test/*.xml'

				// Convert 'UNSTABLE' test results to 'FAILED'
				script {
					if (currentBuild.result == 'UNSTABLE') {
						currentBuild.result = 'FAILED'
					}
				}
			}
		}
	}
}