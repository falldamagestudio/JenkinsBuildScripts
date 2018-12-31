import com.lesfurets.jenkins.unit.*
import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import hudson.tasks.junit.TestResult
import hudson.tasks.junit.TestResultAction
import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.test.PipelineTestDetails
import org.junit.*
import org.junit.rules.TemporaryFolder

import static groovy.test.GroovyAssert.*
import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource
import static org.mockito.Mockito.*


class exampleVarTest extends BasePipelineTest {

    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder()

    static File temp

    @BeforeClass
    static void init() {
        temp = folder.newFolder('libs')
    }

    @Before
    void setup() {
        // TODO: Find a better way of getting project root dir
        String dirPath = new File( System.getProperty("user.dir") )
                .getAbsoluteFile()
                .getAbsolutePath()
                
        def library = library()
                .name('JenkinsBuildScripts')
                .defaultVersion('latest')
                .allowOverride(true)
                .implicit(false)
                .targetPath(dirPath)
                .retriever(new FixedFolderLocalSource(dirPath))
                .build()
        helper.registerSharedLibrary(library)

        super.setUp()
        helper.registerAllowedMethod("echo", [String.class], { String s -> println s})

        TestResult testResult = new TestResult()
        testResult.parse(new File("test/resources/junit-example-results/two-successful-two-failures.xml"), new PipelineTestDetails())
        testResult.tally();

        TestResultAction testResultAction = null

        def rawBuild = mock(MockRun.class)
        when(rawBuild.getAction(AbstractTestResultAction.class)).thenReturn(testResultAction)

        def currentBuild = mock(MockRunWrapper.class)
        when(currentBuild.getRawBuild()).thenReturn(rawBuild)
        when(currentBuild.getResult()).thenReturn('SUCCESS')

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void should_return_true() {
        runScript('pipeline.jenkins')
        printCallStack()
        assertTrue(true)
    }
}