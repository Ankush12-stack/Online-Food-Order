package com.cdacproject.controller;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.cdacproject.config.JwtProvider;
import com.cdacproject.model.Cart;
import com.cdacproject.model.USER_ROLE;
import com.cdacproject.model.User;
import com.cdacproject.repository.CartRepository;
import com.cdacproject.repository.UserRepository;
import com.cdacproject.request.LoginRequest;
import com.cdacproject.response.AuthResponse;
import com.cdacproject.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception{
		
		   // Return the request body (user) directly
//		AuthResponse authResponse = new AuthResponse();
//		authResponse.setMessage("login success");
//	    return new ResponseEntity<>(authResponse, HttpStatus.OK);
		//System.out.println(user);
		//return new ResponseEntity<>(, HttpStatus.CREATED);
	    User isEmailExist = userRepository.findByEmail(user.getEmail());
		
        if(isEmailExist!=null)
		{
			throw new Exception("Email is already used with another account");
		}
		
		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(createdUser);
		
		
		Cart cart = new Cart();
		cart.setCustomer(savedUser);
		cartRepository.save(cart);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register success");
		authResponse.setRole(savedUser.getRole());
		
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}


	@PostMapping("/signin")
	public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequest req){
		
		String username = req.getEmail();
		String password = req.getPassword();
		
		Authentication authentication  = authenticate(username,password);
		Collection<? extends GrantedAuthority>authorities = authentication.getAuthorities();
		String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("login success");
		authResponse.setRole(USER_ROLE.valueOf(role));
		
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
		
		
	}


	
       private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
		
		if(userDetails == null)
		{
			throw new BadCredentialsException("invalid username...");
		}
		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password...");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
}
