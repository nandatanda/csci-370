import java.util.ArrayList;

public class Node {

    public Node(ArrayList<DataRecord> dPoints) {
        this.dataPoints = dPoints;

    }

    private final ArrayList<DataRecord> dataPoints;


    private double giniImpurity;

    private Node left;
    private Node right;

    private String label;

    public ArrayList<DataRecord> getDataPoints() {

        return dataPoints;
    }
    public double getGiniImpurity() {
        return giniImpurity;
    }


    public void setGiniImpurity(double giniImpurity) {
        this.giniImpurity = giniImpurity;
    }


    public Node getLeft() {
        return this.left;
    }

    public void setLeft(Node lNode) {
        this.left = lNode;
    }

    public Node getRight() {
        return this.right;
    }

    public void setRight(Node rNode) {
        this.right = rNode;
    }


    public boolean isLeaf(){
        return (left == null && right == null);
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public void labelLeaf(){

    }






}