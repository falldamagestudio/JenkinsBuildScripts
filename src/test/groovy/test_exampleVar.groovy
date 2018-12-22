import org.junit.*
import com.lesfurets.jenkins.unit.*
import static groovy.test.GroovyAssert.*

class exampleVarTest extends BasePipelineTest {
    def exampleVar

    @Before
    void setUp() {
        super.setUp()
        // load exampleVar
        exampleVar = loadScript("vars/ExampleVar.groovy")
    }

    @Test
    void testCall() {
        // call ExampleVar and check result
        //def result = ExampleVar(text: "a_B-c.1")
        //assertEquals "result:", "abc1", result
    }
}