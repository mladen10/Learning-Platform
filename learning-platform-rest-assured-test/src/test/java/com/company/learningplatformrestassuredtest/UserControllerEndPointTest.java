package com.company.learningplatformrestassuredtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerEndPointTest
{
	enum EndPoint
	{
		CREATE_STUDENT("/users/createStudent"),
		CREATE_PROFESSOR("/users/createProfessor"),
		CREATE_ADMIN("/users/createAdmin"),
		CONFIRMATON_EMAIL("/confirmation"),
		CONTEXT("/lplatform");

		String path;

		EndPoint(String path)
		{
			this.path = path;
		}

	}

	private static final int PORT = 8080;

	@BeforeAll
	static void setUp()
	{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = PORT;
	}

	@Test
	@Order(1)
	void createStudent()
	{
		Map<String, Object> createUserReqModel = new HashMap<>();
		createUserReqModel.put("firstName", "StudentName");
		createUserReqModel.put("lastName", "StudentLastName");
		createUserReqModel.put("email", "studensts@email.com");

		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(createUserReqModel)

				.when()
				.post(EndPoint.CONTEXT.path + EndPoint.CREATE_STUDENT.path)

				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.contentType(ContentType.JSON)

				.extract()
				.response();
		// =============================================================================================
		// TMP CODE ----> get token from email
		String token = response.jsonPath().getString("message");
		assertFalse(token.isEmpty());
		// =============================================================================================

		Map<String, Object> changePasswordReqModel = new HashMap<>();

	}

	@Test
	@Order(2)
	void createProfessor()
	{
		Map<String, Object> createUserReqModel = new HashMap<>();
		createUserReqModel.put("firstName", "ProfessorName");
		createUserReqModel.put("lastName", "ProfessorLastName");
		createUserReqModel.put("email", "Professor@email.com");

		given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(createUserReqModel)

				.when()
				.post(EndPoint.CONTEXT.path + EndPoint.CREATE_PROFESSOR.path)

				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.contentType(ContentType.JSON);
	}

	@Test
	@Order(3)
	void createAdmin()
	{
		Map<String, Object> createUserReqModel = new HashMap<>();
		createUserReqModel.put("firstName", "AdminName");
		createUserReqModel.put("lastName", "AdminLastName");
		createUserReqModel.put("email", "Admin@email.com");

		given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(createUserReqModel)

				.when()
				.post(EndPoint.CONTEXT.path + EndPoint.CREATE_ADMIN.path)

				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.contentType(ContentType.JSON);
	}
}
