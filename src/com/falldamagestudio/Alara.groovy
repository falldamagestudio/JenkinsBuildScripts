package com.falldamagestudio

class Alara implements Serializable {

    Object script

    Alara(Object script) { this.script = script }

    void run(String cmd, List arguments) {
        // if (AlaraPlatform.isWindows) {
        //     script.bat(cmd, arguments);
        // }
        // else {
        //     script.sh(cmd, arguments);
        // }
        script.sh(cmd, arguments);
    }

}
