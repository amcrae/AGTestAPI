package name.mcrae.andrew.research.agtest.tasks;

public class APIErrorMsg {
	
	private String name;
	
	public class ErrorDetails {
		private String location;
		private String param;
		private String msg;
		private String value;
		
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getParam() {
			return param;
		}
		public void setParam(String param) {
			this.param = param;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	private ErrorDetails details;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ErrorDetails getDetails() {
		return details;
	}

	public void setDetails(ErrorDetails details) {
		this.details = details;
	}
	

	public APIErrorMsg(String msg, String varName, Throwable cause) {
		super();
		if (cause instanceof IllegalArgumentException) {
			this.name = "ValidationError";
		} else {
			this.name = cause.getClass().getName();
		}
		ErrorDetails d = new ErrorDetails(); 
		d.setLocation(cause.getStackTrace()[0].toString());
		d.setParam(varName);
		d.setMsg(msg);
		d.setValue("");
		this.details = d;
	}
}
