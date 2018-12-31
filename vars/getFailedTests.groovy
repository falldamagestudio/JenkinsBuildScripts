
def call() {
    def testResults = new com.falldamagestudio.TestResults(this)
    def failedTests = testResults.getFailedTests()
    return failedTests
}
