
def call(channel, projectName, buildVersion, committerToSlackNameLookup) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getSuccessMessages(projectName, buildVersion, scmInfo.getPeopleToInformAboutSuccessfulBuild(), committerToSlackNameLookup, scmInfo.getChangeLogs())

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsMultipleMessages(channel, 'good', messages)
}
