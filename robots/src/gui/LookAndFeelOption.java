package gui;

public class LookAndFeelOption{
	private String systemName;
	private String viewName;
	
	
	public LookAndFeelOption(String sysName, String viewName) {
		this.systemName = sysName;
		this.viewName = viewName;
	}
	
	public String getSystemName() {
		return systemName;
	}
	
	public String getViewName() {
		return viewName;
	}
	
}