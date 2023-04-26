package com.falldamagestudio

class AutoMagic {

    static final Map EMPTY_MAP = Collections.unmodifiableMap([:]);

    Object test(Object script) {
        script.echo("test");
        return null;
    }

    Object runScript(Object script, String cliPath, String amScriptPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments, Boolean returnOutput) {
        String amArugentsString = """--scriptpath="$amScriptPath" """;
        List autoMagicArgumentsList = mapToList(autoMagicArguments);
        for (def item in autoMagicArgumentsList) {
            if (item.value) {
                amArugentsString += """--$item.key="$item.value" """;
            } else {
                amArugentsString += "--$item.key ";
            }
        }

        String scriptArgumentsString = '';
        List scriptArgumentsList = mapToList(scriptArguments);
        for (def item in scriptArgumentsList) {
            scriptArgumentsString += """--$item.key="$item.value" """;
        }

        String cmdString = "$cliPath $amArugentsString run $scriptName $scriptArgumentsString";

        return isWindows(script)
            ? script.bat(script: cmdString, returnStdout:returnOutput)
            : script.sh(script: cmdString, returnStdout:returnOutput);
    }

    void runScriptFrom(Object script, String restartFromPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments) {
        autoMagicArguments['restartfrom'] = restartFromPath;
        runScript(autoMagicArguments, scriptName, scriptArguments);

        String runFilePath = "$restartFromPath/run.txt";
        String commandToRun = readFile(runFilePath);
        echo("Running command from: $runFilePath");
        if (isWindows(script)) {
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

}
