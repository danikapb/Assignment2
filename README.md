This project calculates the size and McCabe code metrics for Java methods, from JSON files of the change history of those methods for the hadoop and checkstyle projects generated from CodeShovel. To run the program, you must specify the directory where the hadoop and 
checkstyle directories can be found. 

The program produces two csv files, one per project. In the csv files, the first column shows the file name of the JSON file for a particular method, the second column shows the size of the method, and the third column shows the McCabe. 

To run the code, compile it with maven using mvn clean package assembly:single

Then, switch to the /target directory and run the code with java -jar .\CodeMetrics-1.0-SNAPSHOT-jar-with-dependencies.jar <output directory where you want the output csv files to go> <directory of the checkstyle JSON files> <directory of the Hadoop JSON files>

Then you can find the output CSV files in the directory that you have specified as the first command line argument. 

Outputs from running the project can also be found in the outputFiles directory.

