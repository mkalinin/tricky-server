package org.ethereum.tserver.control.commands;

import org.ethereum.tserver.exceptions.ValidationException;

import java.util.regex.Pattern;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class CommandParser {

    private static Pattern ID_PATTERN = Pattern.compile("[,;\\s]+");

    public static Command parse(String raw) throws ValidationException {
        String[] args = ID_PATTERN.split(raw);
        return Command.make(args);
    }
}
