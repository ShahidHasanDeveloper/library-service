package com.epam.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Book {

	private Long id;
	private String bookname;
	private String authorname;
	private String category;
	private String description;
	private int publishYear;

}
