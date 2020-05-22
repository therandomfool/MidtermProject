package com.skilldistillery.brushr.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.skilldistillery.brushr.entities.User;

@Controller
public class UserController {

	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BeerDAO beerDAO;
	
	
	@RequestMapping(path="createAccount.do", method= RequestMethod.GET)
	public String createUserAccount() {
		return "profile";//does profile.jsp have an account form??
	}
	@RequestMapping(path="createAccount.do", method= RequestMethod.POST)
	public String createUser(HttpSession session, Model model) {
		if(userDAO.getUserByUsername()== null) {
			
			User p = userDAO.addUser();
			
			session.setAttribute("user", p.getUser());//Profile Object needs a User to get Object from
			session.setAttribute("profile", p);
			
			return "profile";
		} else {
			return "profile";//Requires clarification
		}
		
		return "profile";//does profile.jsp have an account form??
	}
	
	
	@RequestMapping(path="login.do", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	
	@RequestMapping(path="loginUser.do", method = RequestMethod.POST)
	public String loginUser(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpSession session, Model model) {
		User u = userDAO.getByUserName(username);
		if(u != null && u.getPassword().equals(password)) {
			
			List<User> profile = userDAO.getProfileByUsername(u.getUsername());
			if(u.isAdmin()) {//if user is Admin status
				session.setAttribute("admin", u);
			}else {
				session.setAttribute("user", u);
			}
			session.setAttribute("profile", profile.get(0));
			return "profile";
			
		} else {
			session.setAttribute("failed", "Password or Username is invalid");
			return "login";
		}
		
		return "login";
	}
	
	
	@RequestMapping(path="updateProfile.do", method= RequestMethod.GET)
	public String updateProfile() {
		
		return "profile";
	}
	
	@RequestMapping(path="updateProfile.do", method= RequestMethod.POST)
	public String updateUserProfile(@RequestParam(name="firstName") String firstName,
			@RequestParam(name="lastName") String lastName,
			@RequestParam(name="email") String email,
			@RequestParam(name="username") String username,
			HttpSession session, Model model) {
			User u = (User) session.getAttribute("user");
			Profile p = (Profile) session.getAttribute("profile");//if we dont have a Profile entity, FIXME
			
			String updateMessage = null;
			
			List<Object> updatedProfile = userDAO.updateProfile(u.getId(), p.getId(), firstName, lastName, email, username  );//Profile entity required FIX ME
			if(!updateMessage.isEmpty()){
				session.setAttribute("user", updatedProfile.get(0));
				session.setAttribute("profile", updatedProfile.get(1) );
				
				updateMessage = "Profile Successfully Updated";
			} else {
				updateMessage = "Profile Not Updated";
			}
			model.addObject("message",updateMessage );
			
		return "profile";
	}
	
	
	
	@RequestMapping(path="logout.do", method= RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("admin");
		
		return "index";
	}
	
	//Team how will the user see the comments?
	
	
	
}
