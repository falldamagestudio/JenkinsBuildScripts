import groovy.transform.CompileStatic

/**
 * The {@code UnrealPlatform} class represents the platforms supported by Unreal Engine.
 * <p>
 * This class is annotated with {@code @CompileStatic} to generate statically-typed
 * bytecode for better performance and reduced memory usage at runtime.
 * <p>
 * Constants below correspond to Unreal Automation tool cook platform constants
 * They are hardcoded in GetCookPlatform function
 */

@CompileStatic
class UnrealCookPlatform {

    // in UE\Engine\Source\Programs\AutomationTool\Win\WinPlatform.Automation.cs
    static final String COOK_PLATFORM_WINDOWS = 'Windows';
    static final String COOK_PLATFORM_WINDOWS_SERVER = 'WindowsServer';
    static final String COOK_PLATFORM_WINDOWS_CLIENT = 'WindowsClient';

    // in UE\Engine\Source\Programs\AutomationTool\Linux\LinuxPlatform.Automation.cs
    static final String COOK_PLATFORM_LINUX = 'Linux';
    static final String COOK_PLATFORM_LINUX_SERVER = 'LinuxServer';
    static final String COOK_PLATFORM_LINUX_CLIENT = 'LinuxClient';

}
