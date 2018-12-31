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
        def cmResult = script.bat script: "cm status --nochanges ${script.env.SOURCE_DIR}", returnStdout: true
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
}
