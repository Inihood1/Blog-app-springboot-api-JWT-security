package com.blog.api;

import com.blog.api.controllers.UserController;
import com.blog.api.entities.User;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@WebMvcTest(UserController.class)
class BlogAppApiApplicationTests {

//	private static final String END_POINT = "api/users";
//	@MockBean
//	private UserService userService;
//	@Autowired
//	private MockMvc mockMvc;
//	@Autowired
//	ObjectMapper objectMapper;

	@Test
	public void getShouldReturn403B() throws Exception{
		// we use mockito because it loads the service layer
		// mockMvc only loads the controller layer
//		long userId = 123L;
//		String requestUrl = END_POINT;
//		Mockito.when(userService.getAllUsers()).thenThrow(ResourceNotFoundException.class);
//		mockMvc.perform(get(requestUrl).contentType("application/json"))
//				.andExpect(status().isBadRequest())
//				.andDo(print());
	}

}
