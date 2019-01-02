package com.falldamagestudio

class SendSlackNotification implements Serializable {

    def script

    SendSlackNotification(script) {
        this.script = script
    }

    def sendAsMultipleMessages(channel, color, messages) {

        for (message in messages)
            script.slackSend channel: channel, color: color, message: message
    }

    def sendAsThreadedMessage(channel, color, messages) {

        if (messages.size() > 0)
        {
            def slackResponse = script.slackSend(channel: channel, color: color, message: messages[0])

            script.echo "slackResponse: ${slackResponse}"

            for (def i = 1; i < messages.size(); i++)
                script.slackSend channel: slackResponse.threadId, color: color, message: messages[i]
        }
    }

    def sendAsSingleMessageWithThreadsIfNecessary(channel, color, messages, maxMessageLength = 1900) {

        if (messages.size() > 0) {

            def firstMessage = messages[0]

            def i

            for (i = 1; i < messages.size(); i++) {
                if ((firstMessage.length() + messages[i].length()) > maxMessageLength)
                    break;

                firstMessage += messages[i]
            }

            def remainingMessages = messages[(i..<messages.size())]

            def newMessages = []
            newMessages.add(firstMessage)
            newMessages.addAll(remainingMessages)

            sendAsThreadedMessage(channel, color, newMessages)
        }
    }
}
