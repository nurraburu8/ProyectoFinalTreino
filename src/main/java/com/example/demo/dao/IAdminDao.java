package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Admin;

public interface IAdminDao extends CrudRepository<Admin, Long>{
	
	public Admin findByEmail(String email);
	
	public Admin findByEmailAndPassword(String email, String password);
	
	public Optional<Admin> findById (Long id);
	
	@Query("select p from Admin p where p.id=?1")
	public Admin findBySQL(Long id);
}
