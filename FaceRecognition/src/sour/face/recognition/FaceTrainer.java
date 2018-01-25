package sour.face.recognition;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import sour.face.recognition.javafaces2.BuildEigenFaces;

import com.googlecode.javacv.cpp.*;
import com.googlecode.javacpp.Loader;

public class FaceTrainer extends JFrame {
	// GUI components
	private FacePanel facePanel;
	private JButton saveBut, buildEigenBut;
	private JTextField nameField;
	private ActionListener saveFacesAction, dontSaveFacesAction, setNameAction;
	private String personName = "face";

	public FaceTrainer() {
		super("Face Trainer");

		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		// Preload the opencv_objdetect module to work around a known bug.
		Loader.load(opencv_objdetect.class);

		facePanel = new FacePanel(); // the sequence of pictures appear here
		c.add(facePanel, BorderLayout.CENTER);

		// button for saving a highlighted face
		saveBut = new JButton("Set Name");

		buildEigenBut = new JButton("Build Eigen Cache");

		nameField = new JTextField(20); // for the name of the recognized face
		// nameField.setEditable(false);

		dontSaveFacesAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						saveBut.setText("Start Saving Images");
						saveBut.removeActionListener(dontSaveFacesAction);
						saveBut.addActionListener(saveFacesAction);
					}
				});
				facePanel.dontSaveFace();
			}
		};

		saveFacesAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						saveBut.setText("Stop Saving Images");
						saveBut.removeActionListener(saveFacesAction);
						saveBut.addActionListener(dontSaveFacesAction);
					}
				});
				facePanel.saveFace();
			}
		};

		setNameAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				personName = nameField.getText().trim();
				nameField.setEditable(false);
				facePanel.setFileName(personName);
				saveBut.setText("Start Saving Images");
				saveBut.removeActionListener(setNameAction);
				saveBut.addActionListener(saveFacesAction);
			}
		};

		saveBut.addActionListener(setNameAction);
		
		buildEigenBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				facePanel.closeDown();
				BuildEigenFaces.build(0);
			}
		});

		JPanel p = new JPanel();
		p.add(saveBut);
		p.add(new JLabel("Name: "));
		p.add(nameField);
		p.add(buildEigenBut);
		c.add(p, BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				facePanel.closeDown(); // stop snapping pics
				System.exit(0);
			}
		});

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String args[]) {
		new FaceTrainer();
	}

}
