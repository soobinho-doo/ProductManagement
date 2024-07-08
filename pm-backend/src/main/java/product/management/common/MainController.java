package product.management.common;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final UtilConfig utilConfig;
	private final IpConfig ipConfig;
	
	@GetMapping("/")
	public String mainPage(HttpServletRequest request, HttpServletResponse response) {
		String ip = ipConfig.getIpAddress(request);
		int remainingTime = utilConfig.remainingTime();
		
		Cookie guestCookie = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("guest_id")) {
                		guestCookie = cookie;
                }
            }
        }
        
        if(guestCookie == null) {
	        	UUID random = UUID.randomUUID();
	        	String randomGuestId = random.toString().substring(0,8);
	        	
	        	Cookie newGuestCookie = new Cookie("guest_id", randomGuestId);
	        	newGuestCookie.setPath("/");
	        	newGuestCookie.setMaxAge(remainingTime);
            response.addCookie(newGuestCookie);
        }
        
        boolean badIp = ip.matches(".*222.239.104.*");
        if(badIp){
			return null;
		}
		
		return "index.html";
	}
	
}
