import java.util.ArrayList;
import java.util.Random;

public class RandomForest {
    RandomForest(DataSet trainingSet, DataSet testingSet) {
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;
        this.testingClassifications = new ArrayList<>();
        this.decisionTrees = new ArrayList<>();

    }

    private ArrayList<String> testingClassifications;

    private final DataSet trainingSet;
    private final DataSet testingSet;



    UserConfig settings;
    private ArrayList<DecisionTree> decisionTrees;




    public void train() {

        for (int i = 0; i < Main.settings().maxTrees(); i++) {
            DataSet bootstrappedDataSet = generateBootstrapDataSet();
            ArrayList<String> baggedFeatures = generateBaggedFeatures(trainingSet.features());
            DecisionTree tree = new DecisionTree(bootstrappedDataSet, baggedFeatures);
            decisionTrees.add(tree);
        }

    }

    public void test() {
        DataRecord datapoint;
        ArrayList<String> treeVotes = new ArrayList<>();
        for (int i = 0; i < testingSet.size(); i++) {
            datapoint = testingSet.get(i);
            for (DecisionTree decisionTree : decisionTrees) {
                treeVotes.add(decisionTree.castVote(datapoint));
            }
            testingClassifications.add(findMajorityOfTrees(treeVotes));
        }

    }

    public String predict(DataRecord d){
        String predictedLabel = "";
        for (DecisionTree decisionTree : decisionTrees) {
            Node treeNode = decisionTree.root();
            while (!treeNode.isLeaf()) {
                String feature = treeNode.splitFeature();
                if ( d.get(feature)) {
                    treeNode = treeNode.right();
                } else {
                    treeNode = treeNode.left();
                }
            }
            predictedLabel = treeNode.label();
        }
        return predictedLabel;
    }

    private String findMajorityOfTrees(ArrayList<String> votes){
        RatingsMap ratings = new RatingsMap();
        ratings.initialize();
        // loop through tree votes and get those counts
        for(String v : votes){
            ratings.increment(v);
        }
        String highestRating = "";
        int highestCount = 0;
        // loop through and find the highest rating among them
        for (String r : ratings.keySet()) {
           if (ratings.get(r) > highestCount){
               highestCount = ratings.get(r);
               highestRating = r;
           }
        }
        return highestRating;



    }

    public DataSet generateBootstrapDataSet() {
        //Randomly generate bootstrap dataset
        Random rand = new Random();

        DataSet bootstrappedDataset = new DataSet();
        bootstrappedDataset.configure(Main.settings());

        for (int i = 0; i < trainingSet.size(); i++) {
            int r = rand.nextInt(trainingSet.size());
            DataRecord randomRecord = trainingSet.get(r);
            bootstrappedDataset.add(randomRecord);
        }

        return bootstrappedDataset;
    }

    public ArrayList<String> generateBaggedFeatures(ArrayList<String> splittingFeatures) {
        ArrayList<String> baggedFeatures = new ArrayList<>();
        ArrayList<String> localCopy = new ArrayList<>(splittingFeatures);

        Random rand = new Random();
        int randomNumberOfFeatures = rand.nextInt(localCopy.size() + 1);

        while (randomNumberOfFeatures > 0 && !localCopy.isEmpty()) {
            int randomIndex = rand.nextInt(localCopy.size());
            baggedFeatures.add(localCopy.remove(randomIndex));
            randomNumberOfFeatures--;
        }

        return baggedFeatures;
    }

}