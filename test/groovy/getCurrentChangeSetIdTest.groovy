import com.lesfurets.jenkins.unit.*
import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import org.junit.*
import org.junit.rules.TemporaryFolder

import static groovy.test.GroovyAssert.*
import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource
import static org.mockito.Mockito.*

class getCurrentChangeSetIdTest extends BasePipelineTest {

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
    }

    @Test
    void getCurrentChangeSetIdReturnsId() {

        helper.registerAllowedMethod('bat', [LinkedHashMap], { return '''
                                                                        | C:\\Jenkins\\workspace\\PongSP-Windows>cm status --nochanges C:\\Jenkins\\workspace\\PongSP-Windows/PongSP 
                                                                        | cs:67@rep:PongSP@repserver:<org>@Cloud''' })

        def environment = [SOURCE_DIR : 'source_dir']
        binding.setVariable('env', environment)

        binding.setVariable('currentChangeSetId', null)
        runScript('test/jenkins/getCurrentChangeSetId.jenkins')
        def currentChangeSetId = binding.getVariable('currentChangeSetId')
        assertEquals("67", currentChangeSetId)
        printCallStack()
        assertTrue(true)
    }
}