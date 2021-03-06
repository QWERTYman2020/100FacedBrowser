package io.github.qwertyman2020.AutomatedBrowsers;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingWorker;

import org.openqa.selenium.WebDriver;

/**worker thread that manipulates a WebDriver based on which command is passed.
 * 
 * flakey implementation, would recomend replacing it ASAP
 * 
 * @author QWERTYman2020
 *
 */
public class CommandWorker extends SwingWorker<Void,Void> {

	//private static final String propertiesPath ="config.properties";
	private WebDriver driver;
	private Command command;
	public CommandWorker(WebDriver driver,Command command){
		this.driver=driver;
		this.command = command;
	}

	@Override
	protected Void doInBackground() throws RuntimeException, MalformedURLException {
		
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
			case Goto: driver.navigate().to(new URL(command.getDetails()));break;
			case Previous: driver.navigate().back();break;
			case Next: driver.navigate().forward();break;
			case Quit: driver.quit();break;
			default: throw new RuntimeException("not a valid command passed to workerthread. this should be impossible");
		}
		return null;
	}

	/* pointless code unless status is revisted
	//takes care of .publish().
	@Override
	protected void process(List<Status> res){
		for(Status status : res){
			System.out.println(status.getTitle()+" prog:"+status.getProgress());
			window.updateGUI(status);
		}
	}*/
	
	
	//triggers when thread is closed/terminated
	@Override
	protected void done() {
		super.done();
	}
};
