package product.management.product.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.management.product.dto.ProductTB;
import product.management.product.dto.ProductTBVO;

public interface ProductTBService {
	// Select
	ProductTB findProductTBByProductSq(Long product_Sq);

	Map<String, Object> findExistsStockByUserId(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	Map<String, Object> findProductTBPaging(HashMap<String, String> map, HttpServletRequest request);
	Map<String, Object> findExistsProductTBs(HashMap<String, String> map, HttpServletRequest request);
	
	void findProductTBListByExcel(String user_id, String keyword, String branch_office_nm, HttpServletResponse response) throws IOException;
	
	// Insert
	int inputProductTB(ProductTB productTB, HttpServletRequest request);
	
	
	// Update
	int modifyProductTB(ProductTB productTB, Long product_sq, HttpServletRequest request);
	
	
	// Delete
	int deleteProductTBByProductSq(Long product_sq, HttpServletRequest request);
}
