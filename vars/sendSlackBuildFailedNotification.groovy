
def call(channel, projectName, buildVersion, failedStep, committerToSlackNameLookup) {
    def testResults = new com.falldamagestudio.TestResults(this)
    def failedTests = testResults.getFailedTests()

    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getFailureMessages(projectName, buildVersion, failedStep, scmInfo.getPeopleToInformAboutNonSuccessfulBuild(), committerToSlackNameLookup, scmInfo.getChangeLogs(), failedTests)

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsMultipleMessages(channel, 'bad', messages)
}
