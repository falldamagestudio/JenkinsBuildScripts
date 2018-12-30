import com.lesfurets.jenkins.unit.*
import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import hudson.tasks.test.AbstractTestResultAction
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

        def testResults = null

        def rawBuild = mock(MockRun.class)
        when(rawBuild.getAction(AbstractTestResultAction.class)).thenReturn(testResults)

        def currentBuild = mock(MockRunWrapper.class)
        when(currentBuild.getRawBuild()).thenReturn(rawBuild)
        //currentBuild.rawBuild = rawBuild
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