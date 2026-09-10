package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IUsuarioDao;
import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;


@Service
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void saveUsuario(Usuario usuario) {
		usuario.setRol(Rol.USER);
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> getUsuarioProfesor(Long id) {
		return (List<Usuario>) usuarioDao.findByProfesorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findUsuario(Usuario usuario) {
		return (Usuario) usuarioDao.findByEmail(usuario.getEmail());
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario checkUsuarioLogin(Usuario usuario) {
		return (Usuario) usuarioDao.findByEmailAndPassword(usuario.getEmail(), usuario.getPassword());
	}

	@Override
	@Transactional
	public void deleteUsuario(Usuario usuario) {
		usuarioDao.deleteById(usuario.getId());
		
	}

	@Override
	@Transactional
	public void deleteAllUsuarios() {
		usuarioDao.deleteAll();
		
	}

	@Override
	@Transactional
	public Usuario updateUsuario(Usuario usuario) {
		return (Usuario) usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> findUsuarioById(Long usuario_id) {
		return (Optional<Usuario>) usuarioDao.findById(usuario_id);
	}

	@Override
	@Transactional
	public void deleteUsuario(Long id) {
		usuarioDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findById(Long id) {
		return (Usuario) usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario findUsuarioByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioDao.findByEmail(email);
	}

}
