package name.mcrae.andrew.research.agtest.todo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import name.mcrae.andrew.research.agtest.AgTestApiApplication;
import name.mcrae.andrew.research.agtest.todo.ToDoItem;
import name.mcrae.andrew.research.agtest.todo.ToDoItemUpdateRequest;
import name.mcrae.andrew.research.agtest.todo.ToDoService;
import name.mcrae.andrew.research.agtest.todo.ToDoTemplate;
import name.mcrae.andrew.research.agtest.todo.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackageClasses={AgTestApiApplication.class})
@EnableAutoConfiguration
public class ToDoTests {

	@Autowired
	ToDoService tds; 	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateItem_v1() throws ValidationException{
		ToDoTemplate tmp = new ToDoTemplate();
		tmp.setText("Foo");
		ToDoItem item = tds.createItem_v1(tmp);
		assertNotNull("id was not allocated", item.getId());
		assertTrue("id was not allocated", item.getId()!=0L);
		assertEquals("text was not preserved", "Foo", item.getText());
	}

	
	@Test
	public void testGetItem_v1() throws ValidationException{
		ToDoTemplate tmp = new ToDoTemplate();
		tmp.setText("Foo2");
		ToDoItem item = tds.createItem_v1(tmp);
		assertNotNull("id was not allocated", item.getId());
		assertTrue("id was not allocated", item.getId()!=0L);
		
		ToDoItem item2 = tds.getItem_v1(item.getId());
		
		assertEquals("text was not preserved", item.getText(), item2.getText());
		assertEquals("creation timestamp was not preserved", item.getCreatedAt(), item2.getCreatedAt());
	}
	

	@Test
	public void testEditItemAll_v1() throws ValidationException{
		ToDoTemplate tmp = new ToDoTemplate();
		tmp.setText("Foo3 note.");
		ToDoItem item3 = tds.createItem_v1(tmp);
		
		ToDoItemUpdateRequest changes = new ToDoItemUpdateRequest();
		changes.setText("Updated note!");
		changes.setIsCompleted(true);
		ToDoItem throwaway = tds.updateItem_v1(item3.getId(), changes);
		
		ToDoItem item3b = tds.getItem_v1(item3.getId());
		
		assertEquals("text was not updated", changes.getText(), item3b.getText());
		assertEquals("completion flag was not updated", true, item3b.getIsCompleted());
		assertEquals("creation timestamp was not preserved", item3.getCreatedAt(), item3b.getCreatedAt());
	}

	@Test
	public void testEditItemSingle_v1() throws ValidationException{
		ToDoTemplate tmp = new ToDoTemplate();
		tmp.setText("Foo4 note.");
		ToDoItem item3 = tds.createItem_v1(tmp);
		
		ToDoItemUpdateRequest changes = new ToDoItemUpdateRequest();
		changes.setIsCompleted(true);
		ToDoItem throwaway = tds.updateItem_v1(item3.getId(), changes);
		
		ToDoItem item3b = tds.getItem_v1(item3.getId());
		
		assertEquals("text should be same", item3.getText(), item3b.getText());
		assertEquals("completion flag was not updated", true, item3b.getIsCompleted());
		assertEquals("creation timestamp was not preserved", item3.getCreatedAt(), item3b.getCreatedAt());
	}
	
	
	public ToDoService getTds() {
		return tds;
	}

	public void setTds(ToDoService tds) {
		this.tds = tds;
	}

}
