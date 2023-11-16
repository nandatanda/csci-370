import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        char delimiter = ',';
        FileText content = new FileText("data/Video_games_esrb_rating.csv");

        loadDatasetAndSplit(content, delimiter);
    }
    public static void loadDatasetAndSplit(FileText fileAsString, char delimiter) {

        // subsets are an arraylist consisting of { trainingSet, testingSet }
        DataSet superset; ArrayList<DataSet> subsets;
        File subsetsObjectFile = new File("data/Subsets.ser");
        Serialization<DataSet> serializer = new Serialization<>();

        if (!subsetsObjectFile.exists()) {
            superset = new DataSet(fileAsString, delimiter);
            subsets = superset.split();
            serializer.saveToFile(superset, "data/DataSet.ser");
            serializer.saveListToFile(subsets, "data/Subsets.ser");

        } else {
            subsets = serializer.loadListFromFile("data/Subsets.ser", DataSet.class);
        }

        DataSet trainingSet = subsets.get(0);
        System.out.println(trainingSet.dataPoints.get(0).getData().get("title"));


        System.out.println();

    }

}