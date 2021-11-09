package control;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;
import java.io.*;

public class Version {
    
	private static final String urlCheckVersion = "https://pjmeca.github.io/anyfind/assets/version.txt";
	private static final String urlCurrVersion = "assets/version.txt";
	
	public static String readStringFromURL(String requestURL) throws IOException
	{
	    try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
	            StandardCharsets.UTF_8.toString()))
	    {
	        scanner.useDelimiter("\\A");
	        return scanner.hasNext() ? scanner.next() : "";
	    }
	}
	
	public static String getLastVersion() throws IOException {
		return readStringFromURL(urlCheckVersion);
    }
	
	public static String getCurrentVersion() {
		String content;
		try {
			Scanner s = new Scanner(new File(urlCurrVersion));
			content = s.useDelimiter("\\Z").next();
			s.close();
			return content+"\n"; // GitHub automatically adds \n at the end of files
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean isNewVersionAvailable() {
		try {
			return !readStringFromURL(urlCheckVersion).equals(getCurrentVersion());
		} catch (IOException e) {
			System.err.println("Error checking program version!");
			return false;
		}
	}
}
