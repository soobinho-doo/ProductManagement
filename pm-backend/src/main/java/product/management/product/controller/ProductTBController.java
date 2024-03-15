package product.management.product.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.product.dto.ProductTB;
import product.management.product.service.ProductTBService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductTBController {

	private final ProductTBService productTBService;
	
	// 상세 정보
	@GetMapping("/product/{product_sq}")
	public ResponseEntity<ProductTB> findProductTBByProductSq(@PathVariable(value="product_sq") Long product_Sq) {
		ProductTB productTB = productTBService.findProductTBByProductSq(product_Sq);
		//log.info("##"+productTB);
		return ResponseEntity.ok(productTB);
	}
	
	// 상품 재고 리스트 
	@GetMapping("/product/dashboard")
	public ResponseEntity<List<ProductTB>> findHasStockByUserId(HttpServletRequest request) {
		List<ProductTB> productTBList = productTBService.findExistsStockByUserId(request);
		
		return ResponseEntity.ok(productTBList);
	}
	
	// 리스트 페이징
	@PostMapping("/product/paging")
	public ResponseEntity<Map<String, Object>> findProductTBPaging(@RequestBody  HashMap<String, String> map, HttpServletRequest request) {
		Map<String, Object> result = productTBService.findProductTBPaging(map, request);
		
		return ResponseEntity.ok(result);
	}
	
	// 엑셀 다운
	@GetMapping("/product/excel/download")
	public void findStockTBByExcel(@RequestParam(value="user_id") String user_id, @RequestParam(value="keyword") String keyword,@RequestParam(value="branch_office_nm") String branch_office_nm, HttpServletResponse response) throws IOException {
		productTBService.findProductTBListByExcel(user_id, keyword, branch_office_nm, response);
	}
	
	// 등록
	@PostMapping("/product")
	public ResponseEntity<Integer> inputProductTB(@RequestBody ProductTB productTB, HttpServletRequest request) {
		int result = productTBService.inputProductTB(productTB, request);
		
		return ResponseEntity.ok(result);
	}
	
	// 수정
	@PatchMapping("/product/{product_sq}")
	public ResponseEntity<Integer> modifyProductTB(@RequestBody ProductTB productTB, @PathVariable(value="product_sq") Long product_sq) {
		int result = productTBService.modifyProductTB(productTB, product_sq);
		
		return ResponseEntity.ok(result);
	}
	
	// 삭제
	@DeleteMapping("/product/{product_sq}")
	public ResponseEntity<Integer> deleteProductTBByProductSq(@PathVariable(value="product_sq") Long product_sq) {
		int result = productTBService.deleteProductTBByProductSq(product_sq);
		
		return ResponseEntity.ok(result);
	}
}
