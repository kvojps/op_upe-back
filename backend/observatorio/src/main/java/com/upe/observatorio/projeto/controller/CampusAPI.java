package com.upe.observatorio.projeto.controller;

import com.upe.observatorio.projeto.controller.response.CampusResponse;
import com.upe.observatorio.projeto.model.Campus;
import com.upe.observatorio.projeto.model.dto.CampusDTO;
import com.upe.observatorio.projeto.service.CampusService;
import com.upe.observatorio.utils.ObservatoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/campus")
@CrossOrigin
@RequiredArgsConstructor
public class CampusAPI {

	private final CampusService service;

	@PostMapping
	public ResponseEntity<CampusResponse> createCampus(
			@RequestBody @Valid CampusDTO campus,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(new CampusResponse(service.createCampus(campus)));
	}

	@GetMapping
	public ResponseEntity<List<CampusResponse>> readCampus() {
		return ResponseEntity
				.ok(service.readCampus().stream().map(CampusResponse::new).collect(Collectors.toList()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampusResponse> findCampusById(@PathVariable("id") Long id) {
		Campus campus = service.findCampusById(id);

		return ResponseEntity.ok(new CampusResponse(campus));
	}
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCampus(
			@RequestBody @Valid CampusDTO campus,
			@PathVariable Long id,
			BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			throw new ObservatoryException(String.join("; ", bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList()));
		}

		service.updateCampus(campus, id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCampus(@PathVariable("id") Long id) {
		service.deleteCampus(id);

		return ResponseEntity.noContent().build();
	}
}
