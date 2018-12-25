import org.junit.*
import com.lesfurets.jenkins.unit.*
import static groovy.test.GroovyAssert.*
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import hudson.model.Run
//import org.jenkinsci.plugins.workflow.job.WorkflowRun

class FakeRun extends hudson.model.Run<FakeRun,FakeRun> {
    def getExternalizableId() {
        return "externalizable_id_12345678";
    }
}

class exampleVarTest extends BasePipelineTest {
    def getFailedTests

    @Before
    void setUp() {
        super.setUp()

        println "hello"
        // load getFailedTests
        getFailedTests = loadScript("vars/getFailedTests.groovy")

        println "hello2"
        //def currentRun = new WorkflowRun(null)
        def currentRun = new FakeRun()
        println "hello3"
        def currentBuild = new RunWrapper(currentRun, true)
    }

    @Test
    void testCall() {
        // call getFailedTests and check result
        def result = getFailedTests()
        assertEquals "result:", "abc1", result
    }
}