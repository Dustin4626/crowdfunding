package com.dustin.crowd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dustin.crowd.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Page<Role> findByNameLike(String keyword, Pageable pageable);

	@Query(value = "select id,name from t_role where id in (select role_id from inner_admin_role where admin_id=?1)"
			,nativeQuery = true)
	List<Role> getAssignedRole(Long adminId);

	@Query(value = "select id,name from t_role where id not in (select role_id from inner_admin_role where admin_id=?1)"
			,nativeQuery = true)
	List<Role> getUnAssignedRole(Long adminId);

}
