package com.dustin.crowd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dustin.crowd.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Page<Role> findByNameLike(String keyword, Pageable pageable);

}
