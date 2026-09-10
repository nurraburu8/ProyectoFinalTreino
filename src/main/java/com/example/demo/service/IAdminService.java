package com.example.demo.service;



import com.example.demo.entity.Admin;

public interface IAdminService {
	
	public void save(Admin admin);
	
	public Admin findAdmin(Admin admin);
	
	public Admin findAdminByEmail(String email);
		
	public Admin checkAdminLogin(Admin admin);
		
	public Admin updateAdmin(Admin admin);
		
	public void deleteAdmin(Long id);
}
