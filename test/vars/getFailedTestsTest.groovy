import com.lesfurets.jenkins.unit.*
import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import org.junit.*
import org.junit.rules.TemporaryFolder

import static groovy.test.GroovyAssert.*
import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource
import static org.mockito.Mockito.*


class exampleVarTest extends BasePipelineTest {

    //class MockJob extends hudson.model.Job<MockJob, MockRun> {}
    //class MockRun extends hudson.model.Run<MockJob, MockRun> {}

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

        def testResults = []

        def rawBuild = mock(org.jenkinsci.plugins.workflow.job.WorkflowRun.class)
        when(rawBuild.getAction(hudson.tasks.test.AbstractTestResultAction.class)).thenReturn(testResults)

        def currentBuild = mock(org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper.class)
        currentBuild.rawBuild = rawBuild
        currentBuild.result = 'SUCCESS'

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void should_return_true() {
        runScript('pipeline.jenkins')
        printCallStack()
        assertTrue(true)
    }
}