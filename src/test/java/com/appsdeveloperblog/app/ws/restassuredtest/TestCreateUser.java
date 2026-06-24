package com.appsdeveloperblog.app.ws.restassuredtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

class TestCreateUser {
	
	private final String CONTEXT_PATH="/mobile-app-ws";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI="/http://localhost";
		RestAssured.port=8888;
		}

	@Test
	void testCreateUser() {
		fail("Not yet implemented");
	}

}
