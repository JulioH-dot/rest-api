package com.juliohenrique.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.juliohenrique.controllers.BooksController;
import com.juliohenrique.data.vo.v1.BooksVO;
import com.juliohenrique.exceptions.RequiredObjectIsNullException;
import com.juliohenrique.exceptions.ResourceNotFoundException;
import com.juliohenrique.mapper.DozerMapper;
import com.juliohenrique.model.Books;
import com.juliohenrique.repositories.BooksRepository;

@Service
public class BooksServices {
	private Logger logger = Logger.getLogger(BooksServices.class.getName());

    @Autowired
    BooksRepository repository;

	@Autowired
	PagedResourcesAssembler<BooksVO> assembler;
	
	public PagedModel<EntityModel<BooksVO>> findAll(Pageable pageable) {
		
		logger.info("Finding all people!"); 
		
		var booksPage = repository.findAll(pageable);

		var booksVosPage = booksPage.map( p -> DozerMapper.parseObject(p, BooksVO.class));
		booksVosPage.map(
			p -> p.add(
				linkTo(methodOn(BooksController.class)
				.finById(p.getKey()))
				.withSelfRel()
			)
		);
		Link link = linkTo( 
			methodOn(BooksController.class)
				.findAll(pageable.getPageNumber(), 
					pageable.getPageSize(),
					"ASC"))
			.withSelfRel();

		return assembler.toModel(booksVosPage, link) ;
	}
    
	public List<BooksVO> findAll() {
		
		logger.info("Finding all people!"); 
		
		var persons = DozerMapper.parseListObjects(repository.findAll(), BooksVO.class);

		persons.stream().forEach(p -> p.add(linkTo(methodOn(BooksController.class).finById(p.getKey())).withSelfRel()));

		return persons;
	}
	
	public BooksVO findById(Long id) {
		
		logger.info("Finding one person");
		
		var entity = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		var vo = DozerMapper.parseObject(entity, BooksVO.class);
		vo.add(linkTo(methodOn(BooksController.class).finById(id)).withSelfRel());
		return vo ;
	}
	
	public BooksVO create(BooksVO book) {

		if (book == null)throw new RequiredObjectIsNullException();

		logger.info("Creating one book!");
		
		var entity = DozerMapper.parseObject(book, Books.class);
		
		var vo = DozerMapper.parseObject(
				repository.save(entity), BooksVO.class);
		vo.add(linkTo(methodOn(BooksController.class).finById(vo.getKey())).withSelfRel());
		return vo ;
	}
	
	
	public BooksVO update(BooksVO book) {

		if (book == null)throw new RequiredObjectIsNullException();

		logger.info("Update one book!");
		
		var entity = repository.findById(book.getKey())
			.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		
		var vo = DozerMapper.parseObject(
				repository.save(entity), BooksVO.class);
		vo.add(linkTo(methodOn(BooksController.class).finById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}
	

}
