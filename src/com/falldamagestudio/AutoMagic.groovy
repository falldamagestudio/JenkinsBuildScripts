package com.falldamagestudio

class AutoMagic {

    static final Map EMPTY_MAP = Collections.unmodifiableMap([:]);

    static Object runScript(Object script, String cliPath, String amScriptPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments, Boolean returnOutput) {
        String amArugentsString = """--scriptpath="$amScriptPath" """;

        for (def item in autoMagicArguments) {
            if (item.value) {
                amArugentsString += """--$item.key="$item.value" """;
            } else {
                amArugentsString += "--$item.key ";
            }
        }

        String scriptArgumentsString = '';

        for (def item in scriptArguments) {
            scriptArgumentsString += """--$item.key="$item.value" """;
        }

        String cmdString = "$cliPath $amArugentsString run $scriptName $scriptArgumentsString";

        return isWindows(script)
            ? script.bat(script: cmdString, returnStdout:returnOutput)
            : script.sh(script: cmdString, returnStdout:returnOutput);
    }

    static void runScriptFrom(Object script, String restartFromPath, Map autoMagicArguments,
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
