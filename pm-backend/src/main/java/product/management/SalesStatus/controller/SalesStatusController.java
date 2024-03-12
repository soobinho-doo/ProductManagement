package product.management.SalesStatus.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import product.management.SalesStatus.dto.SalesStatusVO;
import product.management.SalesStatus.service.SalesStatusService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SalesStatusController {

	private final SalesStatusService salesStatusService;
	
	// 오늘 판매 총 가격
	@GetMapping("/status/today")
	public ResponseEntity<SalesStatusVO> findTodaySumPrice(HttpServletRequest request) {
		SalesStatusVO result = salesStatusService.findTodaySumPrice(request);
		
		return ResponseEntity.ok(result);
	}
	
	// 연 도별 판매 가격
	@GetMapping("/status/year")
	public ResponseEntity<List<SalesStatusVO>> findYearAverage(HttpServletRequest request) {	
		List<SalesStatusVO> result = salesStatusService.findYearAverage(request);
		
		return ResponseEntity.ok(result);
	}
	
	// 월 별 판매 가격
	@GetMapping("/status/month/{years}")
	public ResponseEntity<List<SalesStatusVO>> findMonthAverage(HttpServletRequest request, @PathVariable(value="years") String years) {
		List<SalesStatusVO> result = salesStatusService.findMonthAverage(request, years);
		
		return ResponseEntity.ok(result);
	}
	
	// 일 별 판매 가격
	@GetMapping("/status/day/{years}/{months}")
	public ResponseEntity<List<SalesStatusVO>> findDayAverage(HttpServletRequest request, @PathVariable(value="years") String years, @PathVariable(value="months") String months) {
		List<SalesStatusVO> result = salesStatusService.findDayAverage(request, years, months);
		
		return ResponseEntity.ok(result);
	}
	
}
