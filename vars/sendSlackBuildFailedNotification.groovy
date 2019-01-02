
def call(channel, projectName, failedStep, maxMessageLength = 1900) {
    def testResults = new com.falldamagestudio.TestResults(this)
    def failedTests = testResults.getFailedTests()

    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getFailureMessages(projectName, scmInfo.getCurrentChangeSetId(), failedStep, scmInfo.getChangeLogs(), failedTests)

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsMultipleMessages(channel, 'bad', messages)
}
