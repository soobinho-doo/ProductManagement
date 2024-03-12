package product.management.stock.service;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface StockBackupTBService {
	Map<String, Object> findStockTBPaging(HashMap<String, String> map, HttpServletRequest request);
	
}
