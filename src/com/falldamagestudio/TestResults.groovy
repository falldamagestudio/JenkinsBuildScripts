package com.falldamagestudio

import hudson.tasks.test.AbstractTestResultAction
import hudson.tasks.junit.CaseResult

class FailedTest {
    String name
}

def getFailedTests() {

    echo "currentBuild.rawBuild type: ${currentBuild.rawBuild.getClass().getName()}"

    AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    if (testResultAction != null) {
        def failedTests0 = testResultAction.failedTests
        if (failedTests0 != null)
            return toFailedTests(failedTests0)
        else
            return null
    }
    else
        return null
}

def toFailedTests(List<CaseResult> failedTests0) {
    def failedTests = new ArrayList<FailedTest>()

    for (CaseResult failedTest0 : failedTests0) {
        def failedTest = new FailedTest()
        failedTest.name = failedTest0.getDisplayName()
        failedTests.add(failedTest)
    }
    return failedTests
}

return this
