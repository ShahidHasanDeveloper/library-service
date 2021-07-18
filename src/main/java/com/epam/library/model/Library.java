package com.epam.library.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@DynamoDBTable(tableName = "library")
public class Library {

	@DynamoDBHashKey(attributeName = "book_id")
	private Long bookid;
	@DynamoDBAttribute(attributeName = "user_id")
	@DynamoDBIndexHashKey(attributeName = "user_id", globalSecondaryIndexName = "user_id-index")
	private Long userid;

	
}
