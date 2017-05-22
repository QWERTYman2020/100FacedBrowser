package io.github.qwertyman2020.AutomatedBrowsers;

public class Command {
	private Action action;
	private String details;
	
	public Command(Action action, String details){
		this.action = action;
		this.details = details;
	}
	public Command(Action action){
		this.action = action;
	}
	
	public Action getAction(){
		return action;
	}
	
	public String getDetails(){
		return details;
	}
}
