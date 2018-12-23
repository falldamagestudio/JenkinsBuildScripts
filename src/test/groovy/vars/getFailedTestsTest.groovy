import org.junit.*
import com.lesfurets.jenkins.unit.*
import static groovy.test.GroovyAssert.*

class exampleVarTest extends BasePipelineTest {
    def getFailedTests

    @Before
    void setUp() {
        super.setUp()
        // load getFailedTests
        getFailedTests = loadScript("vars/getFailedTests.groovy")
    }

    @Test
    void testCall() {
        // call getFailedTests and check result
        def result = getFailedTests()
        assertEquals "result:", "abc1", result
    }
}