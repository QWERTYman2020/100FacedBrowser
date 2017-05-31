package io.github.qwertyman2020.AutomatedBrowsers;

public enum DriverType {
	Chrome64, Chrome32, Opera64, Opera32, IE32, IE64,Firefox32,Firefox64, All;
	
	public static DriverType convertFromString(String string) throws RuntimeException{
		switch(string){
		case "ChromeDriver32": return DriverType.Chrome32;	
		case "ChromeDriver64": return DriverType.Chrome64;	
		case "OperaDriver32": return DriverType.Opera32;	
		case "OperaDriver64": return DriverType.Opera64;	
		case "IEDriver32": return DriverType.IE32;	
		case "IEDriver64": return DriverType.IE64;	
		case "FirefoxDriver32": return DriverType.Firefox32;	
		case "FirefoxDriver64": return DriverType.Firefox64;
		case "Chrome32": return DriverType.Chrome32;	
		case "Chrome64": return DriverType.Chrome64;	
		case "Opera32": return DriverType.Opera32;	
		case "Opera64": return DriverType.Opera64;	
		case "IE32": return DriverType.IE32;	
		case "IE64": return DriverType.IE64;	
		case "Firefox32": return DriverType.Firefox32;	
		case "Firefox64": return DriverType.Firefox64;
		case "All": return DriverType.All;
		default: throw new RuntimeException("string \""+string+ "\" found is not a valid drivertype.");
		}
	}
	
	public static String[] convertToStringArray(DriverType[] oldList){
		String[] result = new String[oldList.length];
		for(int x=0;x<oldList.length;x++){
			result[x]=oldList[x].toString();
		}
		return result;
	}
}

