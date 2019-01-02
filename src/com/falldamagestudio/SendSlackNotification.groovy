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

        // Beware: this message is broken. It appears that the slack plugin doesn't return any slackResponse after all.

        if (messages.size() > 0)
        {
            def slackResponse = script.slackSend(channel: channel, color: color, message: messages[0])

            script.echo "slackResponse: ${slackResponse}"

            for (def i = 1; i < messages.size(); i++)
                script.slackSend channel: slackResponse.threadId, color: color, message: messages[i]
        }
    }
}
