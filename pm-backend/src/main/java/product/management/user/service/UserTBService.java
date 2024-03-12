package product.management.user.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.management.user.dto.UserTB;

public interface UserTBService {

	// SELECT 
		boolean hasById(String user_id);
		boolean hasByEmail(String user_email);
		boolean hasByPhone(String user_phone);
		
		Map<String, String> login(UserTB userTB, HttpServletRequest request, HttpServletResponse response);
		void logout(HttpServletRequest request, HttpServletResponse response);
		Map<String, String> againToken(HttpServletRequest request, HttpServletResponse response, String rt);
		UserTB findUserTBByToken(HttpServletRequest request);
		
		// INSERT
		int inputUserTB(UserTB userTB);
		
		// UPDATE
		int modifyUserPass(String user_pass, String user_id);
	     
	    // DELETE
		int deleteUserTB(String user_pass, HttpServletRequest request, HttpServletResponse response);
}
