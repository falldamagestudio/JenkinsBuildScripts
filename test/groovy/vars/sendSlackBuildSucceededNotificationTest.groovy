import hudson.scm.ChangeLogSet
import hudson.tasks.junit.TestResult
import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.test.PipelineTestDetails
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*

class createSlackBuildSucceededNotificationTest extends LocalSharedLibraryPipelineTest {

    void registerCurrentBuildWithChangeSetsAndTestResults(changeSets, testResultsXmlFile) {

        TestResult testResult = new TestResult()
        testResult.parse(new File(testResultsXmlFile), new PipelineTestDetails())
        testResult.tally();

        MockTestResultAction testResultAction = new MockTestResultAction(testResult)

        def rawBuild = mock(MockRun.class)
        when(rawBuild.getAction(AbstractTestResultAction.class)).thenReturn(testResultAction)

        def currentBuild = mock(MockRunWrapper.class)
        when(currentBuild.getRawBuild()).thenReturn(rawBuild)
        when(currentBuild.getResult()).thenReturn('SUCCESS')
        when(currentBuild.getAbsoluteUrl()).thenReturn('https://my-jenkins-installation.com/job/TestJob/14/')
        when(currentBuild.getChangeSets()).thenReturn(changeSets)

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void sendsWellFormedMessages() {

        List<MockChangeLogSetEntry> changeSets1 = new ArrayList<MockChangeLogSetEntry>();
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1234", "change 1"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user2@example.com", "1235", "change 2"));

        List<MockChangeLogSetEntry> changeSets2 = new ArrayList<MockChangeLogSetEntry>();
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user3@example.com", "1236", "change 3"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1237", "change 4"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user4@example.com", "1238", "change 5"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes.add(new MockChangeLogSet(changeSets1));
        changes.add(new MockChangeLogSet(changeSets2));

        registerCurrentBuildWithChangeSetsAndTestResults(changes, "test/resources/junit-example-results/two-successful.xml")

        binding.setVariable('channel', '#test123')
        binding.setVariable('projectName', 'my-project')
        binding.setVariable('buildVersion', 'Build 12345')
        binding.setVariable('committerToSlackNameLookup', ['user1@example.com' : 'user1nick', 'user2@example.com' : 'user2nick'])

        def slackSendParameters = []

        helper.registerAllowedMethod('slackSend', [Map.class], { map ->
            slackSendParameters.add(new Tuple(map.channel, map.color, map.message))
            return null })

        runScript('test/jenkins/vars/sendSlackBuildSucceededNotification.jenkins')

        assertEquals(1, slackSendParameters.size())

        assertEquals('#test123', (String)slackSendParameters[0][0])
        assertEquals('good', (String)slackSendParameters[0][1])
        assertEquals('''*Build succeeded - my-project - Build 12345*
                       |Changes:
                       |>_user1@example.com_ change 1
                       |>_user2@example.com_ change 2
                       |>_user3@example.com_ change 3
                       |>_user1@example.com_ change 4
                       |>_user4@example.com_ change 5
                       |'''.stripMargin(), (String)slackSendParameters[0][2])
    }
}