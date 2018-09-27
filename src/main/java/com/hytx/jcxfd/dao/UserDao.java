package com.hytx.jcxfd.dao;

import java.util.List;

import com.hytx.jcxfd.model.UserDomain;

public interface UserDao {

    int insert(UserDomain record);

    List<UserDomain> selectUsers();
}