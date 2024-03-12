package product.management.branchoffice.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchOfficeTB {

	private Long branch_office_sq;
	private String user_id;
	private String branch_office_nm;
	private String branch_office_area;
	private Timestamp reg_dt;
	private Timestamp mod_dt;
	
}
