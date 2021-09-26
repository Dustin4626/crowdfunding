package com.dustin.crowd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dustin.crowd.entity.Auth;
import com.dustin.crowd.repository.AuthRepository;
import com.dustin.crowd.service.api.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthRepository authRepository;

	@Override
	public List<Auth> getAll() {
		return authRepository.findAll();
	}

	@Override
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
		return authRepository.selectAssignedAuthIdByRoleId(roleId);
	}

	@Override
	public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
		
		// 1.获取roleId的值
		List<Integer> roleIdList = map.get("roleId");
		Integer roleId = roleIdList.get(0);
		
		// 2.删除旧关联关系数据
		authRepository.deleteOldRelationship(roleId);
		
		// 3.获取authIdList
		List<Integer> authIdList = map.get("authIdArray");
		
		// 4.判断authIdList是否有效
		if(authIdList != null && authIdList.size() > 0) {
			for(Integer authId : authIdList)
			authRepository.insertNewRelationship(roleId, authId);
		}
	}

}
