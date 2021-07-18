package com.epam.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.library.model.Book;
import com.epam.library.model.User;
import com.epam.library.model.UserBookAssociationDetail;
import com.epam.library.services.LibraryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/library")
@Api(tags = "Library Management RESTful Services", description = "Controller for library service")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	@ApiOperation(value = "List of all books")
	@GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Book>> getAllBooks(){
		return new ResponseEntity<List<Book>>(libraryService.getAllBooks(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Get a book's details")
	@GetMapping(value="/books/{book_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> getBookById(@PathVariable ("book_id") Long id){
		return new ResponseEntity<Book>(libraryService.getBookById(id), HttpStatus.OK);
	}
	
	@ApiOperation(value="Add a new book")
	@PostMapping(value="/books", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addBook(@RequestBody Book book, UriComponentsBuilder builder) {
		libraryService.addBook(book, builder);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Delete a book")
	@DeleteMapping(value="/books/{book_id}")
	public ResponseEntity<Void> deleteBookById(@PathVariable("book_id") Long id ) {
		boolean isBookDisAssociated=libraryService.deleteBookAssociationWithUser(id);
		if(isBookDisAssociated) {
			libraryService.deleteBookById(id);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@ApiOperation(value="List all the users")
	@GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<List<User>>(libraryService.getAllUsers(), HttpStatus.OK);
	}
	
	@ApiOperation(value="View user profile with all issued books")
	@GetMapping(value="users/{user_id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserBookAssociationDetail> getAllBooksAssociatedWithUser(@PathVariable("user_id") Long userId){
		return new ResponseEntity<UserBookAssociationDetail>(libraryService.getUserProfileWithIssuedBooks(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value="Add a user")
	@PostMapping(value="/users",consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addUser(@RequestBody User user){
		return libraryService.addUser(user);
	}
	
	@ApiOperation(value="Release all books for that user_id in library table and delete user")
	@DeleteMapping("/users/{user_id}")
	public ResponseEntity<Void>deleteUser(@PathVariable("user_id") Long id){
		libraryService.deleteUserById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@ApiOperation(value="Update user's account details")
	@PutMapping(value="/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<User> updateUser(@PathVariable("user_id") Long id, @RequestBody User user) {
		return new ResponseEntity<User>(libraryService.updateUser(id, user), HttpStatus.OK);
	}
	
	@ApiOperation(value="Issue a book to a user")
	@PostMapping(value="/users/{user_id}/books/{book_id}")
	public ResponseEntity<Void> issueBook(@PathVariable("user_id") Long userId, @PathVariable("book_id") Long bookId){
		boolean isBookIssued = libraryService.issueBook(userId, bookId);
		HttpStatus status = HttpStatus.OK;
		if(!isBookIssued) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Void>(status);
	}
	
	@ApiOperation("Release the book for the user_id")
	@DeleteMapping("/users/{user_id}/books/{book_id}")
	public ResponseEntity<Void> bookReturned(@PathVariable("user_id") Long userId, @PathVariable("book_id") Long bookId) {
		boolean isBookReturned = libraryService.bookReturnedToLibrary(userId, bookId);
		HttpStatus status = HttpStatus.OK;
		if(!isBookReturned) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Void>(status);
	}
	
	

	
}
