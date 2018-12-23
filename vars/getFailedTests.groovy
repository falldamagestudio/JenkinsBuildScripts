import com.falldamagestudio.*

def call() {
    echo "getting failed tests"
    def testResults = new TestResults()
    echo "test results: ${testResults}"
    def failedTests = testResults.getFailedTests()
    echo "failed tests: ${failedTests}"
}
