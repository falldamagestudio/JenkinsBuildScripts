import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library

class LocalSharedLibraryPipelineTest extends BasePipelineTest {

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
}