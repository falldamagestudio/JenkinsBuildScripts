import com.falldamagestudio.AutoMagic

Object call(String scriptName) {
    return AutoMagic.runScript(this,
        AutoMagic.autoMagicPath, AutoMagic.autoMagicScriptsPath, EmptyMap,
        scriptName, EmptyMap, false);
}

Object call(String scriptName, boolean returnOutput) {
    return AutoMagic.runScript(this,
        AutoMagic.autoMagicPath, AutoMagic.autoMagicScriptsPath, EmptyMap,
        scriptName, EmptyMap, returnOutput);
}

Object call(String scriptName,  Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this,
        AutoMagic.autoMagicPath, AutoMagic.autoMagicScriptsPath, EmptyMap,
        scriptName, scriptArguments, returnOutput);
}

Object call(String cliPath, String amScriptPath,
    String scriptName, Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this,
        cliPath, amScriptPath, EmptyMap,
        scriptName, scriptArguments, returnOutput);
}

Object call(Map autoMagicArguments, String scriptName,
    Map scriptArguments, boolean returnOutput = false) {

    return AutoMagic.runScript(this,
        AutoMagic.autoMagicPath, AutoMagic.autoMagicScriptsPath, autoMagicArguments,
         scriptName, scriptArguments, returnOutput);
}

Object call(String cliPath, String amScriptPath,
                    Map autoMagicArguments, String scriptName,
                    Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this, cliPath, amScriptPath, autoMagicArguments,
        scriptName, scriptArguments, returnOutput);
}
