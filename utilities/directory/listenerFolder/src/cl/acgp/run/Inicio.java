package cl.acgp.run;

import java.util.ResourceBundle;

import cl.acgp.listeners.FileMonitor;

public class Inicio {
	public static Thread thread;

	public static void main(String args[]) {
		ResourceBundle properties = ResourceBundle.getBundle("config");
		FileMonitor monitor =new FileMonitor(properties.getString("file.directory"),properties.getString("file.log"));
		thread = new Thread(monitor);
		thread.start();
	}
}
