package name.mcrae.andrew.research.agtest.todo;

public class ValidationException extends Exception {
	
	private String varName;
	
	public ValidationException(String msg, String varName, Throwable cause) {
		super(msg, cause);
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
}
