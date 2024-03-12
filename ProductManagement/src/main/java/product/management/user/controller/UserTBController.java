package product.management.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import product.management.common.MailConfig;
import product.management.user.dto.UserTB;
import product.management.user.service.UserTBService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTBController {

	private final UserTBService userTBService;
	private final MailConfig mailConfig;
	
	// 로그인
	@PostMapping("/user/login")
	public ResponseEntity<String> login(@RequestBody UserTB userTB, HttpServletRequest request, HttpServletResponse response){
		String accessToken = userTBService.login(userTB, request, response);
		
		return ResponseEntity.ok(accessToken);
	}
	
	// 로그아웃
	@GetMapping("/user/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		userTBService.logout(request, response);
	}
	
	// 회원가입
	@PostMapping("/user")
	public ResponseEntity<Integer> inputUserTB(@RequestBody UserTB userTB) {
		int result = userTBService.inputUserTB(userTB);
		
		return ResponseEntity.ok(result);
	}
	
	// 새로고침 시 토큰 체크 후 발급
	@PostMapping("/user/again-token")
	public ResponseEntity<String> againToken(@CookieValue(name="rt", required = false) String rt, HttpServletRequest request, HttpServletResponse response) {
		String againToken = userTBService.againToken(request, response, rt);
		
		return ResponseEntity.ok(againToken);
	}
	
	// 토큰으로 유저 정보 가져오기
	@GetMapping("/user")
	public ResponseEntity<UserTB> detailsByToken(HttpServletRequest request){
		UserTB userTB = userTBService.findUserTBByToken(request);
		
		return ResponseEntity.ok(userTB);
	}
	
	// 중복 체크 모음
	// 아이디 중복 체크
	@GetMapping("/user/id/{user_id}")
	public ResponseEntity<Boolean> hasById(@PathVariable(value="user_id") String user_id){
		boolean result = userTBService.hasById(user_id);
		
		return ResponseEntity.ok(result);
	}
	@GetMapping("/user/email/{user_email}")
	public ResponseEntity<Boolean> hasByEmail(@PathVariable(value="user_email") String user_email){
		boolean result = userTBService.hasByEmail(user_email);
		
		return ResponseEntity.ok(result);
	}
	@GetMapping("/user/phone/{user_phone}")
	public ResponseEntity<Boolean> hasByPhone(@PathVariable(value="user_phone") String user_phone){
		boolean result = userTBService.hasByPhone(user_phone);
		
		return ResponseEntity.ok(result);
	}
	
	// 중복 체크 모음 끝
	
	// 아이디 찾기
	@PostMapping("/user/id")
	public ResponseEntity<Boolean> hasFindByEmail(@RequestBody UserTB userTB){
		String user_eamil = userTB.getUser_email();
		boolean result = mailConfig.idEmail(user_eamil);
		
		return ResponseEntity.ok(result);
	}
	
	// 패스워드 변경 시 확인 인증코드
	@PostMapping("/user/pass")
	public ResponseEntity<Integer> passFromEmail(@RequestBody UserTB userTB){
		String user_eamil = userTB.getUser_email();
		int result = mailConfig.passEmail(user_eamil);
		
		return ResponseEntity.ok(result);
	}
	
	// 패스워드 변경
	@PostMapping("/user/change/pass")
	public ResponseEntity<Integer> modifyPassword(@RequestBody UserTB userTB){
		String user_email = userTB.getUser_email();
		String user_pass = userTB.getUser_pass();
		int result = userTBService.modifyUserPass(user_pass, user_email);
		
		return ResponseEntity.ok(result);
	}
	
}
