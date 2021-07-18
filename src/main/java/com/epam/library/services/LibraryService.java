package com.epam.library.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.epam.library.common.BookServiceFeignClient;
import com.epam.library.common.UserServiceFeignClient;
import com.epam.library.model.Book;
import com.epam.library.model.Library;
import com.epam.library.model.User;
import com.epam.library.model.UserBookAssociationDetail;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@Service
public class LibraryService {

	private final Logger logger = LoggerFactory.getLogger(LibraryService.class);
	private final static  int QUERY_LIMIT=10;
	@Value("${dynamodb.gsi-name}")
	private String gsiName;
	@Autowired
	private UserServiceFeignClient userServiceFiegnClient;

	@Autowired
	private BookServiceFeignClient bookServiceFeignClient;

	@Autowired
	AmazonDynamoDB dynamdoDBClient;

	@HystrixCommand
	public List<Book> getAllBooks(){
		return bookServiceFeignClient.getAllBooks();
	}
	@HystrixCommand
	public Book getBookById(Long id) {
		return bookServiceFeignClient.getBookById(id);
	}

	@HystrixCommand
	public void addBook(Book book, UriComponentsBuilder builder) {
		bookServiceFeignClient.addBook(book);
	}
	@HystrixCommand
	public void deleteBookById(Long id) {
		bookServiceFeignClient.deleteBookById(id);
	}

	@HystrixCommand
	public List<User> getAllUsers(){
		return userServiceFiegnClient.getAllUsers();
	}

	@HystrixCommand
	public UserBookAssociationDetail getUserProfileWithIssuedBooks(Long userId){
		User user=userServiceFiegnClient.getUserById(userId);
		if(user!=null) {
			Library library= new Library();
			library.setUserid(userId);
			DynamoDBMapper mapper = new DynamoDBMapper(dynamdoDBClient);
			DynamoDBQueryExpression<Library> queryExpression= new DynamoDBQueryExpression<Library>()
					.withHashKeyValues(library)
					.withIndexName(gsiName)
					.withLimit(QUERY_LIMIT)
					.withConsistentRead(false);
			try {
				List<Library> libaryRecords=mapper.query(Library.class, queryExpression);
				List<Book> booksAssociatedWithUserId=libaryRecords
						.stream()
						.map(Library::getBookid)
						.map(bookId->bookServiceFeignClient.getBookById(bookId))
						.collect(Collectors.toList());


				return UserBookAssociationDetail
						.builder()
						.user(user)
						.issuedBooks(booksAssociatedWithUserId)
						.build();
			}catch(Exception e) {
				e.printStackTrace();
			}	
		}	
		return null;
	}



	@HystrixCommand
	public boolean deleteBookAssociationWithUser(Long bookId) {	
		try {
			DynamoDBMapper mapper = new DynamoDBMapper(dynamdoDBClient);
			Library library = new Library();
			library.setBookid(bookId);
			mapper.delete(library);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}


	@HystrixCommand
	public ResponseEntity<Void> addUser(User user) {
		return userServiceFiegnClient.createUser(user);

	} 


	@HystrixCommand
	public void deleteUserById(Long id) {
		if(removeBookUserAssociation(id,null)) {
			userServiceFiegnClient.deleteUserById(id);
		}
	}

	@HystrixCommand
	public User updateUser(Long id, User user) {
		return userServiceFiegnClient.updateUserById(id, user);
	}

	@HystrixCommand
	public boolean issueBook(Long userId, Long bookId) {

		try {
			DynamoDBMapper mapper = new DynamoDBMapper(dynamdoDBClient);
			Library library = new Library();
			library.setBookid(bookId);
			library.setUserid(userId);
			mapper.save(library);
			return true;

		}catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@HystrixCommand
	public boolean bookReturnedToLibrary(Long userId, Long bookId) {
		if(bookId==null) return false;
		return removeBookUserAssociation(userId, bookId);

	}


	public boolean removeBookUserAssociation(Long  userId, Long bookId) {
		try {
			Library library= new Library();
			library.setUserid(userId);
			DynamoDBMapper mapper = new DynamoDBMapper(dynamdoDBClient);

			DynamoDBQueryExpression<Library> queryExpression= new DynamoDBQueryExpression<Library>()
					.withHashKeyValues(library)
					.withIndexName(gsiName)
					.withLimit(QUERY_LIMIT)
					.withConsistentRead(false);
			List<Library> libaryRecords=mapper.query(Library.class, queryExpression);
				
			if(null!=libaryRecords && libaryRecords.size()>0) {
				if(bookId==null) {

					libaryRecords
					.stream()
					.map(Library::getBookid)
					.forEach(
							bkId->{
								library.setBookid(bkId);	
								mapper.delete(library);
							}
							);
				} else {
					libaryRecords
					.stream()
					.map(Library::getBookid)
					.filter(bkId->bkId==bookId)
					.forEach(id->{
						library.setBookid(id);
						mapper.delete(library);	
					});
				}


			}

			return true;
		}catch(Exception e) {
			System.out.println("got error :"+e.getMessage());
			e.printStackTrace();
		}	


		return false;

	}

}
