import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        char delimiter = ',';
        FileText content = new FileText(args[0]);

        DataSet superset = new DataSet(content, delimiter);
        System.out.print("\n" + superset.dataPoints.get(0));
    }

}