import com.falldamagestudio.Alara;

void call(String cmd, List arguments) {
    Alara alara = new Alara(this);
    alara.run(cmd, arguments);
}
