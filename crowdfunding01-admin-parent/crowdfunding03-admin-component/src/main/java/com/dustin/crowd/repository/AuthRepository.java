package com.dustin.crowd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dustin.crowd.entity.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

	@Query(nativeQuery = true, value = "select auth_id from inner_role_auth where role_id=?1")
	List<Integer> selectAssignedAuthIdByRoleId(Integer roleId);

	@Modifying
	@Query(nativeQuery = true, value = "delete from inner_role_auth where role_id=?1")
	void deleteOldRelationship(Integer roleId);

	@Modifying
	@Query(nativeQuery = true, value = "insert into inner_role_auth(role_id,auth_id) values(?1,?2)")
	void insertNewRelationship(Integer roleId, Integer authId);

}
