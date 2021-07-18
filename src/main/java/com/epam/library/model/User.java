package com.epam.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {

	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String role;
	private String ssn;


}
