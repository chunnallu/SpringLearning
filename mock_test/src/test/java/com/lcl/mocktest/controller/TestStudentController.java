package com.lcl.mocktest.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class TestStudentController {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StudentController studentController;

	@Before
	public void setUp() throws Exception {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		when(request.getParameter("name")).thenReturn("123");
	}

	@Test
	public void testDoGet() {
		studentController = new StudentController();
		studentController.doGet(request, response);
	}

	@Test
	public void testDoPost() {
	}

}
