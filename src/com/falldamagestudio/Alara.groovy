package com.falldamagestudio

class Alara implements Serializable {

    private static final long serialVersionUID = 1L;

    Object script

    Alara(Object script) { this.script = script }

    void run(String cmdString) {
        runAndReturnStatus(cmdString, false);
    }

    void run(String cmd, Object[] arguments) {
        String stringToCall = null;

        if (arguments) {
            String argumentsString = joinArguments(arguments);
            stringToCall = /$cmd $argumentsString/;
        }
        else {
            stringToCall = cmd;
        }

        run(stringToCall);
    }

    Integer runAndReturnStatus(String cmdString, boolean returnStatus = true) {
        if (AlaraPlatform.IS_WINDOWS) {
            return script.bat(script: cmdString, returnStatus: returnStatus);
        }

        return script.sh(script: cmdString, returnStatus : returnStatus);
    }

    Integer runCmdReturnStatus(String cmd,  Object[] arguments) {
        String stringToCall = null;
        if (arguments) {
            String argumentsString = joinArguments(arguments);
            stringToCall = /$cmd $argumentsString/;
        }
        else {
            stringToCall = cmd;
        }

        return runCmdReturnStatus(stringToCall);
    }

    private String joinArguments(Object[] arguments) {
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
