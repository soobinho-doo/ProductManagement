package product.management.stock.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import product.management.stock.service.StockBackupTBService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StockBackupTBController {

	private final StockBackupTBService stockBackupTBService;
	
	@PostMapping("/stock-backup/paging")
	public ResponseEntity<Map<String, Object>> findStockTBPaging(@RequestBody  HashMap<String, String> map, HttpServletRequest request) {
		Map<String, Object> result = stockBackupTBService.findStockTBPaging(map, request);
		
		return ResponseEntity.ok(result);
	}
}
