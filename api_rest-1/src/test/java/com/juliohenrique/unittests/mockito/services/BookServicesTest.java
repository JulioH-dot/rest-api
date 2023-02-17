package com.juliohenrique.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.juliohenrique.data.vo.v1.BooksVO;
import com.juliohenrique.exceptions.RequiredObjectIsNullException;
import com.juliohenrique.model.Books;
import com.juliohenrique.repositories.BooksRepository;
import com.juliohenrique.services.BooksServices;
import com.juliohenrique.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input;
	
	@InjectMocks
	private BooksServices service;
	
	@Mock
	private BooksRepository repository;
	
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));

		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertNotNull(result.getLaunchDate());
		assertEquals(25D, result.getPrice());
		/* 
		assertEquals("First Name Test1 ", result.getFirstName());
		assertEquals("Last Name Test1 ", result.getLastName());
		assertEquals("Female", result.getGender());
		assertEquals("Addres Test1 ", result.getAdress());*/
		
	}
	
	

	@Test
	void testCreate() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);

		Books persisted = entity;
		persisted.setId(1L);
		
		BooksVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));

		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertNotNull( result.getLaunchDate());
		assertEquals(25D, result.getPrice());
	}
	
	@Test
	void testCreateWithNUllBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
			service.create(null);
		});
		String expetedMessage = "It is not allowed to persist a null object!";
		String actualMassage = exception.getMessage();


		assertNotNull(actualMassage.contains(expetedMessage));
	}

	@Test
	void testUpdate() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);

		Books persisted = entity;
		persisted.setId(1L);
		
		BooksVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		when(repository.save(entity)).thenReturn(persisted);
		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));

		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());
		assertNotNull( result.getLaunchDate());
		assertEquals(25D, result.getPrice());

	}

	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, ()->{
			service.update(null);
		});
		String expetedMessage = "It is not allowed to persist a null object!";
		String actualMassage = exception.getMessage();


		assertNotNull(actualMassage.contains(expetedMessage));
	}

	@Test
	void testDelete() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
		
	}

}
