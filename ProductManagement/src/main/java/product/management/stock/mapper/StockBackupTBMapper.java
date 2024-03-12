package product.management.stock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import product.management.stock.dto.StockBackupTB;
import product.management.stock.dto.StockTBJoinVO;

@Mapper
public interface StockBackupTBMapper {
	// Select
	// Paging 
	@Select("SELECT a.BRANCH_OFFICE_NM, b.PRODUCT_NM, b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, c.STOCK_ST, c.STOCK_NO, c.STOCK_DT FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_BACKUP_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND c.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST LIKE CONCAT('%',#{stock_st},'%') "
			+ "AND c.STOCK_DT BETWEEN #{start_dt} AND #{end_dt}"
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%')"
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%')"
			+ "ORDER BY c.STOCK_DT DESC, c.REG_DT DESC LIMIT #{cp}, #{ps}")
	List<StockTBJoinVO> selectStockTBByPaging(@Param("user_id") String user_id, @Param("stock_st") String stock_st, @Param("start_dt") String start_dt, @Param("end_dt") String end_dt, @Param("keyword") String keyword, @Param("branch_office_nm") String branch_office_nm, @Param("cp") int cp, @Param("ps") int ps);
	
	@Select("SELECT count(*) FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_BACKUP_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND c.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST LIKE CONCAT('%',#{stock_st},'%') "
			+ "AND c.STOCK_DT BETWEEN #{start_dt} AND #{end_dt}"
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%')"
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%')")
	int selectRowCount(@Param("user_id") String user_id, @Param("stock_st") String stock_st, @Param("start_dt") String start_dt, @Param("end_dt") String end_dt, @Param("keyword") String keyword, @Param("branch_office_nm") String branch_office_nm);
	
	// Insert 
	@Insert("INSERT INTO STOCK_BACKUP_TB(STOCK_SQ, USER_ID, STOCK_ST, PRODUCT_SQ, STOCK_NO, STOCK_DT, REG_DT, MOD_DT) "
			+ "VALUES(NULL, #{user_id}, #{stock_st}, #{product_sq}, #{stock_no}, #{stock_dt}, NOW(), NOW())")
	int insertStockTB(StockBackupTB stockBackupTB);
}
