import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Output directory must be specified as first command line argument," +
                    " directory of the checkstyle JSON files must be specified as second command line argument, and directory of the Hadoop JSON files must be specified as the third command line argument");
            return;
        }
        String outputDir = args[0];
        String JSONCheckstyleDir = args[1];
        String JSONHadoopDir = args[2];
        ArrayList<String[]> checkStyleRows = Util.makeRowsForCSVFile(JSONCheckstyleDir);
        Util.writeTOCSV(outputDir, "CheckstyleMetrics.csv", checkStyleRows);
        ArrayList<String[]> hadoopRows = Util.makeRowsForCSVFile(JSONHadoopDir);
        Util.writeTOCSV(outputDir, "HadoopMetrics.csv", hadoopRows);
    }
}
