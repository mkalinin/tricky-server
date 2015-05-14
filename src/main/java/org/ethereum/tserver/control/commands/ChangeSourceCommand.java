package org.ethereum.tserver.control.commands;

import org.ethereum.tserver.exceptions.ValidationException;
import org.ethereum.tserver.scraper.HttpScraper;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
class ChangeSourceCommand extends Command {

    private String source;

    private ChangeSourceCommand(String source) {
        this.source = source;
    }

    @Override
    void execute() {
        HttpScraper.getInstance().setSource(source);
    }


    static Command make(String[] args) throws ValidationException {
        if(args.length != 2) {
            throw new ValidationException("incorrect arguments");
        } else {
            return new ChangeSourceCommand(args[1]);
        }
    }
}
