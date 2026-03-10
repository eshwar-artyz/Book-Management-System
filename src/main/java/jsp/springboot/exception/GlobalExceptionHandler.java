package jsp.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jsp.springboot.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exp) {
		ResponseStructure<String> resp = new ResponseStructure<String>();
		resp.setStatusCode(HttpStatus.NOT_FOUND.value());
		resp.setMessage(exp.getMessage());
		resp.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(resp, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<ResponseStructure<String>> handleNoRecordAvailableException(NoRecordAvailableException exp) {
		ResponseStructure<String> resp = new ResponseStructure<String>();
		resp.setStatusCode(HttpStatus.NO_CONTENT.value());
		resp.setMessage(exp.getMessage());
		resp.setData("Failed");
		return new ResponseEntity<ResponseStructure<String>>(resp, HttpStatus.NO_CONTENT);
	}
	
}
