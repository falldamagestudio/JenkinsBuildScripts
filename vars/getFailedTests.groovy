//import com.falldamagestudio.TestResults

def call() {
    echo "getting failed tests"
    def testResults = new com.falldamagestudio.TestResults()
    echo "test results: ${testResults}"
    def failedTests = testResults.getFailedTests()
    echo "failed tests: ${failedTests}"
}
