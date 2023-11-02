import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FileManager {

    private String path;

    FileManager(String p) {
        path = p;
    }

    public String readFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(path)) {
            int character;
            while ((character = fr.read()) != -1) {
                sb.append((char) character);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            // Handle the case where the file is not found
        }

        return sb.toString();
    }
}
