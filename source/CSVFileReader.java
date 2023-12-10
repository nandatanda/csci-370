import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVFileReader {
    String path = "";
    CSVFileReader(String path){
        this.path = path;
    }

    public void readFile(){
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            // an array with the values
            while ((nextLine = reader.readNext()) != null) {
                // Process each column in the current row

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

    }
}
