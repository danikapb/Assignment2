import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ConditionalExpr;

import com.github.javaparser.ast.stmt.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class Method {
    private String fileName;
    private File file;
    private String sourceCode;
    private boolean noInitialCommit;
    private MethodDeclaration md;
    private int size;
    private int McCabe;

    public Method(File file) {
        String name = file.getName();
        String nameWithoutExtension = name.substring(0, name.lastIndexOf("."));
        this.fileName = nameWithoutExtension;
        this.file = file;
        this.noInitialCommit = false;
        getSourceCode();
        McCabe = 1;
        parseCodeWrapper();
        calculateMcCabe();
        calculateSLOC();
    }

    private void parseCodeWrapper() {
        if (sourceCode == null) {
            return;
        }
        try {
            md = StaticJavaParser.parseMethodDeclaration(sourceCode);
        } catch (ParseProblemException parseProblemException) {
            //do nothing, skip this method if it is not parsable
        }
    }
    public boolean problemParsing(){
        return md == null;
    }

    private void calculateMcCabe() {
        if (sourceCode == null) {
            return;
        } else if (md != null) {
            List<IfStmt> ifList = md.findAll(IfStmt.class);
            McCabe += ifList.size();

            List<DoStmt> doList = md.findAll(DoStmt.class);
            McCabe += doList.size();

            List<ForStmt> forList = md.findAll(ForStmt.class);
            McCabe += forList.size();

            List<ForEachStmt> forEachList = md.findAll(ForEachStmt.class);
            McCabe += forEachList.size();

            List<SwitchEntry> switchEntryList = md.findAll(SwitchEntry.class);
            McCabe += switchEntryList.size();

            List<WhileStmt> whileList = md.findAll(WhileStmt.class);
            McCabe += whileList.size();

            List<ConditionalExpr> conditionalExprList = md.findAll(ConditionalExpr.class);
            McCabe += conditionalExprList.size();
        }
    }

    private void calculateSLOC() {
        if (md != null) {
            this.size = md.getBody().toString().split("\n").length;
        }

    }

    public String getFileName() {
        return fileName;
    }


    public int getSize() {
        return size;
    }

    public int getMcCabe() {
        return McCabe;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Method{" +
                "fileName='" + fileName + '\'' +
                '}';
    }

    public boolean hasNoInitialCommit() {
        return noInitialCommit;
    }

    private void getSourceCode() {
        Gson gson = new Gson();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            LinkedTreeMap<Object, Object> jsonMap = gson.fromJson(bufferedReader, LinkedTreeMap.class);
            Map<String, String> changeMap = (Map<String, String>) jsonMap.get("changeHistoryShort");
            String initialCommit = "";
            for (Map.Entry<String, String> entry : changeMap.entrySet()) {
                if (entry.getValue().equals("Yintroduced")) {
                    initialCommit = entry.getKey();
                }
            }
            //if initial commit is empty, then there was no Yintroduced commit
            if (initialCommit != "") {
                Map<String, Map> changeDetailsMap = (Map<String, Map>) jsonMap.get("changeHistoryDetails");
                Map<String, String> initialCommitMap = (Map<String, String>) changeDetailsMap.get(initialCommit);
                sourceCode = initialCommitMap.get("actualSource");
            } else {
                noInitialCommit = true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
