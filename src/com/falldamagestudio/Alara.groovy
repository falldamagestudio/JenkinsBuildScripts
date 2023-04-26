package com.falldamagestudio

class Alara {

    static void run(Object script, String cmdString) {
        runWithArgs(script, cmdString, []);
    }

    static void runWithArgs(Object script, String cmd, Object[] arguments) {
        runCmdReturnStatusWithArgs(script, cmd, false, arguments);
    }

    static Integer runAndReturnStatus(Object script, String cmdString, boolean returnStatus) {
        return runCmdReturnStatusWithArgs(script, cmdString, returnStatus, null);
    }

    static Integer runCmdReturnStatusWithArgs(Object script, String cmd, boolean returnStatus, Object[] arguments) {
        String stringToCall = null;
        if (arguments) {
            String argumentsString = joinArguments(arguments);
            stringToCall = /$cmd $argumentsString/;
        }
        else {
            stringToCall = cmd;
        }

        if (isWindows(script)) {
            return script.bat(script: stringToCall, returnStatus: returnStatus);
        }

        return script.sh(script: stringToCall, returnStatus : returnStatus);
    }

    static boolean isWindows(Object script) {
        /*
        * In order to figure out OS version we cannot use System.properties['os.name'] here. In lightweight mode it will
        * return 'linux' even if job is running on Windows. Insetead checking for the presense and value of OS
        * environment variable
        */
        return script.env.OS && script.env.OS == 'Windows_NT';
    }

    private static String joinArguments(Object[] arguments) {
        String returnString = '';

        for (def argument in arguments) {
            String stringArgument = argument;

            // if argument contains any of this characters ' @><' -- add quotes around it
            if (stringArgument.matches('.*[ @><].*')) {
                stringArgument = /"$stringArgument"/;
            }

            // add space between arguments if needed
            if (returnString.length() != 0) {
                stringArgument = " $stringArgument";
            }

            returnString += stringArgument;
        }

        return returnString;
    }

}
