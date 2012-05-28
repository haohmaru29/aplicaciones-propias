package cl.acgp.commons.mvc.view.jquery.tree;

import cl.acgp.commons.helper.JsonUtils;

public class TreeNode {

	private String id;
	private String name;
	private Object event;
	private String state;
	
	
	//"[{ \"data\" : { \"title\": \"Empresa DEMO\"}, \"attr\": { \"id\":\"E1\"}, \"state\": \"closed\" }]"
	
	public TreeNode(String id, String name, String state){
		this.id = id;
		this.name = name;
		this.state = state;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEvent(Object event) {
		this.event = event;
	}
	
	public Object getEvent() {
		return event;
	}
	
	public String getJson(){
		return new StringBuilder().append("{")
			.append( JsonUtils.parseJson("data", JsonUtils.jsonObject("title", name)) )
			.append(",").append( JsonUtils.parseJson("state", state ))
			.append(",").append( JsonUtils.parseJson("attr", JsonUtils.jsonObject("id", id) )) 
			.append("}").toString();
	}
	
	
}
