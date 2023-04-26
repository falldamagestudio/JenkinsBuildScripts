import com.falldamagestudio.Alara

void call(String cmdString) {
    Alara.run(this, cmdString);
}

void call(String cmd, Object[] arguments) {
    Alara.runWithArgs(this, cmd, arguments);
}
