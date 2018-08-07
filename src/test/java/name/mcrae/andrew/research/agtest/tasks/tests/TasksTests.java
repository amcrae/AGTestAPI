package name.mcrae.andrew.research.agtest.tasks.tests;

import org.junit.Test;

import junit.framework.TestCase;
import name.mcrae.andrew.research.agtest.tasks.BracketValidationResponse;
import name.mcrae.andrew.research.agtest.tasks.BracketsService;
import name.mcrae.andrew.research.agtest.tasks.ValidationException;

public class TasksTests extends TestCase {

	private BracketsService bapi;
	private String ch55;
	
	@Override
	public void setUp() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<55; i++) sb.append('S');
		ch55 = sb.toString();
		
		bapi = new BracketsService();
	}

	@Test
	public void testBracketsTooLongError() {
		try {
			bapi.validateBrackets_v1(ch55);
			fail("No exception thrown on bad input.");
		} catch (Exception vex) {
			assertTrue("Wrong type of exception", vex instanceof ValidationException);
		}
	}

	@Test
	public void testBracketsTrivial() {
		BracketValidationResponse answer = bapi.validateBrackets_v1("");
		assertTrue("Trivial case was unbalanced.", answer.getIsBalanced());
	}
	
	@Test
	public void testBracketsSimpleSq() {
		BracketValidationResponse answer = bapi.validateBrackets_v1("[]");
		assertTrue("Simple square case was unbalanced.", answer.getIsBalanced());
	}

	@Test
	public void testBracketsSimpleRn() {
		BracketValidationResponse answer = bapi.validateBrackets_v1("[]");
		assertTrue("Simple round case was unbalanced.", answer.getIsBalanced());
	}

	@Test
	public void testBracketsSimpleBr() {
		BracketValidationResponse answer = bapi.validateBrackets_v1("{}");
		assertTrue("Simple brace case was unbalanced.", answer.getIsBalanced());
	}
	

	@Test
	public void testBracketsEg1() {
		String input =  "]{}[";
		BracketValidationResponse answer = bapi.validateBrackets_v1(input);
		assertFalse("Simple mix 1 should be unbalanced.", answer.getIsBalanced());
	}

	@Test
	public void testBracketsEg2() {
		String input =  "{[}]";
		BracketValidationResponse answer = bapi.validateBrackets_v1(input);
		assertFalse("Should be unbalanced.", answer.getIsBalanced());
	}
	
	@Test
	public void testBracketsEg3() {
		String input =  "[{)]";
		BracketValidationResponse answer = bapi.validateBrackets_v1(input);
		assertFalse("Should be unbalanced.", answer.getIsBalanced());
	}
	
	
	@Test
	public void testBracketsEg4() {
		String input =  "({[]})";
		BracketValidationResponse answer = bapi.validateBrackets_v1(input);
		assertTrue("Should be balanced.", answer.getIsBalanced());
	}
	
	@Test
	public void testBracketsEg4Rep() {
		String input =  "({[]})({[]}) ({[]})";
		BracketValidationResponse answer = bapi.validateBrackets_v1(input);
		assertTrue("Should be balanced.", answer.getIsBalanced());
	}
	
	@Test
	public void testBracketsMixed1() {
		String input =  "foo(x[]){ f( ){ } g() }";
		BracketValidationResponse answer = bapi.validateBrackets_v1(input);
		assertTrue("Should be balanced.", answer.getIsBalanced());
	}
	
	
	
}
