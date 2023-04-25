/**
 * The {@code UnrealPlatform} class represents the platforms supported by Unreal Engine.
 * <p>
 * This class is annotated with {@code @CompileStatic} to generate statically-typed
 * bytecode for better performance and reduced memory usage at runtime.
 */

@CompileStatic
class UnrealPlatform {

    /**
     * The {@code platformWin64} field represents the name of the Win64 platform.
     */
    final String platformWin64 = 'Win64';

    /**
     * The {@code platformLinux} field represents the name of the Linux platform.
     */
    final String platformLinux = 'Linux';

}
