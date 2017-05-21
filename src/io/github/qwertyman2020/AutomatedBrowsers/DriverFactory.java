package io.github.qwertyman2020.AutomatedBrowsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {
	public static WebDriver createWebDriver(DriverType type,String path){
		switch(type){
		case Chrome64: return createChrome(path);
		case Chrome32: return createChrome(path);
		case Opera64: return createOpera(path);
		case Opera32: return createOpera(path);
		case IE64: return createIE(path);
		case IE32: return createIE(path);
		default: throw new RuntimeException(type.toString()+" was not a valid drivertype, this should be impossible");
		}
	}
	
	private static WebDriver createChrome(String path){
		
		WebDriver result;
		
		// configure it as a desired capability
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--window-position=9000,9000");// try to place window out of view of user

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.out.println("building chrome driver using path: "+path);
		result = new ChromeDriver(new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(path))
				.withLogFile(null).build() //TODO consider integrating this in new LOgger object (when i implement it)
				,capabilities);
		return result;
	}
	
	private static WebDriver createOpera(String path){
		//TODO create createOpera
		return null;
	}
	
	private static WebDriver createIE(String path){
		//TODO create createIE
		return null;
	}
}
