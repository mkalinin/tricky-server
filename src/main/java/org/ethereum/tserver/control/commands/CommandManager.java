package org.ethereum.tserver.control.commands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class CommandManager {

    private static final CommandManager INSTANCE = new CommandManager();

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private CommandManager() {
    }

    public static CommandManager getInstance() {
        return INSTANCE;
    }

    public void submit(Command cmd) {
        executor.submit(cmd);
    }
}
