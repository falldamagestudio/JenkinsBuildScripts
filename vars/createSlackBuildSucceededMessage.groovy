
def call(projectName) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackNotification = new com.falldamagestudio.FormatSlackNotification(this)

    def message = formatSlackNotification.getSuccessMessage(projectName, scmInfo.getCurrentChangeSetId(), scmInfo.getChangeLogs())

    return message
}
