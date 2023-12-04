import java.util.ArrayList;

public class Node {

    private RatingsMap ratingsCount;
    private String splittingFeature;
    private double giniIndex;
    private double giniImpurity;

    private final DataSet data;
    private Node left;
    private Node right;
    private String label;

    public Node() {

    }

    public Node(DataSet data) {
        this.data = data;
    }

    public Node(String feature, ArrayList<String> ratings) {
        this.splittingFeature = feature;
        this.data = new ArrayList<>();
        this.ratingsCount = new RatingsMap();
        this.ratingsCount.initialize();
    }

    public DataSet data() {
        return data;
    }

    public void add(DataRecord datapoint) {
        String rating = datapoint.rating();
        ratingsCount.increment(rating);
        data.add(datapoint);

    }

    public String splittingFeature() {
        return this.splittingFeature;
    }

    public void setSplittingFeature(String f) {
        this.splittingFeature = f;
    }

    public void calculateGiniImpurity() {
        double gini = 1, classProbability;
        for (String f : ratingsCount.keySet()) {
            classProbability = (double) ratingsCount.get(f) / data.size();
            gini -= Math.pow(classProbability, 2);
        }
        giniImpurity = gini;
    }

    public double giniIndex() {
        return giniIndex;
    }

    public double giniImpurity() {
        return giniImpurity;
    }

    public Node left() {
        return left;
    }

    public Node right() {
        return right;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public String label() {
        return this.label;
    }

    public void assignLabel() {
        //label majority feature in leaf
        String majority = "";
        int greatestCount = 0;
        for (String f : ratingsCount.keySet()) {
            int currentCount = (int) ratingsCount.get(f);
            if (currentCount > greatestCount) {
                greatestCount = currentCount;
                majority = f;
            }
        }

        label = majority;
    }
}