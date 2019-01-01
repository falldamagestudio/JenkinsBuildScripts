import org.junit.Test

import static groovy.test.GroovyAssert.*

class FormatSlackMessageTest extends LocalSharedLibraryPipelineTest {

    @Test
    void returnsSuccessfulHeaderLine() {
        binding.setVariable('failedStep', null)
        binding.setVariable('projectName', 'TestProject')
        binding.setVariable('changeSetId', '12345')
        binding.setVariable('line', null)
        runScript('test/jenkins/FormatSlackMessage/getHeaderLine.jenkins')
        def line = binding.getVariable('line')
        assertEquals('*Build succeeded - TestProject - cs:12345*', (String)line)
    }

    @Test
    void returnsFailedHeaderLine() {
        binding.setVariable('failedStep', 'TestStep')
        binding.setVariable('projectName', 'TestProject')
        binding.setVariable('changeSetId', '12345')
        binding.setVariable('line', null)
        runScript('test/jenkins/FormatSlackMessage/getHeaderLine.jenkins')
        def line = binding.getVariable('line')
        assertEquals('*Build failed in \'TestStep\' - TestProject - cs:12345*', (String)line)
    }

    @Test
    void returnsGoogleDriveDownloadLine() {
        binding.setVariable('bucketName', 'my-bucket')
        binding.setVariable('fileName', 'my-filename')
        binding.setVariable('line', null)
        runScript('test/jenkins/FormatSlackMessage/getGoogleDriveDownloadLine.jenkins')
        def line = binding.getVariable('line')
        assertEquals('<https://storage.cloud.google.com/my-bucket/my-filename|Download build>', (String)line)
    }

    @Test
    void returnsSteamBuildLine() {
        binding.setVariable('productName', 'my-product')
        binding.setVariable('branchName', 'my-branch')
        binding.setVariable('line', null)
        runScript('test/jenkins/FormatSlackMessage/getSteamBuildLine.jenkins')
        def line = binding.getVariable('line')
        assertEquals('Available in Steam application my-product, branch [my-branch]', (String)line)
    }

    @Test
    void returnsNullForNoChanges() {
        def changeLogs = []
        binding.setVariable('changeLogs', changeLogs)
        binding.setVariable('lines', null)
        runScript('test/jenkins/FormatSlackMessage/getChangeLogsLines.jenkins')
        def lines = binding.getVariable('lines')
        assertNull(lines)
    }

    @Test
    void returnsChangeLogsLines() {
        def changeLogs = [new MockChangeLogSetEntry(null, 'user1@example.com', '1234', 'change 1'),
            new MockChangeLogSetEntry(null, 'user2@example.com', '1235', 'change 2')]
        binding.setVariable('changeLogs', changeLogs)
        binding.setVariable('lines', null)
        runScript('test/jenkins/FormatSlackMessage/getChangeLogsLines.jenkins')
        def lines = binding.getVariable('lines')
        assertEquals(3, lines.size())
        assertEquals('Changes:', (String)lines[0])
        assertEquals('>_user1@example.com_ change 1', (String)lines[1])
        assertEquals('>_user2@example.com_ change 2', (String)lines[2])
    }

    @Test
    void returnsNullForNoFailedTests() {
        def failedTests = []
        binding.setVariable('failedTests', failedTests)
        binding.setVariable('lines', null)
        runScript('test/jenkins/FormatSlackMessage/getFailedTestsLines.jenkins')
        def lines = binding.getVariable('lines')
        assertNull(lines)
    }

    @Test
    void returnsFailedTestsLines() {
        def failedTests = [new Tuple('test 1', 'http://test_1_url'),
            new Tuple('test 2', 'http://test_2_url'),
            new Tuple('test 3', 'http://test_3_url')]
        binding.setVariable('failedTests', failedTests)
        binding.setVariable('lines', null)
        runScript('test/jenkins/FormatSlackMessage/getFailedTestsLines.jenkins')
        def lines = binding.getVariable('lines')
        assertEquals(4, lines.size())
        assertEquals('Failed tests:', (String)lines[0])
        assertEquals('<http://test_1_url|test 1>', (String)lines[1])
        assertEquals('<http://test_2_url|test 2>', (String)lines[2])
        assertEquals('<http://test_3_url|test 3>', (String)lines[3])
    }
}