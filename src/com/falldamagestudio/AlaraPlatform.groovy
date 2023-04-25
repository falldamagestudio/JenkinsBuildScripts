package com.falldamagestudio

import groovy.transform.CompileStatic

@CompileStatic
enum AlaraPlatformEnum {

    WINDOWS,
    LINUX,

}

@CompileStatic
class AlaraPlatform {

    static {
        String os = System.getProperty('os.name');
        if (os.contains('Windows')) {
            CURRENT_PLATFORM = AlaraPlatformEnum.WINDOWS;
            IS_WINDOWS = true;
            return;
        } else if (os.contains('Linux')) {
            CURRENT_PLATFORM = AlaraPlatformEnum.LINUX;
            IS_LINUX = true;
            return;
        }

        throw new UnsupportedOperationException('Unsupported OS: ' + os);
    }

    final static AlaraPlatformEnum CURRENT_PLATFORM;
    final static boolean IS_WINDOWS;
    final static boolean IS_LINUX;

}
