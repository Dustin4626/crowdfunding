package com.dustin.crowd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dustin.crowd.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Page<Role> findByNameLike(String keyword, Pageable pageable);

}
