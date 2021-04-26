package encryptedCL;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class EncryptedClassloader extends ClassLoader {

    private final FileEncrypterDecrypter fileEncrypterDecrypter;
    private final String key; //нет необходимости, но поле должно быть по заданию
    private final File dir;

    public EncryptedClassloader(ClassLoader parent, String key, File dir) throws CryptoException {
        super(parent);
        this.key = key;

        File dirFileFromResources = new File(EncryptedClassloader.class.getResource("/" + dir).getFile());
        if(!dirFileFromResources.isDirectory()){
            throw new CryptoException("Error: dir path is not a directory");
        }
        this.dir = dir;
        SecretKey secretKey;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] salt = {
                    (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                    (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
            };
            KeySpec spec = new PBEKeySpec(this.key.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException("Error: unsupported cryptographic algorithm", e);
        } catch (InvalidKeySpecException e) {
            throw new CryptoException("Error: invalid secure key init", e);
        }
        fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey, "AES", "/" + dir);
    }

    public void encryptClass(String name) throws CryptoException {
        File classFile = new File(FileEncrypterDecrypter.class.getResource( "/" + dir + "/" + name + ".class").getFile());
        fileEncrypterDecrypter.encrypt(classFile, name + "_encrypted.enc");
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b;
        try {
            b = fileEncrypterDecrypter.decrypt(name);
        } catch (CryptoException e) {
            throw new ClassNotFoundException("DecryptedClassInit threw an internal exception", e);
        }
        return super.defineClass(null, b, 0, b.length);
    }






}
