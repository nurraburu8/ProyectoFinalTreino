package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="reviews")
public class Review implements Serializable{
	
	
	public Review() {
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="profesor_id")
	private Long profesorId;
	
	@Column(name="usuario_id")
	private Long usuarioId;
	
	@Column(length = 255)
	private String texto;
	
	private int rating;
	
	@Column(length = 255)
	private String respuesta;
	
	@Column(name= "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Long getProfesorId() {
		return profesorId;
	}

	public void setProfesorId(Long profesorId) {
		this.profesorId = profesorId;
	}

	public String getComentario() {
		return texto;
	}

	public void setComentario(String comentario) {
		this.texto = comentario;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	public void updateReview(Review review) {

	    if (review.getProfesorId() != null) {
	        this.setProfesorId(review.getProfesorId());
	    }
	    if (review.getUsuarioId() != null) {
	        this.setUsuarioId(review.getUsuarioId());
	    }
	    if (review.getTexto() != null) {
	        this.setTexto(review.getTexto());
	    }
	    if (review.getRating() != 0) {
	        this.setRating(review.getRating());
	    }
	    if (review.getRespuesta() != null) {
	        this.setRespuesta(review.getRespuesta());
	    }
	}


	
}
