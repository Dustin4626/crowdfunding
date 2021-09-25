package com.dustin.crowd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dustin.crowd.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

	List<Admin> findByLoginAcct(String loginAcct);

	Page<Admin> findByUserNameLike(String userName, Pageable pageable);

}
