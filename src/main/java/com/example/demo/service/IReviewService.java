package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Review;

public interface IReviewService {
	
	public List<Review> findAll();
	
	public void saveReview(Review review);
	
	public List<Review> getReviewProfesor(Long id);
	
	public List<Review> getReviewUsuario(Long id);
	
	public Optional<Review> findReview(Review review);
		
	public void deleteReview(Review review);
	
	public void deleteAllReviews();

	public Review updateReview(Review review);
	
	public Optional<Review> findByUsuarioIdAndProfesorId(Long usuario_id, Long profesor_id);
	
	public Review findById(Long id);
	
	public void deleteReview(Long id);
	
}
