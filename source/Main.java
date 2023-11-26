import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The {@code Main} class serves as the entry point for the program.
 * It demonstrates the loading and manipulation of a dataset.
 */
public class Main {

    private static UserConfig settings = new UserConfig();

    /**
     * The main method that is executed when the program starts.
     * It loads a dataset from a CSV file, splits it into subsets, and performs some basic operations.
     *
     * @param args command-line arguments (not used in this program)
     * @throws IOException if an I/O error occurs during file operations
     */
    public static void main(String[] args) throws IOException {
        // Load settings from config.csv
        settings.loadConfig();

        // Debug settings
        System.out.println(Arrays.toString(settings.ratings()));
        String[] newArray = {"a", "b", "c"};
        for (String s : newArray) {
            System.out.println(s.trim());
        }

        // Create a FileText object to read the content of the CSV file
        FileText content = new FileText(settings.trainingDirectory());

        // Load the dataset and split it into subsets
        //ArrayList<DataSet> subsets = loadDatasetAndSplit(content, settings.delimiter());
        //DataSet trainingSet = subsets.get(0);
        //DataSet testingSet = subsets.get(1);

        //RandomForest rf = new RandomForest(trainingSet);
        //System.out.println(rf.set.data.get(0));

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
            superset = new DataSet(fileText, delimiter, settings.nameIndex(), settings.ratingIndex());
            System.out.println(superset);
            subsets = superset.split();
            serializer.saveToFile(superset, "data/dataset.ser");
            serializer.saveListToFile(subsets, "data/subsets.ser");

        } else {
            // If the serialized subsets file exists, load subsets from the file
            subsets = serializer.loadListFromFile("data/subsets.ser", DataSet.class);
        }

        // Load the superset again (for demonstration purposes)
        superset = new DataSet(fileText, delimiter, settings.nameIndex(), settings.ratingIndex());
        // Print information about a data point from the superset
        System.out.println(superset.get(0));
        return subsets;
    }
}
