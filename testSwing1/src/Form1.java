import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import java.awt.Window.Type;
import javax.swing.JSpinner;


public class Form1 {

	private JFrame frmHello;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form1 window = new Form1();
					window.frmHello.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Form1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHello = new JFrame();
		frmHello.setTitle("Hello");
		frmHello.setForeground(new Color(255, 255, 255));
		frmHello.setBackground(new Color(255, 255, 255));
		frmHello.setIconImage(Toolkit.getDefaultToolkit().getImage(Form1.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		frmHello.setBounds(100, 100, 450, 300);
		frmHello.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHello.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		frmHello.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setBounds(48, 49, 86, 20);
		textField_1.setColumns(10);
		panel.add(textField_1);
		
		textField = new JTextField();
		textField.setBounds(48, 80, 86, 20);
		textField.setColumns(10);
		panel.add(textField);
		
		textField_2 = new JTextField();
		textField_2.setBounds(48, 111, 86, 20);
		textField_2.setColumns(10);
		panel.add(textField_2);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(48, 142, 86, 23);
		panel.add(button);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(186, 90, 108, 41);
		panel.add(spinner);
	}
}
