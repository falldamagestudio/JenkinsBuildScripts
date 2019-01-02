
def call(channel, projectName) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getSuccessMessages(projectName, scmInfo.getCurrentChangeSetId(), scmInfo.getChangeLogs())

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsSingleMessageWithThreadsIfNecessary(channel, 'good', messages)
}
