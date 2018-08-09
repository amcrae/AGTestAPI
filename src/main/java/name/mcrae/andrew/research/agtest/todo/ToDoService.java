package name.mcrae.andrew.research.agtest.todo;

import java.util.MissingResourceException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@Component
public class ToDoService {

	private ToDoRepository db;
	
	@RequestMapping(path="/agtest/1.0/todo", method=RequestMethod.POST)
	@ResponseStatus(code=HttpStatus.OK)
	public ToDoItem createItem_v1( @RequestBody ToDoTemplate initialContent) throws ValidationException {
		ToDoItem item = new ToDoItem(initialContent);
		db.save(item);
		return item;
	}

	@RequestMapping(path="/agtest/1.0/todo/{tid}", method=RequestMethod.GET)
	@ResponseStatus(code=HttpStatus.OK)
	public ToDoItem getItem_v1(@PathVariable("tid") Long itemId) {
		ToDoItem answer = db.findOne(itemId);
		if (answer==null) throw new MissingResourceException("No such item.", "ToDoItem", Long.toString(itemId));
		return answer;
	}

	
	@RequestMapping(path="/agtest/1.0/todo/{tid}", method=RequestMethod.PATCH)
	@ResponseStatus(code=HttpStatus.OK)
	public ToDoItem updateItem_v1(@PathVariable("tid") Long itemId, 
			@RequestBody ToDoItemUpdateRequest changes
	) {
		ToDoItem answer = db.findOne(itemId);
		if (answer==null) throw new MissingResourceException("No such item.", "ToDoItem", Long.toString(itemId));
		answer.update(changes);
		db.save(answer);
		return answer;
	}
	
	@ExceptionHandler({MissingResourceException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public APIErrorMsg handleMissing(Exception cause) {
		return new APIErrorMsg("NotFoundError", "id", cause);
	}
	
	public ToDoRepository getRepo() {
		return db;
	}

	@Autowired
	public void setRepo(ToDoRepository db) {
		this.db = db;
		//sneaky inject test data manually
		if (db.findOne(1L)==null) {
			ToDoItem item = new ToDoItem();
			item.setId(1L);
			item.setText("Test data!");
			db.save(item);
		}
	}
	
}
