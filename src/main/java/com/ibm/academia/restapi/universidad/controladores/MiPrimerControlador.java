package com.ibm.academia.restapi.universidad.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restapi")
public class MiPrimerControlador {
	
	@GetMapping
	public String holaMundo() {
		return "Hola mundo desde REST-API";
	}

}
