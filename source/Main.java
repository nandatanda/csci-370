import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        FileText fileText = readFile(args[0]);
        char commaDelimiter = ',';

        DataSet original = new DataSet(fileText, commaDelimiter);
        System.out.print(original.dataPoints.get(0).getData());




    }

    public static FileText readFile(String filePath){
        FileText fileText;
        try {
            fileText = new FileText(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileText;


    }


}