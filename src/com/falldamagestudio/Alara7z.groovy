package com.falldamagestudio

class Alara7z {

    static void extractFile(Object script, String sourceFilePath, String destinationPath) {
        String[] arguments = [
            'x',                        // extract command (with full path)
            sourceFilePath,             // path to a file to extract
            /-o"$destinationPath"/,     // destination output folder
            '-r',                       // extract recursively
            '-aoa',                     // overwrite destination without asking anything
            ];

        call7z(script, arguments);
    }

    static void compressFolder(Object script, String sourceFodlerPath, String destinationFilePath) {
        /*
            mmt doesn't help too much. It compress files concurently. But we don't have that many in a game build.
            And the biggest one is Alara-WindowsNoEditor.pak taking 85% of the build.

            -mx=1 is the fastest commpression available. But we are not losing that much space.
            From 10.5 GB build on Ryzen 9 5900X:
            -mx=5 compression made 4.56 GB zip in 6:55
            -mx=1 compression made 4.73 GB zip in 2:18
        */
        String[] arguments = [
            'a',                        //  add command
            '-mmt=on',                  //  multi-threading
            '-mx=1',                    //  fastest compression
            destinationFilePath,
            sourceFodlerPath,
            ];
        call7z(script, arguments);
    }

    private static String getExePath(Object script) {
        String workspacePath = script.env.WORKSPACE;
        return Alara.isWindows(script)
            ? "$workspacePath\\Binaries\\7zip\\7za.exe"
            : "$workspacePath/Binaries/debian/7zip/7za";
    }

    private static void call7z(Object script, String[] arguments) {
        // we have to generate command string for 7z ourself because has a special syntax
        // e.g. -o"{destinationPath}" is a valid argument. But -o {destinationPath} is not.
        // so we can't just pass arguments to Alara.runWithArgs. If {destinationPath} has spaces in a path
        // Alara.runWithArgs will try to wrap it with quotes. But 7z will get confused with argument
        // "-o"{destinationPath}"" and will fail.
        String cmdString = '';
        cmdString += getExePath(script);

        for (String argument in arguments) {
            cmdString += ' ';
            cmdString += argument;
        }
        Alara.run(script, cmdString);
    }

}
