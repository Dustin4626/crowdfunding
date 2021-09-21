package com.dustin.crowd.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dustin.crowd.constant.CrowdConstant;
import com.dustin.crowd.entity.Admin;
import com.dustin.crowd.exception.LoginFailedException;
import com.dustin.crowd.repository.AdminRepository;
import com.dustin.crowd.service.api.AdminService;
import com.dustin.crowd.util.CrowdUtil;

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

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
		
		List<Admin> list = adminRepository.findByLoginAcct(loginAcct);
		
		// 2.判断Admin对象是否为null
		if(list == null || list.size() == 0) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		if(list.size() > 1) {
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}
		
		Admin admin = list.get(0);
		
		// 3.如果Admin对象为null则抛出异常
		if(admin == null) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 4.如果Admin对象不为null则将数据库密码从Admin对象中取出
		String userPswdDB = admin.getUserPswd();
		
		// 5.将表单提交的明文密码进行加密
//		String userPswdForm = CrowdUtil.md5(userPswd);
		
		// 6.对密码进行比较
		if(!Objects.equals(userPswdDB, userPswd)) {
			// 7.如果比较结果是不一致则抛出异常
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 8.如果一致则返回Admin对象
		return admin;
	}

	public Page<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
		
		// 1.调用PageHelper的静态方法开启分页功能
		PageRequest pageable = PageRequest.of(pageNum, pageSize);
		
		// 2.执行查询
		Page<Admin> list = adminRepository.findByUserNameLike(keyword,pageable);
		
		return list;
	}

}