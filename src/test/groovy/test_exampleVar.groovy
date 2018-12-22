import org.junit.*
import com.lesfurets.jenkins.unit.*
import static groovy.test.GroovyAssert.*

class exampleVarTest extends BasePipelineTest {
    def exampleVar

    @Before
    void setUp() {
        super.setUp()
        // load exampleVar
        exampleVar = loadScript("vars/exampleVar.groovy")
    }

    @Test
    void testCall() {
        // call exampleVar and check result
        def result = exampleVar(text: "a_B-c.1")
        assertEquals "result:", "abc1", result
    }
}