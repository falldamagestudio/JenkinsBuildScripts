package com.falldamagestudio

import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.junit.CaseResult

// class FailedTest {
//     String name
// }

class TestResults implements Serializable {

    def script

    TestResults(script) {
        this.script = script
    }

    def getFailedTests() {

        script.echo "getFailedTests() starts"
        script.echo "currentBuild: ${script.currentBuild}"
        script.echo "rawBuild: ${script.currentBuild.rawBuild.class}"

        script.echo "testResultAction type: ${script.currentBuild.rawBuild.getAction(AbstractTestResultAction.class).getClass().getName()}"

        AbstractTestResultAction testResultAction = script.currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
        if (testResultAction != null) {
            def failedTests0 = testResultAction.failedTests
            return failedTests0
            // if (failedTests0 != null)
            //     return toFailedTests(failedTests0)
            // else
            //     return null
        }
        else
            return null
    }

    // def toFailedTests(List<CaseResult> failedTests0) {
    //     def failedTests = new ArrayList<FailedTest>()

    //     for (CaseResult failedTest0 : failedTests0) {
    //         def failedTest = new FailedTest()
    //         failedTest.name = failedTest0.getDisplayName()
    //         failedTests.add(failedTest)
    //     }
    //     return failedTests
    // }
}
