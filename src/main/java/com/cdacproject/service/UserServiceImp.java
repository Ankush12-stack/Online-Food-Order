package com.cdacproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdacproject.config.JwtProvider;
import com.cdacproject.model.User;
import com.cdacproject.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		// TODO Auto-generated method stub
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = findUserByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		
		if(user == null)
		{
			throw new Exception("user not found");
		}
		return user;
	}

}
