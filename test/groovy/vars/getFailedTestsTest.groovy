import hudson.tasks.junit.TestResult
import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.test.PipelineTestDetails
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*


class getFailedTestsTest extends LocalSharedLibraryPipelineTest {

    void registerCurrentBuildWithTestResults(testResultsXmlFile) {

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

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void getFailedTestsReturnsFailedTests() {

        registerCurrentBuildWithTestResults("test/resources/junit-example-results/two-successful-two-failures.xml")
        binding.setVariable('failedTests', '')
        runScript('test/jenkins/vars/getFailedTests.jenkins')
        def failedTests = binding.getVariable('failedTests')
        assertEquals(failedTests.size(), 2)
        printCallStack()
        assertTrue(true)
    }

    @Test
    void getFailedTestsReturnsEmptyListWhenSuccessful() {

        registerCurrentBuildWithTestResults("test/resources/junit-example-results/two-successful.xml")
        binding.setVariable('failedTests', '')
        runScript('test/jenkins/vars/getFailedTests.jenkins')
        def failedTests = binding.getVariable('failedTests')
        assertEquals(failedTests.size(), 0)
        printCallStack()
        assertTrue(true)
    }
}