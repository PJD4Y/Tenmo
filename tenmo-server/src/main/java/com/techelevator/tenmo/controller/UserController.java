package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class UserController {

    private UserDao userDao;

    UserController(JdbcTemplate jdbcTemplate){
        this.userDao = new JdbcUserDao(jdbcTemplate);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listAllUsers(){

        return userDao.findAll();
    }

}
