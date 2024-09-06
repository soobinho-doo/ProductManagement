package product.management.branchoffice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import product.management.branchoffice.dto.BranchOfficeTB;


@Mapper
public interface BranchOfficeTBMapper {
	
	@Select("SELECT * FROM BRANCH_OFFICE_TB "
			+ "WHERE BRANCH_OFFICE_SQ = #{branch_office_sq}")
	BranchOfficeTB selectBranchOfficeTBByBranchOfficeSq(Long branch_office_sq);
	
	// Paging
	@Select("SELECT * FROM BRANCH_OFFICE_TB "
			+ "WHERE 1=1 "
			+ "AND USER_ID = #{user_id} "
			+ "AND BRANCH_OFFICE_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "ORDER BY BRANCH_OFFICE_SQ DESC LIMIT #{cp}, #{ps}")
	List<BranchOfficeTB> selectBranchOfficeTBListByBranchOfficeTBAndUserId(@Param("user_id") String user_id, @Param("keyword") String keyword, @Param("cp") int cp, @Param("ps") int ps);
	
	@Select("SELECT count(*) FROM BRANCH_OFFICE_TB "
			+ "WHERE 1=1 "
			+ "AND USER_ID = #{user_id} "
			+ "AND BRANCH_OFFICE_NM LIKE CONCAT('%',#{keyword},'%')")
	int selectBranchOfficeTBRowByKeywordAndUserId(@Param("user_id") String user_id, @Param("keyword") String keyword);
	
	
	@Insert("INSERT INTO BRANCH_OFFICE_TB(BRANCH_OFFICE_SQ, USER_ID, BRANCH_OFFICE_NM, BRANCH_OFFICE_AREA, REG_DT, MOD_DT) "
			+ "VALUES(null, #{user_id}, #{branch_office_nm}, #{branch_office_area}, NOW(), NOW())")
	int insertBranchOfficeTB(BranchOfficeTB branchOfficeTB);
	
	
	@Update("UPDATE BRANCH_OFFICE_TB SET BRANCH_OFFICE_NM = #{branch_office_nm}, BRANCH_OFFICE_AREA = #{branch_office_area}, MOD_DT = NOW() "
			+ "WHERE BRANCH_OFFICE_SQ = #{branch_office_sq}")
	int updateBranchOfficeTB(BranchOfficeTB branchOfficeTB);
	
	
	@Delete("DELETE FROM BRANCH_OFFICE_TB "
			+ "WHERE BRANCH_OFFICE_SQ = #{branch_office_sq}")
	int deleteBranchOfficeTB(Long branch_office_sq);
}
