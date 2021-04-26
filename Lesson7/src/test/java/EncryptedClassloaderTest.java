import encryptedCL.CryptoException;
import encryptedCL.EncryptedClassloader;
import encryptedCL.PrintableDemo;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class EncryptedClassloaderTest {

    @Test
    public void classloaderTest() throws CryptoException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        File rootFolder = new File("cryptoClasses");
        EncryptedClassloader encryptedClassloader = new EncryptedClassloader(
                EncryptedClassloaderTest.class.getClassLoader(), "password_example", rootFolder);

        encryptedClassloader.encryptClass("ClassA");
        Class<?> decryptedClassA = encryptedClassloader.loadClass("ClassA_encrypted.enc");
        PrintableDemo loadedClassA = (PrintableDemo) decryptedClassA.getConstructor().newInstance();
        assertEquals(111, loadedClassA.printReference());

        encryptedClassloader.encryptClass("ClassB");
        Class<?> decryptedClassB = encryptedClassloader.loadClass("ClassB_encrypted.enc");
        PrintableDemo loadedClassB = (PrintableDemo) decryptedClassB.getConstructor().newInstance();
        assertEquals(222, loadedClassB.printReference());

        encryptedClassloader.encryptClass("ClassC");
        Class<?> decryptedClassC = encryptedClassloader.loadClass("ClassC_encrypted.enc");
        PrintableDemo loadedClassC = (PrintableDemo) decryptedClassC.getConstructor().newInstance();
        assertEquals(333, loadedClassC.printReference());
    }
}
