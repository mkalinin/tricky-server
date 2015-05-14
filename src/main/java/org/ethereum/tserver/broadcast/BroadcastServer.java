package org.ethereum.tserver.broadcast;

import java.io.IOException;
import java.net.*;
import java.net.DatagramPacket;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class BroadcastServer {

    private static final int PORT = 3249;
    private static final BroadcastServer INSTANCE = new BroadcastServer();

    private InetSocketAddress multicastAddress;
    private DatagramSocket socket;

    private BroadcastServer() {
    }

    public static BroadcastServer getInstance() {
        return INSTANCE;
    }

    public synchronized void setBroadcast(String broadcastUri) {
        try {
            createSocket();
            URI uri = new URI(broadcastUri);
            multicastAddress = new InetSocketAddress(uri.getHost(), uri.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(byte[] buf) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, multicastAddress);
        socket.send(packet);
    }

    private void createSocket() throws SocketException {
        if(socket == null) {
            socket = new DatagramSocket(PORT);
        }
    }
}
