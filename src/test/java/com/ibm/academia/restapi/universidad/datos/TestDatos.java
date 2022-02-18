package com.ibm.academia.restapi.universidad.datos;

import java.math.BigDecimal;

import com.ibm.academia.restapi.universidad.enumeradores.TipoEmpleado;
import com.ibm.academia.restapi.universidad.enumeradores.TipoPizarron;
import com.ibm.academia.restapi.universidad.modelo.entidades.Alumno;
import com.ibm.academia.restapi.universidad.modelo.entidades.Aula;
import com.ibm.academia.restapi.universidad.modelo.entidades.Carrera;
import com.ibm.academia.restapi.universidad.modelo.entidades.Direccion;
import com.ibm.academia.restapi.universidad.modelo.entidades.Empleado;
import com.ibm.academia.restapi.universidad.modelo.entidades.Pabellon;
import com.ibm.academia.restapi.universidad.modelo.entidades.Persona;
import com.ibm.academia.restapi.universidad.modelo.entidades.Profesor;

public class TestDatos {
	
	public static Aula aula1() {
		return new Aula(null, 12, "236", 30, TipoPizarron.PIZARRA_TIZA, "eneri");
	}
	
	public static Aula aula2() {
		return new Aula(null, 21, "500", 40, TipoPizarron.PIZARRA_TIZA, "eneri");
	}

	public static Carrera carrera1() {
		return new Carrera(null, "Ingenieria en computacion", 60, 5, "eneri");
	}
	
	public static Carrera carrera2() {
		return new Carrera(null, "Administracion", 50, 4, "eneri");
	}
	
	public static Persona empleado1() {
		return new Empleado(null, "Jorge", "Perez", "1236547896", "eneri", new Direccion(), new BigDecimal("25000.30"), TipoEmpleado.ADMINISTRATIVO);
	}
	
	public static Persona empleado2() {
		return new Empleado(null, "Cristian", "Morales", "1246547896", "eneri", new Direccion(), new BigDecimal("25007.30"), TipoEmpleado.MANTENIMIENTO);
	}
	
	public static Persona profesor1(){
		return new Profesor(null, "Erick", "Adame", "1239547896", "eneri", new Direccion(), new BigDecimal("28000.30"));
	}
	
	public static Persona profesor2(){
		return new Profesor(null, "Santiago", "Fernandez", "1239549896", "eneri", new Direccion(), new BigDecimal("26000.30"));
	}
	
	public static Persona alumno1(){
		return new Alumno(null, "Cristopher", "Valencia", "1239547888", "eneri", new Direccion());
	}
	
	public static Persona alumno2(){
		return new Alumno(null, "Armando", "Trejo", "1239337896", "eneri", new Direccion());
	}
	
	public static Pabellon pabellon1() {
		return new Pabellon(null, (double) 200, "Pabellon 1", new Direccion("Mariano", "45", "56523", "3", "1", "Mexico"), "eneri");
	}
	
	public static Pabellon pabellon2() {
		return new Pabellon(null, (double) 200, "Pabellon 2", new Direccion("Guadalupe", "14", "89523", "2", "2", "Puebla"), "eneri");
	}
	
}
