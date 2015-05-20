package org.ethereum.tserver.util;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class EncryptedContentReader {

    private static final String SALT = "123"; // TODO move to .properties
    private static final int BUFF_SIZE = 256;
    private static final int KEY_LENGTH = 256;
    private static final int ITERATIONS_COUNT = 4096;

    private byte[] msgBuf = new byte[BUFF_SIZE];
    private byte[] dataBuf;
    private InputStream is;
    private Cipher aes;

    public EncryptedContentReader(String content, String encryptionKey) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), SALT.getBytes(), ITERATIONS_COUNT, KEY_LENGTH);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        aes = Cipher.getInstance("AES/CFB/NoPadding");
        aes.init(Cipher.ENCRYPT_MODE, secret);
        byte[] iv = aes.getIV();

        // preparing buffers
        dataBuf = new byte[BUFF_SIZE - iv.length];
        // sending IV within data
        System.arraycopy(iv, 0, msgBuf, 0, iv.length);

        is = new ByteArrayInputStream(content.getBytes("UTF-8"));
    }

    public boolean read() throws Exception {
        int bytesRead = is.read(dataBuf);
        if(bytesRead == -1) {
            return false;
        }
        if(bytesRead < dataBuf.length) {
            Arrays.fill(dataBuf, bytesRead, dataBuf.length, (byte)0);
        }

        // encrypting and adding data to message buffer
        byte[] encrypted = aes.doFinal(dataBuf);
        System.arraycopy(encrypted, 0, msgBuf, aes.getBlockSize(), encrypted.length);

        return true;
    }

    public byte[] getBuf() {
        return msgBuf;
    }
}
