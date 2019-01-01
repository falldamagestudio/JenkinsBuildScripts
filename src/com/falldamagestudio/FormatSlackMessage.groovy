package com.falldamagestudio

class FormatSlackMessage implements Serializable {

    def script

    FormatSlackMessage(script) {
        this.script = script
    }

    def getHeaderLine(projectName, changeSetId, failedStep) {

        def result = (failedStep ? "failed in '${failedStep}'" : 'succeeded')
        return "*Build ${result} - ${projectName} - cs:${changeSetId}*"
    }

    def getGoogleDriveDownloadLine(bucketName, fileName) {
        return "<https://storage.cloud.google.com/${bucketName}/${fileName}|Download build>"
    }

    def getSteamBuildLine(steamProductName, steamBranchName) {
        return "Available in Steam application ${steamProductName}, branch [${steamBranchName}]"
    }

    def getChangeLogsLines(changeLogs) {
        if (changeLogs.size() > 0) {
            def lines = ["Changes:"]
            for (entry in changeLogs) {
                def user = entry.user.toString()
                def comment = entry.msg.toString()
                def line = ">_" + user + "_ " + comment.replace("\n", ";")
                lines.add(line)
            }
            return lines
        } else
            return null
    }

    def getFailedTestsLines(failedTests) {
        if (failedTests.size() > 0) {
            def lines = ["Failed tests:"]
            for (testCase in failedTests) {
                def line = "<${testCase.url}|${testCase.name}>"
                lines.add(line)
            }
            return lines
        } else
            return null
    }

    def concatenateLinesToMessage(lines) {
        def message = ""
        for (line in lines) {
            if (line) {
                message += "${line}\n"
            }
        }

        return message
    }

    def getFailedMessage(projectName, changeSetId, failedStep, changeLogs, failedTests) {
        def lines = []
        lines.addAll(getHeaderLine(projectName, changeSetId, failedStep))
        lines.addAll(getChangeLogsLines(changeLogs))
        lines.addAll(getFailedTestsLines(failedTests))
        return concatenateLinesToMessage(lines)
    }

    def getSuccessMessage_gDrive(projectName, changeSetId, changeLogs, bucketName, fileName) {
        def lines = []
        lines.addAll(getHeaderLine(projectName, changeSetId, null))
        lines.addAll(getGoogleDriveDownloadLine(bucketName, fileName))
        lines.addAll(getChangeLogsLines(changeLogs))
        return concatenateLinesToMessage(lines)
    }

    def getSuccessMessage_steam(projectName, changeSetId, changeLogs, steamProductName, steamBranchName) {
        def lines = []
        lines.addAll(getHeaderLine(projectName, changeSetId, null))
        lines.addAll(getSteamBuildLine(steamProductName, steamBranchName))
        lines.addAll(getChangeLogsLines(changeLogs))
        return concatenateLinesToMessage(lines)
    }
}
