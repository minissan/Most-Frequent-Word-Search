/**
 *
 * @author nissan
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class multithreaded {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		ThreadsCreator.Operations();
	}
}

class ThreadsCreator {
	public static void Operations() throws Exception {
		// Create a new task
		ThreadsWorker task = new ThreadsWorker();
		// Create a new thread
		Thread thread = new Thread(task);
		// Initialize the thread
		thread.start();
		thread.join();
	}
}

class ThreadsWorker implements Runnable {

	public ThreadsWorker() {

	}

	@Override
	public void run() {
		try {
			String data = readFileAsString("File Location");
			data = data.toLowerCase();
			String pattern = "[a-zA-Z]{5,}+";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(data);
			// call getFileName() and get FileName path object
			Path path = Paths.get("File_Location");
			Path fileName = path.getFileName();
			HashMap<String, Integer> map = new HashMap<>();
			while (m.find()) {
				int count = 1;
				if (map.containsKey(m.group()))// key contains
				{
					count = map.get(m.group());// get corresponding value
					count++;
				}
				map.put(m.group(), count);
			}
			// System.out.println(map)
			int maxValueInMap = (Collections.max(map.values())); // This will return max value in the Hashmap
			for (Entry<String, Integer> entry : map.entrySet()) { // Iterate through Hashmap
				if (entry.getValue() == maxValueInMap) {
					System.out.print("File Name: " + fileName.toString()); // print FileName
					System.out.println(" | Most Frequent Word: " + entry.getKey()); // Print the key with max value
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
