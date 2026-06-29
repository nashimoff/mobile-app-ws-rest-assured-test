package com.appsdeveloperblog.app.ws.restassuredtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
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
	private static String authorizationHeader;
	private static String userId;

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8888;
	}
	
	/*
	 * testUserLogin
	 */

	@Test
	final void a() {
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
		
		authorizationHeader = response.header("Authorization");
		userId = response.header("UserID");
		
		assertNotNull(authorizationHeader);
		assertNotNull(userId);
		
		}
	
	/*
	 * testGetUserDetails
	 */

	@Test
	final void b() {
		
		Response response = given()
				.pathParam("id", userId)
				.header("Authorization",authorizationHeader)
		.accept(JSON)
		.when().get(CONTEXT_PATH + "/users/{id}")
		.then()
		.statusCode(200)
		.contentType(JSON)
		.extract()
		.response();
		
		System.out.println(response.body().asString()); 
		System.out.println("JSON RESPONSE: " + response.body().asString());
		String userPublicId = response.jsonPath().getString("userId");
		String userEmail = response.jsonPath().getString("email");
		String firstName = response.jsonPath().getString("firstName");
		String lastName = response.jsonPath().getString("lastName");
		List<Map<String, String>> addresses = response.jsonPath().getList("addresses");
		String addressId = addresses.get(0).get("addressId");
		
		assertNotNull(userPublicId);
		assertNotNull(userEmail);
		assertNotNull(firstName);
		assertNotNull(lastName);
		assertEquals(EMAIL_ADDRESS, userEmail);
		
		assertTrue(addresses.size() == 2);
		assertTrue(addressId.length() == 30);
		
	}
	
	/*
	 * Test Update User Details
	 */
	@Test
	final void c()
	{
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("firstName", "Serge");
		userDetails.put("lastName", "Kargopolov");
		
		Response response = given()
		.contentType(JSON)
		.accept(JSON)
		.header("Authorization",authorizationHeader)
		.pathParam("id", userId)
		.body(userDetails)
		.when()
		.put(CONTEXT_PATH + "/users/{id}")
		.then()
		.statusCode(200)
		.contentType(JSON)
		.extract()
		.response();
	}

}
