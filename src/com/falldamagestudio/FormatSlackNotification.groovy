package com.falldamagestudio

class FormatSlackNotification implements Serializable {

    def script

    FormatSlackNotification(script) {
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
            return []
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
            return []
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

    def concatenateLinesToMessages(lines, maxMessageLength = 1000, maxMessageCount = 5) {

        // maxMessageLength = Max number of characters in a message.
        // Slack has documented limits of ~16kB in a HTTP request and max 40000 chars in a message
        //  but in practice messages with >2000 chars have failed when using slackSend in Jenkins
        //  so let's set the limit a bit lower.

        // maxMessageCount = Max number of messages to generate.
        // Slack is documented to not like too much message spam (plus, it gets unwieldy for users)
        //  so if there are more lines than can fit into this number of messages,
        //  remaining lines will be omitted and an 'n lines omitted' message will be included instead.

        def messages = []
        def message = ""

        for (def i = 0; i < lines.size(); i++) {
            def line = lines[i]

            if ((message.length() > 0) && (message.length() + line.length() >= maxMessageLength)) {
                messages.add(message)
                message = ""

                if (messages.size() >= maxMessageCount)
                {
                    message = "(${lines.size() - i} lines omitted)\n"
                    break
                }
            }

            message += "${line}\n"
        }

        if (message.length() > 0)
            messages.add(message)

        return messages
    }

    def getFailureMessages(projectName, changeSetId, failedStep, changeLogs, failedTests) {
        def messages = []
        messages.addAll(concatenateLinesToMessages([getHeaderLine(projectName, changeSetId, failedStep)]))
        messages.addAll(concatenateLinesToMessages(getChangeLogsLines(changeLogs)))
        messages.addAll(concatenateLinesToMessages(getFailedTestsLines(failedTests)))
        return messages
    }

    def getSuccessMessage(projectName, changeSetId, changeLogs) {
        def lines = []
        lines.addAll(getHeaderLine(projectName, changeSetId, null))
        lines.addAll(getChangeLogsLines(changeLogs))
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
