
def call(channel, projectName, buildVersion, committerToSlackNameLookup, bucketName, fileName) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)
    def messages = formatSlackNotification.getSuccessMessages_gDrive(projectName, buildVersion, scmInfo.getPeopleToInformAboutSuccessfulBuild(), committerToSlackNameLookup, bucketName, fileName, scmInfo.getChangeLogs())

    def sendSlackNotification = new com.falldamagestudio.SendSlackNotification(this)
    sendSlackNotification.sendAsMultipleMessages(channel, 'good', messages)
}
