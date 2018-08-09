package name.mcrae.andrew.research.agtest.todo.tests;

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
		//tds = new ToDoService();		
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
	}

	
	
	
	
	public ToDoService getTds() {
		return tds;
	}

	public void setTds(ToDoService tds) {
		this.tds = tds;
	}

}
