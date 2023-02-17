package com.juliohenrique.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juliohenrique.data.vo.v1.PersonVO;
import com.juliohenrique.services.PersonServices;
import com.juliohenrique.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name="People", description = "Endpoints for Managing People")
public class PersonController {

	@Autowired
	private PersonServices service;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
			produces ={ 
				MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YML
			})
	@Operation(summary = "Finds a Person", 
		description = "Finds a Person",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "Success", 
				responseCode= "200", 
				content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PersonVO.class)
						)
			),
			@ApiResponse(description = "No Content", responseCode= "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public PersonVO finById(
			@PathVariable(value = "id") Long id){
		return service.findById(id);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(
		value = "/findPersonByName/{firstName}",
		produces = { 
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
	})
	@Operation(summary = "Finds People by name", 
		description = "Finds People by name",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "Success", 
				responseCode= "200", 
				content = { 
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
					)}
			),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByName(
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction)
				? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonByName(firstName, pageable));
	}

	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(
		produces = { 
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
	})
	@Operation(summary = "Finds all People", 
		description = "Finds all People",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "Success", 
				responseCode= "200", 
				content = { 
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
					)}
			),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "12") Integer size,
		@RequestParam(value = "direction", defaultValue = "asc") String direction
	) {

		var sortDirection = "asc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

		return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(
		consumes ={ 
			MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML
	},
			produces = { 
				MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YML
			})
	@Operation(summary = "Adds new Person", 
		description = "Adds new Person by passing in a JSON, XML or YML representation of the person!",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "Success", 
				responseCode= "200", 
				content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PersonVO.class)
						)
			),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public PersonVO create(
			@RequestBody PersonVO person){
		return service.create(person);
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PutMapping(
			consumes = { 
				MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YML
			},
			produces = { 
				MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YML
			})
	@Operation( summary = "Updated a Person", 
		description = "Updates a Person by passing in a JSON, XML or YML representation of the person!",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "Updated", 
				responseCode= "200", 
				content = { 
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
					)}
			),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public PersonVO update(
			@RequestBody PersonVO person){
		return service.update(person);
	}


	@PatchMapping(value = "/{id}",
			produces ={ 
				MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YML
			})
	@Operation(summary = "Disabled a specific Person by your ID", 
		description = "Disabled a specific Person by your ID",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "Success", 
				responseCode= "200", 
				content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PersonVO.class)
						)
			),
			@ApiResponse(description = "No Content", responseCode= "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public PersonVO disabledPerson(@PathVariable(value = "id") Long id){
		return service.disabledPerson(id);
	}


	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping(value = "/{id}")
	@Operation( summary = "Deletes a Person", 
		description = "Deletes a Person by passing in a JSON, XML or YML representation of the person!",
		tags = {"People"},
		responses = {
			@ApiResponse(description = "No Content", 
				responseCode= "204", 
				content = @Content
			),
			@ApiResponse(description = "Bad Request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode= "500", content = @Content),
		}
	)
	public ResponseEntity<?> delete(
			@PathVariable(value = "id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
