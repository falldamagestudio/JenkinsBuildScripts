import com.falldamagestudio.Alara;

Integer call(String cmdString, boolean returnStatus = true) {
    return Alara.runAndReturnStatus(this, cmdString, returnStatus);
}

Integer call(String cmd,  Object[] arguments) {
    return Alara.runCmdReturnStatus(this, cmd, arguments);
}
