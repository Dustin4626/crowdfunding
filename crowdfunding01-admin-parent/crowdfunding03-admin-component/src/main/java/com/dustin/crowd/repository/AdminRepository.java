package com.dustin.crowd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dustin.crowd.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	List<Admin> findByLoginAcct(String loginAcct);

	Page<Admin> findByUserNameLike(String userName, Pageable pageable);

	@Modifying
	@Query(value = "delete from inner_admin_role where admin_id=?1", nativeQuery = true)
	void deleteOldRelationship(Integer adminId);

	@Modifying
	@Query(value = "insert into inner_admin_role(admin_id,role_id) values (?1,?2)", nativeQuery = true)
	void insertNewRelationship(Integer adminId, Long roleIdList);
	
	@Query(value = "select * from t_admin where login_acct = :username", nativeQuery = true)
	List<Admin> getAdminByLoginAcct(String username);

}
