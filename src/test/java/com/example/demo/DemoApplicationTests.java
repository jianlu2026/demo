package com.example.demo;

import com.example.demo.controller.GreetingController;
import com.example.demo.dto.CreateGreetingRequest;
import com.example.demo.dto.GreetingResponse;
import com.example.demo.exception.GreetingNotFoundException;
import com.example.demo.model.Greeting;
import com.example.demo.repository.GreetingRepository;
import com.example.demo.service.GreetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@SpringBootTest
	@ActiveProfiles("test")
	@Transactional
	static class GreetingServiceTests {

		@Autowired
		private GreetingService greetingService;

		@Autowired
		private GreetingRepository greetingRepository;

		@BeforeEach
		void setUp() {
			greetingRepository.deleteAll();
		}

		@Test
		@DisplayName("Should create and retrieve greetings")
		void shouldCreateAndRetrieveGreetings() {
			// Create greeting
			GreetingResponse created = greetingService.createGreeting("Hello, World!");
			assertThat(created.getMessage()).isEqualTo("Hello, World!");

			// Retrieve by ID
			GreetingResponse retrieved = greetingService.getGreetingById(created.getId());
			assertThat(retrieved.getMessage()).isEqualTo("Hello, World!");

			// Get all greetings
			assertThat(greetingService.getAllGreetings()).hasSize(1);
		}

		@Test
		@DisplayName("Should handle not found scenarios")
		void shouldHandleNotFound() {
			assertThatThrownBy(() -> greetingService.getGreetingById(999L))
					.isInstanceOf(GreetingNotFoundException.class);

			assertThatThrownBy(() -> greetingService.deleteGreeting(999L))
					.isInstanceOf(GreetingNotFoundException.class);
		}
	}

	@WebMvcTest(GreetingController.class)
	@ActiveProfiles("test")
	static class GreetingControllerTests {

		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private GreetingService greetingService;

		@Autowired
		private ObjectMapper objectMapper;

		@Test
		@DisplayName("Should handle POST requests")
		void shouldCreateGreeting() throws Exception {
			CreateGreetingRequest request = new CreateGreetingRequest();
			request.setMessage("Hello from test");
			GreetingResponse response = new GreetingResponse(1L, "Hello from test");

			when(greetingService.createGreeting("Hello from test")).thenReturn(response);

			mockMvc.perform(post("/api/v1/greetings")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.message").value("Hello from test"));
		}

		@Test
		@DisplayName("Should handle GET requests and errors")
		void shouldGetGreetingAndHandleErrors() throws Exception {
			// Successful GET
			when(greetingService.getGreetingById(1L))
					.thenReturn(new GreetingResponse(1L, "Test greeting"));

			mockMvc.perform(get("/api/v1/greetings/1"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.message").value("Test greeting"));

			// 404 error
			when(greetingService.getGreetingById(999L))
					.thenThrow(new GreetingNotFoundException(999L));

			mockMvc.perform(get("/api/v1/greetings/999"))
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("Should validate input")
		void shouldValidateInput() throws Exception {
			CreateGreetingRequest request = new CreateGreetingRequest();
			request.setMessage(""); // Invalid - blank message

			mockMvc.perform(post("/api/v1/greetings")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
					.andExpect(status().isBadRequest());

			verify(greetingService, never()).createGreeting(any());
		}
	}
}