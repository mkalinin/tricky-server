package org.ethereum.tserver.util;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class EncryptedContentReader {

    private static final String SALT = "123"; // TODO move to .properties
    private static final int BUFF_SIZE = 256;

    private byte[] buf = new byte[BUFF_SIZE];
    private InputStream is;

    public EncryptedContentReader(String content, String encryptionKey) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(encryptionKey.toCharArray(), SALT.getBytes(), 65536, BUFF_SIZE);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, secret);
        is = new CipherInputStream(new ByteArrayInputStream(content.getBytes("UTF-8")), aes);
    }

    public boolean read() throws IOException {
        return -1 != is.read(buf);
    }

    public byte[] getBuf() {
        return buf;
    }
}
