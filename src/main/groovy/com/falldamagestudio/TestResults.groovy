package com.falldamagestudio

import hudson.tasks.test.AbstractTestResultAction

AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
echo testResultAction
if (testResultAction != null) {
    def failedTests = testResultAction.failedTests
    echo failedTests
    return failedTests
}
else
    return null
