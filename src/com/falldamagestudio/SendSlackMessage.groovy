package com.falldamagestudio

class SendSlackMessage implements Serializable {

    def script

    SendSlackMessage(script) {
        this.script = script
    }

    def sendAsMultipleMessages(channel, color, messages) {

        for (message in messages)
            script.slackSend channel: channel, color: color, message: message
    }

    def sendAsThreadedMessage(channel, color, messages) {

        if (messages.size() > 0)
        {
            def slackResponse = script.slackSend channel: channel, color: color, message: messages[0]

            for (def i = 1; i < messages.size(); i++)
                script.slackSend channel: slackResponse.threadId, color: color, message: messages[i]
        }
    }
}
