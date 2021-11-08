package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import control.MessageWithLink;
import java.awt.Font;

public class GUI {

	public static final String LOGO_PATH = "assets/eye.png";
	
	private final Program p;
	private JFrame frame = new JFrame(Program.programName);
	
	private JTextField txtDefaultPrompt = new JTextField(); // textfield auxiliar para ocultar el prompt por defecto
	protected JTextField txtPath = new JTextField();
	private TextPrompt samplePath = new TextPrompt("C:\\Users\\user\\ExampleDirectory", txtPath,
			TextPrompt.Show.FOCUS_LOST);
	protected JTextField txtRegex = new JTextField();
	private TextPrompt sampleRegex = new TextPrompt(".*", txtRegex, TextPrompt.Show.FOCUS_LOST);
	private JLabel lblPath = new JLabel("Path:");
	private JLabel lblRegex = new JLabel("Regex:");
	private JCheckBox checkDir = new JCheckBox("Include Directories?");
	private JButton btnDelete = new JButton("Find!");
	private JScrollPane scrollPane;

	public GUI(Program p) {
		this.p = p;
		init();
	}

	public String getPath() {
		return txtPath.getText();
	}

	public String getRegex() {
		return txtRegex.getText();
	}

	public void init() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); //TODO Make adaptable
		frame.getContentPane().setLayout(null);

		// Icono TODO cambiar imagen
		ImageIcon icon = new ImageIcon(LOGO_PATH);
		frame.setIconImage(icon.getImage());

		// Menu Bar
		JMenuBar mb = new JMenuBar();
		JMenu mHelp = new JMenu("Help");
		JMenuItem mRegex = new JMenuItem("Regex");
		mRegex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.helpRegex();
			}
		});
		JMenuItem mAbout = new JMenuItem("About");
		mAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.about();
			}
		});
		mb.add(mHelp);
		mHelp.add(mRegex);
		mHelp.add(mAbout);
		frame.setJMenuBar(mb);

		checkDir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				p.includeDirectories();
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				p.reader();
				p.finder();
			}
		});
		
		initRegex();
	}
	
	private void initRegex() {
		frame.setSize(658, 477);

		// JTextField auxiliar para ocultar el prompt
		txtDefaultPrompt.setBounds(0, 0, 0, 0);
		frame.getContentPane().add(txtDefaultPrompt);

		// Path
		lblPath.setBounds(10, 7, 40, 20);
		frame.getContentPane().add(lblPath);
		txtPath.setBounds(48, 8, 590, 18);
		frame.getContentPane().add(txtPath);
		samplePath.changeAlpha(0.6f);

		// Regex
		lblRegex.setBounds(10, 30, 40, 20);
		frame.getContentPane().add(lblRegex);
		txtRegex.setBounds(60, 31, 578, 18);
		frame.getContentPane().add(txtRegex);
		sampleRegex.changeAlpha(0.6f);

		// Check Directories
		checkDir.setBounds(5, 53, 140, 20);
		frame.getContentPane().add(checkDir);
		
		// Button
		btnDelete.setBounds(10, 381, 628, 30);
		frame.getContentPane().add(btnDelete);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 105, 632, 261);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblResults = new JLabel("Results:");
		lblResults.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResults.setBounds(10, 80, 112, 20);
		frame.getContentPane().add(lblResults);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setVisible(true);
	}

	public void showDialog(String title, String message, int messageType) {
		JOptionPane.showMessageDialog(frame, message, title, messageType);
	}
	
	public void showDialog(String title, MessageWithLink message, int messageType) {
		JOptionPane.showMessageDialog(frame, message, title, messageType);
	}

	public void showResultados(String title, String message) {
		scrollPane.setViewportView(new JLabel(message));
	}
}