package com.falldamagestudio

import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.junit.CaseResult

class TestResults implements Serializable {

    def script

    TestResults(script) {
        this.script = script
    }

    def getFailedTests() {

        // script.echo "getFailedTests() starts"
        // script.echo "currentBuild: ${script.currentBuild}"
        // script.echo "rawBuild: ${script.currentBuild.rawBuild.class}"

        // script.echo "testResultAction type: ${script.currentBuild.rawBuild.getAction(AbstractTestResultAction.class).getClass().getName()}"

        AbstractTestResultAction testResultAction = script.currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
        if (testResultAction != null) {
            def caseResults = testResultAction.failedTests
            if (caseResults != null)
                return toTestCases(caseResults)
            else
                return null
        }
        else
            return null
    }

    def toTestCases(List<CaseResult> caseResults) {
        def testCases = new ArrayList<TestCase>()

        for (CaseResult caseResult : caseResults) {
            testCases.add(new TestCase(caseResult))
        }
        return testCases
    }
}
