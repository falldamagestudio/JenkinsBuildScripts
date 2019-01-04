
def call(channel, projectName, buildVersion, committerToSlackNameLookup, steamProductName, steamBranchName) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getSuccessMessages_steam(projectName, buildVersion, scmInfo.getPeopleToInformAboutSuccessfulBuild(), committerToSlackNameLookup, steamProductName, steamBranchName, scmInfo.getChangeLogs())

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsMultipleMessages(channel, 'good', messages)
}
