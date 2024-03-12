package product.management.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.user.dto.UserTB;
import product.management.user.dto.UserTBVO;
import product.management.user.mapper.UserTBMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserTBMapper userTBMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserTB userTB = userTBMapper.selectByUserId(userId);
		if(userTB == null) {new UsernameNotFoundException("아이디를 가진 유저 없음");}
		
		return new UserTBVO(userTB);
	}

}
