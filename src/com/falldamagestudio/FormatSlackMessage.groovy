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

    def getGoogleDriveDownloadLine(bucket, fileName) {
        return "<https://storage.cloud.google.com/${bucket}/${fileName}|Download build>"
    }

    def getSteamBuildLine(steamProductName, branchName) {
        return "Available in Steam application ${steamProductName}, branch [${branchName}]"
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
}
