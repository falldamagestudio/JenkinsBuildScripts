This is a Shared Library for Jenkins. It contains tooling common for the different build scripts we have for our Unity projects.

It glues together our use of Plastic Cloud (for source control), Jenkins (for running jobs), and Slack (for collaboration). The primary benefit is Slack notifications
that list changes, failed tests, and notify people.

# Using the library

Branch the library to your own repository. Register the library within Jenkins as a Global Shared Library with the name 'JenkinsBuildScripts'.

Once you have done this, you can reference the library from your own build scripts. Here is an example Jenkinsfile that uses the library:
```
Library("JenkinsBuildScripts") _

def failedStep = null

def committersToSlackNameLookup = [
	'user1@example.com' : 'user1NickName',
	'user2@example.com' : 'user2NickName'
]

pipeline {

	agent { label 'master' }
	
	environment {
		PROJECT_NAME = "JenkinsBuildScripts-Example"
		SLACK_CHANNEL = "#test123"
	}

	stages {

		stage('Example Pipeline Stage') {
			steps {
				script {
					failedStep = 'Example Pipeline Stage'
		
					// Put your own logic here
					
					error "Pipeline Stage has been forced to fail"
				}
			}
		}
	}
	
	post {
		failure {
			sendSlackBuildFailedNotification(SLACK_CHANNEL, PROJECT_NAME, BUILD_DISPLAY_NAME, failedStep, committersToSlackNameLookup)
		}
		success {
			sendSlackBuildSucceededNotification(SLACK_CHANNEL, PROJECT_NAME, BUILD_DISPLAY_NAME, committersToSlackNameLookup)
		}
		unstable {
			sendSlackBuildFailedNotification(SLACK_CHANNEL, PROJECT_NAME, BUILD_DISPLAY_NAME, "Unstable", committersToSlackNameLookup)
		}
	}
}
```


# Developing the library

Install the Java 8 Development Kit.

Run `gradlew test` or `gradlew test --info` to run the tests.

This library uses [Jenkins Pipeline Unit](https://github.com/jenkinsci/JenkinsPipelineUnit) for unit tests. All interactions against Jenkins are mocked.

There is a set of Mock* classes which are replacements for the native Jenkins classes.

A typical test is a /test/*/*Test.groovy file, which sets up an environment, runs a *.jenkins file via Jenkins Pipeline Unit's script runner, and validates the results.
The *.jenkins file will pull in the shared library and execute code. The actual shared library code resides under vars/ and src/.
