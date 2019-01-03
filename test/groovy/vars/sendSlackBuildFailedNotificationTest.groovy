import hudson.scm.ChangeLogSet
import hudson.tasks.junit.TestResult
import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.test.PipelineTestDetails
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*

class sendSlackBuildFailedNotificationTest extends LocalSharedLibraryPipelineTest {

    void registerCurrentBuildWithChangeSetsAndTestResults(changeSets, testResultsXmlFile) {

        TestResult testResult = new TestResult()
        testResult.parse(new File(testResultsXmlFile), new PipelineTestDetails())
        testResult.tally();

        MockTestResultAction testResultAction = new MockTestResultAction(testResult)

        def rawBuild = mock(MockRun.class)
        when(rawBuild.getAction(AbstractTestResultAction.class)).thenReturn(testResultAction)

        def currentBuild = mock(MockRunWrapper.class)
        when(currentBuild.getRawBuild()).thenReturn(rawBuild)
        when(currentBuild.getResult()).thenReturn('FAILED')
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

        registerCurrentBuildWithChangeSetsAndTestResults(changes, "test/resources/junit-example-results/two-successful-two-failures.xml")

        def environment = [PLASTICSCM_CHANGESET_ID : '67']
        binding.setVariable('env', environment)

        binding.setVariable('channel', '#test123')
        binding.setVariable('projectName', 'my-project')
        binding.setVariable('failedStep', 'Tests')
        binding.setVariable('committerToSlackNameLookup', ['user1@example.com' : 'user1nick', 'user2@example.com' : 'user2nick'])

        def slackSendParameters = []

        helper.registerAllowedMethod('slackSend', [Map.class], { map ->
            slackSendParameters.add(new Tuple(map.channel, map.color, map.message))
            return [threadId: 'thread-id'] })

        runScript('test/jenkins/vars/sendSlackBuildFailedNotification.jenkins')

        assertEquals(1, slackSendParameters.size())

        assertEquals('#test123', (String)slackSendParameters[0][0])
        assertEquals('bad', (String)slackSendParameters[0][1])
        assertEquals('''*Build failed in \'Tests\' - my-project - cs:67*
                       |People to notify: user4@example.com (No Slack name given) user3@example.com (No Slack name given) @user2nick @user1nick
                       |Changes:
                       |>_user1@example.com_ change 1
                       |>_user2@example.com_ change 2
                       |>_user3@example.com_ change 3
                       |>_user1@example.com_ change 4
                       |>_user4@example.com_ change 5
                       |Failed tests:
                       |<https://my-jenkins-installation.com/job/TestJob/14/testReport/junit/(root)/foo3/AFailingTest/|AFailingTest>
                       |<https://my-jenkins-installation.com/job/TestJob/14/testReport/junit/(root)/foo4/ASecondFailingTest/|ASecondFailingTest>
                       |'''.stripMargin(), (String)slackSendParameters[0][2])

    }
}