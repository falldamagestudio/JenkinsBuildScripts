import com.falldamagestudio.AutoMagic

Object call(String restartFromPath, Map autoMagicArguments,
                        String scriptName, Map scriptArguments) {
    return AutoMagic.runScriptFrom(this, restartFromPath, autoMagicArguments,
        scriptName, scriptArguments);
}
