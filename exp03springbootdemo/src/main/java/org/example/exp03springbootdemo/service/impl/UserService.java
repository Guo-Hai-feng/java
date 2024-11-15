package org.example.exp03springbootdemo.service.impl;

import org.example.exp03springbootdemo.pojo.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);
    List<User> getUsers();
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
}
