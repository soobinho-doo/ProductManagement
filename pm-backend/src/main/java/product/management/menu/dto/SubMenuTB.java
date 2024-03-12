package product.management.menu.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubMenuTB {

	private String menu_cd;
	private String sub_menu_cd;
	private String menu_nm;
	private String menu_srs;
	private Timestamp reg_dt;
	private Timestamp mod_dt;
	
}
