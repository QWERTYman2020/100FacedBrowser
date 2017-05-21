package io.github.qwertyman2020.AutomatedBrowsers;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
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
	
	
	//TODO make createBrowser methods less generic

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
		WebDriver result;
		
		// configure it as a desired capability
		OperaOptions options = new OperaOptions();
		//options.addArguments("--window-position=9000,9000");// try to place window out of view of user

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		capabilities.setCapability(OperaOptions.CAPABILITY, options);
		
		System.out.println("building Opera driver using path: "+path);
		result = new OperaDriver(new OperaDriverService.Builder()
				.usingDriverExecutable(new File(path))
				.withLogFile(null).build() //TODO consider integrating this in new LOgger object (when i implement it)
				,capabilities);
		return result;
	}
	
	private static WebDriver createIE(String path){
		WebDriver result;
		
		// configure it as a desired capability
		//InternetexplorerOptions options = new ChromeOptions();
		//options.addArguments("--window-position=9000,9000");// try to place window out of view of user

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		//capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.out.println("building IE driver using path: "+path);
		result = new InternetExplorerDriver(new InternetExplorerDriverService.Builder()
				.usingDriverExecutable(new File(path))
				.withLogFile(null).build() //TODO consider integrating this in new LOgger object (when i implement it)
				,capabilities);
		return result;
	}
}
