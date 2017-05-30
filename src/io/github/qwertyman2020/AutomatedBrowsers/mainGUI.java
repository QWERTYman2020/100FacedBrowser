package io.github.qwertyman2020.AutomatedBrowsers;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.openqa.selenium.WebDriver;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class mainGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private JProgressBar progressBar;
	private static mainGUI  window;
	private volatile Status status;
	private JTextField adressField; //TODO refactor this name.
	private JTextField textField;
	private Config mainCFG;
	private HashMap<DriverType,String> pathMap;
	private HashMap<DriverType, WebDriver> driverMap = new HashMap<DriverType, WebDriver>();
	private DriverFactory factory;
	
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
		
		//read config.properties
		mainCFG = new Config(Config.GenericPathToConfig);
		try {
			mainCFG.readAll();
			System.out.println("main config created succesfully");
			System.out.println(mainCFG.getProperty("DriverFolder").orElse("but property was empty?"));
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			System.out.print("caught AccessDeniedException (check permissions of main config file)");
			System.out.println(e.toString());
			e.printStackTrace();
		}
		try{
			pathMap = mainCFG.getDriverPathHashMap();
			System.out.println("pathMap: "+ mainCFG.toString());
			//check if all drivers exist and can be executed.
			//remove from list if not.
		    Iterator it = pathMap.entrySet().iterator();
		    while (it.hasNext()) {
		        HashMap.Entry pair = (HashMap.Entry)it.next();
		        //TODO verification code
		        //it.remove(); 
		    }
			//TODO check if all drivers exist and are executable
		}catch(Exception e){
			System.out.println("getDriverFolderHashMap is broken");
			System.out.println(e.toString());
			e.printStackTrace();
			//TODO disable all buttons on init, create popup
		}
		//TODO comment out for ddesigner errors
		factory = new DriverFactory(mainCFG);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				cleanupDrivers(driverMap);
			}
		});
		frame.setBounds(100, 100, 451, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("program");
		frame.getContentPane().setLayout(null);
				
		progressBar = new JProgressBar();
		progressBar.setLocation(0, 0);
		progressBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		progressBar.setSize(new Dimension(425, 14));
		frame.getContentPane().add(progressBar);
		
		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//PREVIOUS
				Set<DriverType> keySet = driverMap.keySet();
				for(DriverType t:keySet){
					Command toExecute = new Command(Action.Previous);
					CommandWorker worker = new CommandWorker(window,driverMap.get(t),toExecute);		    
					worker.execute();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_1.setBounds(10, 25, 45, 45);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(">");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//NEXT
				Set<DriverType> keySet = driverMap.keySet();
				for(DriverType t:keySet){
					Command toExecute = new Command(Action.Next);
					CommandWorker worker = new CommandWorker(window,driverMap.get(t),toExecute);		    
					worker.execute();
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_2.setBounds(65, 25, 45, 45);
		frame.getContentPane().add(btnNewButton_2);
		
		adressField = new JTextField();
		adressField .setText("https://gogoduck.com");
		adressField .setFont(new Font("Tahoma", Font.PLAIN, 11));
		adressField .setBounds(116, 31, 231, 29);
		frame.getContentPane().add(adressField );
		adressField .setColumns(10);
		
		JButton btnNewButton = new JButton("Go");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Goto
				Set<DriverType> keySet = driverMap.keySet();
				for(DriverType t:keySet){
					Command toExecute = new Command(Action.Goto,adressField.getText());
					CommandWorker worker = new CommandWorker(window,driverMap.get(t),toExecute);
					worker.execute();
				}
			}
		});
		btnNewButton.setBounds(374, 34, 51, 23);
		frame.getContentPane().add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		//System.out.println(pathMap.toString());
		comboBox.setModel(new DefaultComboBoxModel(new DriverType[] {DriverType.All}));
		for(Object t:pathMap.keySet().toArray()){
			comboBox.addItem(t);
		}
		comboBox.setBounds(230, 76, 77, 20);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Control");
		lblNewLabel.setBounds(174, 79, 46, 14);
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
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new DriverType[] {DriverType.All}));
		for(Object t:pathMap.keySet().toArray()){
			comboBox_2.addItem(t);
		}
		comboBox_2.setBounds(143, 230, 86, 20);
		frame.getContentPane().add(comboBox_2);
		
		JLabel lblNewLabel_3 = new JLabel("Available browsers");
		lblNewLabel_3.setBounds(10, 233, 118, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton_4 = new JButton("SpinUp");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//TODO make spinup using workerthreads NOTE: this is pure polish.
				DriverType currentlySelected = DriverType.convertFromString(comboBox_2.getSelectedItem().toString());
				
				if(!driverMap.containsKey(currentlySelected)){
					if(!currentlySelected.equals(DriverType.All)){
						driverMap.put(currentlySelected, factory.createWebDriver(currentlySelected,pathMap.get(currentlySelected)));
					}else{
						try{
							Set<DriverType> keySet = pathMap.keySet();
							for(DriverType t:keySet){
								System.out.println("trying drivermap.put "+t.toString());
								driverMap.put(t, factory.createWebDriver(t,pathMap.get(t).toString()));
							}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println(e.toString());
								e.printStackTrace();
							}
						}
					}else{
					//TODO popup that the program thinks there is one already or has been interfened with manually.
				}
			}
		});
		btnNewButton_4.setBounds(230, 230, 77, 21);
		frame.getContentPane().add(btnNewButton_4);
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

	public static void cleanupDrivers(Map<DriverType,WebDriver> map){
		Set<DriverType> drivers = map.keySet();
		for(DriverType t:drivers){
			Command toExecute = new Command(Action.Quit);
			CommandWorker worker = new CommandWorker(window,map.get(t),toExecute);		    
			worker.execute();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//i forgot why this was here.
		//i think it is required by "runnable"
	}
}