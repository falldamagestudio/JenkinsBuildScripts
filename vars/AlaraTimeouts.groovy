import groovy.transform.CompileStatic

/**
 * The {@code AlaraTimeouts} contains Alara Jenkins timeouts constants
 */

@CompileStatic
class AlaraTimeouts {

    static final int DEFAULT_STAGE_TIMEOUT_MINUTES = 15;
    static final int UPDATE_UE4_STAGE_TIMEOUT_MINUTES = 60;
    static final int BUILD_STAGE_TIMEOUT_MINUTES = 180;
    static final int RUNTIME_STAGE_TIMEOUT_MINUTES = 120;

    static final int UPLOAD_TO_STEAM_STAGE_TIMEOUT_MINUTES = 60;
    static final int ZIP_STAGE_TIMEOUT_MINUTES = 30;

}
