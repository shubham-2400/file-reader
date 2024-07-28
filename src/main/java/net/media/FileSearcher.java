package src.main.java.net.media;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class FileSearcher {
    public static Map<Integer, List<List<String>>> output = new HashMap<Integer, List<List<String>>>();
    public static Map<Integer, Integer> completionStatus = new HashMap<Integer, Integer>();
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while(true){
            
            System.out.println("instructions to use the file searcher: ");
            System.out.println("1. input a query format: \"1 keyword1 keyword2 ... \"");
            System.out.println("2. input the query to get the output of a query or get the completion status format: \"2 queryId\"");
            System.out.println("3. to quit the application\n\n");
            int command = scanner.nextInt();  
            if(command==1) {
                Random rand = new Random();
                int queryId = rand.nextInt(1000);
                String inputString = scanner.nextLine();
                String[] keywords;
                keywords = inputString.split(" ");
                System.out.println("the numbers you entered: ");
                for (String keyword: keywords){
                    System.out.println(keyword);
                }
                System.out.print("Query id alloted: "+queryId+"\n\n");

                Query query = new Query(keywords, queryId, output, completionStatus);
                completionStatus.put(queryId, -1);
                Thread task = new Thread(query);
                task.start();
            }
            else if(command==2) {
                try{
                    int queryId = scanner.nextInt();
    
                    if(completionStatus.get(queryId) == -1) {
                        System.out.println("the query has not yet completed.");
                        continue;
                    }
                    List<List<String>> queryOutput =  output.get(queryId);
                    for(List<String> token: queryOutput){
                        System.out.println("{file name: " + token.get(0) + ", line number: " + token.get(1) + ", keyword: " + token.get(2) + "}");
                    }
                    System.err.println("\n");

                }catch(Exception e){
                    System.err.println("please enter valid input");
                }
            }
            else {
                break;
            }

        }
        scanner.close();
    }
}   

