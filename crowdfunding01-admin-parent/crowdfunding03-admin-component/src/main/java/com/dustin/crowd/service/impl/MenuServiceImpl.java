package com.dustin.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dustin.crowd.entity.Menu;
import com.dustin.crowd.repository.MenuRepository;
import com.dustin.crowd.service.api.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> getAll() {
		return menuRepository.findAll();
	}

	@Override
	public void saveMenu(Menu menu) {
		menuRepository.save(menu);
		
	}

	@Override
	public void updateMenu(Menu menu) {
		Menu byId = menuRepository.getById(menu.getId());
		menu.setPid(byId.getPid());
		menuRepository.save(menu);
		
	}

	@Override
	public void removeMenu(Integer id) {
		menuRepository.deleteById(id);
		
	}

	

	

}
