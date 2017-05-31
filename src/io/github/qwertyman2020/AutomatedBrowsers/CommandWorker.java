package io.github.qwertyman2020.AutomatedBrowsers;

import java.net.URL;
import java.util.List;

import javax.swing.SwingWorker;

import org.openqa.selenium.WebDriver;

public class CommandWorker extends SwingWorker<Void,Status> {

	//private static final String propertiesPath ="config.properties";
	private mainGUI window;
	private WebDriver driver;
	private Command command;
	public CommandWorker(mainGUI window, WebDriver driver,Command command){
		this.window=window;
		this.driver=driver;
		this.command = command;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		//Status status = new Status();
		//TODO initial setup
		

		//TODO replace system outs with a logger object.
		/*
		System.out.println("initial setup completed");
		status.setTitle("initial setup completed");
		status.setProgress(1);
		this.publish(status); //allows data to flow from this thread to other places in the program
		*/
		
		switch(command.getAction()){
			case Goto: driver.navigate().to(new URL(command.getDetails()));driver.navigate().refresh();break;
			case Previous: driver.navigate().back();break;
			case Next: driver.navigate().forward();break;
			case Quit: driver.quit();break;
			default: throw new RuntimeException("not a valid command passed to workerthread. this should be impossible");
		}
		return null;
	}

	//takes care of .publish().
	@Override
	protected void process(List<Status> res){
		for(Status status : res){
			System.out.println(status.getTitle()+" prog:"+status.getProgress());
			window.updateGUI(status);
		}
	}
	
	//triggers when thread is closed/terminated
	@Override
	protected void done() {
		super.done();
	}
};
