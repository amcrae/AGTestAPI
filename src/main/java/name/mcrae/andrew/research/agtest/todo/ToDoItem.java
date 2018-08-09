package name.mcrae.andrew.research.agtest.todo;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TODO")
public class ToDoItem implements Serializable {

	@Id
	@GeneratedValue
	private long id;
	
	private String text;
	
	private boolean isCompleted = false;
	
	private ZonedDateTime createdAtTimestamp = ZonedDateTime.now();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public String getCreatedAt() {
		return createdAtTimestamp.format(java.time.format.DateTimeFormatter.ISO_INSTANT);
	}
	
	public void setCreatedAt(String createdAt) {
		TemporalAccessor parsed = java.time.format.DateTimeFormatter.ISO_INSTANT.parse(
				createdAt
		);
		ZonedDateTime instant = ZonedDateTime.from(
			parsed
		);
		this.createdAtTimestamp = instant;
	}

	public ToDoItem() {
		super();
	}
	
	public ToDoItem(ToDoTemplate initial) {
		this();
		setText(initial.getText());
	}
	
	public void update(ToDoItemUpdateRequest up) {
		if (up.getText()!=null) this.setText(up.getText());
		if (up.getIsCompleted().isPresent()) this.setIsCompleted( up.getIsCompleted().get() );
	}
}
