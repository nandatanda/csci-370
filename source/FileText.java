import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code FileText} class reads the content of a file and provides it as a text.
 * The content is stored in an immutable {@code String}.
 */
public class FileText {

    /**
     * The text content of the file.
     */
    private final String text;

    /**
     * Constructs a {@code FileText} object by reading the content of the specified file.
     *
     * @param p the path of the file to read
     * @throws IOException if an I/O error occurs while reading the file
     */
    public FileText(String p) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (FileReader fr = new FileReader(p)) {
            int character;
            while ((character = fr.read()) != -1) {
                sb.append((char) character);
            }
        }

        this.text = sb.toString();
    }

    /**
     * Gets the text content of the file.
     *
     * @return the content of the file as a {@code String}
     */
    public String getText() {
        return text;
    }
}