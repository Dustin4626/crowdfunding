package com.dustin.crowd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dustin.crowd.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	List<Admin> findByLoginAcct(String loginAcct);

	Page<Admin> findByUserNameLike(String userName, Pageable pageable);

}
