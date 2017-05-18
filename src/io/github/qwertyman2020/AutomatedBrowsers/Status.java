package io.github.qwertyman2020.AutomatedBrowsers;

public class Status {
	
	private static final int maxBarLength = 100;
	private double Progress;
	private String Title;
	
	public Status(){
		Progress = 0;
		//Title = "";
	}
	
	public void setProgress(int nProgress){
		Progress = nProgress;
	}
	public void addProgress(int add){
		Progress+=add;
	}
	public void addProgress(double add){
		Progress+=add;
	}
	public void setTitle(String nTitle){
		Title=nTitle;
	}
	public double getProgress(){
		if(Progress<maxBarLength){
			return Progress;
		}else{
			return maxBarLength;
		}
	}
	public String getTitle(){
		return Title;
	}
}