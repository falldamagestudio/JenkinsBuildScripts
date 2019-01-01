
def call(projectName) {
    def scmInfo = new com.falldamagestudio.SCMInfo(this)

    def formatSlackMessage = new com.falldamagestudio.FormatSlackMessage(this)

    def message = formatSlackMessage.getSuccessMessage(projectName, scmInfo.getCurrentChangeSetId(), scmInfo.getChangeLogs())

    return message
}
