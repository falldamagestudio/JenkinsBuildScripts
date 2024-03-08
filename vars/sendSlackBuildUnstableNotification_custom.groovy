
def call(channel, projectName, buildVersion, committerToSlackNameLookup, customLines) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getSuccessMessages_custom(projectName, buildVersion, scmInfo.getPeopleToInformAboutSuccessfulBuild(), committerToSlackNameLookup, customLines, scmInfo.getChangeLogs())

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsMultipleMessages(channel, 'warning', messages)
}
