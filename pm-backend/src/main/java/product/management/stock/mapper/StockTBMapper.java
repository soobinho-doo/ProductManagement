package product.management.stock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import product.management.stock.dto.StockTB;
import product.management.stock.dto.StockTBJoinVO;
import product.management.stock.dto.StockTBUpdateVO;

@Mapper
public interface StockTBMapper {

	// Select
	// Paging 
	@Select("SELECT a.BRANCH_OFFICE_NM, b.PRODUCT_SQ, b.PRODUCT_NM, b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, c.STOCK_SQ, c.STOCK_ST, c.STOCK_NO, c.STOCK_DT FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND c.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST LIKE CONCAT('%',#{stock_st},'%') "
			+ "AND c.STOCK_DT BETWEEN #{start_dt} AND #{end_dt} "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%') "
			+ "ORDER BY c.STOCK_DT DESC, c.REG_DT DESC LIMIT #{cp}, #{ps}")
	List<StockTBJoinVO> selectStockTBByPaging(@Param("user_id") String user_id, @Param("stock_st") String stock_st, @Param("start_dt") String start_dt, @Param("end_dt") String end_dt, @Param("keyword") String keyword, @Param("branch_office_nm") String branch_office_nm, @Param("cp") int cp, @Param("ps") int ps);
	
	@Select("SELECT a.BRANCH_OFFICE_NM, b.PRODUCT_SQ, b.PRODUCT_NM, b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, c.STOCK_SQ, c.STOCK_ST, c.STOCK_NO, c.STOCK_DT FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND c.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST LIKE CONCAT('%',#{stock_st},'%') "
			+ "AND c.STOCK_DT BETWEEN #{start_dt} AND #{end_dt} "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%')")
	List<StockTBJoinVO> selectStockTBByAllPaging(@Param("user_id") String user_id, @Param("stock_st") String stock_st, @Param("start_dt") String start_dt, @Param("end_dt") String end_dt, @Param("keyword") String keyword, @Param("branch_office_nm") String branch_office_nm);
	
	@Select("SELECT count(*) FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND c.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST LIKE CONCAT('%',#{stock_st},'%') "
			+ "AND c.STOCK_DT BETWEEN #{start_dt} AND #{end_dt} "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%')")
	int selectRowCount(@Param("user_id") String user_id, @Param("stock_st") String stock_st, @Param("start_dt") String start_dt, @Param("end_dt") String end_dt, @Param("keyword") String keyword, @Param("branch_office_nm") String branch_office_nm);
	
	@Select("SELECT * FROM STOCK_TB WHERE 1=1 AND STOCK_SQ = #{stock_sq} AND USER_ID = #{user_id}")
	StockTB selectStockTBByStockSq(@Param("stock_sq") Long stock_sq, @Param("user_id") String user_id);
	
	// Excel
	@Select("SELECT a.BRANCH_OFFICE_NM, b.PRODUCT_SQ, b.PRODUCT_NM, b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, c.STOCK_SQ, c.STOCK_ST, c.STOCK_NO, c.STOCK_DT FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND c.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST LIKE CONCAT('%',#{stock_st},'%') "
			+ "AND c.STOCK_DT BETWEEN #{start_dt} AND #{end_dt} "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%') "
			+ "ORDER BY c.STOCK_DT DESC, c.REG_DT DESC")
	List<StockTBJoinVO> selectStockTBByExcel(@Param("user_id") String user_id, @Param("stock_st") String stock_st, @Param("start_dt") String start_dt, @Param("end_dt") String end_dt, @Param("keyword") String keyword, @Param("branch_office_nm") String branch_office_nm);
	
	// Insert 
	@Insert("INSERT INTO STOCK_TB(STOCK_SQ, USER_ID, STOCK_ST, PRODUCT_SQ, STOCK_NO, STOCK_DT, REG_DT, MOD_DT) "
			+ "VALUES(NULL, #{user_id}, #{stock_st}, #{product_sq}, #{stock_no}, #{stock_dt}, NOW(), NOW())")
	int insertStockTB(StockTB stockTB);
	
	// Update 
	@Update("UPDATE STOCK_TB SET STOCK_ST = #{stock_st}, PRODUCT_SQ = #{product_sq}, STOCK_NO = #{stock_no}, STOCK_DT = #{stock_dt}, MOD_DT = NOW() WHERE STOCK_SQ = #{stock_sq}")
	int updateStockTB(StockTBUpdateVO stockTBUpdateVO);
	
	// Delete
	@Delete("DELETE FROM STOCK_TB WHERE STOCK_SQ = #{stock_sq}")
	int deleteStockTB(@Param("stock_sq") Long stock_sq);
}
