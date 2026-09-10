package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Profesor;
import com.example.demo.entity.Review;

public interface IReviewDao extends CrudRepository<Review, Long>{
	
	public Optional<Review> findByProfesorIdAndUsuarioId(Long idUsuario, Long idProfesor);
		
	public List<Review> findByProfesorId(Long id);
	
	public List<Review> findByUsuarioId(Long id);
	
}
 