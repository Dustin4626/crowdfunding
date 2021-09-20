package com.dustin.crowd.service.api;

import java.util.List;

import com.dustin.crowd.entity.Admin;

public interface AdminService {
	
	void saveAdmin(Admin admin);

	List<Admin> getAll();

}
