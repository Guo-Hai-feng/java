package org.example.exp03springbootdemo.service.impl;

import org.example.exp03springbootdemo.mapper.UserMapper;
import org.example.exp03springbootdemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUserById(id);
    }
}
