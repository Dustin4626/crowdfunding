package com.dustin.crowd.service.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dustin.crowd.entity.Role;

public interface RoleService {

	Page<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

	void saveRole(Role role);

	void updateRole(Role role);

	void removeRole(List<Long> roleIdList);
}
