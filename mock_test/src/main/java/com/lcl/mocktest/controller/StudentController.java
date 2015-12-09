package com.lcl.mocktest.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentController extends HttpServlet{
	
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name");
		System.out.println(name);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response){
		
	}

}
