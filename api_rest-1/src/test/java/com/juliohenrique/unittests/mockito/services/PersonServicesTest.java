package com.juliohenrique.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.juliohenrique.data.vo.v1.PersonVO;
import com.juliohenrique.exceptions.RequiredObjectIsNullException;
import com.juliohenrique.model.Person;
import com.juliohenrique.repositories.PersonRepository;
import com.juliohenrique.services.PersonServices;
import com.juliohenrique.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	MockPerson input;
	
	@InjectMocks
	private PersonServices service;
	
	@Mock
	private PersonRepository repository;
	
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

		assertEquals("Addres Test1", result.getAdress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		/* 
		assertEquals("First Name Test1 ", result.getFirstName());
		assertEquals("Last Name Test1 ", result.getLastName());
		assertEquals("Female", result.getGender());
		assertEquals("Addres Test1 ", result.getAdress());*/
		
	}
	
	

	@Test
	void testCreate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);

		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

		assertEquals("Addres Test1", result.getAdress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	@Test
	void testCreateWithNUllPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
			service.create(null);
		});
		String expetedMessage = "It is not allowed to persist a null object!";
		String actualMassage = exception.getMessage();


		assertNotNull(actualMassage.contains(expetedMessage));
	}

	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);

		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

		assertEquals("Addres Test1", result.getAdress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());

	}

	@Test
	void testUpdateWithNUllPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
			service.update(null);
		});
		String expetedMessage = "It is not allowed to persist a null object!";
		String actualMassage = exception.getMessage();


		assertNotNull(actualMassage.contains(expetedMessage));
	}

	@Test
	void testDelete() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
		
	}

}
