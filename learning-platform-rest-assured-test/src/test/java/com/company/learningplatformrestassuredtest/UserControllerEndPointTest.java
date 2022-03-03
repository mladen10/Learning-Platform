package com.company.learningplatformrestassuredtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.common.net.HttpHeaders;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerEndPointTest
{
	enum EndPoint
	{
		CREATE_STUDENT("/createStudent"),
		CREATE_PROFESSOR("/createProfessor"),
		CREATE_ADMIN("/createAdmin"),
		CONFIRMATON_EMAIL("/confirmation"),
		CONTEXT("/lplatform/users"),
		LOGIN("/login");

		String path;

		EndPoint(String path)
		{
			this.path = path;
		}

	}

	static String rootAdminToken;
	static String adminToken;
	static String tokenViaEmail;
	static final String newPassword = "1Aa)vvvv";

	@BeforeAll
	static void setUp()
	{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@Test
	@Order(1)
	void loginRootAdmin()
	{
		Map<String, String> login = new HashMap<>();
		login.put("email", "rootadmin@email.com");
		login.put("password", "1111");

		Response response = login(login);

		rootAdminToken = response.getHeader("Token");
		assertTrue(() -> {
			Matcher m = Pattern.compile("^Bearer +[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$")
					.matcher(rootAdminToken);
			return m.matches();
		}, "JWT patern match");
	}

	private Response login(Map<String, String> body)
	{
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(body)

				.when()
				.post(EndPoint.CONTEXT.path + EndPoint.LOGIN.path)

				.then()
				.statusCode(HttpStatus.SC_OK)
				.contentType(ContentType.JSON)

				.extract()
				.response();

		return response;
	}

	@Test
	@Order(2)
	// @Disabled
	void createAdmin()
	{
		Map<String, String> createUserReqModel = new HashMap<>();
		createUserReqModel.put("firstName", "AdminName");
		createUserReqModel.put("lastName", "AdminLastName");
		createUserReqModel.put("email", "Admin@email.com");

		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(createUserReqModel)
				.header("Authorization", rootAdminToken)

				.when()
				.post(EndPoint.CONTEXT.path + EndPoint.CREATE_ADMIN.path)

				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.contentType(ContentType.JSON)

				.extract()
				.response();

		tokenViaEmail = response.jsonPath().getString("message");
		assertNotNull(tokenViaEmail);
		assertTrue(() -> {
			Matcher m = Pattern.compile("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$")
					.matcher(tokenViaEmail);
			return m.matches();
		}, "JWT patern match");
	}

	@Test
	@Order(3)
//	@Disabled
	void confirmEmail()
	{
		Map<String, String> firstLogin = new HashMap<>();
		firstLogin.put("token", tokenViaEmail);
		firstLogin.put("newPassword", newPassword);

		String path = EndPoint.CONTEXT.path + EndPoint.CONFIRMATON_EMAIL.path;
		System.out.println(path);

		given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(firstLogin)

				.when()
				.put(EndPoint.CONTEXT.path + EndPoint.CONFIRMATON_EMAIL.path)

				.then()
				.statusCode(HttpStatus.SC_OK)
				.contentType(ContentType.JSON);
	}

	@Test
	@Order(4)
	void loginAdmin() throws InterruptedException
	{
		Thread.sleep(2000);
		Map<String, String> login2 = new HashMap<>();
		login2.put("email", "Admin@email.com");
		login2.put("password", newPassword);

		Response response = login(login2);

		adminToken = response.getHeader("Token");
		assertNotNull(adminToken);
		assertTrue(() -> {
			Matcher m = Pattern.compile("^Bearer +[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$")
					.matcher(adminToken);
			return m.matches();
		}, "JWT patern match");
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//

}
