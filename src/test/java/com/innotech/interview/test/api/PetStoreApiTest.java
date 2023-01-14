package com.innotech.interview.test.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

class PetStoreApiTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Integer enteredUserId = 11;
		
		User user = new User();
		user.setEmail("test@email.com");
		user.setFirstName("John");
		user.setLastName("Smith");
		user.setPhone("+79000000000");
		user.setPassword("1234");
		user.setUsername("tester001_hgt731hkf");
		user.setId(enteredUserId);
		user.setUserStatus(12);

        String uri = "https://petstore.swagger.io/v2";
        System.out.println("creating user ...");
        String userId = RestAssured.given().contentType("application/json")
            .body(user)
            .when()
            .post(uri  + "/user")
            .then()
            .log()
            .body()
            .assertThat()
            .statusCode(200)
            .extract()
            .path("message");
        Assertions.assertThat(Integer.parseInt(userId)).isEqualTo(enteredUserId);
		System.out.println("response: " + userId);
		
		String updatedFirstName = "Carl";
		user.setFirstName(updatedFirstName );
		
		System.out.println("updating user ...");
        RestAssured.given().contentType("application/json")
                .body(user)
                .when()
                .put(uri  + "/user/" + user.getUsername())
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200);

        System.out.println("extracting info about the user ...");
        User updatedUser = RestAssured.given().contentType("application/json")
                .body(user)
                .when()
                .get(uri  + "/user/" + user.getUsername())
                .getBody()
                .as(User.class);
        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo(updatedFirstName);
        System.out.println("user info: " + updatedUser.toString());
        Assertions.assertThat(user).isEqualTo(updatedUser);
		
        System.out.println("deleting user ...");
        RestAssured.given().contentType("application/json")
                .body(user)
                .when()
                .delete(uri  + "/user/" + user.getUsername())
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200);
	}

}
