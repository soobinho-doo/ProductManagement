package product.management.branchoffice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import product.management.branchoffice.dto.BranchOfficeTB;
import product.management.branchoffice.service.BranchOfficeTBService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BranchOfiiceTBController {

	private final BranchOfficeTBService branchOfficeTBService;
	
	@GetMapping("/branch-office")
	public ResponseEntity<List<BranchOfficeTB>> findBranchOfficeTBListByUserId(HttpServletRequest request) {
		List<BranchOfficeTB> list = branchOfficeTBService.findBranchOfficeTBListByUserId(request);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/branch-office/{branch_office_sq}")
	public ResponseEntity<BranchOfficeTB> findBranchOfficeTBByBranchOfficeSq(@PathVariable(value="branch_office_sq") Long branch_office_sq) {
		BranchOfficeTB branchOfficeTB = branchOfficeTBService.findBranchOfficeTBByBranchOfficeSq(branch_office_sq);
		
		return ResponseEntity.ok(branchOfficeTB);
	}
	
	@PostMapping("/branch-office/paging")
	public ResponseEntity<Map<String, Object>> findBranchOfficeTBPaging(@RequestBody  HashMap<String, String> map, HttpServletRequest request) {
		Map<String, Object> result = branchOfficeTBService.findBranchOfficeTBPaging(map, request);
		
		return ResponseEntity.ok(result);
	}
	
	
	@PostMapping("/branch-office")
	public ResponseEntity<Integer> inputBranchOfficeTB(@RequestBody BranchOfficeTB branchOfficeTB, HttpServletRequest request) {
		int result = branchOfficeTBService.inputBranchOfficeTB(branchOfficeTB, request);
		
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/branch-office/{branch_office_sq}")
	public ResponseEntity<Integer> modifyBranchOfficeTB(@PathVariable(value="branch_office_sq") Long branch_office_sq, @RequestBody BranchOfficeTB branchOfficeTB) {
		int result = branchOfficeTBService.modifyBranchOfficeTB(branchOfficeTB);
		
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/branch-office/{branch_office_sq}")
	public ResponseEntity<Integer> deleteBranchOfficeTB(@PathVariable(value="branch_office_sq") Long branch_office_sq) {
		int result = branchOfficeTBService.deleteBranchOfficeTB(branch_office_sq);
		
		return ResponseEntity.ok(result);
	}
}
