package product.management.stock.dto;

import java.util.List;

import lombok.Data;

@Data
public class StockTBVO {
	private int cp; // 현재 페이지
	private int ps; // 페이지 당 개수
	private int rowCount; // 행 개수
	private int pageCount; 
	private List<StockTBJoinVO> list;
	private String keyword; // 검색 키워드
	private String stockSt;
	private String startDt;
	private String endDt;
	
	public StockTBVO(int cp, int ps, String stockSt, String startDt, String endDt, String keyword, int rowCount, List<StockTBJoinVO> list) {
		cp = this.calCp();
		ps = this.ps;
		stockSt = this.stockSt;
		startDt = this.startDt;
		endDt = this.endDt;
		keyword = this.keyword;
		rowCount = this.rowCount;
		list = this.list;
	}
	
	
	// Calculator Method
	private int calCp() {
		int calPage = 0;
		calPage = (cp-1) * ps;
		return calPage;
	}
	
	private int calPage() {
		int cnt = rowCount / ps;
		if(cnt != 0) {cnt++;}
		if(cnt == 0) {cnt = 1;}
		
		return cnt;
	}
}
