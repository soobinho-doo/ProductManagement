package product.management.branchoffice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchOfficeTBVO {

	private int cp; // 현재 페이지
	private int ps; // 페이지 당 개수
	private int rowCount; // 행 개수
	private int pageCount; 
	private List<BranchOfficeTB> list;
	private String keyword; // 검색 키워드
		
	public BranchOfficeTBVO(int cp, int ps, String keyword, int rowCount, List<BranchOfficeTB> list) {
		this.cp = cp;
		this.ps = ps;
		this.keyword = keyword;
		this.rowCount = rowCount;
		this.list = list;
		this.pageCount = this.calPage();
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
