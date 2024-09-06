package product.management.branchoffice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.branchoffice.dto.BranchOfficeTB;
import product.management.branchoffice.dto.BranchOfficeTBVO;
import product.management.branchoffice.mapper.BranchOfficeTBMapper;
import product.management.common.jwt.JwtProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class BranchOfficeTBServiceImp implements BranchOfficeTBService {

	private final BranchOfficeTBMapper branchOfficeTBMapper;
	private final JwtProvider jwtProvider;
	
	@Override
	public Map<String, Object> findBranchOfficeTBPaging(HashMap<String, String> map, HttpServletRequest request) {
		int cp = Integer.parseInt(map.get("cp"));
		int ps = Integer.parseInt(map.get("ps"));
		int calPage = (cp-1) * ps;
		
		String keyword = map.get("keyword");
		
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		
		int rowCount = branchOfficeTBMapper.selectBranchOfficeTBRowByKeywordAndUserId(user_id, keyword);

		List<BranchOfficeTB> list = branchOfficeTBMapper.selectBranchOfficeTBListByBranchOfficeTBAndUserId(user_id, keyword, calPage, ps);

		int pageCount = rowCount / ps;
		if(pageCount != 0) {pageCount++;}
		if(pageCount == 0) {pageCount = 1;}
		
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
					"ps", ps,
					"count", rowCount,
					"pageCount",pageCount,
					"list", list,
					"sp", sp,
					"ep", ep
				);
		
		
		return result;
	}
	
	@Override
	public BranchOfficeTB findBranchOfficeTBByBranchOfficeSq(Long branch_office_sq) {
		
		return branchOfficeTBMapper.selectBranchOfficeTBByBranchOfficeSq(branch_office_sq);
	}

	@Override
	@Transactional
	public int inputBranchOfficeTB(BranchOfficeTB branchOfficeTB, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String user_id = jwtProvider.getIdByToken(token);
		branchOfficeTB.setUser_id(user_id);
		
		return branchOfficeTBMapper.insertBranchOfficeTB(branchOfficeTB);
	}

	@Override
	@Transactional
	public int modifyBranchOfficeTB(BranchOfficeTB branchOfficeTB) {

		return branchOfficeTBMapper.updateBranchOfficeTB(branchOfficeTB);
	}

	@Override
	@Transactional
	public int deleteBranchOfficeTB(Long branch_office_sq) {
		
		return branchOfficeTBMapper.deleteBranchOfficeTB(branch_office_sq);
	}

}
