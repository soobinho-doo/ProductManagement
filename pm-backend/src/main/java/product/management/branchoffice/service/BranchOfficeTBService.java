package product.management.branchoffice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import product.management.branchoffice.dto.BranchOfficeTB;

public interface BranchOfficeTBService {
	
	Map<String, Object> findBranchOfficeTBPaging(HashMap<String, String> map, HttpServletRequest request);
	
	BranchOfficeTB findBranchOfficeTBByBranchOfficeSq(Long branch_office_sq);
	
	int inputBranchOfficeTB(BranchOfficeTB branchOfficeTB, HttpServletRequest request);
	
	int modifyBranchOfficeTB(BranchOfficeTB branchOfficeTB);
	
	int deleteBranchOfficeTB(Long branch_office_sq);
}
