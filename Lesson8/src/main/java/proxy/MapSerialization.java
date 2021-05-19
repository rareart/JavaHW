package proxy;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MapSerialization {
    public void serializeMap(HashMap<String, HashMap<List<NonSerializableClassWrapper>, NonSerializableClassWrapper>> inputMap, String dirPath, boolean ZIPed) throws MapSerializationException {
        File dir;
        try {
            dir = new File(Objects.requireNonNull(MapSerialization.class.getResource(dirPath)).toURI());
        } catch (URISyntaxException e) {
            throw new MapSerializationException("Error: dir path is incorrect", e);
        }
        dir.mkdir();
        if(ZIPed){
            File cachedZipFile = new File(dir, "cachedMap.zip");
            try (FileOutputStream fos = new FileOutputStream(cachedZipFile);
                 ZipOutputStream zipOut = new ZipOutputStream(fos);
                 ObjectOutputStream out = new ObjectOutputStream(zipOut))
            {
                out.writeObject(inputMap);
            } catch (FileNotFoundException e) {
                throw new MapSerializationException("Wrong directory path", e);
            } catch (IOException e) {
                throw new MapSerializationException("Map zip file writing error", e);
            }
        } else {
            File cachedFile = new File(dir, "cachedMap");
            try (FileOutputStream fos = new FileOutputStream(cachedFile);
                 ObjectOutputStream out = new ObjectOutputStream(fos))
            {
                out.writeObject(inputMap);
            } catch (FileNotFoundException e) {
                throw new MapSerializationException("Wrong directory path", e);
            } catch (IOException e) {
                throw new MapSerializationException("Map file writing error", e);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public HashMap<String, HashMap<List<NonSerializableClassWrapper>, NonSerializableClassWrapper>> deserializeMap(String dirPath) throws MapSerializationException {
        //implementation with auto priority for unZIPed files
        URL res = MapSerialization.class.getResource(dirPath + "/cachedMap");
        if(res!=null){
            File cachedFile = new File(res.getFile());
            if(!cachedFile.isFile()){
                throw new MapSerializationException("Cached map file could not be initialized");
            }
            try (FileInputStream fis = new FileInputStream(cachedFile);
                 ObjectInputStream in = new ObjectInputStream(fis))
            {
                return (HashMap<String, HashMap<List<NonSerializableClassWrapper>, NonSerializableClassWrapper>>) in.readObject();
            } catch (FileNotFoundException e) {
                throw new MapSerializationException("Cached map file not found or wrong directory path", e);
            } catch (IOException e) {
                throw new MapSerializationException("Map file reading error", e);
            } catch (ClassNotFoundException e) {
                throw new MapSerializationException("Class not found in bytecode or in classpath", e);
            }
        } else {
            URL zipRes = MapSerialization.class.getResource(dirPath + "/cachedMap.zip");
            if (zipRes!=null) {
                File cachedZipFile = new File(zipRes.getFile());
                if(!cachedZipFile.isFile()){
                    throw new MapSerializationException("Cached map zip file could not be initialized");
                }
                try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(cachedZipFile));
                     ObjectInputStream in = new ObjectInputStream(zipInput)) {
                    return (HashMap<String, HashMap<List<NonSerializableClassWrapper>, NonSerializableClassWrapper>>) in.readObject();
                } catch (FileNotFoundException e) {
                    throw new MapSerializationException("Cached map zip file not found or wrong directory path", e);
                } catch (IOException e) {
                    throw new MapSerializationException("Map zip file reading error", e);
                } catch (ClassNotFoundException e) {
                    throw new MapSerializationException("Class not found in bytecode or in classpath", e);
                }
            } else {
                return new HashMap<>();
            }
        }
    }


}
