package com.ipp.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homeGET() throws Exception {
		logger.info("register get..........");
		
		return "home";
				
	}
	
	@RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)

	public void favicon( HttpServletRequest request, HttpServletResponse reponse ) {

	try {

	  reponse.sendRedirect("/resources/favicon.ico");

	} catch (IOException e) {

	  e.printStackTrace();

	}

	}


}
