import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    private String path;

    FileManager(String p) {
        path = p;
    }

    public String readFile() {
        // Read an entire file's contents into a String
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(path)) {
            int character;
            while ((character = fr.read()) != -1) {
                sb.append((char) character);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
