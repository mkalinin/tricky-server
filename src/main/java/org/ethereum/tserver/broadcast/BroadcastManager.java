package org.ethereum.tserver.broadcast;

import org.ethereum.tserver.scraper.HttpScraper;
import org.ethereum.tserver.util.EncryptedContentReader;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class BroadcastManager {

    private static final BroadcastManager INSTANCE = new BroadcastManager();

    private String encryptionKey;
    private Loop loop;

    private BroadcastManager() {
    }

    public synchronized void setBroadcast(String uri, String encryptionKey) {
        this.encryptionKey = encryptionKey;
        BroadcastServer.getInstance().setBroadcast(uri);
        // run this loop only ones
        if(loop == null) {
            loop = new Loop();
            loop.start();
        }
    }

    public synchronized String getEncryptionKey() {
        return encryptionKey;
    }

    public static BroadcastManager getInstance() {
        return INSTANCE;
    }

    private class Loop extends Thread {

        public void run() {
            try {
                // infinite loop
                // TODO add ability to stop the loop
                for(;;) {
                    // get the content
                    String content = HttpScraper.getInstance().getContent();

                    // reading, encrypting and sending
                    EncryptedContentReader reader = new EncryptedContentReader(content, getEncryptionKey());
                    while(reader.read()) {
                        byte[] buf = reader.getBuf();
                        BroadcastServer.getInstance().send(buf);
                    }

                    System.out.print(String.format("done %s\n", HttpScraper.getInstance().getSource()));

                    // a small delay to avoid ban
                    Thread.sleep(3000);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
