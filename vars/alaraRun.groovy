void call(String cmdString) {
    runAndReturnStatus(cmdString, false);
}

void call(String cmd, Object[] arguments) {
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
