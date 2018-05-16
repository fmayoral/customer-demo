package dev.fmayoral.customermanagement.integration;

import dev.fmayoral.customermanagement.extension.PropertiesInjector;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Properties;

import static io.restassured.RestAssured.given;

@ExtendWith(PropertiesInjector.class)
public class RestResourceIT {
	private static final String RESOURCE_PATH = "customers";
	private static RequestSpecification request = null;

	@BeforeAll
	static void setup(final Properties testProperties) {
		final String schema = testProperties.getProperty("server.schema");
		final String host = testProperties.getProperty("server.host");
		final String port = testProperties.getProperty("server.port");
		final String context = testProperties.getProperty("server.context");
		request = given().baseUri(String.format("%s://%s:%s%s", schema, host, port, context));
	}

	@Test
	@DisplayName("Customer note can be retrieved")
	void getResources() {
		request.when()
				.get(RESOURCE_PATH)
				.then()
				.assertThat().statusCode(200);
	}
}
