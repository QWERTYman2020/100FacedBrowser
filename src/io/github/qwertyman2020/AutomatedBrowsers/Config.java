package io.github.qwertyman2020.AutomatedBrowsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;

/** config.properties reader
 * 
 * @author Bart Terpstra
 */
public class Config {
	
	private String path=null;
	private Properties prop =null;
	private final static String DriverConfigName = "driver.properties";
	public final static String GenericPathToConfig = "settings.properties";
	public static final String BrowserHeightKey = "DefaultBrowserHeight";
	public static final String BrowserWidthKey = "DefaultBrowserWidth";
	
	
	/** initialises and reads config
	 * 
	 * saves the given path.
	 * checks if the path is safe to read from.
	 * otherwise throw exception or generate a generic .properties
	 * reads the .properties file
	 * 
	 * @param path
	 */
	public Config(String path){
		this.path = path;
	}
	
	/** read as many key values as possible
	 * 
	 * attempts to read as much from .properties file as possible.
	 */
	public void readAll() throws AccessDeniedException{
		File file = new File(this.path);
		if(!file.exists()){
			throw new InvalidPathException("A config file","a config file does not exist.");
		}
		if(!file.canRead()){
			throw new AccessDeniedException(file.getPath(),"","can't read a config file file.");
		}

		prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(this.path);

			// load a properties file
			prop.load(input);
		

			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	
	
	/** creates a generic .properties file in given path
	 * 
	 * @param String to path
	 * @throws AccessDeniedException
	 */
	public void createGenericMainConfig(String Path) throws AccessDeniedException{
		//TODO write createGenericMain()
		
		//create a .properties file in the path that is supplied.
		//if(!file.canWrite()){throws}
	}
	
	/** creates a generic .properties file for Drivers
	 * 
	 * @param file
	 * @throws AccessDeniedException
	 */
	public void createGenericDriverConfig(String Path) throws AccessDeniedException{
		//TODO write createGenericMain()
		
		//create a .properties file in the path that is supplied.
		//if(!file.canWrite()){throws}
	}
	
	
	/**gets value from properties based on key
	*
	*@param key of the property value you want.
	*@return the value wrapped in an "Optional"
	*/
	public Optional<String> getProperty(String key){		
		return Optional.ofNullable(prop.getProperty(key)) ;		
	}
	
	public HashMap<DriverType,String> getDriverPathHashMap() throws InvalidPathException, RuntimeException, AccessDeniedException {
		
		//check if DriverFolder is safe
		String driverFolderPath = this.getProperty("DriverFolder").orElseThrow(() -> new RuntimeException("no \"DriverFolder\" path in confguration file."));
		File file = new File(driverFolderPath);
		if(!file.isDirectory() || !file.exists()){
			throw new InvalidPathException("DriverFolder","Driver Folder path in configuration is invalid.");
		}
		if(!file.canRead()){
			throw new AccessDeniedException(file.getPath(),"","Driver Folder Path had read access denied.");
		}
		Config result = new Config(driverFolderPath+File.separator+DriverConfigName);
		result.readAll();
		if(result.isEmpty()){
			throw new RuntimeException("Driver Config was empty.");
		}
		//TODO iterate to make sure each key has a value associated with it.
		//TODO make sure all paths are valid and executable0
		return result.toPathHashMap(driverFolderPath);
	}
	
	private HashMap<DriverType,String> toPathHashMap(String prePath){

		HashMap<DriverType,String> result = new HashMap<DriverType, String>();
	    Iterator it = prop.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        try{
	        	DriverType newKey = DriverType.convertFromString(pair.getKey().toString());
		        result.put(newKey, prePath+File.separator+pair.getValue().toString());
	        }catch(RuntimeException e){
				System.out.println(e.toString());
				e.printStackTrace();
	        	//TODO throw warning for invalid drivertype in config
	        }
	        //it.remove(); 
	    }
	    //TODO check if toHashMap is empty. throw runtimeexception.
		return result;
	}
	
	private boolean isEmpty(){
		return prop.isEmpty();
	}
}