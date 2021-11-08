package main;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder {

	private final Program p;
	private static String path;
	private static String regex;
	private LinkedList<File> results;
	private static boolean includeDirectories = false;

	public Finder(Program p) {
		this.p = p;
	}

	public static String getPath() {
		return path;
	}

	public static String getRegex() {
		return regex;
	}

	private static File[] getFiles(String path) {
		// Creating a File object for directory
		File directoryPath = new File(path);

		// List of all files and directories
		File[] contents;
		if(includeDirectories) {
			contents = directoryPath.listFiles();
		}else {
			contents = directoryPath.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File f) {
					return !f.isDirectory();
				}
			});
		}
		
		return contents;
	}

	/*
	 * Returns just the content that matches the extension
	 */
	private static LinkedList<File> filter(File[] contents, String regex) {

		LinkedList<File> result = new LinkedList<>();

		for (File content : contents) {
			Pattern p = Pattern.compile("^"+regex+"$");
			Matcher m = p.matcher(content.getName());
			if (m.matches())
				result.add(content);
		}

		return result;
	}

	/*
	 * Shows the results of the search.
	 * @param currRegex The regular expression.
	 * @param currPath Path where regex is applied.
	 */
	public void find(String currRegex, String currPath) {
		//TODO Add recursive find
		
		regex = currRegex;
		path = currPath;
		
		if(regex.isEmpty() || path.isEmpty()) {
			p.empty();
			return;
		}

		File[] contents = getFiles(path);

		if (contents != null) {
			String printableContents = "";
			if(includeDirectories)
				printableContents += "There are " + contents.length + " files and directories in \""+Program.revertDirectory(path)+"\":<br>";
			else
				printableContents += "There are " + contents.length + " files in \""+Program.revertDirectory(path)+"\":<br>";

			results = filter(contents, regex);
			printableContents += results.size() + " files matched the regex \"" + regex + "\" statement:<br>";
			printableContents += "----------------------------------<br>";
			for (File f : results)
				printableContents += f.getName()+"<br>";

			p.showResults(printableContents);
		} else {
			p.notFound();
		}
	}
	
	public static boolean getIncludeDirectories() {
		return includeDirectories;
	}
	
	public static void setIncludeDirectories(boolean value) {
		includeDirectories = value;
	}
}