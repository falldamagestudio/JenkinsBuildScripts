
def call(projectName, failedStep) {
    def testResults = new com.falldamagestudio.TestResults(this)
    def failedTests = testResults.getFailedTests()

    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackMessage = new com.falldamagestudio.FormatSlackMessage(this)

    def message = formatSlackMessage.getFailureMessage(projectName, scmInfo.getCurrentChangeSetId(), failedStep, scmInfo.getChangeLogs(), failedTests)

    return message
}
