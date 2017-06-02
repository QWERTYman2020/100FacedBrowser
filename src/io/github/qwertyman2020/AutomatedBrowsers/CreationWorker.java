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
		this.factory = factory;
		this.key = key;
		this.path = path;
		this.window=window;
	}

	/**adds a new webdriver to the driver Map in mainGUI.
	 * 
	 */
	
	@Override
	protected Void doInBackground() throws Exception {
		
		//TODO replace system outs with a logger object.
		window.putDriver(key, factory.createWebDriver(key,path));
		return null;
	}
	
	//triggers when thread is closed/terminated
	@Override
	protected void done() {
		super.done();
	}
};
