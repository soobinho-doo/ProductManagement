package product.management.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.common.jwt.JwtProvider;
import product.management.product.mapper.ProductTBMapper;
import product.management.stock.dto.StockTBJoinVO;
import product.management.stock.dto.StockTBVO;
import product.management.stock.mapper.StockBackupTBMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockBackupTBServiceImp implements StockBackupTBService {
	
	private final StockBackupTBMapper stockBackupTBMapper;
	private final JwtProvider jwtProvider;

	@Override
	public Map<String, Object> findStockTBPaging(HashMap<String, String> map, HttpServletRequest request) {
		int cp = Integer.parseInt(map.get("cp"));
		int ps = 10;
		int calPage = 0;
		calPage = (cp-1) * ps;
		String stockSt = map.get("stock_st");
		String startDt = map.get("start_dt");
		String endDt = map.get("end_dt");
		String keyword = map.get("keyword");
		String branchOfficeNm = map.get("branch_office_nm");
		
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		int rowCount = stockBackupTBMapper.selectRowCount(user_id, stockSt, startDt, endDt, keyword, branchOfficeNm);
		List<StockTBJoinVO> list = stockBackupTBMapper.selectStockTBByPaging(user_id, stockSt, startDt, endDt, keyword, branchOfficeNm, calPage, ps);
		//log.info("Stock rowCount = {}", rowCount);
		//log.info("Stock List = {}", list);
		
		//StockTBVO paging = new StockTBVO(calPage, ps, stockSt, startDt, endDt, keyword, rowCount, list);
		int pageCount = (int)(Math.ceil((double)rowCount/ps));
		
		int showPage = 5; // 한페이지에 보여줄 페이징 개수
		int pageGrp = (int)Math.ceil((double)cp / showPage);
		
		int sp = ((pageGrp-1) * showPage) + 1;
		int ep = pageGrp * showPage;
		
		if(ep > pageCount) {
			ep = pageCount;
		}else if(ep == 0) {
			ep = 1;
		}
		
		Map<String, Object> result = Map.of(
					"cp", cp,
					"count", rowCount,
					"list", list,
					"pageCount",pageCount,
					"sp", sp,
					"ep", ep
				);
		
		
		return result;
	}

}
