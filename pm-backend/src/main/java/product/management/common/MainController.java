package product.management.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final IpConfig ipConfig;
	
	@GetMapping("/")
	public String mainPage(HttpServletRequest request, HttpServletResponse response) {
		String ip = ipConfig.getIpAddress(request);
		
        
        boolean badIp = ip.matches(".*222.239.104.*");
        if(badIp){
			return null;
		}
		
		return "index.html";
	}
	
}
