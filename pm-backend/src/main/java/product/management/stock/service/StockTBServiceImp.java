package product.management.stock.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.common.jwt.JwtProvider;
import product.management.product.mapper.ProductTBMapper;
import product.management.stock.dto.StockBackupTB;
import product.management.stock.dto.StockTB;
import product.management.stock.dto.StockTBJoinVO;
import product.management.stock.dto.StockTBUpdateVO;
import product.management.stock.dto.StockTBVO;
import product.management.stock.mapper.StockBackupTBMapper;
import product.management.stock.mapper.StockTBMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockTBServiceImp implements StockTBService {

	private final StockTBMapper stockTBMapper;
	private final StockBackupTBMapper stockBackupTBMapper;
	private final ProductTBMapper productTBMapper;
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
		
		int rowCount = stockTBMapper.selectRowCount(user_id, stockSt, startDt, endDt, keyword, branchOfficeNm);
		List<StockTBJoinVO> list = stockTBMapper.selectStockTBByPaging(user_id, stockSt, startDt, endDt, keyword, branchOfficeNm, calPage, ps);
		List<StockTBJoinVO> listAll = stockTBMapper.selectStockTBByAllPaging(user_id, stockSt, startDt, endDt, keyword, branchOfficeNm);

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
					"pageCount",pageCount,
					"list", list,
					"listAll",listAll,
					"sp", sp,
					"ep", ep
				);
		
		
		return result;
	}
	
	@Override
	public StockTB findStockTBByStockSq(Long stock_sq, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		StockTB result = stockTBMapper.selectStockTBByStockSq(stock_sq, user_id);
		
		return result;
	}
	
	@Override
	public void findStockTBByExcel(String user_id, String stock_st, String start_dt, String end_dt, String keyword,
			String branch_office_nm, HttpServletResponse response) throws IOException {
		
		List<StockTBJoinVO> datas = stockTBMapper.selectStockTBByExcel(user_id, stock_st, start_dt, end_dt, keyword, branch_office_nm);
		//log.info("Get "+datas);
		
		// 엑셀 파일 하나를 만듭니다
		/**
         * excel sheet 생성
         */
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("판매현황"); // 엑셀 sheet 이름
        

        // 스타일
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);  
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);
        
        /**
         * header data
         */
        int rowCount = 0; // 데이터가 저장될 행
        String headerNames[] = new String[]{"구분", "지점", "상품", "단위", "단가", "수수료", "진열", "수량", "날짜"};

        Row headerRow = null;
        Cell headerCell = null;
        
        headerRow = sheet.createRow(rowCount++);
        for(int i=0; i<headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]); // 데이터 추가
            headerCell.setCellStyle(headerStyle);
        }

        
        
        /**
         * body data
         */
        
        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);  
        
        CellStyle rightStyle = workbook.createCellStyle();
        rightStyle.setAlignment(HorizontalAlignment.RIGHT);

        Row bodyRow = null;
        Cell bodyCell = null;
        // 날짜 포맷을 설정한다.
        SimpleDateFormat numberDataFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(StockTBJoinVO data : datas) {
	        	bodyRow = sheet.createRow(rowCount++);
	        	
	        	bodyCell = bodyRow.createCell(0);
	        	if(data.getStock_st().equals("1")) {
	        		bodyCell.setCellValue("납품"); // 데이터 추가
	        		bodyCell.setCellStyle(centerStyle);
	        	}else if(data.getStock_st().equals("2")) {
	        		bodyCell.setCellValue("판매"); // 데이터 추가
	        		bodyCell.setCellStyle(centerStyle);
	        	}else if(data.getStock_st().equals("3")) {
	        		bodyCell.setCellValue("회수"); // 데이터 추가
	        		bodyCell.setCellStyle(centerStyle);
	        	}
	        	
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(data.getBranch_office_nm()); // 데이터 추가
            
            
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(data.getProduct_nm()); // 데이터 추가
            
            
            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(data.getProduct_weight()+" "+data.getProduct_weight_dt()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(data.getProduct_price()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(5);
            bodyCell.setCellValue(data.getProduct_commission()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(6);
            if(data.getProduct_st().equals("1")) {
            		bodyCell.setCellValue("기본"); // 데이터 추가
            		bodyCell.setCellStyle(centerStyle);
            }else if(data.getProduct_st().equals("2")) {
            		bodyCell.setCellValue("냉장"); // 데이터 추가
            		bodyCell.setCellStyle(centerStyle);
            }else if(data.getProduct_st().equals("3")) {
            		bodyCell.setCellValue("냉동"); // 데이터 추가
            		bodyCell.setCellStyle(centerStyle);
            }
            
            bodyCell = bodyRow.createCell(7);
            bodyCell.setCellValue(data.getStock_no()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(8);
            bodyCell.setCellValue(numberDataFormat.format(data.getStock_dt())); // 데이터 추가
            bodyCell.setCellStyle(centerStyle);
        }
        
        //컬럼 너비 자동 설정
        for (int i=0; i<=headerNames.length; i++){
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)2400);
		}
        

        LocalDate today = LocalDate.now();
        String fileName = today+"_로컬푸드_판매현황";

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");
        //파일명은 URLEncoder로 감싸주는게 좋다!

        workbook.write(response.getOutputStream());
        workbook.close();
		
	}
	
	
	@Override
	@Transactional
	public int inputStockTB(StockTB stockTB, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		stockTB.setUser_id(user_id);
		int stockNo = stockTB.getStock_no();
		
		if(stockTB.getStock_st().equals("1")) {
			productTBMapper.updateAddProductStock(stockNo, stockTB.getProduct_sq());
		}else if (stockTB.getStock_st().equals("2")) {
			productTBMapper.updateAddProductSell(stockNo, stockTB.getProduct_sq());
		}else if(stockTB.getStock_st().equals("3")) {
			productTBMapper.updateAddProductRecall(stockNo, stockTB.getProduct_sq());
		}
		
		int result = stockTBMapper.insertStockTB(stockTB);
		StockBackupTB stockBackupTB = new StockBackupTB();
		stockBackupTB.setUser_id(user_id);
		stockBackupTB.setStock_st(stockTB.getStock_st());
		stockBackupTB.setProduct_sq(stockTB.getProduct_sq());
		stockBackupTB.setStock_no(stockNo);
		stockBackupTB.setStock_dt(stockTB.getStock_dt());
		int backResult = stockBackupTBMapper.insertStockTB(stockBackupTB);
		
		
		return result;
	}
	
	@Override
	@Transactional
	public int modifyStockTB(StockTBUpdateVO stockTBUpdateVO, HttpServletRequest request) {
		
		String beforeStockSt = stockTBUpdateVO.getBefore_stock_st();
		int beforeStockNo = stockTBUpdateVO.getBefore_stock_no();
		
		String newStockSt = stockTBUpdateVO.getStock_st();
		int newStockNo = stockTBUpdateVO.getStock_no();
		
		// 이전 데이터 지우기		
		if(beforeStockSt.equals("1")) {
			productTBMapper.updateMinusProductStock(beforeStockNo, stockTBUpdateVO.getBefore_product_sq());
		}else if(beforeStockSt.equals("2")) {
			productTBMapper.updateMinusProductSell(beforeStockNo, stockTBUpdateVO.getBefore_product_sq());
		}else if(beforeStockSt.equals("3")) {
			productTBMapper.updateMinusProductRecall(beforeStockNo, stockTBUpdateVO.getBefore_product_sq());
		}
		
		
		// 새로운 데이터 채우기 
		if(newStockSt.equals("1")) {
			productTBMapper.updateAddProductStock(newStockNo, stockTBUpdateVO.getProduct_sq());
		}else if(newStockSt.equals("2")) {
			productTBMapper.updateAddProductSell(newStockNo, stockTBUpdateVO.getProduct_sq());
		}else if(newStockSt.equals("3")) {
			productTBMapper.updateAddProductRecall(newStockNo, stockTBUpdateVO.getProduct_sq());
		}
		
		int result = stockTBMapper.updateStockTB(stockTBUpdateVO);
		
		return result;
	}

	@Override
	@Transactional
	public int deleteStockTB(StockTBJoinVO stockTBJoinVO, HttpServletRequest request) {
		//log.info("Stock-Delete = {}", stockTBJoinVO);
		int stockNo = stockTBJoinVO.getStock_no();
		
		if(stockTBJoinVO.getStock_st().equals("1")) {
			productTBMapper.updateMinusProductStock(stockNo, stockTBJoinVO.getProduct_sq());
		}else if (stockTBJoinVO.getStock_st().equals("2")) {
			productTBMapper.updateMinusProductSell(stockNo, stockTBJoinVO.getProduct_sq());
		}else if(stockTBJoinVO.getStock_st().equals("3")) {
			productTBMapper.updateMinusProductRecall(stockNo, stockTBJoinVO.getProduct_sq());
		}
		
		return stockTBMapper.deleteStockTB(stockTBJoinVO.getStock_sq());
	}
}
