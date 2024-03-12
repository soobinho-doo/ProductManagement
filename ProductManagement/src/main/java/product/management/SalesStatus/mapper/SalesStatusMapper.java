package product.management.SalesStatus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import product.management.SalesStatus.dto.SalesStatusVO;

@Mapper
public interface SalesStatusMapper {

	// 오늘의 판매 가격
	@Select("SELECT SUM(b.PRODUCT_PRICE * c.STOCK_NO) as sum_price, SUM(b.PRODUCT_PRICE * c.STOCK_NO - ((b.PRODUCT_PRICE * c.STOCK_NO) * b.PRODUCT_COMMISSION)/100) as sum_commission_price "
			+ "FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST = 2 "
			+ "AND c.STOCK_DT = #{stock_dt} ")
	SalesStatusVO selectTodaySumPrice(@Param("user_id") String user_id, @Param("stock_dt") String stock_dt);
	
	// 연 별 판매 가격
	@Select("SELECT DATE_FORMAT(c.STOCK_DT, '%Y') AS years, SUM(b.PRODUCT_PRICE * c.STOCK_NO) as sum_price, SUM(b.PRODUCT_PRICE * c.STOCK_NO - ((b.PRODUCT_PRICE * c.STOCK_NO) * b.PRODUCT_COMMISSION)/100) as sum_commission_price "
			+ "FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST = 2 "
			+ "GROUP BY DATE_FORMAT(c.STOCK_DT, '%Y') ")
	List<SalesStatusVO> selectYearAverage(@Param("user_id") String user_id);
	
	// 월 별 판매 가격
	@Select("SELECT DATE_FORMAT(c.STOCK_DT, '%m') AS months, SUM(b.PRODUCT_PRICE * c.STOCK_NO) as sum_price, SUM(b.PRODUCT_PRICE * c.STOCK_NO - ((b.PRODUCT_PRICE * c.STOCK_NO) * b.PRODUCT_COMMISSION)/100) as sum_commission_price "
			+ "FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST = 2 "
			+ "AND DATE_FORMAT(c.STOCK_DT, '%Y') = #{years}"
			+ "GROUP BY DATE_FORMAT(c.STOCK_DT, '%m') ")
	List<SalesStatusVO> selectMonthAverage(@Param("user_id") String user_id, @Param("years") String years);
	
	// 일 별 판매 가격
	@Select("SELECT DATE_FORMAT(c.STOCK_DT, '%d') AS days, SUM(b.PRODUCT_PRICE * c.STOCK_NO) as sum_price, SUM(b.PRODUCT_PRICE * c.STOCK_NO - ((b.PRODUCT_PRICE * c.STOCK_NO) * b.PRODUCT_COMMISSION)/100) as sum_commission_price "
			+ "FROM BRANCH_OFFICE_TB a, PRODUCT_TB b, STOCK_TB c "
			+ "WHERE a.BRANCH_OFFICE_SQ = b.BRANCH_OFFICE_SQ AND b.PRODUCT_SQ = c.PRODUCT_SQ "
			+ "AND b.USER_ID = #{user_id} "
			+ "AND c.STOCK_ST = 2 "
			+ "AND DATE_FORMAT(c.STOCK_DT, '%Y') = #{years} "
			+ "AND DATE_FORMAT(c.STOCK_DT, '%m') = #{months}"
			+ "GROUP BY DATE(c.STOCK_DT) "
			+ "ORDER BY days DESC")
	List<SalesStatusVO> selectDayAverage(@Param("user_id") String user_id, @Param("years") String years, @Param("months") String months);
}
