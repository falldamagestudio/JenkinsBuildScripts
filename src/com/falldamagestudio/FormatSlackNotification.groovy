package com.falldamagestudio

class FormatSlackNotification implements Serializable {

    def script

    FormatSlackNotification(script) {
        this.script = script
    }

    def getHeaderLine(projectName, buildVersion, failedStep) {

        def result = (failedStep ? "failed in '${failedStep}'" : 'succeeded')
        return "*Build ${result} - ${projectName} - ${buildVersion}*"
    }

    def getGoogleDriveDownloadLine(bucketName, fileName) {
        return "<https://storage.cloud.google.com/${bucketName}/${fileName}|Download build>"
    }

    def getSteamBuildLine(steamProductName, steamBranchName) {
        return "Available in Steam application ${steamProductName}, branch [${steamBranchName}]"
    }

    def getNotificationLines(committers, committerToSlackNameLookup) {

        def notification = ""

        for (committer in committers) {
            if (committerToSlackNameLookup.containsKey(committer))
                notification += " @${committerToSlackNameLookup[committer]}"
            else
                notification += " ${committer} (No Slack name given)"
        }

        if (notification != "")
            return ["People to notify:${notification}"]
        else
            return []
    }

    def getChangeLogsLines(changeLogs) {
        if (changeLogs.size() > 0) {
            def lines = ["Changes:"]
            for (entry in changeLogs) {
                def userId = entry.author.id.toString()
                def comment = entry.msg.toString()
                def line = ">_" + userId + "_ " + comment.replace("\n", ";")
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

    def concatenateLinesToMessages(lines, maxMessageLength = 1900, maxMessageCount = 5) {

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

    def getFailureMessages(projectName, buildVersion, failedStep, committers, committerToSlackNameLookup, changeLogs, failedTests) {
        def lines = []
        lines.addAll([getHeaderLine(projectName, buildVersion, failedStep)])
        lines.addAll(getNotificationLines(committers, committerToSlackNameLookup))
        lines.addAll(getChangeLogsLines(changeLogs))
        lines.addAll(getFailedTestsLines(failedTests))
        def messages = concatenateLinesToMessages(lines)
        return messages
    }

    def getSuccessMessages(projectName, buildVersion, committers, committerToSlackNameLookup, changeLogs) {
        def lines = []
        lines.addAll([getHeaderLine(projectName, buildVersion, null)])
        lines.addAll(getNotificationLines(committers, committerToSlackNameLookup))
        lines.addAll(getChangeLogsLines(changeLogs))
        def messages = concatenateLinesToMessages(lines)
        return messages
    }

    def getSuccessMessages_gDrive(projectName, buildVersion, committers, committerToSlackNameLookup, bucketName, fileName, changeLogs) {
        def lines = []
        lines.addAll([getHeaderLine(projectName, buildVersion, null)])
        lines.addAll([getGoogleDriveDownloadLine(bucketName, fileName)])
        lines.addAll(getNotificationLines(committers, committerToSlackNameLookup))
        lines.addAll(getChangeLogsLines(changeLogs))
        def messages = concatenateLinesToMessages(lines)
        return messages
    }

    def getSuccessMessages_steam(projectName, buildVersion, committers, committerToSlackNameLookup, steamProductName, steamBranchName, changeLogs) {
        def lines = []
        lines.addAll([getHeaderLine(projectName, buildVersion, null)])
        lines.addAll([getSteamBuildLine(steamProductName, steamBranchName)])
        lines.addAll(getNotificationLines(committers, committerToSlackNameLookup))
        lines.addAll(getChangeLogsLines(changeLogs))
        def messages = concatenateLinesToMessages(lines)
        return messages
    }
}
