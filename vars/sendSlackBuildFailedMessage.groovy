
def call(channel, projectName, failedStep) {
    def testResults = new com.falldamagestudio.TestResults(this)
    def failedTests = testResults.getFailedTests()

    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackMessage = new com.falldamagestudio.FormatSlackMessage(this)

    def message = formatSlackMessage.getFailureMessage(projectName, scmInfo.getCurrentChangeSetId(), failedStep, scmInfo.getChangeLogs(), failedTests)

    def slackResponse = slackSend channel: channel, color: 'bad', message: message

    def failedTestsMessages = formatSlackMessage.convertFailedTestsToMessages(failedTests)

    for (failedTestsMessage in failedTestsMessages) {
        slackSend channel: slackResponse.threadId, color: 'bad', message: failedTestsMessage
    }
}
