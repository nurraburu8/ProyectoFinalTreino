package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAdminDao;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Rol;


@Service
public class AdminServiceImpl implements IAdminService{
	
	@Autowired
	private IAdminDao adminDao;

	@Override
	@Transactional
	public void save(Admin admin) {
		admin.setRol(Rol.ADMIN);
		adminDao.save(admin);
	}

	@Override
	@Transactional(readOnly=true)
	public Admin checkAdminLogin(Admin admin) {
		return (Admin) adminDao.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
	}

	@Override
	@Transactional
	public Admin updateAdmin(Admin admin) {
		return (Admin) adminDao.save(admin);
	}

	@Override
	@Transactional
	public void deleteAdmin(Long id) {
		adminDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Admin findAdmin(Admin admin) {
		return adminDao.findByEmail(admin.getEmail());
	}

	@Override
	public Admin findAdminByEmail(String email) {
		// TODO Auto-generated method stub
		return adminDao.findByEmail(email);
	}

}
