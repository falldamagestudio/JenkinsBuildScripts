
def call() {
    echo "getFailedTests (var) () starts"
    echo "currentBuild: ${currentBuild}"
    echo "getting failed tests"
    echo "this: ${this}"
    def testResults = new com.falldamagestudio.TestResults(this)
    echo "Created test object"
    echo "test results: ${testResults}"
    def failedTests = testResults.getFailedTests()
    echo "failed tests: ${failedTests}"
}
