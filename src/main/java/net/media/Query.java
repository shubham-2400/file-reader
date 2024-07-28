package src.main.java.net.media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.*;

class Query implements Runnable{
    public String[] keywords;
    public int queryId;
    public Map<Integer, List<List<String>>> output;
    public Map<Integer, Integer> completionStatus;
    public List<List<String>> queryOutput = new ArrayList<List<String>>();
    public Query(String[] keywords, int queryId, Map<Integer, List<List<String>>> output, Map<Integer, Integer> completionStatus){
        this.keywords = keywords;
        this.queryId = queryId;
        this.output = output;
        this.completionStatus = completionStatus;
    }
    synchronized public void run(){
        File input = new File("/Users/shubham.gho/file-searcher/src/main/java/net/media/input");
        File[] files = input.listFiles();
        for(File file: files){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String aLine;
                int lineNumber = 1;
                while ((aLine = reader.readLine()) != null) {
                    String[] words = aLine.split(" ");
                    List<String> wordsList = Arrays.asList(words);
                    for(String keyword: keywords){
                        if(wordsList.contains(keyword)){
                            List<String> token = new ArrayList<>();
                            token.add(file.getName());
                            token.add(Integer.toString(lineNumber));
                            token.add(keyword);
                            queryOutput.add(token);
                        }
                    }
                    lineNumber ++;
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        output.put(queryId, queryOutput);
        completionStatus.put(queryId, 1);
    }
    
}
