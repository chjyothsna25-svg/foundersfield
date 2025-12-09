package com.project.foundersfeild.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.foundersfeild.model.user;
import com.project.foundersfeild.repository.userrepo;

@Service
public class userserv {
	
	  @Autowired
	    private PasswordEncoder passwordEncoder; 
	
	@Autowired
	userrepo repo;
	
	public List<user> getusers()
	{
		return repo.findAll();
	}
	
	public user getuserbyid(Long id)
	{
		return repo.getById(id);
	}
	
	public void adduser(user user)
	{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		repo.save(user);
	}
	
	 public List<user> searchByName(String name) {
	        return repo.findByNameContainingIgnoreCase(name);
	    }
}
