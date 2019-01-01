package com.falldamagestudio

class SCMInfo implements Serializable {

    def script

    SCMInfo(script) {
        this.script = script
    }

    def getChangeLogs()
    {
        def changeLogs = []

        def changeLogSets = script.currentBuild.changeSets

        HashSet<String> encounteredCommitIds = new HashSet<String>()

        // Remove duplicate entries
        //
        // This is because Jenkins will fetch the change log descriptions twice
        //   when the build jobs runs on a slave --
        //   and this avoids showing duplicate information
        for (int i = 0; i < changeLogSets.size(); i++) {
            def entries = changeLogSets[i].items
            for (int j = 0; j < entries.length; j++) {
                def entry = entries[j]
                if (encounteredCommitIds.add(entry.commitId)) {
                    changeLogs += entry
                }
            }
        }
        return changeLogs
    }

    def getCurrentChangeSetId()
    {
        def cmResult = null
        if (script.isWindows())
            cmResult = script.bat script: "cm status --nochanges ${script.env.SOURCE_DIR}", returnStdout: true
        else
            cmResult = script.sh script: "cm status --nochanges ${script.env.SOURCE_DIR}", returnStdout: true

        // Result will be a multiline string like this:
        //
        //		<blank line>
        //      C:\Jenkins\workspace\PongSP-Windows>cm status --nochanges C:\Jenkins\workspace\PongSP-Windows/PongSP 
        //      cs:67@rep:PongSP@repserver:<org>@Cloud

        // Extract the number '67' from the above multiline string
        def cmResultLines = cmResult.split('\n')
        assert 3 == cmResultLines.size()
        def changeSetId = cmResultLines[2].tokenize(':@')[1]
        
        return changeSetId
    }

    def getAllCommittersSinceLastSuccessfulBuild(firstBuildToCheck) {

        def changes = ""

        def committers = new HashSet<String>()

        def build = firstBuildToCheck

        while (build && (!build.result || (build.result.toString() != 'SUCCESS'))) {

            for (changeSet in build.changeSets) {
                for (entry in changeSet) {
                    committers.add(entry.user)
                }
            }

            build = build.getPreviousBuild()
        }

        return committers
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Gather up e-mail addresses of all people who have committed anything during the last series of non-successful builds (including current build)
    // If the last build was successful, this will return an empty set
    def getPeopleToInformAboutNonSuccessfulBuild() {
        if (script.currentBuild) {
            return getAllCommittersSinceLastSuccessfulBuild(script.currentBuild)
        }
        else
            return new HashSet<String>()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    // Gather up e-mail addresses of all people who have committed anything during the last series of non-successful builds (excluding current build)
    // If the second-to-last build was successful, this will return an empty set
    def getPeopleToInformAboutSuccessfulBuild() {
        if (script.currentBuild) {
            return getAllCommittersSinceLastSuccessfulBuild(script.currentBuild.getPreviousBuild())
        }
        else
            return new HashSet<String>()
    }
}
