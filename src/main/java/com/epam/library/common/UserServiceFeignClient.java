package com.epam.library.common;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.model.User;

@FeignClient("userservice")
public interface UserServiceFeignClient {

	@GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers();
	
	@GetMapping(value="/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserById(@PathVariable("user_id") Long id);
	
	@PostMapping(value="/users",consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createUser(@RequestBody User user);
	
	@DeleteMapping("/users/{user_id}")
	public void deleteUserById(@PathVariable("user_id") Long id );
	
	@PutMapping(value="/users/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	public User updateUserById(@PathVariable("user_id") Long id, @RequestBody User user) ;
		
}
