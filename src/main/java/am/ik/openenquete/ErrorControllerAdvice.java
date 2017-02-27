package am.ik.openenquete;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(annotations = Controller.class)
public class ErrorControllerAdvice {
	private final Environment env;

	public ErrorControllerAdvice(Environment env) {
		this.env = env;
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String noSuchEelemtException(NoSuchElementException e, Model model) {
		//addErrors(e, HttpStatus.NOT_FOUND, model);
		return "error/404";
	}

	void addErrors(Exception e, HttpStatus status, Model model) {
if (env.acceptsProfiles("default")) {
	StringWriter stackTrace = new StringWriter();
	e.printStackTrace(new PrintWriter(stackTrace));
	stackTrace.flush();
	model.addAttribute("status", status.value());
	model.addAttribute("error", status.getReasonPhrase());
	model.addAttribute("exception", e.getClass());
	model.addAttribute("message", e.getMessage());
	model.addAttribute("trace", stackTrace.toString());
}
	}
}
