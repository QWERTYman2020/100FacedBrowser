package io.github.qwertyman2020.AutomatedBrowsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.Properties;

/** config.properties reader
 * 
 * @author Bart Terpstra
 */
public class Config {
	
	private String path=null;
	private Properties prop =null;
	private final static String PathToConfig = "config.properties";
	
	
	/** initialises and reads config
	 * 
	 * saves the given path.
	 * checks if the path is safe to read from.
	 * otherwise throw exception or generate a generic .properties
	 * reads the .properties file
	 * 
	 * @param path
	 * @throws AccessDeniedException
	 */
	public Config(String path) throws AccessDeniedException{
		this.path = path;
		
		File file = new File(this.path);
		if(!file.isDirectory()){
			file = new File("config.properties");
			this.path = "config.properties";
		}
		if(!file.exists()){
			createGeneric(file);
		}
		if(!file.canRead()){
			throw new AccessDeniedException(file.getPath(),"","read access denied");
		}
		readAll();
	}
	
	/** read as much from file as possible
	 * 
	 * attempts to read as much from .properties file as possible.
	 * if missing: use generics or $absent.
	 * if excess:  ignore
	 */
	private void readAll(){
		

		prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(PathToConfig);

			// load a properties file
			prop.load(input);

			//mandatory driverfolder check
			File TempFolder =new File(prop.getProperty("DriverFolder"));
			if(!TempFolder.isDirectory() || !TempFolder.canRead()) {
				//TODO halt, warning, close on "ok"
			}

			//other mandatory property checks go here.
			
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
	 * @param file
	 * @throws AccessDeniedException
	 */
	private void createGeneric(File file) throws AccessDeniedException{
		//TODO write createGeneric()
		
		//figure out platform
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
}