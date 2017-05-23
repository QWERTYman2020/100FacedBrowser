package io.github.qwertyman2020.AutomatedBrowsers;

import java.net.URL;
import java.util.List;

import javax.swing.SwingWorker;

import org.openqa.selenium.WebDriver;

public class CreationWorker extends SwingWorker<WebDriver,Void> {

	private DriverFactory factory;
	private DriverType key;
	private String path;
	public CreationWorker(DriverFactory factory, DriverType key, String path){
		//TODO set variables
		this.factory = factory;
		this.key = key;
		this.path = path;
	}

	@Override
	protected WebDriver doInBackground() throws Exception {
		
		//TODO replace system outs with a logger object.
		/*
		System.out.println("initial setup completed");
		status.setTitle("initial setup completed");
		status.setProgress(1);
		this.publish(status); //allows data to flow from this thread to other places in the program
		*/
		return factory.createWebDriver(key,path);
	}
	
	//triggers when thread is closed/terminated
	@Override
	protected void done() {
		super.done();
	}
	
	public DriverType getKey(){
		return this.key;
	}
};
