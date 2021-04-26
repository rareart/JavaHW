package encryptedCL;

import javax.crypto.*;
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileEncrypterDecrypter {
    private final Cipher cipher;
    private final SecretKey secretKey;
    private final String rootDir;

    public FileEncrypterDecrypter(SecretKey secretKey, String transformation, String rootDir) throws CryptoException {
        this.rootDir = rootDir;
        try {
            this.cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException("This cryptographic algorithm is not supported. Try using an another", e);
        } catch (NoSuchPaddingException e) {
            throw new CryptoException("This padding is not supported. Try using an another", e);
        }
        this.secretKey = secretKey;
    }

    public void encrypt(File encryptedContent, String newFileName) throws CryptoException {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (InvalidKeyException e) {
            throw new CryptoException("Invalid secret key. Try using an another", e);
        }

        File dir;
        try {
            dir = new File(FileEncrypterDecrypter.class.getResource(rootDir).toURI());
        } catch (URISyntaxException e) {
            throw new CryptoException("Error: dir file or full path to the class is incorrect", e);
        }
        dir.mkdir();
        File file = new File(dir, newFileName);

        try (
                InputStream inputStream = new FileInputStream(encryptedContent)
        ) {
            byte[] inputBytes = new byte[(int) encryptedContent.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            try (
                    FileOutputStream outputStream = new FileOutputStream(file)
            ) {
                outputStream.write(outputBytes);
            }

        } catch (FileNotFoundException e) {
            throw new CryptoException("Error: class file not found", e);
        } catch (IOException e) {
            throw new CryptoException("Error: unable to read or write class file", e);
        } catch (BadPaddingException e) {
            throw new CryptoException("Error: bad crypto algorithm padding or invalid input password", e);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException("Error: input data is not a multiple of the block-size",e );
        }
    }

    public byte[] decrypt(String fileName) throws CryptoException {

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (InvalidKeyException e) {
            throw new CryptoException("Invalid secret key. Try using an another", e);
        }

        File encryptedClassFile = new File(
                FileEncrypterDecrypter.class.getResource(rootDir + "/" + fileName).getFile());
        byte[] inputBytes = new byte[(int) encryptedClassFile.length()];

        try (
                FileInputStream inputStream = new FileInputStream(encryptedClassFile)
        ) {
            inputStream.read(inputBytes);
            inputBytes = cipher.doFinal(inputBytes);

        } catch (FileNotFoundException e) {
            throw new CryptoException("Error: encrypted class file not found", e);
        } catch (IOException e) {
            throw new CryptoException("Error: unable to read encrypted class file", e);
        } catch (BadPaddingException e) {
            throw new CryptoException("Error: wrong password or invalid encrypting", e);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException("Error: input data is not a multiple of the block-size in encrypted class file", e);
        }

        return inputBytes;
    }
    }
