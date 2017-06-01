package io.github.qwertyman2020.AutomatedBrowsers;

/** struct for the command data structure.
 * used to specify actions and their details to commandworkers.
 * 
 * @author QWERTYman2020
 *
 */
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
