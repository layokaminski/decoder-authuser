package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> findAll();
}
