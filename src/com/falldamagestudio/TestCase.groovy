package com.falldamagestudio

import hudson.tasks.junit.CaseResult

class TestCase implements Serializable {
    String name
    String url

    TestCase(name, url) {
        this.name = name
        this.url = url
    }
}
