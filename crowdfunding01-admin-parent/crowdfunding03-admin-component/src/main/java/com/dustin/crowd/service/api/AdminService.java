package com.dustin.crowd.service.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dustin.crowd.entity.Admin;

public interface AdminService {
	
	void saveAdmin(Admin admin);

	List<Admin> getAll();

	Admin getAdminByLoginAcct(String loginAcct, String userPswd);

	Page<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

	Admin getAdminById(Long adminId);

	void update(Admin admin);

	void remove(Long adminId);

	void saveAdminRoleRelationship(Integer adminId, List<Long> roleIdList);

	Admin getAdminByLoginAcct(String username);

}
