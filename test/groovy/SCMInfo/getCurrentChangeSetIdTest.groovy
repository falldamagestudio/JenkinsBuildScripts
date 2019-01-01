import hudson.scm.ChangeLogSet
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*

class getCurrentChangeSetIdTest extends LocalSharedLibraryPipelineTest {

    @Test
    void getCurrentChangeSetIdReturnsId() {

        helper.registerAllowedMethod('bat', [LinkedHashMap], { return '''
                                                                        | C:\\Jenkins\\workspace\\PongSP-Windows>cm status --nochanges C:\\Jenkins\\workspace\\PongSP-Windows/PongSP 
                                                                        | cs:67@rep:PongSP@repserver:<org>@Cloud'''.stripMargin() })

        helper.registerAllowedMethod('isWindows', [], { return true })

        def environment = [SOURCE_DIR : 'source_dir']
        binding.setVariable('env', environment)

        binding.setVariable('currentChangeSetId', null)
        runScript('test/jenkins/SCMInfo/getCurrentChangeSetId.jenkins')
        def currentChangeSetId = binding.getVariable('currentChangeSetId')
        assertEquals("67", currentChangeSetId)
        printCallStack()
        assertTrue(true)
    }
}