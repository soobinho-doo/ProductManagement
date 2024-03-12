package product.management.user.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import product.management.user.dto.UserTB;

@Mapper
public interface UserTBMapper {

	// SELECT 
	// 유저 아이디로 정보 Get
	@Select("SELECT * FROM USER_TB WHERE USER_ID = #{user_id}")
	UserTB selectByUserId(String user_id);
	
	// 유저 이메일로 정보 Get
	@Select("SELECT * FROM USER_TB WHERE USER_EMAIL = #{user_email}")
	UserTB selectByUserEmail(String user_email);
	
	// 중복체크 조회
	// 1. 아이디 중복 체크
	@Select("SELECT EXISTS (SELECT 1 FROM USER_TB WHERE USER_ID = #{user_id})")
	boolean existsById(String user_id);
	
    // 2. 이메일 중복 체크
    @Select("SELECT EXISTS (SELECT 1 FROM USER_TB WHERE USER_EMAIL = #{user_email})")
    boolean existsByEmail(String user_email);

    // 3. 핸드폰 중복 체크
    @Select("SELECT EXISTS (SELECT 1 FROM USER_TB WHERE USER_PHONE = #{user_phone})")
    boolean existsByPhone(String user_phone);
	
    
    // INSERT
    // 회원가입
    @Insert("INSERT INTO USER_TB(USER_ID, USER_PASS, USER_NM, USER_PHONE, USER_EMAIL, USER_AUTH, REG_DT, MOD_DT) " +
            "VALUES(#{user_id}, #{user_pass}, #{user_nm}, #{user_phone}, #{user_email}, 'ROLE_USER', now(), now())")
    int insertUserTB(UserTB userTB);
    
    // UPDATE
    // 비밀번호 변경
    @Update("UPDATE USER_TB SET USER_PASS = #{user_pass} WHERE USER_ID = #{user_id}")
    int updateUserPass(@Param("user_pass") String user_pass, @Param("user_id") String user_id);
    
    // DELETE
    // 회원 삭제
    @Delete("DELETE FROM USER_TB WHERE USER_ID = #{user_id}")
    int deleteUserTB(String user_id);
	
}
