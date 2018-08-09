package name.mcrae.andrew.research.agtest.todo;

import java.io.Serializable;
import java.util.Optional;

public class ToDoItemUpdateRequest implements Serializable {

	private String text;
	
	private Optional<Boolean> isCompleted = Optional.empty();
	
	public Optional<Boolean> getIsCompleted() {
		return isCompleted;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = Optional.ofNullable(isCompleted);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
