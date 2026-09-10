package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Profesor;
import com.example.demo.entity.Review;
import com.example.demo.entity.Usuario;
import com.example.demo.service.IProfesorService;
import com.example.demo.service.IReviewService;

@RestController
@RequestMapping("/api")
public class ReviewRestController {
	
	@Autowired
	private IReviewService reviewService;
	
	@Autowired
	private IProfesorService profesorService;
	
	@GetMapping("/reviews")
	public ResponseEntity<?> listaReviews(){
		List<Review> listaReviews = reviewService.findAll();
		if(listaReviews!=null) {
		if(listaReviews.size()!=0) {
			return new ResponseEntity<>(listaReviews, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/reviews_profesor/{profesor_id}")
	public ResponseEntity<?> verReviewsProfesor(@PathVariable(value="profesor_id")Long idProfesor){
		List<Review> listaReviews = reviewService.getReviewProfesor(idProfesor);
		if(listaReviews!=null) {
		if(listaReviews.size()!=0) {
			return new ResponseEntity<>(listaReviews, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	}
	
	@GetMapping("/reviews_usuario/{usuario_id}")
	public ResponseEntity<?> verReviewsUsuario(@PathVariable(value="usuario_id")Long idUsuario){
		List<Review> listaReviews = reviewService.getReviewUsuario(idUsuario);
		if(listaReviews!=null) {
		if(listaReviews.size()!=0) {
			return new ResponseEntity<>(listaReviews, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	}
	
	@DeleteMapping("/delete_review/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable(value="id")Long id){
		Review reviewDb = null;
		reviewDb = reviewService.findById(id);
		if(reviewDb != null) {
			reviewService.deleteReview(id);
		    return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update_review/{id}")
	public ResponseEntity<?> updateReview(@PathVariable(value="id")Long id, @RequestBody Review review){
		Review reviewDb = reviewService.findById(id);
		if(reviewDb != null) {
			review.setId(id);
			reviewDb.updateReview(review);
			reviewService.saveReview(reviewDb);
		    return new ResponseEntity<Review>(reviewDb, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/crear_review")
	public ResponseEntity<?> agregarReview(@RequestBody Review review){
		if(review==null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		reviewService.saveReview(review);
		Profesor profesorDb = profesorService.findById(review.getProfesorId());
		profesorDb.setRating();
		profesorService.save(profesorDb);
			return new ResponseEntity<>(review, HttpStatus.CREATED);
	}
	
	@GetMapping("/find_review/{id}")
	public ResponseEntity<?> encontrarReviewPorId(@PathVariable(value="id")Long id){
		
		Review reviewDb = reviewService.findById(id);
		if(reviewDb==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(reviewDb, HttpStatus.OK);
	}
	}
	
	@GetMapping("/find_review")
	public ResponseEntity<?> encontrarReviewPorUsuarioIdYEntrenadorId(@RequestBody Review review){
		Optional<Review> reviewDb = reviewService.findByUsuarioIdAndProfesorId(review.getUsuarioId(), review.getProfesorId());
		if(reviewDb.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(reviewDb.get(), HttpStatus.OK);
	}
	}
}
