package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="usuario")
@JsonTypeName("usuario")
public class Usuario extends Persona implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public Usuario() {

	}
	
	@Column(name="profesor_id")
	private Long profesorId;
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	 * 
	 * @Column (length=60, name="nombre", nullable = false) private String nombre;
	 * 
	 * @Column(length=60, unique=true, nullable = false) private String email;
	 * 
	 * @Column(length=60, nullable = false) private String password;
	 */
	@Column(length=20)
	private String telefono;
	
   @Lob
    @Column(length = Integer.MAX_VALUE)	   	
    private byte[] foto;
	
	private String objetivos;
	
	private String preferencias;
	
    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USER;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name="usuario_id", referencedColumnName = "id")
	private List<Review> reviews = new ArrayList<>();
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY) //revisar cascadeType
	@JoinColumn(name="usuario_id", referencedColumnName = "id")
	private List<Profesor> entrenadoresFavoritos = new ArrayList<>();
	
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
	/*
	 * public Long getId() { return id; }
	 * 
	 * 
	 * public void setId(Long id) { this.id = id; }
	 * 
	 * public String getNombre() { return nombre; }
	 * 
	 * public void setNombre(String nombre) { this.nombre = nombre; }
	 * 
	 * 
	 * public String getEmail() { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; } public String
	 * getPassword() { return password; }
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 */

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}





	public List<Profesor> getEntrenadoresFavoritos() {
		return entrenadoresFavoritos;
	}


	public void setEntrenadoresFavoritos(List<Profesor> entrenadoresFavoritos) {
		this.entrenadoresFavoritos = entrenadoresFavoritos;
	}


	public String getObjetivos() {
		return objetivos;
	}


	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}


	public String getPreferencias() {
		return preferencias;
	}


	public void setPreferencias(String preferencias) {
		this.preferencias = preferencias;
	}


	public Rol getRol() {
		return rol;
	}


	public void setRol(Rol rol) {
		this.rol = rol;
	}


	public void updateUsuario(Usuario usuario) {


	    if (usuario.getNombre() != null) {
	        this.setNombre(usuario.getNombre());
	    }
	    if (usuario.getEmail() != null) {
	        this.setEmail(usuario.getEmail());
	    }
	    if (usuario.getPassword() != null) {
	        this.setPassword(usuario.getPassword());
	    }
	    if (usuario.getTelefono() != null) {
	        this.setTelefono(usuario.getTelefono());
	    }
//	    if (usuario.getFoto() != null) {
//	        this.setFoto(usuario.getFoto());
//	    }
	    if (usuario.getObjetivos() != null) {
	        this.setObjetivos(usuario.getObjetivos());
	    }
	    if (usuario.getPreferencias() != null) {
	        this.setPreferencias(usuario.getPreferencias());
	    }
	    if (usuario.getReviews() != null) {
	        this.setReviews(usuario.getReviews());
	    }
	    if (usuario.getEntrenadoresFavoritos() != null) {
	        this.setEntrenadoresFavoritos(usuario.getEntrenadoresFavoritos());
	    }
	    if (usuario.getRol() != null) {
	        this.setRol(usuario.getRol());
	    }
	}



	
}
