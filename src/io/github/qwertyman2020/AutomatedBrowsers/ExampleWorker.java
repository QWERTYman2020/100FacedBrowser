package io.github.qwertyman2020.AutomatedBrowsers;

import java.util.List;

import javax.swing.SwingWorker;

public class ExampleWorker extends SwingWorker<Void,Status> {

	//private static final String propertiesPath ="config.properties";
	private mainGUI window;
	public ExampleWorker(mainGUI nwindow){
		window=nwindow;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		Status status = new Status();
		//TODO initial setup
		
		//TODO replace system outs with a logger object.
		System.out.println("initial setup completed");
		status.setTitle("initial setup completed");
		status.setProgress(1);
		this.publish(status); //allows data to flow from this thread to other places in the program
		
		
		for(int x=0;x<10;x++){
			status.addProgress(2.5*x);
			this.publish(status);
			Thread.sleep(500);
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
		window.switchMagicDevButton();
		super.done();
	}
};
