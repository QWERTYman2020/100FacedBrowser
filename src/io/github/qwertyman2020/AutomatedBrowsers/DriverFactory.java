package io.github.qwertyman2020.AutomatedBrowsers;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/** class for creating instances of browsers on the correct places on screen.
 * 
 * @author QWERTYman2020
 *
 */
public class DriverFactory {
	private static int screenWidth;
	private static int browserWidth;
	private static int browserHeight;
	private int nextX;
	private int nextY;

	public DriverFactory(Config config){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenWidth = gd.getDisplayMode().getWidth();
		browserWidth= Integer.parseInt(config.getProperty(Config.BrowserWidthKey).orElse("640"));
		browserHeight = Integer.parseInt(config.getProperty(Config.BrowserWidthKey).orElse("360"));
		nextX = 0;
		nextY = 0;
	}
	
	
	public WebDriver createWebDriver(DriverType type,String path){
		
		switch(type){
		case Chrome64: return createChrome(path);
		case Chrome32: return createChrome(path);
		case Opera64: return createOpera(path);
		case Opera32: return createOpera(path);
		case IE64: return createIE(path);
		case IE32: return createIE(path);
		case Firefox64: return createFirefox(path);
		case Firefox32: return createFirefox(path);
		default: throw new RuntimeException(type.toString()+" was not a valid drivertype, this should be impossible");
		}
	}
	
	
	
	//TODO make createBrowser methods less generic

	private WebDriver createChrome(String path){
		WebDriver result;
		
		// configure it as a desired capability
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-position");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.out.println("building chrome driver using path: "+path);
    	System.setProperty("webdriver.chrome.driver",path);
		result = new ChromeDriver(capabilities);
		
		initWindow(result);
		
		return result;
	}
	
	private WebDriver createOpera(String path){
		WebDriver result;
		
		// configure it as a desired capability
		OperaOptions options = new OperaOptions();
		//options.addArguments("");// try to place window out of view of user

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		
		
		capabilities.setCapability(OperaOptions.CAPABILITY, options);
		System.out.println("building Opera driver using path: "+path);
    	System.setProperty("webdriver.opera.driver",path);
		result = new OperaDriver(capabilities);
		
		initWindow(result);
		return result;
	}
	
	private WebDriver createIE(String path){
		WebDriver result;
		
		// issues that can cause creation fail:
		// IE does not have same security settings for each zone.
		// you tried cookie maniplation (this is not allowed for IE)
		// zoom level needs to be 100%

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		//capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		System.out.println("building IE driver using path: "+path);
    	System.setProperty("webdriver.ie.driver",path);

		result = new InternetExplorerDriver(capabilities);
		
		initWindow(result);
		return result;
	}
	
	private WebDriver createFirefox(String path){
		WebDriver  result;
		
		FirefoxOptions options = new FirefoxOptions();
		//options.addArguments("");// try to place window out of view of user

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);             
		
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
		System.out.println("building Firefox driver with path: "+path);
    	System.setProperty("webdriver.gecko.driver",path);
		result = new FirefoxDriver(capabilities);
		
		initWindow(result);
		return result;
	}
	
	/**resizes and positions browser.
	 * 
	 * will start placing at 0,0 but increase x by browserwidth until screen width is reached.
	 * @param driver
	 */
	
	private void initWindow(WebDriver driver){
		Window window = driver.manage().window();
		window.setPosition(new Point(nextX, nextY));
		window.setSize(new Dimension(browserWidth, browserHeight));
		if(nextX+browserWidth<screenWidth){
			nextX+=browserWidth;
		}else{
			nextX=screenWidth;
		}
	}
}
