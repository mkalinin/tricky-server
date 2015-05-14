package org.ethereum.tserver;

import org.ethereum.tserver.control.server.ControlServer;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class AppEntry {

    public static void main(String[] args) throws InterruptedException {
        ControlServer.getInstance().start();
    }
}
