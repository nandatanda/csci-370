import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code Main} class serves as the entry point for the program.
 * It demonstrates the loading and manipulation of a dataset.
 */
public class Main {

    /**
     * The main method that is executed when the program starts.
     * It loads a dataset from a CSV file, splits it into subsets, and performs some basic operations.
     *
     * @param args command-line arguments (not used in this program)
     * @throws IOException if an I/O error occurs during file operations
     */
    public static void main(String[] args) throws IOException {
        // Set the delimiter for parsing the CSV file
        String delimiter = ",";

        // Create a FileText object to read the content of the CSV file
        FileText content = new FileText("data/Video_games_esrb_rating.csv");

        // Load the dataset and split it into subsets
        ArrayList<DataSet> subsets = loadDatasetAndSplit(content, delimiter);
        DataSet trainingSet = subsets.get(0);
        DataSet testingSet = subsets.get(1);

        RandomForest rf = new RandomForest(trainingSet);
        System.out.println(rf.set.data.get(0));

    }

    /**
     * Loads the dataset from a file, splits it into subsets, and performs some basic operations.
     *
     * @param fileText  the FileText object containing dataset information
     * @param delimiter the delimiter used in the dataset
     */
    public static ArrayList<DataSet> loadDatasetAndSplit(FileText fileText, String delimiter) {
        // Create variables to hold the superset and subsets of the dataset
        DataSet superset;
        ArrayList<DataSet> subsets;

        // File to store serialized subsets
        File subsetsObjectFile = new File("data/subsets.ser");

        // Create a serializer for object serialization
        Serialization<DataSet> serializer = new Serialization<>();

        // Check if serialized subsets file exists
        if (!subsetsObjectFile.exists()) {
            // If not, create a new superset, split it into subsets, and save them to files
            superset = new DataSet(fileText, delimiter);
            System.out.println(superset);
            subsets = superset.split();
            serializer.saveToFile(superset, "data/dataset.ser");
            serializer.saveListToFile(subsets, "data/subsets.ser");

        } else {
            // If the serialized subsets file exists, load subsets from the file
            subsets = serializer.loadListFromFile("data/subsets.ser", DataSet.class);
        }

        // Load the superset again (for demonstration purposes)
        superset = new DataSet(fileText, delimiter);
        // Print information about a data point from the superset
        return subsets;
    }
}
