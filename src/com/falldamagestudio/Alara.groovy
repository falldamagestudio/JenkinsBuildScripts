package com.falldamagestudio

class Alara {

    static void run(Object script, String cmdString) {
        runAndReturnStatus(script, cmdString, false);
    }

    static void run(Object script, String cmd, Object[] arguments) {
        String stringToCall = null;

        if (arguments) {
            String argumentsString = joinArguments(arguments);
            stringToCall = /$cmd $argumentsString/;
        }
        else {
            stringToCall = cmd;
        }

        run(script, stringToCall);
    }

    static Integer runAndReturnStatus(Object script, String cmdString, boolean returnStatus = true) {
        if (AlaraPlatform.IS_WINDOWS) {
            return script.bat(script: cmdString, returnStatus: returnStatus);
        }

        return script.sh(script: cmdString, returnStatus : returnStatus);
    }

    static Integer runCmdReturnStatus(Object script, String cmd,  Object[] arguments) {
        String stringToCall = null;
        if (arguments) {
            String argumentsString = joinArguments(arguments);
            stringToCall = /$cmd $argumentsString/;
        }
        else {
            stringToCall = cmd;
        }

        return runCmdReturnStatus(script, stringToCall);
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
