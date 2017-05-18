package io.github.qwertyman2020.AutomatedBrowsers;

import java.io.File;
import java.nio.file.AccessDeniedException;

/** config.properties reader
 * 
 * @author Bart Terpstra
 */
public class Config {
	
	//global vars
	private String path=null;
	//TODO path for driverfolder.
	//TODO hashtable with driver configurations
	private int[] defaultResolution = new int[2];
	
	
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
	 * if missing: use generics and write to end of file.
	 * if excess:  ignore
	 */
	private void readAll(){
		//TODO write readAll()
		
		// read all properties withing a .properties file
		//to private variables.
		
		//if excess statements. ignore (implement #ignored later)
		//if missing, throw warning and use generic.
		//returns succes.
	}
	
	/** creates a generic .properties file in given path
	 * 
	 * @param file
	 * @throws AccessDeniedException
	 */
	private void createGeneric(File file) throws AccessDeniedException{
		//TODO write createGeneric()
		
		//create a .properties file in the path that is supplied.
		//if(!file.canWrite()){throws}
		
	}
}