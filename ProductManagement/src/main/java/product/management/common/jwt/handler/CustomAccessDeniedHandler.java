package product.management.common.jwt.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// 권한 문제가 발생했을 때 이 부분을 호출한다.
        response.setStatus(403);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("권한이 없는 사용자입니다");

	}

}
