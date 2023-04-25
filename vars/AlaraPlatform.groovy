import groovy.transform.CompileStatic

@CompileStatic
enum AlaraPlatformEnum {

    WINDOWS,
    LINUX,

}

class AlaraPlatform {

    static AlaraPlatformEnum currentPlatform = {
        String os = System.getProperty('os.name');
        if (os.contains('Windows')) {
            return AlaraPlatformEnum.WINDOWS;
        } else if (os.contains('Linux')) {
            return AlaraPlatformEnum.LINUX;
        }

        throw new UnsupportedOperationException('Unsupported OS: ' + os);
     } ();

    static boolean isWindows = { currentPlatform == AlaraPlatformEnum.WINDOWS } ();

    static boolean isLinux = { currentPlatform == AlaraPlatformEnum.LINUX } ();

}
