/**
 *
 * @author nissan
 */
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreaded {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Date start = new Date();
        ThreadsCreator.Operations();
        Date end = new Date();
        System.out.println("\nRuntime: " + ((end.getTime() - start.getTime()) / 1000) + " seconds");
    }

}

class ThreadsCreator {

    public static void Operations() throws Exception {

        File directoryPath = new File("File Location");
        File filesList[] = directoryPath.listFiles();
        List<Thread> threads = new ArrayList<Thread>();

        for (File file : filesList) {
            // Create a new task
            ThreadsWorker task = new ThreadsWorker(file);
            // Create a new thread
            Thread thread = new Thread(task);
            //Initialize the thread
            thread.start();
            threads.add(thread);
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

class ThreadsWorker implements Runnable {

    public File file;

    public ThreadsWorker(File file) {
        this.file = file;
    }

    public void run() {
        try {
            String data = readFileAsString(file.getAbsolutePath());
            data = data.toLowerCase();
            String pattern = "[a-zA-Z]{5,}+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(data);

            Path path = Paths.get(file.getAbsolutePath());
            // call getFileName() and get FileName path object
            Path fileName = path.getFileName();

            //HashMap<String, Integer> map = new HashMap<>();
            Map<String, Integer> map = Collections.synchronizedMap(new HashMap<String, Integer>());
            while (m.find()) {
                int count = 1;
                if (map.containsKey(m.group()))//key contains
                {
                    count = map.get(m.group());//get corresponding value
                    count++;

                }
                map.put(m.group(), count);
                // System.out.println(map);
                //System.out.println(m.group());
            }
            //System.out.println(map);

            int maxValueInMap = (Collections.max(map.values()));  // This will return max value in the Hashmap
            for (Entry<String, Integer> entry : map.entrySet()) {  // Itrate through hashmap
                if (entry.getValue() == maxValueInMap) {
                    System.out.print("File Name: " + fileName.toString());          // print FileName
                    System.out.println(" | Most Frequent Word: " + entry.getKey());     // Print the key with max value
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ThreadsWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readFileAsString(String fileName) throws Exception {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
