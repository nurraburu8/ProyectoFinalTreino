package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IReviewDao;
import com.example.demo.entity.Review;
import com.example.demo.entity.Usuario;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	@Autowired
	private IReviewDao reviewDao;

	@Override
	@Transactional
	public List<Review> findAll() {
		return (List<Review>) reviewDao.findAll();
	}

	@Override
	public void saveReview(Review review) {
		reviewDao.save(review);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Review> getReviewProfesor(Long id) {
		return (List<Review>) reviewDao.findByProfesorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Review> getReviewUsuario(Long id) {
		return (List<Review>) reviewDao.findByUsuarioId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Review> findReview(Review review) {
		return (Optional<Review>) reviewDao.findByProfesorIdAndUsuarioId(review.getProfesorId(), review.getUsuarioId());
	}

	@Override
	@Transactional
	public void deleteReview(Review review) {
		reviewDao.deleteById(review.getId());
		
	}

	@Override
	@Transactional
	public void deleteAllReviews() {
		reviewDao.deleteAll();
		
	}

	@Override
	@Transactional
	public Review updateReview(Review review) {
		return (Review) reviewDao.save(review);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Review> findByUsuarioIdAndProfesorId(Long usuario_id, Long profesor_id) {
		return (Optional<Review>) reviewDao.findByProfesorIdAndUsuarioId(usuario_id, profesor_id);
	}

	@Override
	@Transactional(readOnly=true)
	public Review findById(Long id) {
		return (Review) reviewDao.findById(id).orElse(null);
	}

	@Override
	public void deleteReview(Long id) {
		reviewDao.deleteById(id);
		
	}

}
