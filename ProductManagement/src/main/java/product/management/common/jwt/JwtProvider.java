package product.management.common.jwt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.common.AesUtil;
import product.management.common.jwt.dto.TokenVO;
import product.management.common.security.CustomUserDetailService;
import product.management.user.dto.UserTB;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
	
	@Value("${jwt.secret.key}")
    private String secretKey;
	
	private final AesUtil aesUtil;
	private final CustomUserDetailService customUserDetailService;
    
 // 토큰 생성 Method
    public TokenVO createToken(UserTB userTB) {

        // Access Token
        String accessToken = Jwts.builder()
                .subject(aesUtil.aes256Encode(userTB.getUser_id()))
                .issuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(setSecretKey())
                .compact();

        // Refresh Token
        String refreshToken = Jwts.builder()
    		    .subject(aesUtil.aes256Encode(userTB.getUser_id()))
                .issuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(LocalDateTime.now().plusHours(24).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(setSecretKey())
                .compact();


        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccess_token(accessToken);
        tokenVO.setRefresh_token(refreshToken);

        return tokenVO;
    	
    }
    
    // 키 세팅
    private SecretKey setSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    
 // AccessToken에 담겨져 있는 유저 ID 흭득
    public String getUserId(String token){
        return aesUtil.aes256Decode(
    			Jwts.parser()
        			.verifyWith(setSecretKey())
        			.build()
        			.parseSignedClaims(token)
        			.getPayload()
        			.getSubject()
		);
    }
    
    // Token을 이용한 authentication 인증과정
    public Authentication authentication(String token) {
		UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }
	
    // Authorization Header를 통해 Token 인증
    public String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
    
    // Token 유효성 및 만료일자 검증
    public boolean validateToken(String token) {
    	
		if(!token.substring(0, "Bearer ".length()).equalsIgnoreCase("Bearer ")){
            return false;
        }else{
            token = token.split(" ")[1].trim();
        }
    		
		Jws<Claims> claims = Jwts.parser().verifyWith(setSecretKey()).build().parseSignedClaims(token);
        return !claims.getPayload().getExpiration().before(new Date());
    }
    
    // 유효한 토큰을 가지고 있을 때 아이디 값 얻기
    public String getIdByToken(String token) {
		String id = null;
		if(token != null && validateToken(token)) {
			token = token.split(" ")[1].trim();
			Authentication authInfo = authentication(token);
			id = authInfo.getName();
		}else {
			id = null;
		}
		
		return id;
    }

}
