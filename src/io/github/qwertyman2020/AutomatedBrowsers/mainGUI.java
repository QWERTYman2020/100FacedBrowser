package io.github.qwertyman2020.AutomatedBrowsers;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

public class mainGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JProgressBar progressBar;
	private static mainGUI  window;
	private JButton mgcDevBtn;
	private volatile Status status;
	private JTextField txtHttpsgogoduckcom;
	private JTextField textField;

	
	/**
	 * Launch the application. 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new mainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("program");
		frame.getContentPane().setLayout(null);
				
		progressBar = new JProgressBar();
		progressBar.setLocation(0, 0);
		progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		progressBar.setSize(new Dimension(434, 14));
		frame.getContentPane().add(progressBar);
		
		mgcDevBtn = new JButton("Magic Dev Button");
		mgcDevBtn.setBounds(317, 14, 117, 248);
		mgcDevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchButton(mgcDevBtn);
				ExampleWorker worker = new ExampleWorker(window);
			    worker.execute();
			}
		});
		frame.getContentPane().add(mgcDevBtn);
		
		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_1.setBounds(10, 25, 40, 40);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(">");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_2.setBounds(52, 25, 40, 40);
		frame.getContentPane().add(btnNewButton_2);
		
		txtHttpsgogoduckcom = new JTextField();
		txtHttpsgogoduckcom.setText("https://gogoduck.com");
		txtHttpsgogoduckcom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtHttpsgogoduckcom.setBounds(102, 31, 156, 29);
		frame.getContentPane().add(txtHttpsgogoduckcom);
		txtHttpsgogoduckcom.setColumns(10);
		
		JButton btnNewButton = new JButton("Go");
		btnNewButton.setBounds(268, 34, 51, 23);
		frame.getContentPane().add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"all", "chrome only", "opera only", "firefox only"}));
		comboBox.setBounds(230, 71, 77, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Control:");
		lblNewLabel.setBounds(174, 74, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(78, 76, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Element ID");
		lblNewLabel_1.setBounds(4, 79, 64, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"leftclick", "select", "focus", "rightclick", "type"}));
		comboBox_1.setBounds(78, 107, 86, 20);
		frame.getContentPane().add(comboBox_1);
		
		JLabel lblNewLabel_2 = new JLabel("Action");
		lblNewLabel_2.setBounds(10, 110, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton_3 = new JButton("do");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_3.setBounds(77, 137, 51, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_3 = new JLabel("<insert \"load script\" interface wich can load a kind of script file.>");
		lblNewLabel_3.setBounds(0, 198, 307, 41);
		frame.getContentPane().add(lblNewLabel_3);
	}
	
	public void updateGUI(Status status){
		   setStatus(status);
		   //use this.status for all gui data
		   progressBar.setValue(Math.toIntExact(Math.round(this.status.getProgress())));
		   frame.setTitle(this.status.getTitle());
		   SwingUtilities.invokeLater(this);
	}
	
	private synchronized void setStatus(Status status) {
		  this.status = status;
	}

	public void switchButton(JButton btn){
		btn.setEnabled(!btn.isEnabled());
	}
	
	//REMOVE WHEN MAGIC DEV BUTON GOES
	public void switchMagicDevButton(){
		mgcDevBtn.setEnabled(!mgcDevBtn.isEnabled());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//i forgot why this was here.
		//i think it is required by "runnable"
	}
}