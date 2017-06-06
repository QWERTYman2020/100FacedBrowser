package io.github.qwertyman2020.AutomatedBrowsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/** config.properties reader and holding object
 * 
 * @author QWERTYman2020
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
	public void readAll() throws AccessDeniedException, InvalidPathException{
		File file = new File(this.path);
		
		if(!file.exists()){
			throw new InvalidPathException(path,"a config file does not exist.");
		}
		if(!file.canRead()){
			throw new AccessDeniedException(file.getPath(),"","can't read this config file.");
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
	 * pre:  called when config file was not found
	 * post:  created a generic config file in the location specified
	 * 
	 * @param String to path
	 * @throws AccessDeniedException
	 */
	public void createGenericMainConfig(String Path) throws AccessDeniedException{
		//TODO write createGenericMain()
		
		//create a .properties file in the path that is supplied.
		//if(!file.canWrite()){throws}
		
		//safety test creation location
		//create stream
		//write static final variable to the file
	}
	
	/** creates a generic .properties file for Drivers
	 * 
	 * pre:  called when config file was not found
	 * post:  created a generic config file in the location specified
	 * 
	 * @param file
	 * @throws AccessDeniedException
	 */
	public void createGenericDriverConfig(String Path) throws AccessDeniedException{
		//TODO write createGenericMain()
		
		//create a .properties file in the path that is supplied.
		//if(!file.canWrite()){throws}
		
		//safety test creation location
		//create stream
		//write static final variable to the file
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
			throw new InvalidPathException(driverFolderPath,"Driver Folder path in configuration is invalid.");
		}
		if(!file.canRead()){
			throw new AccessDeniedException(file.getPath(),"","Driver Folder Path had read access denied.");
		}
		Config result = new Config(driverFolderPath+File.separator+DriverConfigName);
		result.readAll();
		if(result.isEmpty()){
			throw new RuntimeException("Driver Config was empty.");
		}
		Set<Object> keySet = result.prop.keySet();
		for(Object t:keySet){
			try{
				String evalPath= result.prop.getProperty(t.toString());
				File f = new File(evalPath);
				if(!file.exists()||!file.canExecute()){
					keySet.remove(t);
					System.out.println("Warning: "+evalPath+" was not a valid driver path");
				}else{
					System.out.println(evalPath + " is a valid driver path");
				}
			}catch(Exception e){
				System.out.println("unprecedented fault appeared in 'Config' class, trying to carry on");
				System.out.println(e.getMessage());
			}
		}
		
		if(result.isEmpty()){
			throw new RuntimeException("Driver Config contained no valid/useable driver paths.");
		}
		return result.toPathHashMap(driverFolderPath);
	}
	
	/** uses the objects property variable to create a hashmap with paths to driverexecutables.
	 * 
	 * @param prePath
	 * @return a hashmap with relatively safe paths to driver executables
	 */
	private HashMap<DriverType,String> toPathHashMap(String prePath){

		HashMap<DriverType,String> result = new HashMap<DriverType, String>();
		Set<Object> keySet = prop.keySet();
			for(Object t:keySet){	        
				try{
					result.put(DriverType.convertFromString((String) t), prePath+File.separator+prop.getProperty((String) t));
				}catch(RuntimeException e){
					System.out.println(e.toString());
					e.printStackTrace();	        }
	    }
		if(!result.isEmpty()){
			return result;
		}else{
			throw new RuntimeException("no paths inside driver.properties are executable or exist.");
		}
	}
	
	private boolean isEmpty(){
		return prop.isEmpty();
	}
}