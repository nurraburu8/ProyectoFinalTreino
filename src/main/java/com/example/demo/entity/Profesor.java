package com.example.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name="profesores")
@JsonTypeName("profesor")
	public class Profesor extends Persona implements Serializable {
		
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
	
	   public Profesor() {
	    }
		
		@Column(length=20)
		private String telefono;
		
		private String especialidad;
		
		@Column(length=30)
		private String ciudad;
		
	    //@Column(precision = 11, scale = 8)
	    private Double latitud;
	    
	    //@Column(precision = 11, scale = 8)
	    private Double longitud;
	    
	    @Column(length = 255)
	    private String mapLink;
	    
	    //@Column(precision = 12, scale = 2)
	    private Double precio;
	    
	    @Column(columnDefinition = "boolean default false")
		private boolean verificado;
	    
	    @Column(columnDefinition = "boolean default false")
		private boolean solicitoVerificado;
	    
	    @Column(columnDefinition = "LONGBLOB")
	    private byte[] fotoVerificacion;
		
		// @Column(precision = 3, scale = 2)
	    private Double rating;
	    
	    @Lob
	    @Column(length = Integer.MAX_VALUE)	    
	    private byte[] foto;
		
		@Column(length = 255)
		private String descripcion;
		
		@Column(length = 255)
		private String sobreMi;
		
		@Column(length = 255)
		private String experiencia;
		
		@Column(length = 255)
		private String servicios;
		
		@Column(length = 255)
		private String disponibilidad;
		
		@Column(length = 255)
		private String pricing;
		
	    @Enumerated(EnumType.STRING)
	    private PlanDeMembresia membresia;
	    
	    @ElementCollection
	    private Map<String, String> social;
	    
	    @ElementCollection
	    private Map<String, String> media;
	    
	    @Enumerated(EnumType.STRING)
	    private Rol rol = Rol.TRAINER;	
	    
	    @Column(columnDefinition = "boolean default false")
		private boolean perfilCompleto;
	    
	    @Enumerated(EnumType.STRING)
	    private PlanDeMembresia membresiaSolicitada;
	    
	    @Enumerated(EnumType.STRING)
	    private VisibilidadPerfil visibilidadPerfil;
	    
	    @Column(columnDefinition = "boolean default false")
		private boolean solicitoPublicacion;
	    
		@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
		@JoinColumn(name="profesor_id", referencedColumnName = "id")
		private List<Usuario> usuarios = new ArrayList<>();
		
		@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
		@JoinColumn(name="profesor_id", referencedColumnName = "id")
		private List<Review> reviews = new ArrayList<>();
		
		@Column(name= "create_at")
		@Temporal(TemporalType.DATE)
		private Date createAt;
		

		
		@PrePersist
		public void prePersist() {
			createAt = new Date();
		}
		
	

		public List<Usuario> getUsuarios() {
			return usuarios;
		}

		public void setUsuarios(List<Usuario> usuarios) {
			this.usuarios = usuarios;
		}



		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		/*
		 * public Long getId() { return id; }
		 * 
		 * public void setId(Long id) { this.id = id; }
		 * 
		 * public String getNombre() { return nombre; }
		 * 
		 * public void setNombre(String nombre) { super.g this.nombre = nombre; }
		 * 
		 * public String getEmail() { return email; }
		 * 
		 * public void setEmail(String email) { this.email = email; }
		 * 
		 * public String getPassword() { return password; }
		 * 
		 * public void setPassword(String password) { this.password = password; }
		 */

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

		public String getEspecialidad() {
			return especialidad;
		}

		public void setEspecialidad(String especialidad) {
			this.especialidad = especialidad;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public boolean isVerificado() {
			return verificado;
		}

		public void setVerificado(boolean verificado) {
			this.verificado = verificado;
		}


		public List<Review> getReviews() {
			return reviews;
		}


		public void setReviews(List<Review> reviews) {
			this.reviews = reviews;
		}


		public PlanDeMembresia getMembresia() {
			return membresia;
		}


		public void setMembresia(PlanDeMembresia membresia) {
			this.membresia = membresia;
		}


		public String getCiudad() {
			return ciudad;
		}


		public void setCiudad(String ciudad) {
			this.ciudad = ciudad;
		}


		public Double getLatitud() {
			return latitud;
		}


		public void setLatitud(Double latitud) {
			this.latitud = latitud;
		}


		public Double getLongitud() {
			return longitud;
		}


		public void setLongitud(Double longitud) {
			this.longitud = longitud;
		}


		public String getMapLink() {
			return mapLink;
		}


		public void setMapLink(String mapLink) {
			this.mapLink = mapLink;
		}


		public Double getPrecio() {
			return precio;
		}


		public void setPrecio(Double precio) {
			this.precio = precio;
		}


		public boolean isSolicitoVerificado() {
			return solicitoVerificado;
		}


		public void setSolicitoVerificado(boolean solicitoVerificado) {
			this.solicitoVerificado = solicitoVerificado;
		}


		public byte[] getFotoVerificacion() {
			return fotoVerificacion;
		}


		public void setFotoVerificacion(byte[] fotoVerificacion) {
			this.fotoVerificacion = fotoVerificacion;
		}


		public Double getRating() {
			return rating;
		}


		public void setRating() {
		        if (reviews.isEmpty()) {
		            this.rating = (double) 0;
		            return;
		        }

		        Double total = (double) 0;
		        for (Review review : reviews) {
		            total += review.getRating();
		        }

		        this.rating = total / reviews.size();
		    }
		
		public String getSobreMi() {
			return sobreMi;
		}

		public void setSobreMi(String sobreMi) {
			this.sobreMi = sobreMi;
		}

		public String getExperiencia() {
			return experiencia;
		}

		public void setExperiencia(String experiencia) {
			this.experiencia = experiencia;
		}

		public String getServicios() {
			return servicios;
		}

		public void setServicios(String servicios) {
			this.servicios = servicios;
		}

		public String getDisponibilidad() {
			return disponibilidad;
		}

		public void setDisponibilidad(String disponibilidad) {
			this.disponibilidad = disponibilidad;
		}

		public String getPricing() {
			return pricing;
		}

		public void setPricing(String pricing) {
			this.pricing = pricing;
		}

		public Map<String, String> getSocial() {
			return social;
		}

		public void setSocial(Map<String, String> social) {
			this.social = social;
		}

		public Map<String, String> getMedia() {
			return media;
		}

		public void setMedia(Map<String, String> media) {
			this.media = media;
		}

		public boolean isPerfilCompleto() {
			return perfilCompleto;
		}

		public void setPerfilCompleto(boolean perfilCompleto) {
			this.perfilCompleto = perfilCompleto;
		}

		public PlanDeMembresia getMembresiaSolicitada() {
			return membresiaSolicitada;
		}



		public void setMembresiaSolicitada(PlanDeMembresia membresiaSolicitada) {
			this.membresiaSolicitada = membresiaSolicitada;
		}




		public Rol getRol() {
			return rol;
		}



		public void setRol(Rol rol) {
			this.rol = rol;
		}



		public VisibilidadPerfil getVisibilidadPerfil() {
			return visibilidadPerfil;
		}



		public void setVisibilidadPerfil(VisibilidadPerfil visibilidadPerfil) {
			this.visibilidadPerfil = visibilidadPerfil;
		}



		public boolean isSolicitoPublicacion() {
			return solicitoPublicacion;
		}



		public void setSolicitoPublicacion(boolean solicitoPublicacion) {
			this.solicitoPublicacion = solicitoPublicacion;
		}




		private static final long serialVersionUID = 1L;



		public void updateProfesor(Profesor profesor) {
		 
		    if (profesor.getNombre() != null) {
		        setNombre(profesor.getNombre());
		    }
		    if (profesor.getEmail() != null) {
		        setEmail(profesor.getEmail());
		    }
		    if (profesor.getPassword() != null) {
		        setPassword(profesor.getPassword());
		    }
		    if (profesor.getTelefono() != null) {
		        setTelefono(profesor.getTelefono());
		    }
		    if (profesor.getEspecialidad() != null) {
		        setEspecialidad(profesor.getEspecialidad());
		    }
		    if (profesor.getCiudad() != null) {
		        setCiudad(profesor.getCiudad());
		    }
		    if (profesor.getLatitud() != null) {
		        setLatitud(profesor.getLatitud());
		    }
		    if (profesor.getLongitud() != null) {
		        setLongitud(profesor.getLongitud());
		    }
		    if (profesor.getMapLink() != null) {
		        setMapLink(profesor.getMapLink());
		    }
		    if (profesor.getPrecio() != null) {
		        setPrecio(profesor.getPrecio());
		    }
		    // Para campos booleanos, simplemente asigna el valor, sin necesidad de null check
		    setVerificado(profesor.isVerificado());
		    setSolicitoVerificado(profesor.isSolicitoVerificado());

		    if (profesor.getFotoVerificacion() != null) {
		        setFotoVerificacion(profesor.getFotoVerificacion());
		    }
//		    if (profesor.getFoto() != null) {
//		        setFoto(profesor.getFoto());
//		    }
		    if (profesor.getDescripcion() != null) {
		        setDescripcion(profesor.getDescripcion());
		    }
		    if (profesor.getSobreMi() != null) {
		        setSobreMi(profesor.getSobreMi());
		    }
		    if (profesor.getExperiencia() != null) {
		        setExperiencia(profesor.getExperiencia());
		    }
		    if (profesor.getServicios() != null) {
		        setServicios(profesor.getServicios());
		    }
		    if (profesor.getDisponibilidad() != null) {
		        setDisponibilidad(profesor.getDisponibilidad());
		    }
		    if (profesor.getPricing() != null) {
		        setPricing(profesor.getPricing());
		    }
		    if (profesor.getMembresia() != null) {
		        setMembresia(profesor.getMembresia());
		    }
		    if (profesor.getSocial() != null) {
		        setSocial(profesor.getSocial());
		    }
		    if (profesor.getMedia() != null) {
		        setMedia(profesor.getMedia());
		    }
		        setPerfilCompleto(profesor.isPerfilCompleto());
		    
		    if (profesor.getUsuarios() != null) {
		        setUsuarios(profesor.getUsuarios());
		    }
		    if (profesor.getReviews() != null) {
		        setReviews(profesor.getReviews());
		    }
		    if (profesor.getMembresiaSolicitada() != null) {
		        setMembresiaSolicitada(profesor.getMembresiaSolicitada());
		    }
		    if (profesor.getRol() != null) {
		        setRol(profesor.getRol());
		    }
		    if (profesor.getVisibilidadPerfil() != null) {
		        setVisibilidadPerfil(profesor.getVisibilidadPerfil());
		    }
		    
		        setSolicitoPublicacion(profesor.isSolicitoPublicacion());
		    
		}


		
}
