package product.management.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import product.management.menu.dto.MenuTB;
import product.management.menu.mapper.MenuTBMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuTBServiceImp implements MenuTBService {

	private final MenuTBMapper menuTBMapper;
	
	@Override
	public List<MenuTB> findMenus() {
		return menuTBMapper.selectMenus();
	}

}
