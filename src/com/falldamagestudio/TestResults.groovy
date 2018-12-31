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
                return toTestCases(script.currentBuild.absoluteUrl, testResultAction, caseResults)
            else
                return new ArrayList<TestCase>()
        }
        else
            return new ArrayList<TestCase>()
    }

    def toTestCases(String runUrl, AbstractTestResultAction testResultAction, List<CaseResult> caseResults) {
        def testCases = new ArrayList<TestCase>()

        for (CaseResult caseResult : caseResults) {
            testCases.add(new TestCase(caseResult.displayName, runUrl + testResultAction.getTestResultPath(caseResult)))
        }
        return testCases
    }
}
