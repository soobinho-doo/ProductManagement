package product.management.SalesStatus.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.SalesStatus.dto.SalesStatusVO;
import product.management.SalesStatus.mapper.SalesStatusMapper;
import product.management.common.jwt.JwtProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesStatusServiceImp implements SalesStatusService {

	private final SalesStatusMapper salesStatusMapper;
	private final JwtProvider jwtProvider;
	
	@Override
	public SalesStatusVO findTodaySumPrice(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		LocalDate today = LocalDate.now();
		String stock_dt = today.toString();
		
		SalesStatusVO ssv = salesStatusMapper.selectTodaySumPrice(user_id, stock_dt);
		
		return ssv;
	}

	@Override
	public List<SalesStatusVO> findYearAverage(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);

		List<SalesStatusVO> ssv = salesStatusMapper.selectYearAverage(user_id);
		
		return ssv;
	}

	@Override
	public List<SalesStatusVO> findMonthAverage(HttpServletRequest request, String years) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);

		List<SalesStatusVO> result = salesStatusMapper.selectMonthAverage(user_id, years);
		
		return result;
	}

	@Override
	public List<SalesStatusVO> findDayAverage(HttpServletRequest request, String years, String months) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		List<SalesStatusVO> result = salesStatusMapper.selectDayAverage(user_id, years, months);
		
		return result;
	}

}
