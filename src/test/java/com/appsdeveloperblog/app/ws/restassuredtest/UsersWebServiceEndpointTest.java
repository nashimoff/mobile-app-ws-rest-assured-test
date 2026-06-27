package com.appsdeveloperblog.app.ws.restassuredtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import io.restassured.response.Response;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.MethodName.class)
class UsersWebServiceEndpointTest {

	private final String CONTEXT_PATH = "/mobile-app-ws";
	private final String EMAIL_ADDRESS = "sergey.kargopolov@swiftdeveloperblog.com";
	private final String JSON = "application/json";

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8888;
	}
	
	/*
	 * testUserLogin
	 */

	@Test
	void a() {
		Map<String, String> loginDetails = new HashMap<>();
		loginDetails.put("email", EMAIL_ADDRESS);
		loginDetails.put("password", "123");
		
		Response response = given().
		contentType(JSON).
		accept(JSON).
		body(loginDetails).
		when().
		post(CONTEXT_PATH + "/users/login").
		then().
		statusCode(200).extract().response();
		
		String authorizationHeader = response.header("Authorization");
		String UserId = response.header("UserID");
		
		assertNotNull(authorizationHeader);
		assertNotNull(UserId);
		
		}

}
