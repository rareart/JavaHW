package proxy;


import java.io.*;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.*;

public class ObjectSerializer {

    private final String dirPath;
    private final boolean zipEnabled;

    public ObjectSerializer(String dirPath, boolean zipEnabled) {
        this.dirPath = dirPath;
        this.zipEnabled = zipEnabled;
    }

    public synchronized void serialize(CachedDTO cachedDTO) throws CachedProxyException {
        URL res;
        if (zipEnabled) {
            res = ObjectSerializer.class.getResource(dirPath + "/cachedProxy.zip");
        } else {
            res = ObjectSerializer.class.getResource(dirPath + "/cachedProxy");
        }
        if (res != null) {
            File cachedFile = new File(res.getFile());
            if (cachedFile.isFile()) {
                if (zipEnabled){
                    unzipping(cachedFile);
                    res = ObjectSerializer.class.getResource(dirPath + "/cachedProxy");
                    if (res != null) {
                        cachedFile = new File(res.getFile());
                        if (!cachedFile.isFile()) {
                            createNewCachedFile(cachedDTO);
                            return;
                        }
                    } else {
                        createNewCachedFile(cachedDTO);
                        return;
                    }
                }
                try (FileOutputStream fos = new FileOutputStream(cachedFile, true);
                     AppendableObjectOutputStream out = new AppendableObjectOutputStream(fos)) {
                    out.writeObject(cachedDTO);
                } catch (FileNotFoundException e) {
                    throw new CachedProxyException("Cached file not found", e);
                } catch (IOException e) {
                    throw new CachedProxyException("Cached file writing error", e);
                }
                finally {
                    if (zipEnabled){
                        zipping(cachedFile);
                    }
                }
            } else {
                createNewCachedFile(cachedDTO);
            }
        } else {
            createNewCachedFile(cachedDTO);
        }
    }

    public Object deserializeResult(Method method, Object[] args) throws CachedProxyException, IOException {
        URL res;
        if (zipEnabled){
            res = ObjectSerializer.class.getResource(dirPath + "/cachedProxy.zip");
        } else {
            res = ObjectSerializer.class.getResource(dirPath + "/cachedProxy");
        }
        if (res != null) {
            File cachedFile = new File(res.getFile());
            if (!cachedFile.isFile()) {
                return null;
            }
            InputStream fileStream;
            if (zipEnabled){
                fileStream = unzippingForReadOnly(cachedFile);
                if (fileStream == null){
                    return null;
                }
            } else {
                try {
                    fileStream = new FileInputStream(cachedFile);
                } catch (FileNotFoundException e) {
                    throw new CachedProxyException("Cached file not found or wrong directory path", e);
                }
            }
            try (ObjectInputStream in = new ObjectInputStream(fileStream)) {
                for (; ; ) {
                    CachedDTO cachedDTO = (CachedDTO) in.readObject();
                    if (cachedDTO.getMethodName().equals(method.getName()) && Arrays.equals(cachedDTO.getArgs(), args)) {
                        return cachedDTO.getResult();
                    }
                }
            } catch (EOFException e) {
                return null;
            } catch (IOException e) {
                throw new CachedProxyException("Cached file reading error", e);
            } catch (ClassNotFoundException e) {
                throw new CachedProxyException("Class not found in bytecode or in classpath", e);
            } finally {
                fileStream.close();
            }
        }
        return null;
    }

    private void deleteCache() throws CachedProxyException {
        URL res = ObjectSerializer.class.getResource(dirPath + "/cachedProxy");
        try {
            if (res != null) {
                Files.deleteIfExists(Paths.get(res.toURI()));
            }
        } catch (IOException e) {
            throw new CachedProxyException("Cached file deleting error", e);
        } catch (URISyntaxException e) {
            throw new CachedProxyException("Error: dir path is incorrect", e);
        }
    }

    private void createNewCachedFile(CachedDTO cachedDTO) throws CachedProxyException {
        File dir;
        try {
            if (dirPath.equals("")) {
                dir = new File(Objects.requireNonNull(ObjectSerializer.class.getResource("/" + dirPath)).toURI());
            } else {
                dir = new File(Objects.requireNonNull(ObjectSerializer.class.getResource(dirPath)).toURI());
            }
        } catch (URISyntaxException e) {
            throw new CachedProxyException("Error: dir path is incorrect", e);
        }
        dir.mkdir();
        File cachedFile = new File(dir, "cachedProxy");
        try (FileOutputStream fos = new FileOutputStream(cachedFile);
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(cachedDTO);
        } catch (FileNotFoundException e) {
            throw new CachedProxyException("Wrong directory path", e);
        } catch (IOException e) {
            throw new CachedProxyException("Cached file writing error", e);
        } finally {
            if (zipEnabled) {
                zipping(cachedFile);
            }
        }
    }

    private void zipping(File cachedFile) throws CachedProxyException {
        File dir;
        try {
            if (dirPath.equals("")) {
                dir = new File(Objects.requireNonNull(ObjectSerializer.class.getResource("/" + dirPath)).toURI());
            } else {
                dir = new File(Objects.requireNonNull(ObjectSerializer.class.getResource(dirPath)).toURI());
            }
        } catch (URISyntaxException e) {
            throw new CachedProxyException("Error: dir path is incorrect", e);
        }
        dir.mkdir();
        File cachedZipFile = new File(dir, "cachedProxy.zip");

        try (ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(cachedZipFile));
             FileInputStream fis = new FileInputStream(cachedFile)) {
            ZipEntry entry1 = new ZipEntry("cachedProxy");
            zOut.putNextEntry(entry1);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zOut.write(buffer);
            zOut.closeEntry();
        } catch (FileNotFoundException e) {
            throw new CachedProxyException("Cached file not found (zipping stage)", e);
        } catch (IOException e) {
            throw new CachedProxyException("Cached file writing error (zipping stage)", e);
        } finally {
            if (zipEnabled) {
                deleteCache();
            }
        }
    }

    private void unzipping(File cachedZipFile) throws CachedProxyException {
        File dir;
        try {
            if (dirPath.equals("")) {
                dir = new File(Objects.requireNonNull(ObjectSerializer.class.getResource("/" + dirPath)).toURI());
            } else {
                dir = new File(Objects.requireNonNull(ObjectSerializer.class.getResource(dirPath)).toURI());
            }
        } catch (URISyntaxException e) {
            throw new CachedProxyException("Error: dir path is incorrect", e);
        }
        dir.mkdir();
        File cachedFile = new File(dir, "cachedProxy");

        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(cachedZipFile)))
        {
            while((zin.getNextEntry()) != null){
                try(FileOutputStream fOut = new FileOutputStream(cachedFile)){
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fOut.write(c);
                    }
                    fOut.flush();
                    zin.closeEntry();
                }
            }
        } catch (FileNotFoundException e) {
            throw new CachedProxyException("Cached file not found (unzipping stage)", e);
        } catch (IOException e) {
            throw new CachedProxyException("Cached file I/O error (unzipping stage)", e);
        }
    }

    //performance enhanced unzipping-method for read-only operations:
    private InputStream unzippingForReadOnly(File cachedZipFile) throws CachedProxyException {
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(cachedZipFile));
            for (
                    ZipEntry e; (e = zin.getNextEntry()) != null; ) {
                if (e.getName().equals("cachedProxy")) {
                    return zin;
                }
            }
            zin.close();
            return null;
        } catch (FileNotFoundException e) {
            throw new CachedProxyException("Cached file not found (read-only unzipping stage)", e);
        } catch (IOException e) {
            throw new CachedProxyException("Cached file I/O error (read-only unzipping stage)", e);
        }
    }
}
