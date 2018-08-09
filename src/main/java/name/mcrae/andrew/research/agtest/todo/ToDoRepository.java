package name.mcrae.andrew.research.agtest.todo;

import org.springframework.data.domain.*;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDoItem, Long> {

	Page<ToDoItem> findAll(Pageable page);
	
	ToDoItem findById(Long id);
	
}
