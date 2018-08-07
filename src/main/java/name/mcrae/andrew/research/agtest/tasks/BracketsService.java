package name.mcrae.andrew.research.agtest.tasks;

import java.util.Stack;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class BracketsService {

	public BracketsService() {
		super();
	}
	
	/**
	 * Check if brackets are balanced.
	 * Implementation of: 
	 * {@linkplain https://join.autogeneral.com.au/swagger-ui/?url=/swagger.json#/tasks/get_tasks_validateBrackets }
	 * */
	@RequestMapping(path="v1/tasks/validateBrackets")
	public BracketValidationResponse validateBrackets_v1(@RequestParam(name="input") String input ) throws ValidationException {
		if (input==null || input.length()>50) {
			throw new ValidationException("Must be between 0 and 50 chars long", "text", new IllegalArgumentException("input"));
		}
		BracketValidationResponse answer = new BracketValidationResponse();
		answer.setInput(input);
		answer.setIsBalanced(true);
		
		Stack<NestingElement> nestings = new Stack<NestingElement>();
		
		int i = 0;
		int nextNestChangePos = -1;
		NestingElement nextNestPair = null;
		// Consume all input.
		while(i<input.length()) {
			nextNestChangePos = -1;
			for (NestingElement el : knownPairs) {
				int po = input.indexOf(el.openSymbol, i);
				int pc = input.indexOf(el.closeSymbol, i);
				if (po>-1 && (po<nextNestChangePos || nextNestChangePos==-1)) {nextNestPair = el; nextNestChangePos=po; }
				if (pc>-1 && (pc<nextNestChangePos || nextNestChangePos==-1)) {nextNestPair = el; nextNestChangePos=pc; }
			}
			if (i == nextNestChangePos) {
				if (input.substring(i, i+nextNestPair.openSymbol.length()).equals(nextNestPair.openSymbol)) {
					nestings.push(nextNestPair);
					i += nextNestPair.openSymbol.length();
				} else if (input.substring(i, i+nextNestPair.closeSymbol.length()).equals(nextNestPair.closeSymbol)) {
					//Have found a close symbol, so ensure there is something to close,
					// and that it is the right one.
					if (!nestings.isEmpty() && nestings.peek().equals(nextNestPair)) {
						nestings.pop();
						i += nextNestPair.closeSymbol.length();
					} else {
						//Expected close symbol was not the next closed.
						answer.setIsBalanced(false);
						//early bailout as we know the answer already.
						i = input.length();
					}
				} else {
					throw new AssertionError("A symbol which matched did not match?");
				}
			} else {
				//Not required to do anything with other intermediate characters,
				//so always move forward, skipping to next relevant char when possible.
				i=Math.max(i+1,nextNestChangePos);
			}
		}
		if (!nestings.isEmpty()) answer.setIsBalanced(false);
		return answer;
	}

	final NestingElement[] knownPairs = {
			new NestingElement("(",")"),
			new NestingElement("[","]"),
			new NestingElement("{","}")
	};
	final int shortestSymbolLen = 1;
	final int longestSymbolLen = 1;
	
	private static class NestingElement {
		public NestingElement(String open, String close) {
			super();
			this.openSymbol = open;
			this.closeSymbol = close;
		}
		String openSymbol;
		String closeSymbol;
		@Override
		public boolean equals(Object obj) {
			try {
				NestingElement other = (NestingElement)obj;
				return this.openSymbol!=null && this.closeSymbol!=null 
						&& this.openSymbol.equals(other.openSymbol)
						&& this.closeSymbol.equals(other.closeSymbol);
			} catch (ClassCastException cce) {
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			return openSymbol.hashCode() ^ (closeSymbol!=null ? closeSymbol.hashCode() : 0);
		}
		
		@Override
		public String toString() {
			return openSymbol+"/"+closeSymbol;
		}
	}
	
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	APIErrorMsg handle(ValidationException vex) {
		return new APIErrorMsg(vex.getMessage(),  vex.getVarName(),  vex.getCause());
	}
	
	
	
}
