import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;


public class Util {

    public static void writeTOCSV(String outputDir, String csvFileName, ArrayList<String[]> row) {

        try {
            FileWriter outputCSV = new FileWriter(outputDir + File.separator + csvFileName);
            CSVWriter csvWriter = new CSVWriter(outputCSV);
            csvWriter.writeAll(row);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> makeRowsForCSVFile(String dir) {
        File[] fileList = new File(dir).listFiles();
        ArrayList<String[]> rows = new ArrayList<String[]>();
        for (File file : fileList) {
            Method method = new Method(file);
            if (!method.hasNoInitialCommit()) {
                String[] methodRow = {method.getFileName(), String.valueOf(method.getSize()), String.valueOf(method.getMcCabe())};
                rows.add(methodRow);
            }
        }
        return rows;
    }

    public static ArrayList<String> getSourceCode(String JSONDir) {
        ArrayList<String> sourceCodeList = new ArrayList<String>();


        return sourceCodeList;

    }


}
