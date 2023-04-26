import com.falldamagestudio.AutoMagic

Object call(String scriptName) {
    return AutoMagic.test(this);
    // return AutoMagic.runScript(this,
    //     AutoMagic.getAutoMagicPath(this), AutoMagic.getAutoMagicScriptsPath(this), AutoMagic.EMPTY_MAP,
    //     scriptName, AutoMagic.EMPTY_MAP, False);
}

Object call(String scriptName, boolean returnOutput) {
    return AutoMagic.runScript(this,
        AutoMagic.getAutoMagicPath(this), AutoMagic.getAutoMagicScriptsPath(this), AutoMagic.EMPTY_MAP,
        scriptName, AutoMagic.EMPTY_MAP, returnOutput);
}

Object call(String scriptName,  Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this,
        AutoMagic.getAutoMagicPath(this), AutoMagic.getAutoMagicScriptsPath(this), AutoMagic.EMPTY_MAP,
        scriptName, scriptArguments, returnOutput);
}

Object call(String cliPath, String amScriptPath,
    String scriptName, Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this,
        cliPath, amScriptPath, AutoMagic.EMPTY_MAP,
        scriptName, scriptArguments, returnOutput);
}

Object call(Map autoMagicArguments, String scriptName,
    Map scriptArguments, boolean returnOutput = false) {

    return AutoMagic.runScript(this,
        AutoMagic.getAutoMagicPath(this), AutoMagic.getAutoMagicScriptsPath(this), autoMagicArguments,
         scriptName, scriptArguments, returnOutput);
}

Object call(String cliPath, String amScriptPath,
                    Map autoMagicArguments, String scriptName,
                    Map scriptArguments, boolean returnOutput = false) {
    return AutoMagic.runScript(this, cliPath, amScriptPath, autoMagicArguments,
        scriptName, scriptArguments, returnOutput);
}
