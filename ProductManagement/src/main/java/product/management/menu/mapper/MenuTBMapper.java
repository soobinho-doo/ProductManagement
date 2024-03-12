package product.management.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import product.management.menu.dto.MenuTB;
import product.management.menu.dto.SubMenuTB;

@Mapper
public interface MenuTBMapper {
	
	@Select("SELECT MENU_CD, MENU_NM FROM MENU_TB")
	@Results(id = "selectMenuList", value = {
			@Result(property = "menu_cd", column = "MENU_CD"),
			@Result(property = "menu_nm", column = "MENU_NM"),
			@Result(property = "subMenus", column = "menu_cd", many = @Many(select = "product.management.menu.mapper.MenuTBMapper.selectSubMenuTBByMenuCd"))
	})
	List<MenuTB> selectMenus();
	
	@Select("SELECT MENU_CD, SUB_MENU_CD, MENU_NM, MENU_SRS FROM SUB_MENU_TB WHERE MENU_CD = #{menu_cd}")
	SubMenuTB selectSubMenuTBByMenuCd(String menu_cd);
		
	
}
