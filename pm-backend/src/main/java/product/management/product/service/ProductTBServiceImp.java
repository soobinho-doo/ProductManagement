package product.management.product.service;

import java.io.IOException;
import java.net.URLEncoder;
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
import product.management.product.dto.ProductTB;
import product.management.product.dto.ProductTBVO;
import product.management.product.mapper.ProductTBMapper;
import product.management.stock.dto.StockTBJoinVO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductTBServiceImp implements ProductTBService {

	private final ProductTBMapper productTBMapper;
	private final JwtProvider jwtProvider;
	
	@Override
	public ProductTB findProductTBByProductSq(Long product_Sq) {
		return productTBMapper.selectProductTBByProductSq(product_Sq);
	}
	
	@Override
	public List<ProductTB> findExistsStockByUserId(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		List<ProductTB> result = productTBMapper.selectExistsStockByUserId(user_id);
		
		return result;
	}	
	
	@Override
	public Map<String, Object> findProductTBPaging(HashMap<String, String> map, HttpServletRequest request) {
		int cp = Integer.parseInt(map.get("cp"));
		int ps = 10;
		int calPage = 0;
		calPage = (cp-1) * ps;
		String branchOfficeNm = map.get("branch_office_nm");
		String keyword = map.get("keyword");
		
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		int rowCount = productTBMapper.selectProductRowByKeywordAndUserId(user_id, branchOfficeNm, keyword);
		List<ProductTB> list = productTBMapper.selectProductTBListByProductTBVOAndUserId(user_id, branchOfficeNm, keyword, calPage, ps);
		
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
		
		List<ProductTB> commissionList = productTBMapper.selectProductTBListByUserIdAndKeyword(user_id, branchOfficeNm, keyword);
		int inCommission = 0;
		int exCommission = 0;
		for(ProductTB product : commissionList) {
			inCommission += (product.getProduct_price() * product.getProduct_sell()) * Double.parseDouble(product.getProduct_commission()) / 100;
			exCommission += product.getProduct_price() * product.getProduct_sell();
		}
		//log.info("Commission = "+inCommission +"  "+exCommission);
		int commissionResult = exCommission - inCommission;
		
		Map<String, Object> result = Map.of(
					"cp", cp,
					"count", rowCount,
					"list", list,
					"pageCount",pageCount,
					"sp", sp,
					"ep", ep,
					"inCommission", commissionResult,
					"exCommission", exCommission
				);
		
		
		return result;
	}
	

	@Override
	public void findProductTBListByExcel(String user_id, String keyword, String branch_office_nm,
			HttpServletResponse response) throws IOException {
		
		List<ProductTB> datas = productTBMapper.selectProductTBListByExcel(user_id, branch_office_nm, keyword);
		
		// 엑셀 파일 하나를 만듭니다
			/**
	         * excel sheet 생성
	         */
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("상품현황"); // 엑셀 sheet 이름
	        

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
	        String headerNames[] = new String[]{"지점", "진열", "상품", "단위", "단가", "수수료", "납품", "판매", "회수", "재고", "수익"};

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

	        for(ProductTB data : datas) {
	        	bodyRow = sheet.createRow(rowCount++);
	        	
	        	bodyCell = bodyRow.createCell(0);
	        	bodyCell.setCellValue(data.getBranch_office_nm()); // 데이터 추가
        		bodyCell.setCellStyle(centerStyle);
        		
        		bodyCell = bodyRow.createCell(1);
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
            bodyCell.setCellValue(data.getProduct_stock()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(7);
            bodyCell.setCellValue(data.getProduct_sell()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(8);
            bodyCell.setCellValue(data.getProduct_recall()); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(9);
            int have = data.getProduct_stock() - data.getProduct_sell() - data.getProduct_recall();
            bodyCell.setCellValue(have); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
            bodyCell = bodyRow.createCell(10);
            int sellMoney = (int) ((data.getProduct_price() * data.getProduct_sell()) - ((data.getProduct_price() * data.getProduct_sell()) * Double.parseDouble(data.getProduct_commission()) / 100));
            bodyCell.setCellValue(sellMoney); // 데이터 추가
            bodyCell.setCellStyle(rightStyle);
            
        }
        
        //컬럼 너비 자동 설정
        for (int i=0; i<=headerNames.length; i++){
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+(short)2400);
		}
        

        LocalDate today = LocalDate.now();
        String fileName = today+"_로컬푸드_상품현황";

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+".xlsx");
        //파일명은 URLEncoder로 감싸주는게 좋다!

        workbook.write(response.getOutputStream());
        workbook.close();
		
	}
	
	
	@Override
	@Transactional
	public int inputProductTB(ProductTB productTB, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		productTB.setUser_id(user_id);
		
		int result = productTBMapper.insertProductTB(productTB);
		
		return result;
	}
	
	@Override
	@Transactional
	public int modifyProductTB(ProductTB productTB, Long product_sq, HttpServletRequest request) {
		Long branch_office_sq = productTB.getBranch_office_sq();
		String product_nm = productTB.getProduct_nm();
		String product_st = productTB.getProduct_st();
		int product_price = productTB.getProduct_price();
		String product_commission = productTB.getProduct_commission();
		String product_weight = productTB.getProduct_weight();
		String product_weight_dt = productTB.getProduct_weight_dt();
		String product_measure = productTB.getProduct_measure();
		
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		int result = productTBMapper.updateProductTB(branch_office_sq, product_nm, product_st, product_price, product_commission, product_weight, product_weight_dt, product_measure, product_sq, user_id);
		
		return result;
	}

	@Override
	@Transactional
	public int deleteProductTBByProductSq(Long product_sq, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		int result = productTBMapper.deleteProductTBByProductSq(product_sq, user_id);
		
		return result; 
	}

}
