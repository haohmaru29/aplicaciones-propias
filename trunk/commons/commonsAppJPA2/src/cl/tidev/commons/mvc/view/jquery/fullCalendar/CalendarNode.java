package cl.tidev.commons.mvc.view.jquery.fullCalendar;

public class CalendarNode {

	private Integer id;
	private String title;
	private String start;
	private String end;
	private String url;
	private Boolean allDay;
	
	public CalendarNode(Integer id){
		this.setId(id);
		setAllDay(false);
	}
	
	public CalendarNode(Integer id, String title, String start){
		this.setId(id);
		this.setTitle(title);
		this.setStart(start);
		this.setAllDay(false);
	}
	
	public CalendarNode(Integer id, String title, String start, String url){
		this.setId(id);
		this.setTitle(title);
		this.setStart(start);
		this.setUrl(url);
		this.setAllDay(false);
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStart() {
		return start;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getEnd() {
		return end;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
}
