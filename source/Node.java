import java.util.ArrayList;

public class Node<DataPoint>{
    String[] features;
    public Node(ArrayList<DataPoint> dPoints, String){
        this.dataPoints = dPoints;
    }
    private ArrayList<DataPoint> dataPoints;
    private int getNumOfTargetFeatures;

    public Node<DataPoint> left;
    public Node<DataPoint> right;

    public void calculateGiniImpurity(){
        // Insert Gini Impurity equation
        try{
            if (dataPoints == null){
                throw new NullPointerException("DataPoint Array in Node is null");
            }
        } catch(NullPointerException e){
            System.err.println("Caught NullPointerException: " + e.getMessage());
            e.printStackTrace();
        }
        // count the number of points that classify as one of the target features E, ET, T, M

    }




}
