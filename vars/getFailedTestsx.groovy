//import com.falldamagestudio.TestResults

// TODO: handle context with 'this' passed around & returned

def call() {
    echo "getFailedTestsx() starts"
    echo "currentBuild: ${currentBuild}"
    echo "getting failed tests"
    echo "this: ${this}"
    def testResults = new com.falldamagestudio.TestResults(this)
    echo "Created test object"
    echo "test results: ${testResults}"
    def failedTests = testResults.getFailedTests()
    echo "failed tests: ${failedTests}"
}
