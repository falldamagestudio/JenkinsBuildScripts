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

    @Test
    void concatenatesLinesToMessage() {
        def lines = ['line 1', 'line 2', 'line 3']
        binding.setVariable('lines', lines)
        binding.setVariable('message', null)
        runScript('test/jenkins/FormatSlackMessage/concatenateLinesToMessage.jenkins')
        def message = binding.getVariable('message')
        assertEquals('''line 1
                       |line 2
                       |line 3
                       |'''.stripMargin(), (String)message)
    }

    @Test
    void returnsFailedMessageWithTestDetails() {
        binding.setVariable('projectName', 'my-project')
        binding.setVariable('changeSetId', '12345')
        binding.setVariable('failedStep', 'Tests')
        def changeLogs = [new MockChangeLogSetEntry(null, 'user1@example.com', '1234', 'change 1'),
            new MockChangeLogSetEntry(null, 'user2@example.com', '1235', 'change 2')]
        binding.setVariable('changeLogs', changeLogs)
        def failedTests = [new Tuple('test 1', 'http://test_1_url'),
            new Tuple('test 2', 'http://test_2_url'),
            new Tuple('test 3', 'http://test_3_url')]
        binding.setVariable('failedTests', failedTests)
        binding.setVariable('message', null)
        runScript('test/jenkins/FormatSlackMessage/getFailedMessage.jenkins')
        def message = binding.getVariable('message')

        assertEquals('''*Build failed in \'Tests\' - my-project - cs:12345*
                       |Changes:
                       |>_user1@example.com_ change 1
                       |>_user2@example.com_ change 2
                       |Failed tests:
                       |<http://test_1_url|test 1>
                       |<http://test_2_url|test 2>
                       |<http://test_3_url|test 3>
                       |'''.stripMargin(), (String)message)
    }

    @Test
    void returnsSuccessMessageWithGoogleDriveLink() {
        binding.setVariable('projectName', 'my-project')
        binding.setVariable('changeSetId', '12345')
        def changeLogs = [new MockChangeLogSetEntry(null, 'user1@example.com', '1234', 'change 1'),
            new MockChangeLogSetEntry(null, 'user2@example.com', '1235', 'change 2')]
        binding.setVariable('changeLogs', changeLogs)
        binding.setVariable('bucketName', 'my-bucket')
        binding.setVariable('fileName', 'my-filename')
        binding.setVariable('message', null)
        runScript('test/jenkins/FormatSlackMessage/getSuccessMessage_gDrive.jenkins')
        def message = binding.getVariable('message')

        assertEquals('''*Build succeeded - my-project - cs:12345*
                       |<https://storage.cloud.google.com/my-bucket/my-filename|Download build>
                       |Changes:
                       |>_user1@example.com_ change 1
                       |>_user2@example.com_ change 2
                       |'''.stripMargin(), (String)message)
    }

    @Test
    void returnsSuccessMessageWithSteamBranchName() {
        binding.setVariable('projectName', 'my-project')
        binding.setVariable('changeSetId', '12345')
        def changeLogs = [new MockChangeLogSetEntry(null, 'user1@example.com', '1234', 'change 1'),
            new MockChangeLogSetEntry(null, 'user2@example.com', '1235', 'change 2')]
        binding.setVariable('changeLogs', changeLogs)
        binding.setVariable('steamProductName', 'my-product')
        binding.setVariable('steamBranchName', 'my-branch')
        binding.setVariable('message', null)
        runScript('test/jenkins/FormatSlackMessage/getSuccessMessage_steam.jenkins')
        def message = binding.getVariable('message')

        assertEquals('''*Build succeeded - my-project - cs:12345*
                       |Available in Steam application my-product, branch [my-branch]
                       |Changes:
                       |>_user1@example.com_ change 1
                       |>_user2@example.com_ change 2
                       |'''.stripMargin(), (String)message)
    }
}