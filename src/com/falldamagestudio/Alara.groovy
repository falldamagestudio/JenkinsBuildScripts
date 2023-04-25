package com.falldamagestudio

class Alara implements Serializable {

    Object script

    Alara(Object script) { this.script = script }

    void run(String cmd) {
        if (AlaraPlatform.IS_WINDOWS) {
            script.bat(cmd);
        }
        else {
            script.sh(cmd);
        }
    }

}
