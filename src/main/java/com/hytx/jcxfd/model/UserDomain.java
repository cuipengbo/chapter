package com.hytx.jcxfd.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDomain implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;

    private String userName;

    private String password;

    private String phone;
}