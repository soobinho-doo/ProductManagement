package product.management.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainErrorController implements ErrorController {

	@GetMapping({"/error"})
	public String errorPage(HttpServletRequest request, HttpServletResponse response) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(statusCode == 404) {
			return null;

		}else {
			return "index.html";
		}
	
	}
	
}
