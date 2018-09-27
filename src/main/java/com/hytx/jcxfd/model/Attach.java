package com.hytx.jcxfd.model;

import lombok.Data;

@Data
public class Attach {

	private Integer id;
	
	private String fname;
	
	private String ftype;
	
	private String fkey;
	
	private Integer authorId;
	
	private Integer created;
}
