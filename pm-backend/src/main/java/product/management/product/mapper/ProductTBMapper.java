package product.management.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import product.management.branchoffice.dto.BranchOfficeTB;
import product.management.product.dto.ProductTB;
import product.management.product.dto.ProductTBVO;

@Mapper
public interface ProductTBMapper {

	// Select
	// Paging
	@Select("SELECT b.PRODUCT_SQ, b.PRODUCT_NM, b.BRANCH_OFFICE_SQ, a.BRANCH_OFFICE_NM, "
			+ "b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, b.PRODUCT_STOCK, b.PRODUCT_SELL, b.PRODUCT_RECALL "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ  "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%') "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "ORDER BY a.BRANCH_OFFICE_NM ASC, b.PRODUCT_NM ASC, b.PRODUCT_PRICE ASC LIMIT #{cp}, #{ps}")
	List<ProductTB> selectProductTBListByProductTBVOAndUserId(@Param("user_id") String user_id, @Param("branch_office_nm") String branch_office_nm ,@Param("keyword") String keyword, @Param("cp") int cp, @Param("ps") int ps);
	
	@Select("SELECT count(*) FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%') "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%')")
	int selectProductRowByKeywordAndUserId(@Param("user_id") String user_id, @Param("branch_office_nm") String branch_office_nm, @Param("keyword")  String keyword);
	// End Paging
	
	// 재고가 있는 상품 리스트 페이징 
	@Select("SELECT b.PRODUCT_SQ, b.BRANCH_OFFICE_SQ, a.BRANCH_OFFICE_NM, b.PRODUCT_NM, b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_STOCK, b.PRODUCT_SELL, b.PRODUCT_RECALL "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branchOfficeName},'%') "
			+ "AND b.PRODUCT_STOCK - b.PRODUCT_SELL - b.PRODUCT_RECALL > 0 "
			+ "ORDER BY a.BRANCH_OFFICE_NM ASC, b.PRODUCT_NM ASC, b.PRODUCT_PRICE ASC LIMIT #{cp}, #{ps}")
	List<ProductTB> selectExistsProductTBs(@Param("user_id") String user_id, @Param("branchOfficeName") String branchOfficeName, @Param("cp") int cp, @Param("ps") int ps);
	@Select("SELECT COUNT(*) "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branchOfficeName},'%') "
			+ "AND b.PRODUCT_STOCK - b.PRODUCT_SELL - b.PRODUCT_RECALL > 0 ")
	int selectExistsProductTBsCount(@Param("user_id") String user_id, @Param("branchOfficeName") String branchOfficeName);
	
	// 유저의 재고가 있는 지점별 For
	@Select("SELECT DISTINCT a.BRANCH_OFFICE_NM "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND b.PRODUCT_STOCK - b.PRODUCT_SELL - b.PRODUCT_RECALL > 0")
	List<BranchOfficeTB> selectExistsStockBranchOfficeByUserId(@Param("user_id") String user_id);
	// 유저의 재고가 있는 상품이름별 For
	@Select("SELECT DISTINCT b.PRODUCT_SQ, b.PRODUCT_NM, b.PRODUCT_PRICE, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND b.PRODUCT_STOCK - b.PRODUCT_SELL - b.PRODUCT_RECALL > 0 "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branchName},'%')")
	List<ProductTB> selectExistsStockProductNmByUserId(@Param("branchName")String branchName, @Param("user_id") String user_id);
	// 유저의 재고가 있는 상품들 For Dashboard
	@Select("SELECT a.BRANCH_OFFICE_NM, b.PRODUCT_NM, b.PRODUCT_PRICE, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_STOCK, b.PRODUCT_SELL, b.PRODUCT_RECALL "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND b.PRODUCT_STOCK - b.PRODUCT_SELL - b.PRODUCT_RECALL > 0 "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branchName},'%') "
			+ "AND b.PRODUCT_SQ LIKE CONCAT('%',#{productSq},'%') "
			+ "ORDER BY a.BRANCH_OFFICE_NM ASC, b.PRODUCT_NM ASC, b.PRODUCT_PRICE ASC")
	List<ProductTB> selectExistsStockByUserId(@Param("branchName")String branchName, @Param("productSq")String productSq, @Param("user_id") String user_id);
	
	
	// 수수료 시 
	@Select("SELECT * "
			+ "FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%')"
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%')")
	List<ProductTB> selectProductTBListByUserIdAndKeyword(@Param("user_id") String user_id, @Param("branch_office_nm") String branch_office_nm, @Param("keyword")  String keyword);
	
	@Select("SELECT b.PRODUCT_SQ, b.PRODUCT_NM, b.BRANCH_OFFICE_SQ, a.BRANCH_OFFICE_NM, b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, b.PRODUCT_STOCK, b.PRODUCT_SELL, b.PRODUCT_RECALL FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ "
			+ "WHERE 1=1 AND b.PRODUCT_SQ = #{product_sq}")
	ProductTB selectProductTBByProductSq(Long product_Sq);
	
	// Excel
	@Select("SELECT b.PRODUCT_SQ, b.PRODUCT_NM, b.BRANCH_OFFICE_SQ, a.BRANCH_OFFICE_NM, "
			+ "b.PRODUCT_ST, b.PRODUCT_PRICE, b.PRODUCT_COMMISSION, b.PRODUCT_WEIGHT, b.PRODUCT_WEIGHT_DT, b.PRODUCT_MEASURE, b.PRODUCT_STOCK, b.PRODUCT_SELL, b.PRODUCT_RECALL FROM BRANCH_OFFICE_TB a INNER JOIN PRODUCT_TB b ON a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ  "
			+ "WHERE b.USER_ID = #{user_id} "
			+ "AND a.BRANCH_OFFICE_NM LIKE CONCAT('%',#{branch_office_nm},'%') "
			+ "AND b.PRODUCT_NM LIKE CONCAT('%',#{keyword},'%') "
			+ "ORDER BY a.BRANCH_OFFICE_NM ASC, b.PRODUCT_NM ASC, b.PRODUCT_PRICE ASC")
	List<ProductTB> selectProductTBListByExcel(@Param("user_id") String user_id, @Param("branch_office_nm") String branch_office_nm ,@Param("keyword") String keyword);
	
	// Insert
	@Insert("INSERT INTO PRODUCT_TB(PRODUCT_SQ, USER_ID, BRANCH_OFFICE_SQ, PRODUCT_NM, PRODUCT_ST, PRODUCT_PRICE, PRODUCT_COMMISSION, PRODUCT_WEIGHT, PRODUCT_WEIGHT_DT, PRODUCT_MEASURE, PRODUCT_STOCK, PRODUCT_SELL, PRODUCT_RECALL, REG_DT, MOD_DT) "
			+ "VALUES(NULL, #{user_id}, #{branch_office_sq}, #{product_nm}, #{product_st}, #{product_price}, #{product_commission}, #{product_weight}, #{product_weight_dt}, #{product_measure}, 0, 0, 0, NOW(), NOW())")
	int insertProductTB(ProductTB productTB);
	
	// Update
	@Update("UPDATE PRODUCT_TB SET BRANCH_OFFICE_SQ = #{branch_office_sq}, PRODUCT_NM = #{product_nm}, PRODUCT_ST = #{product_st}, PRODUCT_PRICE = #{product_price}, PRODUCT_COMMISSION = #{product_commission}, PRODUCT_WEIGHT = #{product_weight}, PRODUCT_WEIGHT_DT = #{product_weight_dt}, PRODUCT_MEASURE = #{product_measure}, MOD_DT = NOW() "
			+ "WHERE PRODUCT_SQ = #{product_sq} AND USER_ID = #{user_id}")
	int updateProductTB(@Param("branch_office_sq") Long branch_office_sq, @Param("product_nm") String product_nm, @Param("product_st") String product_st, @Param("product_price") int product_price, @Param("product_commission") String product_commission, @Param("product_weight") String product_weight, @Param("product_weight_dt") String product_weight_dt, @Param("product_measure") String product_measure, @Param("product_sq") Long product_sq, @Param("user_id") String user_id);
	
	@Update("UPDATE PRODUCT_TB SET PRODUCT_STOCK = PRODUCT_STOCK + #{stock_no} WHERE PRODUCT_SQ = #{product_sq}")
	int updateAddProductStock(@Param("stock_no") int stock_no, @Param("product_sq") Long product_sq);
	@Update("UPDATE PRODUCT_TB SET PRODUCT_STOCK = PRODUCT_STOCK - #{stock_no} WHERE PRODUCT_SQ = #{product_sq}")
	int updateMinusProductStock(@Param("stock_no") int stock_no, @Param("product_sq") Long product_sq);
	
	@Update("UPDATE PRODUCT_TB SET PRODUCT_SELL = PRODUCT_SELL + #{stock_no} WHERE PRODUCT_SQ = #{product_sq}")
	int updateAddProductSell(@Param("stock_no") int stock_no, @Param("product_sq") Long product_sq);
	@Update("UPDATE PRODUCT_TB SET PRODUCT_SELL = PRODUCT_SELL - #{stock_no} WHERE PRODUCT_SQ = #{product_sq}")
	int updateMinusProductSell(@Param("stock_no") int stock_no, @Param("product_sq") Long product_sq);
	
	@Update("UPDATE PRODUCT_TB SET PRODUCT_RECALL = PRODUCT_RECALL + #{stock_no} WHERE PRODUCT_SQ = #{product_sq}")
	int updateAddProductRecall(@Param("stock_no") int stock_no, @Param("product_sq") Long product_sq);
	@Update("UPDATE PRODUCT_TB SET PRODUCT_RECALL = PRODUCT_RECALL - #{stock_no} WHERE PRODUCT_SQ = #{product_sq}")
	int updateMinusProductRecall(@Param("stock_no") int stock_no, @Param("product_sq") Long product_sq);
	
	
	// Delete
	@Delete("DELETE FROM PRODUCT_TB WHERE PRODUCT_SQ = #{product_sq} AND USER_ID = #{user_id}")
	int deleteProductTBByProductSq(@Param("product_sq") Long product_sq, @Param("user_id") String user_id);
	
}
