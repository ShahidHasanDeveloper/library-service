package com.epam.library.common;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.model.Book;

@FeignClient("bookservice")
public interface BookServiceFeignClient {


	@GetMapping(value="/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAllBooks();


	@GetMapping(value="/books/{book_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Book getBookById(@PathVariable("book_id") Long id);
	
	@PostMapping(value="/books", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addBook(@RequestBody Book book) ;
	
	@DeleteMapping(value="/books/{book_id}")
	public void deleteBookById(@PathVariable("book_id") Long id ) ;
	
}
