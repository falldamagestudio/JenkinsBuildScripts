
def call(channel, projectName, failedStep) {
    def testResults = new com.falldamagestudio.TestResults(this)
    def failedTests = testResults.getFailedTests()

    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackMessage = new com.falldamagestudio.FormatSlackMessage(this)
    def messages = formatSlackMessage.getFailureMessages(projectName, scmInfo.getCurrentChangeSetId(), failedStep, scmInfo.getChangeLogs(), failedTests)

    def sendSlackMessage = new com.falldamagestudio.SendSlackMessage(this)
    sendSlackMessage.sendAsMultipleMessages(channel, 'bad', messages)
}
