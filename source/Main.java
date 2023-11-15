import java.io.IOException;
import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "../data/Video_games_esrb_rating.csv";
        File dataSet = new File(filePath);

        char delimiter = ',';
        FileText content = new FileText(args[0]);


        DataSet superset = new DataSet(content, delimiter);
        System.out.print("\n" + superset.dataPoints.get(1));
    }

}