import com.falldamagestudio.AutoMagic

Object call(Object script, String cmd, boolean returnOutput = false) {
    return AutoMagic.runScript(script, cmd, returnOutput);
}

Object call(Object script, String scriptName,  Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(script, EmptyMap, scriptName, scriptArguments, returnOutput);
}

Object call(Object script, String cliPath, String amScriptPath,
    String scriptName, Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(script, cliPath, amScriptPath, EmptyMap, scriptName, scriptArguments, returnOutput);
}

Object call(Object script, Map autoMagicArguments, String scriptName,
    Map scriptArguments, boolean returnOutput = false) {

    return AutoMagic.runScript(script, autoMagicPath, autoMagicScriptsPath,
        autoMagicArguments, scriptName, scriptArguments, returnOutput);
}

Object call(Object script, String cliPath, String amScriptPath,
                    Map autoMagicArguments, String scriptName,
                    Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(script, cliPath, amScriptPath, autoMagicArguments,
        scriptName, scriptArguments, returnOutput);
}
