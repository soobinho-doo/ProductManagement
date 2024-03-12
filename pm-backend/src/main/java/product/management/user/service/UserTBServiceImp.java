package product.management.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.common.jwt.JwtProvider;
import product.management.common.jwt.dto.TokenVO;
import product.management.user.dto.UserTB;
import product.management.user.mapper.UserTBMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTBServiceImp implements UserTBService {

	private final UserTBMapper userTBMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtProvider jwtProvider;
	
	@Override
	public boolean hasById(String user_id) {
		boolean result = userTBMapper.existsById(user_id);
		
		return result;
	}

	@Override
	public boolean hasByEmail(String user_email) {
		boolean result = userTBMapper.existsByEmail(user_email);
		
		return result;
	}

	@Override
	public boolean hasByPhone(String user_phone) {
		boolean result = userTBMapper.existsByPhone(user_phone);
		
		return result;
	}

	@Override
	public Map<String, String> login(UserTB userTB, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		String accessToken = null;
		String refreshToken = null;
		
		UserTB user = userTBMapper.selectByUserId(userTB.getUser_id());
		
		if(user == null){
            throw new IllegalArgumentException("가입되지 않은 아이디 또는 잘못된 아이디 입니다.");
        }
        if(!bCryptPasswordEncoder.matches(userTB.getUser_pass(), user.getUser_pass())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다");
        }else {
    		TokenVO tokenVO = jwtProvider.createToken(user);
    		accessToken = tokenVO.getAccess_token();
    		refreshToken = tokenVO.getRefresh_token();
    		
    		result.put("Authorization", accessToken);
    		
    		// 리프레쉬 쿠키에 저장
	        Cookie cookie = new Cookie("rt", refreshToken);
	        cookie.setHttpOnly(true);
	        cookie.setMaxAge(24 * 60 * 60); // 1일 설정
	        cookie.setSecure(false); // 일단 False 해 놓음..
	        cookie.setPath("/");
	        //cookie.setDomain("everyrat.com");
	
	        response.addCookie(cookie);
        }
		
		return result;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("rt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        //cookie.setDomain("everyrat.com");
        response.addCookie(cookie);
	}

	@Override
	public Map<String, String> againToken(HttpServletRequest request, HttpServletResponse response, String rt) {
		Map<String, String> result = new HashMap<String, String>();

		String refreshToken = rt;
		String accessToken = null;
		
		if(rt != null && jwtProvider.validateToken(refreshToken)) {
			String user_id = jwtProvider.getIdByToken(refreshToken);
			UserTB user = userTBMapper.selectByUserId(user_id);
			TokenVO tokenVO = jwtProvider.createToken(user);
			accessToken = tokenVO.getAccess_token();
			result.put("Authorization", accessToken);
		}else {
			Cookie cookie = new Cookie("rt", null);
	        cookie.setMaxAge(0);
	        cookie.setPath("/");
	        //cookie.setDomain("everyrat.com");
	        response.addCookie(cookie);
			
			accessToken = null;
			result.put("Authorization", accessToken);
		}
		
		return result;
	}

	@Override
	public UserTB findUserTBByToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
	
		String user_id = jwtProvider.getIdByToken(token);
		UserTB userTB = userTBMapper.selectByUserId(user_id);
		
		return userTB;
	}

	@Override
	@Transactional
	public int inputUserTB(UserTB userTB) {
		userTB.setUser_pass(bCryptPasswordEncoder.encode(userTB.getUser_pass())); // 패스워드 암호화
		int result = userTBMapper.insertUserTB(userTB);
		
		return result;
	}

	@Override
	@Transactional
	public int modifyUserPass(String user_pass, String user_email) {
		UserTB userTB = userTBMapper.selectByUserEmail(user_email);
		String user_id = userTB.getUser_id();
		
		user_pass = bCryptPasswordEncoder.encode(user_pass);
		int result = userTBMapper.updateUserPass(user_pass, user_id);
		
		return result;
	}
	
	@Override
	@Transactional
	public int deleteUserTB(String user_pass, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return 0;
	}

}
