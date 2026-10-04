package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Resenas", description = "Resenas que los alumnos dejan a los entrenadores")
@RestController
@RequestMapping("/api")
public class ReviewRestController {
	
	@Autowired
	private IReviewService reviewService;
	
	@Autowired
	private IProfesorService profesorService;
	
	@Operation(summary = "Listar todas las resenas")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de resenas"),
	    @ApiResponse(responseCode = "404", description = "No hay resenas registradas")
	})
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
	
	@Operation(summary = "Listar las resenas recibidas por un entrenador")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de resenas"),
	    @ApiResponse(responseCode = "404", description = "El entrenador no tiene resenas")
	})
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
	
	@Operation(summary = "Listar las resenas escritas por un alumno")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de resenas"),
	    @ApiResponse(responseCode = "404", description = "El alumno no escribio resenas")
	})
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
	
	@Operation(summary = "Eliminar una resena")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Resena eliminada"),
	    @ApiResponse(responseCode = "404", description = "No existe una resena con ese id")
	})
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
	
	@Operation(summary = "Actualizar una resena")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Resena actualizada"),
	    @ApiResponse(responseCode = "404", description = "No existe una resena con ese id")
	})
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
	
	@Operation(summary = "Crear una resena",
	           description = "Registra la resena de un alumno sobre un entrenador.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Resena creada"),
	    @ApiResponse(responseCode = "400", description = "Datos invalidos")
	})
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
	
	@Operation(summary = "Buscar una resena por id")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Resena encontrada"),
	    @ApiResponse(responseCode = "404", description = "No existe una resena con ese id")
	})
	@GetMapping("/find_review/{id}")
	public ResponseEntity<?> encontrarReviewPorId(@PathVariable(value="id")Long id){
		
		Review reviewDb = reviewService.findById(id);
		if(reviewDb==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(reviewDb, HttpStatus.OK);
	}
	}
	
	@Operation(summary = "Buscar la resena de un alumno a un entrenador",
	           description = "Busca por la combinacion de alumno y entrenador enviada en el cuerpo.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Resena encontrada"),
	    @ApiResponse(responseCode = "404", description = "No existe esa resena")
	})
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
