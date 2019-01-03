import hudson.scm.ChangeLogSet
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*

class getCurrentChangeSetIdTest extends LocalSharedLibraryPipelineTest {

    @Test
    void getCurrentChangeSetIdReturnsId() {

        def environment = [PLASTICSCM_CHANGESET_ID : '67']
        binding.setVariable('env', environment)

        binding.setVariable('currentChangeSetId', null)
        runScript('test/jenkins/SCMInfo/getCurrentChangeSetId.jenkins')
        def currentChangeSetId = binding.getVariable('currentChangeSetId')
        assertEquals("67", currentChangeSetId)
        printCallStack()
        assertTrue(true)
    }
}