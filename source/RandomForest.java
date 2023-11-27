import java.util.ArrayList;
import java.util.Random;

public class RandomForest {
    public DataSet dataSet;
    UserConfig settings;
    public int[] treeVotes;
    private ArrayList<DecisionTree> decisionTrees;

    private final int MIN_SAMPLES = 100;
    private final int TREES = 100;
    private final int MAX_DEPTH = 6;
    RandomForest(DataSet dSet, UserConfig settings){
        this.dataSet = dSet;
        this.settings = settings;

    }
    public void train(DataSet dSet){

        for(int i = 0; i < TREES; i++){
            DataSet bootstrappedDataSet = generateBootstrapDataSet(dataSet);
            ArrayList<String> baggedFeatures = generateBaggedFeatures(dataSet.getSplittingFeatures());
            DecisionTree tree = new DecisionTree(bootstrappedDataSet, baggedFeatures, settings);
            decisionTrees.add(tree);
        }

    }

    public DataSet generateBootstrapDataSet(DataSet dSet)
    {
        //Randomly generate bootstrap dataset
        Random rand = new Random();

        DataSet bootstrappedDataset = new DataSet(dataSet.getFeatures(), dataSet.getSplittingFeatures(), dataSet.size());
        for (int i = 0; i < this.dataSet.size(); i++) {
            int r = rand.nextInt(this.dataSet.size());
            DataRecord randomRecord = dataSet.get(r);
            bootstrappedDataset.addEntry(randomRecord);
        }

        return bootstrappedDataset;
    }

    public ArrayList<String> generateBaggedFeatures(ArrayList<String> splittingFeatures){
        ArrayList<String> baggedFeatures = new ArrayList<>();
        Random rand = new Random();
        int fSize = rand.nextInt(splittingFeatures.size());

        for(int i = 0; i < fSize; i++){
            int randomIndex = rand.nextInt(splittingFeatures.size());
            baggedFeatures.add(splittingFeatures.get(randomIndex));

        }

        return baggedFeatures;
    }

}