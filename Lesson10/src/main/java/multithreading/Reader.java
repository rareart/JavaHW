package multithreading;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public class Reader {

    private final ArrayList<Integer> nums;
    private Iterator<Integer> numsIterator;
    private final String pathStr;

    public Reader(String path){
        this.pathStr = "/" + path;
        nums = new ArrayList<>();
    }

    public void fileInit() throws URISyntaxException, IOException {
        URL res = Reader.class.getResource(pathStr);
        Path path = null;
        if (res != null) {
            path = Paths.get(res.toURI());
        }
        if (path != null) {
            try (Stream<String> lines = Files.lines(path)){
                lines.mapToInt(Integer::parseInt).forEach(nums::add);
            }
        } else {
            throw new IOException("wrong filepath");
        }
        this.numsIterator = nums.iterator();
    }

    public Iterator<Integer> getNumsIterator() {
        return numsIterator;
    }

    public int getNumberOfNums(){
        return nums.size();
    }
}
