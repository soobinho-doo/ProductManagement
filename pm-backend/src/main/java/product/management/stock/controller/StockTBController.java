package product.management.stock.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import product.management.stock.dto.StockTB;
import product.management.stock.dto.StockTBJoinVO;
import product.management.stock.dto.StockTBUpdateVO;
import product.management.stock.service.StockTBService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StockTBController {

	private final StockTBService stockTBService;
	
	@PostMapping("/stock/paging")
	public ResponseEntity<Map<String, Object>> findStockTBPaging(@RequestBody  HashMap<String, String> map, HttpServletRequest request) {
		Map<String, Object> result = stockTBService.findStockTBPaging(map, request);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/stock/{stock_sq}")
	public ResponseEntity<StockTB> findStockTBByStockSq(@PathVariable(value="stock_sq") Long stock_sq) {
		StockTB stockTB = stockTBService.findStockTBByStockSq(stock_sq);
		
		return ResponseEntity.ok(stockTB);
	}
	
	// For Excel ====
	@GetMapping("/stock/authentication")
	public ResponseEntity<String> stockAuthentication(HttpServletRequest request) {
		String user_id = stockTBService.stockAuthentication(request);
		return ResponseEntity.ok(user_id);
	}
	
	@GetMapping("/stock/excel/download")
	public void findStockTBByExcel(@RequestParam(value="user_id") String user_id, @RequestParam(value="stock_st") String stock_st, @RequestParam(value="start_dt") String start_dt, @RequestParam(value="end_dt") String end_dt,@RequestParam(value="keyword") String keyword,@RequestParam(value="branch_office_nm") String branch_office_nm, HttpServletResponse response) throws IOException {
		stockTBService.findStockTBByExcel(user_id, stock_st, start_dt, end_dt, keyword, branch_office_nm, response);
	}
	// ====
	
	@PostMapping("/stock")
	public ResponseEntity<Integer> inputStockTB(@RequestBody StockTB stockTB, HttpServletRequest request) {
		int result = stockTBService.inputStockTB(stockTB, request);
		
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/stock")
	public ResponseEntity<Integer> inputStockTB(@RequestBody StockTBUpdateVO stockTBUpdateVO) {
		int result = stockTBService.modifyStockTB(stockTBUpdateVO);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/stock/delete")
	public ResponseEntity<Integer> deleteStockTB(@RequestBody StockTBJoinVO stockTBJoinVO) {
		int result = stockTBService.deleteStockTB(stockTBJoinVO);
		
		return ResponseEntity.ok(result);
	}
}