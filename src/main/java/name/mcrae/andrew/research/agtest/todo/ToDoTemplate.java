package name.mcrae.andrew.research.agtest.todo;

import java.io.Serializable;

public class ToDoTemplate implements Serializable {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
