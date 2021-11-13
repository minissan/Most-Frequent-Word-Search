/**
 *
 * @author nissan
 */

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Date;

public class SingleThreaded {

	public static void main(String[] args) throws Exception {
		Date start = new Date();
		// Creating a File object for directory
		File directoryPath = new File("File Location");
		File filesList[] = directoryPath.listFiles();
		// String text = "This. is test text. this is. test. teXt This. is test text.
		// This is.. .. test text";
		for (File file : filesList) {

			String data = readFileAsString(file.getAbsolutePath());

			data = data.toLowerCase();
			String pattern = "[a-zA-Z]{5,}+";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(data);

			Path path = Paths.get(file.getAbsolutePath());

			// call getFileName() and get FileName path object
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
				// System.out.println(map);
				// System.out.println(m.group());
			}

			// System.out.println(map);

			int maxValueInMap = (Collections.max(map.values())); // This will return max value in the Hashmap
			for (Entry<String, Integer> entry : map.entrySet()) { // Iterate through hashmap
				if (entry.getValue() == maxValueInMap) {
					System.out.print("File Name: " + fileName.toString()); // print FileName
					System.out.println(" | Most Frequent Word: " + entry.getKey()); // Print the key with max value
				}
			}
		}
		Date end = new Date();
		System.out.println("\nRuntime: " + ((end.getTime() - start.getTime()) / 1000) + " seconds");
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data;
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
}
