import org.junit.Test

import static groovy.test.GroovyAssert.*

class SendSlackNotificationTest extends LocalSharedLibraryPipelineTest {

    @Test
    void sendAsMultipleMessagesSendsIndividualMessages() {
 
        def slackSendParameters = []

        helper.registerAllowedMethod('slackSend', [Map.class], { map ->
            slackSendParameters.add(new Tuple(map.channel, map.color, map.message))
                return null
            })

        binding.setVariable('channel', '#test123')
        binding.setVariable('color', 'good')
        binding.setVariable('messages', ['line 1\n', 'line 2\n', 'line 3\n'])
        runScript('test/jenkins/SendSlackNotification/sendAsMultipleMessages.jenkins')

        assertEquals(3, slackSendParameters.size())

        assertEquals('#test123', (String)slackSendParameters[0][0])
        assertEquals('good', (String)slackSendParameters[0][1])
        assertEquals('line 1\n', (String)slackSendParameters[0][2])

        assertEquals('#test123', (String)slackSendParameters[1][0])
        assertEquals('good', (String)slackSendParameters[1][1])
        assertEquals('line 2\n', (String)slackSendParameters[1][2])

        assertEquals('#test123', (String)slackSendParameters[2][0])
        assertEquals('good', (String)slackSendParameters[2][1])
        assertEquals('line 3\n', (String)slackSendParameters[2][2])
    }

    @Test
    void sendAsThreadedMessageSendsThreadedMessages() {
 
        def slackSendParameters = []

        helper.registerAllowedMethod('slackSend', [Map.class], { map ->
            slackSendParameters.add(new Tuple(map.channel, map.color, map.message))
                return [threadId: 'thread-id']
            })

        binding.setVariable('channel', '#test123')
        binding.setVariable('color', 'good')
        binding.setVariable('messages', ['line 1\n', 'line 2\n', 'line 3\n'])
        runScript('test/jenkins/SendSlackNotification/sendAsThreadedMessage.jenkins')

        assertEquals(3, slackSendParameters.size())

        assertEquals('#test123', (String)slackSendParameters[0][0])
        assertEquals('good', (String)slackSendParameters[0][1])
        assertEquals('line 1\n', (String)slackSendParameters[0][2])

        assertEquals('thread-id', (String)slackSendParameters[1][0])
        assertEquals('good', (String)slackSendParameters[1][1])
        assertEquals('line 2\n', (String)slackSendParameters[1][2])

        assertEquals('thread-id', (String)slackSendParameters[2][0])
        assertEquals('good', (String)slackSendParameters[2][1])
        assertEquals('line 3\n', (String)slackSendParameters[2][2])
    }
}