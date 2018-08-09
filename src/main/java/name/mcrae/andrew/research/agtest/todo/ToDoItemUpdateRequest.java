package name.mcrae.andrew.research.agtest.todo;

import java.io.Serializable;

public class ToDoItemUpdateRequest implements Serializable {

	private String text;
	
	private boolean isCompleted;
	
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
