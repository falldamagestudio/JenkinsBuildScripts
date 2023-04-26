import com.falldamagestudio.AutoMagic

Object call(String cmd, boolean returnOutput = false) {
    return AutoMagic.runScript(this, cmd, returnOutput);
}

Object call(String scriptName,  Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this, EmptyMap, scriptName, scriptArguments, returnOutput);
}

Object call(String cliPath, String amScriptPath,
    String scriptName, Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this, cliPath, amScriptPath, EmptyMap, scriptName, scriptArguments, returnOutput);
}

Object call(Map autoMagicArguments, String scriptName,
    Map scriptArguments, boolean returnOutput = false) {

    return AutoMagic.runScript(this, autoMagicPath, autoMagicScriptsPath,
        autoMagicArguments, scriptName, scriptArguments, returnOutput);
}

Object call(String cliPath, String amScriptPath,
                    Map autoMagicArguments, String scriptName,
                    Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this, cliPath, amScriptPath, autoMagicArguments,
        scriptName, scriptArguments, returnOutput);
}
