import com.falldamagestudio.Alara;

Integer call(String cmdString) {
    return Alara.runAndReturnStatus(this, cmdString, true);
}

Integer call(String cmd, Object[] arguments) {
    return Alara.runCmdReturnStatusWithArgs(this, cmd, true, arguments);
}
