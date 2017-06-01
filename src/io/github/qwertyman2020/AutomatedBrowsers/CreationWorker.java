package io.github.qwertyman2020.AutomatedBrowsers;


import javax.swing.SwingWorker;

/** worker thread that creates the specified browser
 * 
 * great target for structure changes
 * 
 * @author QWERTYman2020
 *
 */
public class CreationWorker extends SwingWorker<Void,Void> {

	private DriverFactory factory;
	private DriverType key;
	private String path;
	private mainGUI window;
	public CreationWorker(DriverFactory factory, DriverType key, String path,mainGUI window){
		//TODO set variables
		this.factory = factory;
		this.key = key;
		this.path = path;
		this.window=window;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		//TODO replace system outs with a logger object.
		/*
		System.out.println("initial setup completed");
		status.setTitle("initial setup completed");
		status.setProgress(1);
		this.publish(status); //allows data to flow from this thread to other places in the program
		*/
		 window.putDriver(key, factory.createWebDriver(key,path));
		return null;
	}
	
	//triggers when thread is closed/terminated
	@Override
	protected void done() {
		super.done();
	}
};
