package cl.tidev.commons.library.json;

import java.util.List;

public class JsonResponse {

	private Boolean success;
	private Integer rowCount;
	private List<?> results;
	
	public JsonResponse(){
		
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	
	public Integer getRowCount() {
		return rowCount;
	}
	
	public void setResults(List<?> results) {
		this.results = results;
	}
	
	public List<?> getResults() {
		return results;
	}
	
}
