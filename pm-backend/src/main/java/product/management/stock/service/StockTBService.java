package product.management.stock.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.management.stock.dto.StockTB;
import product.management.stock.dto.StockTBJoinVO;
import product.management.stock.dto.StockTBUpdateVO;

public interface StockTBService {
	Map<String, Object> findStockTBPaging(HashMap<String, String> map, HttpServletRequest request);
	
	StockTB findStockTBByStockSq(Long stock_sq);
	
	int inputStockTB(StockTB stockTB, HttpServletRequest request);
	int modifyStockTB(StockTBUpdateVO stockTBUpdateVO);
	int deleteStockTB(StockTBJoinVO stockTBJoinVO);
	
	void findStockTBByExcel(String user_id, String stock_st, String start_dt, String end_dt, String keyword, String branch_office_nm, HttpServletResponse response) throws IOException;
}
