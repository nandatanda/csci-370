import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

public class DataSet {
    public ArrayList<DataPoint> dataPoints = new ArrayList<>();
    private String[] features;
    //boolean array that can be used later for checking
    private boolean[] used;
    private int size;

    // Constructor for creating Testing, Validation, & Training
    DataSet(String[] features, int size){
        this.features = features;
        this.size = 0;
        this.used = new boolean[size];
    }

    // Constructor for creating bootstrapped datasets
    public DataSet(String[] features, ArrayList<DataPoint> data){
        this.features = features;
        this.dataPoints = data;
        this.size = dataPoints.size();
        this.used = new boolean[size];

    }

    // Constructor for original read
    public DataSet(FileText fileText, char delimiter){
        String fileAsString = fileText.getText();
        String[] entries;
        entries = fileAsString.split("\n");
        this.features = entries[0].split(String.valueOf(delimiter));

        for(int i = 1; i < entries.length; i++){
            DataPoint row = new DataPoint(features, entries[i], delimiter);
            dataPoints.add(row);
        }
        this.size = dataPoints.size();

    }

    public int getSize(){
        return size;
    }

    public void addEntry(DataPoint dp){
        dataPoints.add(dp);
        this.size++;
    }


    public ArrayList<DataSet> split(){
        // training, testing, validation
        ArrayList<DataSet> subsets = new ArrayList<>();

        // randomize in O(n) time
        Collections.shuffle(dataPoints, new Random());

        for(int i = 0; i < 3; i++){
            subsets.add(new DataSet(features, 0));
        }

        // loop through all data points and separate
        for(int i = 0; i < size; i++){
            int subsetIndex = i % 10;
            int targetSubset = (subsetIndex < 7) ? 0 : ((subsetIndex < 9) ? 1:2);
            subsets.get(targetSubset).addEntry(dataPoints.get(i));
        }

        return subsets;

    }

}