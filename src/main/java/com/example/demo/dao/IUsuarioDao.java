package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	public List<Usuario> findByProfesorId(Long id);
	
	public Usuario findByEmail(String email);
	
	public Usuario findByEmailAndPassword(String email, String password);
	
	public Optional<Usuario> findById (Long id);
}
