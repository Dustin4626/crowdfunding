package com.dustin.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dustin.crowd.entity.Role;
import com.dustin.crowd.repository.RoleRepository;
import com.dustin.crowd.service.api.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Page<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
		
		// 1.开启分页功能
		PageRequest pageable = PageRequest.of(pageNum, pageSize);
		
		// 2.执行查询
		Page<Role> roleList = roleRepository.findByNameLike(keyword+"%",pageable);
		
		// 3.封装为PageInfo对象返回
		return roleList;
	}

	@Override
	public void saveRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void updateRole(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void removeRole(List<Long> roleIdList) {
		roleRepository.deleteAllById(roleIdList);
	}

	@Override
	public List<Role> getAssignedRole(Integer adminId) {
		return roleRepository.getAssignedRole(adminId);
	}

	@Override
	public List<Role> getUnAssignedRole(Integer adminId) {
		return roleRepository.getUnAssignedRole(adminId);
	}

}
