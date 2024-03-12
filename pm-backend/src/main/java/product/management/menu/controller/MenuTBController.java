package product.management.menu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import product.management.menu.dto.MenuTB;
import product.management.menu.service.MenuTBService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuTBController {

	private final MenuTBService menuTBSerivce;
	
	@GetMapping("/menu")
	public ResponseEntity<List<MenuTB>> findMenus () {
		List<MenuTB> menus = menuTBSerivce.findMenus();
		
		return ResponseEntity.ok(menus);
	}
	
}
