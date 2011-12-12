package cl.tidev.commons.mvc.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import cl.tidev.commons.library.export.Engine;
import cl.tidev.commons.library.export.ExcelEngine;
import cl.tidev.commons.library.export.Export;

public class ExcelView extends View {
	
	private FileInputStream fis;
	
	public void prepareResponse(List<?> results, List<String> header, Class<?> clazz) throws RowsExceededException, WriteException, IllegalArgumentException, IOException, IllegalAccessException {
		ExcelEngine export = (ExcelEngine) Export.factory(Engine.EXCEL);
		export.createFromObject(results, header, clazz);
		
		fis = export.getFile();
	}
	
	public void prepareFromMap(List<?> results, List<String> header) throws WriteException, IOException, IllegalArgumentException, IllegalAccessException {
		ExcelEngine export = (ExcelEngine) Export.factory(Engine.EXCEL);
		export.createFromMap(results, header);
	
		fis =  export.getFile();
	}
	
	public void prepareResponse(List<?> results, List<String> header) throws WriteException, IOException, IllegalArgumentException, IllegalAccessException {
		ExcelEngine export = (ExcelEngine) Export.factory(Engine.EXCEL);
		export.createFromObject(results, header);
		
		fis =  export.getFile();
	}
	
	public void prepareResponse(List<?> list, Class<?> clazz) throws WriteException, IOException, IllegalArgumentException, IllegalAccessException {
		ExcelEngine export = (ExcelEngine) Export.factory(Engine.EXCEL);
		export.createFromObject(list, clazz);
		
		fis =  export.getFile();
	}
	
	@Override
	public void render(HttpServletResponse response){
		// TODO Auto-generated method stub
		byte[] bytes;
		try {
			bytes = new byte[fis.available()];
			fis.read(bytes);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename=excel.xls");
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			fis.close();
		} catch (IOException e) {
			Logger.getLogger( ExcelView.class).error(e);
		}
		
	}

	@Override
	public void prepareResponse(List<?> list) {
		// TODO Auto-generated method stub
	}

	@Override
	public void prepareResponse(Map<?, ?> map) {
		// TODO Auto-generated method stub
	}

	@Override
	public void prepareResponse(Object object) {
		// TODO Auto-generated method stub
	}

	@Override
	public void prepareResponse(String responseString) {
		// TODO Auto-generated method stub
	}

	@Override
	public void prepareResponse(Boolean success, String value) {
		// TODO Auto-generated method stub
	}

}
