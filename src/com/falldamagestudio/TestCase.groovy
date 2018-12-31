package com.falldamagestudio

import hudson.tasks.junit.CaseResult

class TestCase implements Serializable {
    String name
    String url

    TestCase(CaseResult caseResult) {
        name = caseResult.displayName
        url = caseResult.safeName
    }
}
