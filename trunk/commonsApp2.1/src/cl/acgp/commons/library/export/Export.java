package cl.acgp.commons.library.export;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.acgp.commons.mvc.service.jpa.ServiceManager;

public class Export {

	public static Export instance;
	public static Map<Engine, String> engineMap = new HashMap<Engine, String>();
	
	static{
		engineMap.put(Engine.PDF, "PdfEngine");
		engineMap.put(Engine.EXCEL, "ExcelEngine");
	}
	
	private Export(){ }
	
	public static ExportEngine factory(Engine engineType){
		if( instance == null)
			instance = new Export();
		
		return instance.getEngine(engineType);
	}
	
	private ExportEngine getEngine(Engine engineType){
		Class<?> export = null;
		try {
			export = Class.forName("cl.ejedigital.commons.library.export.".concat( engineMap.get(engineType) ));
			return (ExportEngine) export.newInstance();
			
		} catch (InstantiationException e) {
			Logger.getLogger( ServiceManager.class ).error( e );
		} catch (IllegalAccessException e) {
			Logger.getLogger( ServiceManager.class ).error( e );
		} catch (Exception e){
			Logger.getLogger( ServiceManager.class ).error( e );
		}
		
		return null;
	}
	
}
