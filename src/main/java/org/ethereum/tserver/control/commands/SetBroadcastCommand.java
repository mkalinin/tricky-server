package org.ethereum.tserver.control.commands;

import org.ethereum.tserver.broadcast.BroadcastManager;
import org.ethereum.tserver.exceptions.ValidationException;

import java.net.URI;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class SetBroadcastCommand extends Command {

    private String broadcastUri;
    private String encryptionKey;

    private SetBroadcastCommand(String broadcastUri, String encryptionKey) {
        this.broadcastUri = broadcastUri;
        this.encryptionKey = encryptionKey;
    }

    @Override
    void execute() {
        BroadcastManager.getInstance().setBroadcast(broadcastUri, encryptionKey);
    }

    static Command make(String[] args) throws ValidationException {
        if(args.length != 3) {
            throw new ValidationException("incorrect arguments");
        } else {
            try {
                URI uri = new URI(args[1]); // just for validation purposes
                if(!"udp".equals(uri.getScheme())) {
                    throw new ValidationException("incorrect uri schema");
                }
            } catch (Exception e) {
                throw new ValidationException(e);
            }
            return new SetBroadcastCommand(args[1], args[2]);
        }
    }
}
