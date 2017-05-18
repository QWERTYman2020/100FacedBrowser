package io.github.qwertyman2020.AutomatedBrowsers;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JProgressBar progressBar;
	private static mainGUI  window;
	private JButton btnNewButton;
	private volatile Status status;

	
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
				
		progressBar = new JProgressBar();
		progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		progressBar.setSize(new Dimension(300, 100));
		frame.getContentPane().add(progressBar, BorderLayout.CENTER);
		
		btnNewButton = new JButton("Start Working");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.switchButton();
				ExampleWorker worker = new ExampleWorker(window);
			    worker.execute();
			}
		});
		frame.getContentPane().add(btnNewButton, BorderLayout.EAST);
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

	public void switchButton(){
		btnNewButton.setEnabled(!btnNewButton.isEnabled());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//i forgot why this was here.
		//i think it is required by "implements jframe"
	}
}