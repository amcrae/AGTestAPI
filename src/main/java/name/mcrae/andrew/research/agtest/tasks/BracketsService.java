package name.mcrae.andrew.research.agtest.tasks;

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

	/**
	 * 
	 * {@linkplain https://join.autogeneral.com.au/swagger-ui/?url=/swagger.json#/tasks/get_tasks_validateBrackets }
	 * */
	
	@RequestMapping(path="v1/tasks/validateBrackets")
	public BracketValidationResponse validateBrackets_v1(@RequestParam(name="input") String input ) throws ValidationException {
		if (input==null || input.length()>50) {
			throw new ValidationException("Must be between 1 and 50 chars long", "text", new IllegalArgumentException("input"));
		}
		BracketValidationResponse answer = new BracketValidationResponse();
		return answer;
	}
	
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	APIErrorMsg handle(ValidationException vex) {
		return new APIErrorMsg(vex.getMessage(),  vex.getVarName(),  vex.getCause());
	}
	
}
