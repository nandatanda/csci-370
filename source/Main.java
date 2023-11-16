import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        char delimiter = ',';
        FileText content = new FileText("data/Video_games_esrb_rating.csv");

        DataSet superset = new DataSet(content, delimiter);
        ArrayList<DataSet> subsets;
        File dataSetObjectFile = new File("data/DataSet.ser");
        File subsetFile = new File("data/Subsets.ser");
        Serialization<DataSet> serializer = new Serialization<DataSet>();

        if (!dataSetObjectFile.exists()){
            serializer.saveToFile(superset, "data/DataSet.ser");
            subsets = superset.split();
            serializer.saveListToFile(subsets, "data/Subsets.ser");

        } else{
            superset = serializer.loadFromFile("data/DataSet.ser", DataSet.class);
            subsets = serializer.loadListFromFile("data/Subsets.ser", DataSet.class);
        }

        DataSet trainingSet = subsets.get(0);
        System.out.println(trainingSet.dataPoints.get(0).getData().get("title"));








        System.out.println();


    }

}