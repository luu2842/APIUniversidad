package com.ibm.academia.restapi.universidad.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "carreras", schema = "universidad")
public class Carrera implements Serializable{

	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotNull(message = "No puede ser nulo")
	 @NotEmpty(message = "No puede estar vacio")
	 @Column(name = "nombre", nullable = false, unique = true, length = 80)
	 private String nombre;
	 
	 @Positive(message = "El valor debe de ser mayor a 0")
	 @Max(value= 60, message = "No puede ser mayor a 60 materias")
	 @Min(value= 40, message = "No puede ser menor a 40")
	 @Column(name = "cantidad_materias")
	 private Integer cantidadmaterias;
	 
	 @Positive(message = "El valor debe de ser mayor a 0")
	 @Max(value= 5, message = "No puede ser mayor a 5 anios")
	 @Min(value= 3, message = "No puede ser menor a 3 anios")
	 @Column(name = "cantidad_anios")
	 private Integer cantidadAnios;
	 
	 @NotNull(message = "No puede ser nulo")
	 @NotEmpty(message = "No puede estar vacio")
	 @Column(name = "usuario_creacion", nullable = false)
	 private String usuarioCreacion;
		
	 @Column(name = "fecha_creacion", nullable = false)
	 private Date fechaCreacion;
		
	 @Column(name = "fecha_modificacion")
	 private Date fechaModificacion;
	 
	 @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
	 @JsonIgnoreProperties({"carrera"})
	 private Set<Alumno> alumnos;
	 
	 @ManyToMany(mappedBy = "carreras", fetch = FetchType.LAZY)
	 @JsonIgnoreProperties({"carreras"})
	 private Set<Profesor> profesores;
	 
	 public Carrera(Long id, String nombre, Integer cantidadmaterias, Integer cantidadAnios, String usuarioCreacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidadmaterias = cantidadmaterias;
		this.cantidadAnios = cantidadAnios;
		this.usuarioCreacion = usuarioCreacion;
	}
	 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Carrera [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", cantidadmaterias=");
		builder.append(cantidadmaterias);
		builder.append(", cantidadAnios=");
		builder.append(cantidadAnios);
		builder.append(", usuarioCreacion=");
		builder.append(usuarioCreacion);
		builder.append(", fechaCreacion=");
		builder.append(fechaCreacion);
		builder.append(", fechaModificacion=");
		builder.append(fechaModificacion);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrera other = (Carrera) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}

	@PrePersist
	private void antesPersistir() {
		this.fechaCreacion = new Date();
	}
	
	@PreUpdate
	private void antesActualizar() {
		this.fechaModificacion = new Date();
	}
	
	private static final long serialVersionUID = -2579040836580509358L;
}
