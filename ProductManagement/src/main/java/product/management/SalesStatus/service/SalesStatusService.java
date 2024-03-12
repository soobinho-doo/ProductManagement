package product.management.SalesStatus.service;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import product.management.SalesStatus.dto.SalesStatusVO;

public interface SalesStatusService {
	// 오늘의 판매량
	SalesStatusVO findTodaySumPrice(HttpServletRequest request);
	
	// 연 별 판매량
	List<SalesStatusVO> findYearAverage(HttpServletRequest request);
	
	// 월 별 판매량
	List<SalesStatusVO> findMonthAverage(HttpServletRequest request, String years);
	
	// 일 별 판매량
	List<SalesStatusVO> findDayAverage(HttpServletRequest request, String years, String months);
}
