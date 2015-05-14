package org.ethereum.tserver.control.commands;

import org.apache.commons.lang3.EnumUtils;
import org.ethereum.tserver.exceptions.ValidationException;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public abstract class Command implements Runnable {

    public void run() {
        execute();
    }

    abstract void execute();

    static Command make(String[] args) throws ValidationException {
        if(args.length == 0) {
            throw new ValidationException("empty command");
        }
        Type type = EnumUtils.getEnum(Type.class, args[0].toUpperCase());
        if(type == null) {
            throw new ValidationException("unknown command");
        }
        switch (type) {
            case SET_BROADCAST: return SetBroadcastCommand.make(args);
            case CHANGE_SOURCE: return ChangeSourceCommand.make(args);
            default: throw new IllegalArgumentException("unsupported command type " + type.name());
        }
    }

    enum Type {
        SET_BROADCAST,
        CHANGE_SOURCE
    }
}
