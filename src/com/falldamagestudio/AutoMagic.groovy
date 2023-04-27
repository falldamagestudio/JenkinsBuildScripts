package com.falldamagestudio

class AutoMagic {

    static final Map EMPTY_MAP = Collections.unmodifiableMap([:]);

    static Object runScript(Object script, String cliPath, String amScriptPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments, Boolean returnOutput) {
        String amArugentsString = """--scriptpath="$amScriptPath" """;

        for (def item in mapToList(autoMagicArguments)) {
            if (item.value) {
                amArugentsString += """--$item.key="$item.value" """;
            } else {
                amArugentsString += "--$item.key ";
            }
        }

        String scriptArgumentsString = '';

        for (def item in mapToList(scriptArguments)) {
            scriptArgumentsString += """--$item.key="$item.value" """;
        }

        String cmdString = "$cliPath $amArugentsString run $scriptName $scriptArgumentsString";

        return Alara.isWindows(script)
            ? script.bat(script: cmdString, returnStdout:returnOutput)
            : script.sh(script: cmdString, returnStdout:returnOutput);
    }

    static void runScriptFrom(Object script, String restartFromPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments) {
        autoMagicArguments['restartfrom'] = restartFromPath;
        runScript(script,
            getAutoMagicPath(script), getAutoMagicScriptsPath(script), autoMagicArguments,
            scriptName, scriptArguments, false);

        String runFilePath = "$restartFromPath/run.txt";
        String commandToRun = script.readFile(runFilePath);
        script.echo("Running command from: $runFilePath");
        if (Alara.isWindows(script)) {
            script.bat(commandToRun);
        }
        else {
            script.sh(commandToRun);
        }
    }

    static String getAutoMagicPath(Object script) {
        String workspacePath = script.env.WORKSPACE;

        return Alara.isWindows(script)
            ? "$workspacePath\\Binaries\\windows\\AutoMagic\\AutoMagic.CommandLine.exe"
            : "$workspacePath/Binaries/debian/AutoMagic/AutoMagic.CommandLine";
    }

    static String getAutoMagicScriptsPath(Object script) {
        String workspacePath = script.env.WORKSPACE;
        return "$workspacePath/Scripts/AutoMagic/AutoMagic.Scripts";
    }

    // This is a workaround for a Jenkins crash "java.io.NotSerializableException: java.util.LinkedHashMap$Entry".
    // It doesn't look that this bug would be fixed any time soon https://issues.jenkins.io/browse/JENKINS-49732
    // So, we need to do this workaround. Copy-pasted from:
    // https://stackoverflow.com/questions/40159258/impossibility-to-iterate-over-a-map-using-groovy-within-jenkins-pipeline
    @NonCPS
    private static List mapToList(Map inMap) {
        List outList = [];
        for (def mapEntry in inMap) {
            outList.add(new java.util.AbstractMap.SimpleImmutableEntry(mapEntry.key, mapEntry.value));
        }
        return outList;
    }

}
