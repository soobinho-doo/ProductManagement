package product.management.menu.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTB {

	private String menu_cd;
	private String menu_nm;
	private Timestamp reg_dt;
	private Timestamp mod_dt;
	
	private List<SubMenuTB> subMenus;
	
}
