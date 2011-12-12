package cl.tidev.commons.library.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import cl.tidev.commons.helper.JpaUtils;
import cl.tidev.commons.helper.ObjectUtils;

public class ExcelEngine extends ExportEngine {

	private WritableFont writableFont;
	private WritableCellFormat writableCellFormat;
	private WritableWorkbook writableBook;
	private WritableSheet writableSheet;
	private File tmpExcelFile;
	
	public ExcelEngine() throws WriteException, IOException{
		writableFont = new WritableFont(WritableFont.ARIAL,8,WritableFont.BOLD);
		writableCellFormat = new WritableCellFormat( writableFont );
		writableCellFormat.setAlignment(Alignment.CENTRE);
		tmpExcelFile = File.createTempFile("excelTmp", "xls");
		writableBook = Workbook.createWorkbook( tmpExcelFile );
		writableSheet = writableBook.createSheet("Excel", 0);
	}
	
	private void buildHeader(List<String> header) throws RowsExceededException, WriteException{
		int excelField = 0;
		
		ListIterator<String> iter = header.listIterator();
		while(iter.hasNext()){
			writableSheet.addCell(new jxl.write.Label(excelField, 0, iter.next(), writableCellFormat ));
			excelField++;
		}
		
	}
	
	private void buildHeader(Class<?> clazz) throws RowsExceededException, WriteException{
		int excelField = 0;
		Field[] fields = clazz.getDeclaredFields();
		for( int i = 0; i < fields.length ; i++){
			Field field = fields[i];
			Boolean isAccessible = field.isAccessible();
			field.setAccessible(true);
			if( !JpaUtils.isToManyAnnotation(field) ){
				writableSheet.addCell(new jxl.write.Label(excelField, 0, field.getName(), writableCellFormat ));
				excelField++;
			}
			
			field.setAccessible(isAccessible);
		}
	}
	
	private void buildContent(List<?> results) throws RowsExceededException, WriteException, IOException, IllegalArgumentException, IllegalAccessException{
		Iterator<?> iter = results.iterator();
		Integer row = 1;
	
		WritableFont writableFont = new WritableFont(WritableFont.ARIAL,9,WritableFont.NO_BOLD);
		WritableCellFormat writableCellFormat = new WritableCellFormat( writableFont );
		writableCellFormat.setAlignment(Alignment.LEFT);
		
		CellView autoSizeCellView = new CellView();
		autoSizeCellView.setAutosize(true); 
		
		while( iter.hasNext() ){
			Object[] o = (Object[]) iter.next();
			Integer cell = 0;
			for( Object data : o ) {
				writableSheet.setColumnView(cell, autoSizeCellView);
				try{
					writableSheet.addCell(new jxl.write.Label(cell, row, data.toString(), writableCellFormat ));
					cell++;
				} catch (NullPointerException e ){
					writableSheet.addCell(new jxl.write.Label(cell, row, "", writableCellFormat ));
					cell++;
				}
			}
			row++;
		}
		writableBook.write();
		writableBook.close();
		
	}
	
	@SuppressWarnings("rawtypes")
	private void buildContent(List<?> results, Boolean Map) throws RowsExceededException, WriteException, IOException, IllegalArgumentException, IllegalAccessException{
		Iterator<?> iter = results.iterator();
		Integer row = 1;
		
		WritableFont writableFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD);
		WritableCellFormat writableCellFormat = new WritableCellFormat( writableFont );
		writableCellFormat.setAlignment(Alignment.LEFT);
		
		CellView autoSizeCellView = new CellView();
		autoSizeCellView.setAutosize(true);
		
		while(iter.hasNext()) {
			Map<? ,?> data = (Map<?, ?>) iter.next();
			Iterator it = data.entrySet().iterator();
			Integer cell = 0;
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
		        writableSheet.setColumnView(cell, autoSizeCellView);
		        writableSheet.addCell(new jxl.write.Label(cell, row, pairs.getValue().toString()
		        		, writableCellFormat ));
		        cell++;
		    }
			row++;
		}
		
		writableBook.write();
		writableBook.close();
	}
	
	private void buildContent(Class<?> clazz, List<?> results) throws RowsExceededException, WriteException, IOException, IllegalArgumentException, IllegalAccessException{
		Field[] fields = clazz.getDeclaredFields();
		Iterator<?> iter = results.iterator();
		Integer row = 1;
		
		WritableFont writableFont = new WritableFont(WritableFont.ARIAL,9,WritableFont.NO_BOLD);
		WritableCellFormat writableCellFormat = new WritableCellFormat( writableFont );
		writableCellFormat.setAlignment(Alignment.LEFT);
		
		CellView autoSizeCellView = new CellView();
		autoSizeCellView.setAutosize(true); 
		
		while(iter.hasNext()) {
			Object classInstance = iter.next();
			Integer cell = 0;
			for( int i = 0; i < fields.length ; i++){
				Field field = fields[i];
				Boolean isAccessible = field.isAccessible();
				field.setAccessible(true);
				
				if( !JpaUtils.isToManyAnnotation(field) ){
					String value = "";
					Type dataType = field.getType();
					writableSheet.setColumnView(cell, autoSizeCellView);
					if( field.get(classInstance) != null){
						if( dataType.equals(String.class)
						  		|| dataType.equals(Long.class) 
						  		|| dataType.equals(long.class)
						  		|| dataType.equals(Integer.class) 
						  		|| dataType.equals(int.class)
						  		|| dataType.equals(Boolean.class) 
						  		|| dataType.equals(boolean.class)
						  		|| dataType.equals(BigDecimal.class)
						  		|| dataType.equals(Timestamp.class)) {
							value = ObjectUtils.parseToString( field.get(classInstance), field.getType() );
						}
						writableSheet.addCell(new jxl.write.Label(cell, row, value, writableCellFormat ));
						
					} else {
						writableSheet.addCell(new jxl.write.Label(cell, row, "", writableCellFormat ));
					}
					cell++;
				}
				field.setAccessible(isAccessible);
			}
			row++;
		}
		writableBook.write();
		writableBook.close();
	}
	
	
	
	public void createFromMap(List<?> results, List<String> header) throws WriteException, IOException, IllegalArgumentException, IllegalAccessException{
		buildHeader(header);
		buildContent(results, true);
	}
	
	public void createFromObject(List<?> results, List<String> header) throws WriteException, IOException, IllegalArgumentException, IllegalAccessException{
		buildHeader(header);
		buildContent(results);
	}
	
	public void createFromObject(List<?> results, List<String> header, Class<?> clazz) throws RowsExceededException, WriteException, IllegalArgumentException, IOException, IllegalAccessException {
		buildHeader(header);
		buildContent(clazz, results);
	}
	
	public void createFromObject(List<?> results, Class<?> clazz) throws WriteException, IOException, IllegalArgumentException, IllegalAccessException{
		buildHeader(clazz);
		buildContent(clazz, results);
	}
	
	public FileInputStream getFile() throws FileNotFoundException{
		return new FileInputStream( tmpExcelFile );
	}
	
	public String getFilePath() throws IOException {
		return tmpExcelFile.getCanonicalPath();
	}
}
