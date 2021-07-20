package com.allopathy.management.resource;

import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allopathy.management.data.model.MaterialMedica;
import com.allopathy.management.data.model.Repertory;
import com.allopathy.management.repository.MaterialMedicaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MaterialMedicaResource {
	
	@Autowired
	MaterialMedicaRepository materialMedicaRepository;

	@GetMapping("/materialMedica")
	public MaterialMedica getMaterialMedicaEntry() {
		log.debug("Getting Entry for Material medica : {}", "yo");
		Repertory a = new Repertory();
		a.setGeneral("String");
		a.setPointer("String");
		return new MaterialMedica("String","String",a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,a,"String");
	}
	
	@PostMapping("/materialMedica")
	public ResponseEntity<MaterialMedica> createMaterialMedicaEntry(
			@RequestBody MaterialMedica materialMedica) {
		materialMedica.setId();
		log.debug("Creating Entry for Material medica : {}", materialMedica);
		return new ResponseEntity<>( materialMedicaRepository.save(materialMedica),HttpStatus.OK);
	}
	
	
}
