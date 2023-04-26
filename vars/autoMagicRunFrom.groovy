import com.falldamagestudio.AutoMagic

Object call(Object script, String restartFromPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments) {
    return AutoMagic.runScriptFrom(script, restartFromPath, autoMagicArguments,
        scriptName, scriptArguments);
}
