package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import control.MessageWithLink;
import control.Version;

public class Program extends JFrame {
	public Program() {
	}

	public static String programName = "AnyFind";
	public static final String downloadURL = "https://github.com/pjmeca/anyfind";
	public static final String workingDir = System.getProperty("user.dir");

	private static final long serialVersionUID = 1L;
	private static final String regexHelpURL = "https://cheatography.com/davechild/cheat-sheets/regular-expressions/";
	private static final String additionalCredits = "Eye-Brown.svg: unknown:simple wide-spread figurederivative work: Viniciusmc, CC0, via Wikimedia Commons\n";
	private static final String aboutText = programName
			+ " is a freeware utility that joints the power of regular expressions with massive file finding.\r\n"
			+ "Fast. Simple. Anything you want.\n" + "Version: " + Version.getCurrentVersion() + ".\r\n"
			+ "Developed by Pablo Meca.\n"+additionalCredits;
	private String path;
	private String oldPath;
	private String extension;
	private static GUI g;
	private static Finder d;

	private static char[] stringToChar(String og) {
		char[] result = new char[og.length()];
		og.getChars(0, og.length(), result, 0);

		return result;
	}

	public static String convertDirectory(String path) {
		String newPath = "";

		for (char c : stringToChar(path)) {
			newPath += c;
			if (c == '\\')
				newPath += c;
		}

		return newPath;
	}

	public static String revertDirectory(String path) {
		String newPath = "";
		boolean hayBarra = false;

		for (char c : stringToChar(path)) {
			if (c == '\\') {
				if (!hayBarra) {
					hayBarra = true;
					newPath += c;
				}
			} else {
				newPath += c;
				hayBarra = false;
			}
		}

		return newPath;
	}

	public void includeDirectories() {
		Finder.setIncludeDirectories(!Finder.getIncludeDirectories());
	}

	public void reader() {
		path = g.getPath();
		oldPath = path;
		path = convertDirectory(path);
		extension = g.getRegex();
	}

	public void finder() {
		d.find(extension, path);
	}

	public void showResults(String text) {
		g.showResultados("Confirmation", "<html>" + text + "</html>");
	}

	public void notFound() {
		g.showDialog("Error", "No files found at \"" + oldPath + "\" with \"" + extension + "\" extension.",
				JOptionPane.ERROR_MESSAGE);
	}

	public void empty() {
		g.showDialog("Error", "Please, fulfill all the fields to continue.", JOptionPane.ERROR_MESSAGE);
	}

	public void helpRegex() {
		try {
			java.awt.Desktop.getDesktop().browse(new java.net.URI(regexHelpURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
		g.showDialog("Regex Help",
				"<html>A Regex reference site has been opened,"
						+ " please check your browser.<br>This site, nor its information," + " is associated with "
						+ programName + " by any means.</html>",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void about() {
		g.showDialog("About", aboutText, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showUpdate() {
		g.showDialog("New Update!",
				new MessageWithLink("A new version of " + programName + " is available " + "<a href=\\\"" + downloadURL
						+ "\\\">here</a>.<br>Please download it to get a better experience."),
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
		Program p = new Program();
		d = new Finder(p);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				g = new GUI(p);
			}
		});

		if (Version.isNewVersionAvailable())
			showUpdate();

	}
}
