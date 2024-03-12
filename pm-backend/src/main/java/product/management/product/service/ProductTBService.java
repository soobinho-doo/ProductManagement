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
	List<ProductTB> findProductTBListByUserId(HttpServletRequest request);
	List<ProductTB> findProductTBListByUserIdAndBranchOfficeNm(HttpServletRequest request, String branch_office_nm);
	List<ProductTB> findExistsStockByUserId(HttpServletRequest request);
	
	Map<String, Object> findProductTBPaging(HashMap<String, String> map, HttpServletRequest request);
	
	String productAuthentication(HttpServletRequest request);
	void findProductTBListByExcel(String user_id, String keyword, String branch_office_nm, HttpServletResponse response) throws IOException;
	
	// Insert
	int inputProductTB(ProductTB productTB, HttpServletRequest request);
	
	
	// Update
	int modifyProductTB(ProductTB productTB, Long product_sq);
	
	
	// Delete
	int deleteProductTBByProductSq(Long product_sq);
}
