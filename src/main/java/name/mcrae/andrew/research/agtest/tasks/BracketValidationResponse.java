package name.mcrae.andrew.research.agtest.tasks;

import java.io.Serializable;

public class BracketValidationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4921650303828928756L;
	
	private String input;
	private boolean isBalanced;
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}

	public boolean getIsBalanced() {
		return isBalanced;
	}

	public void setIsBalanced(boolean isBalanced) {
		this.isBalanced = isBalanced;
	}

}
