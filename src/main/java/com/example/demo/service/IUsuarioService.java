package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public void saveUsuario(Usuario usuario);
	
	public List<Usuario> getUsuarioProfesor(Long id);
	
	public Usuario findUsuario(Usuario usuario);
	
	public Usuario findUsuarioByEmail(String email);
	
	public Usuario checkUsuarioLogin(Usuario usuario);
	
	public void deleteUsuario(Usuario usuario);
	
	public void deleteAllUsuarios();

	public Usuario updateUsuario(Usuario usuario);
	
	public Optional<Usuario> findUsuarioById(Long usuario_id);
	
	public void deleteUsuario(Long id);
	
	public Usuario findById(Long id);

}
