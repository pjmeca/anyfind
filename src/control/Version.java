package control;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.*;

public class Version {
    
	private static final String urlCheckVersion = "https://pjmeca.github.io/anyfind/latestVersion.txt";
	private static final String currentVersion = "2021-11 (0.9)\n";
	
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
		return currentVersion;
	}
	
	public static boolean isNewVersionAvailable() {
		try {
			return !readStringFromURL(urlCheckVersion).equals(currentVersion);
		} catch (IOException e) {
			return false;
		}
	}
}
