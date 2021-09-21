package com.dustin.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dustin.crowd.constant.CrowdConstant;
import com.dustin.crowd.entity.Admin;
import com.dustin.crowd.exception.LoginAcctAlreadyInUseException;
import com.dustin.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
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

		// 生成当前系统时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(date);
		
		admin.setCreateTime(createTime);
		
		// 针对登录密码进行加密
		String source = admin.getUserPswd();
		String encoded = CrowdUtil.md5(source);
		admin.setUserPswd(encoded);
		// 执行保存，如果账户被占用会抛出异常
		try {
			adminRepository.save(admin);
		} catch (Exception e) {
			e.printStackTrace();
			// 检测当前捕获的异常对象，如果是 DuplicateKeyException 类型说明是账号重复导致的
			if (e instanceof DataIntegrityViolationException) {
				// 抛出自定义的 LoginAcctAlreadyInUseException 异常
				throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
			throw e;
		}
	}

	@Override
	public List<Admin> getAll() {
		return adminRepository.findAll();
	}

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

		List<Admin> list = adminRepository.findByLoginAcct(loginAcct);

		// 2.判断Admin对象是否为null
		if (list == null || list.size() == 0) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}

		if (list.size() > 1) {
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}

		Admin admin = list.get(0);

		// 3.如果Admin对象为null则抛出异常
		if (admin == null) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}

		// 4.如果Admin对象不为null则将数据库密码从Admin对象中取出
		String userPswdDB = admin.getUserPswd();

		// 5.将表单提交的明文密码进行加密
		String userPswdForm = CrowdUtil.md5(userPswd);

		// 6.对密码进行比较
		if (!Objects.equals(userPswdDB, userPswdForm)) {
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
		Page<Admin> list = adminRepository.findByUserNameLike(keyword, pageable);

		return list;
	}

	@Override
	public Admin getAdminById(Long adminId) {
		return adminRepository.getById(adminId);
	}

	@Override
	public void update(Admin admin) {
		Optional<Admin> findById = adminRepository.findById(admin.getId());
		Admin byId = findById.get();
		try {
			byId.setLoginAcct(admin.getLoginAcct());
			byId.setUserName(admin.getUserName());
			byId.setEmail(admin.getEmail());
//			BeanUtils.copyProperties(admin, byId);
			adminRepository.saveAndFlush(byId);
		} catch (Exception e) {
			e.printStackTrace();
			
//			logger.info("异常全类名="+e.getClass().getName());
			
			if(e instanceof DataIntegrityViolationException) {
				throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
		
	}

	@Override
	public void remove(Long adminId) {
		adminRepository.deleteById(adminId);
		
	}

}