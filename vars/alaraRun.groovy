import com.falldamagestudio.Alara;

void call(String cmd, Object[] arguments) {
    Alara alara = new Alara(this);
    alara.run(cmd, arguments);
}
