import groovy.transform.CompileStatic

/**
 * The {@code UnrealPlatform} class represents the platforms supported by Unreal Engine.
 * <p>
 * This class is annotated with {@code @CompileStatic} to generate statically-typed
 * bytecode for better performance and reduced memory usage at runtime.
 */

@CompileStatic
class UnrealPlatform {

    /**
     * The {@code PLATFORM_WIN64} field represents the name of the Win64 platform.
     */
    static final String PLATFORM_WIN64 = 'Win64';

    /**
     * The {@code PLATFORM_LINUX} field represents the name of the Linux platform.
     */
    static final String PLATFORM_LINUX = 'Linux';

}
