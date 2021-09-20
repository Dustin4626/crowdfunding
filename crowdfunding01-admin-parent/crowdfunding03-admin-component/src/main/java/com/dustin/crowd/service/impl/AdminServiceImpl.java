package com.dustin.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dustin.crowd.entity.Admin;
import com.dustin.crowd.repository.AdminRepository;
import com.dustin.crowd.service.api.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public void saveAdmin(Admin admin) {
		
		adminRepository.save(admin);
		
		// throw new RuntimeException();
		
	}

	@Override
	public List<Admin> getAll() {
		return adminRepository.findAll();
	}

}